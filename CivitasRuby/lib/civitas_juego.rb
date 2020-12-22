# encoding:utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative "jugador.rb"
require_relative "gestor_estados.rb"
require_relative "mazo_sorpresas.rb"
require_relative "tablero.rb"
require_relative "estados_juego.rb"
require_relative "operaciones_juego.rb"
require_relative "sorpresa.rb"

module Civitas
 
  class CivitasJuego
    
    def initialize(nombres)
      @jugadores = []
      for i in nombres
        @jugadores << Jugador.new_JUGADOR(i)
      end
      
      @gestorEstados = Gestor_estados.new
      @estado = @gestorEstados.estado_inicial
      
      @dado = Dado.instance
      
      @indiceJugadorActual = @dado.quienEmpieza(nombres.length)
      
      @mazo = MazoSorpresas.new_mazo_sorpresas2
      @tablero = Tablero.new(5)
      
      inicializa_tablero(@mazo)
      inicializa_mazo_sorpresas(@tablero)
    end
     
    private
    def inicializa_tablero(mazo)
      @tablero.añade_casilla(CasillaCalle.new(TituloPropiedad.new( "Calle de la Palmita", 10, 500, 250, 50, 150)))
      @tablero.añade_casilla(CasillaCalle.new(TituloPropiedad.new( "Calle de la Espartera", 10, 500, 250, 50, 150)))
      @tablero.añade_casilla(CasillaSorpresa.new(mazo, "Sorpresa1"))
      @tablero.añade_casilla(CasillaCalle.new(TituloPropiedad.new( "Calle Valle Gran Rey", 11, 600, 300, 60, 300)))
      @tablero.añade_casilla(CasillaCalle.new(TituloPropiedad.new( "Calle Santo Cristo", 11, 600, 300, 60, 400)))
      @tablero.añade_casilla(CasillaImpuesto.new(7500, "Impuesto"))
      @tablero.añade_casilla(CasillaCalle.new(TituloPropiedad.new( "Calle Recogidas", 12, 600, 300, 60, 400)))
      @tablero.añade_casilla(CasillaCalle.new(TituloPropiedad.new( "Calle Gracia", 12, 600, 350, 65, 500)))
      @tablero.añade_casilla(Casilla.new("PARKING"))
      @tablero.añade_casilla(CasillaCalle.new(TituloPropiedad.new( "Calle Pedro Antonio de Alarcón", 13, 600, 350, 65, 500)))
      @tablero.añade_casilla(CasillaSorpresa.new(mazo, "Sorpresa2"))
      @tablero.añade_casilla(CasillaCalle.new(TituloPropiedad.new( "Calle Gonzalo Gallas", 13, 700, 400, 70, 600)))
      @tablero.añade_casilla(CasillaCalle.new(TituloPropiedad.new( "Camino de Ronda", 14, 800, 500, 80, 700)))
      @tablero.añade_casilla(CasillaJuez.new(5, "Juez"))
      @tablero.añade_casilla(CasillaCalle.new(TituloPropiedad.new( "Gran Via de Colón", 14, 800, 500, 80, 700)))
      @tablero.añade_casilla(CasillaSorpresa.new(mazo, "Sorpresa3"))
      @tablero.añade_casilla(CasillaCalle.new(TituloPropiedad.new( "Calle Yanguas", 15, 1000, 750, 100, 1000)))
      @tablero.añade_casilla(CasillaCalle.new(TituloPropiedad.new( "Calle San Juan de los Reyes", 15, 1000, 750, 100, 1000)))     
    end
    
    
    def inicializa_mazo_sorpresas(tablero)
#      @mazo.al_mazo(SorpresaIrCarcel.new(tablero))
#      @mazo.al_mazo(SorpresaIrCasilla.new(tablero, 0))
#      @mazo.al_mazo(SorpresaSalirCarcel.new(@mazo))
#      @mazo.al_mazo(SorpresaPagarCobrar.new(-1000))
#      @mazo.al_mazo(SorpresaPagarCobrar.new(100))
#      @mazo.al_mazo(SorpresaPorJugador.new(200))
#      @mazo.al_mazo(SorpresaPagarCobrar.new(1700))
#      @mazo.al_mazo(SorpresaIrCasilla.new(tablero, 8))
#      @mazo.al_mazo(SorpresaPagarCobrar.new(-500))
#      @mazo.al_mazo(SorpresaPorCasaHotel.new( -100))
      @mazo.al_mazo(SorpresaConvertirJugador.new("JugadorEspeculador", 100))
    end
    
   
    def contabilizar_pasos_por_salida(jugadorActual)
      if @tablero.por_salida > 0 and @jugadores[@indiceJugadorActual].numCasillaActual == 0
        jugadorActual.pasa_por_salida
      end
    end
    
    
    def pasar_turno
      @indiceJugadorActual = (@indiceJugadorActual + 1)%@jugadores.length
    end
    
    public
    def siguiente_paso_completo(operacion)
      @estado = @gestorEstados.siguiente_estado(@jugadores[@indiceJugadorActual], @estado, operacion)
    end
    
    def vender(ip)
      return @jugadores[@indiceJugadorActual].vender(ip)
    end
    
    def salir_carcel_pagando
      return @jugadores[@indiceJugadorActual].salir_carcel_pagando
    end
    
    def salir_carcel_tirando
      return @jugadores[@indiceJugadorActual].salir_carcel_tirando
    end
    
    def final_del_juego
      for i in @jugadores
        if i.en_bancarrota
          result = true
          break
        else
          result = false
        end
      end
      
      return result
    end
    
    def casilla_actual
      return @tablero.casilla(@jugadores[@indiceJugadorActual].numCasillaActual)
    end
    
    def jugador_actual
      return @jugadores[@indiceJugadorActual]
    end
    
    def ranking
      @jugadores.sort 
      return @jugadores
    end
    
    def info_jugador_texto
      @jugadores[@indiceJugadorActual].to_s
    end
    
    private
    
    def avanza_jugador
      jugador_actual = @jugadores[@indiceJugadorActual]
      posicion_actual = jugador_actual.numCasillaActual
      dado = Dado.instance
      tirada = dado.tirar
      posicion_nueva = @tablero.nueva_posicion(posicion_actual, tirada)
      casilla = @tablero.casilla(posicion_nueva)
      contabilizar_pasos_por_salida(jugador_actual)
      jugador_actual.mover_a_casilla(posicion_nueva)
      casilla.recibe_jugador(@indiceJugadorActual, @jugadores)
      contabilizar_pasos_por_salida(jugador_actual)
    end
    
    public
    
    def cancelar_hipoteca(ip)
      return @jugadores[@indiceJugadorActual].cancelar_hipoteca(ip)
    end
    
    def construir_casa(ip)
      return @jugadores[@indiceJugadorActual].construir_casa(ip)
    end
    
    def construir_hotel(ip)
      return @jugadores[@indiceJugadorActual].construir_hotel(ip)
    end
    
    def hipotecar(ip)
      return @jugadores[@indiceJugadorActual].hipotecar(ip)
    end
    
    def siguiente_paso()
      jugador_actual = @jugadores[@indiceJugadorActual]
      operacion = @gestorEstados.operaciones_permitidas(jugador_actual, @estado)
            
      case operacion
      when Civitas::Operaciones_juego::PASAR_TURNO
        pasar_turno
        siguiente_paso_completo(operacion)
      when Civitas::Operaciones_juego::AVANZAR
        avanza_jugador
        siguiente_paso_completo(operacion)
      end
      
      return operacion
    end
    
    def comprar()
      res = false
      jugador_actual = @jugadores[@indiceJugadorActual]
      casilla = @tablero.casilla(jugador_actual.numCasillaActual)
      titulo = casilla.tituloPropiedad
      res = jugador_actual.comprar(titulo)
      return res
    end
    
  
  end
end