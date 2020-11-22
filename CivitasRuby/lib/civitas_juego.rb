# encoding:utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative "jugador.rb"
require_relative "gestor_estados.rb"
require_relative "mazo_sorpresas.rb"
require_relative "tablero.rb"
require_relative "tipo_sorpresa.rb"

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
      
      inicializa_tablero(@mazo)
      inicializa_mazo_sorpresas(@tablero)
    end
    
    def inicializa_tablero(mazo)
      @tablero = Tablero.new(5)
      @tablero.añade_casilla(Casilla.new_CALLE(TituloPropiedad.new( "Calle de la Palmita", 10, 500, 250, 50, 150)))
      @tablero.añade_casilla(Casilla.new_CALLE(TituloPropiedad.new( "Calle de la Espartera", 10, 500, 250, 50, 150)))
      @tablero.añade_casilla(Casilla.new_SORPRESA(mazo, "Sorpresa1"))
      @tablero.añade_casilla(Casilla.new_CALLE(TituloPropiedad.new( "Calle Valle Gran Rey", 11, 600, 300, 60, 300)))
      @tablero.añade_casilla(Casilla.new_CALLE(TituloPropiedad.new( "Calle Santo Cristo", 11, 600, 300, 60, 400)))
      @tablero.añade_casilla(Casilla.new_IMPUESTO(7500, "Impuesto"))
      @tablero.añade_casilla(Casilla.new_CALLE(TituloPropiedad.new( "Calle Recogidas", 12, 600, 300, 60, 400)))
      @tablero.añade_casilla(Casilla.new_CALLE(TituloPropiedad.new( "Calle Gracia", 12, 600, 350, 65, 500)))
      @tablero.añade_casilla(Casilla.new_DESCANSO("PARKING"))
      @tablero.añade_casilla(Casilla.new_CALLE(TituloPropiedad.new( "Calle Pedro Antonio de Alarcón", 13, 600, 350, 65, 500)))
      @tablero.añade_casilla(Casilla.new_SORPRESA(mazo, "Sorpresa2"))
      @tablero.añade_casilla(Casilla.new_CALLE(TituloPropiedad.new( "Calle Gonzalo Gallas", 13, 700, 400, 70, 600)))
      @tablero.añade_casilla(Casilla.new_CALLE(TituloPropiedad.new( "Camino de Ronda", 14, 800, 500, 80, 700)))
      @tablero.añade_casilla(Casilla.new_JUEZ(5, "Juez"))
      @tablero.añade_casilla(Casilla.new_CALLE(TituloPropiedad.new( "Gran Via de Colón", 14, 800, 500, 80, 700)))
      @tablero.añade_casilla(Casilla.new_SORPRESA(mazo, "Sorpresa3"))
      @tablero.añade_casilla(Casilla.new_CALLE(TituloPropiedad.new( "Calle Yanguas", 15, 1000, 750, 100, 1000)))
      @tablero.añade_casilla(Casilla.new_CALLE(TituloPropiedad.new( "Calle San Juan de los Reyes", 15, 1000, 750, 100, 1000)))     
    end
    
    
    def inicializa_mazo_sorpresas(tablero)
      @mazo.al_mazo(Sorpresa.new_IRCARCEL(Civitas::Tipo_sorpresa::IRCARCEL, tablero))
      @mazo.al_mazo(Sorpresa.new_IRCASILLA(Civitas::Tipo_sorpresa::IRCASILLA, tablero, 0, "Vuelve a la casilla salida sin cobrar"))
      @mazo.al_mazo(Sorpresa.new_SALIRCARCEL(Civitas::Tipo_sorpresa::SALIRCARCEL, @mazo))
      @mazo.al_mazo(Sorpresa.new_SORPRESA(Civitas::Tipo_sorpresa::PAGARCOBRAR, -1000, "Toca pagar facturas"))
      @mazo.al_mazo(Sorpresa.new_SORPRESA(Civitas::Tipo_sorpresa::PORCASAHOTEL, 100, "Van bien los negocios"))
      @mazo.al_mazo(Sorpresa.new_SORPRESA(Civitas::Tipo_sorpresa::PORJUGADOR, 200, "Tus amigos te devuelven tu dinero"))
      @mazo.al_mazo(Sorpresa.new_SORPRESA(Civitas::Tipo_sorpresa::PAGARCOBRAR, 1700, "Cobras la beca de la universidad"))
      @mazo.al_mazo(Sorpresa.new_IRCASILLA(Civitas::Tipo_sorpresa::IRCASILLA, tablero, 8, "Nos vamos de viaje, ve a la casilla sin pasar por la salida "))
      @mazo.al_mazo(Sorpresa.new_SORPRESA(Civitas::Tipo_sorpresa::PAGARCOBRAR, -500, "Te ha pillado el radar duplicando el limite de velocidad"))
      @mazo.al_mazo(Sorpresa.new_SORPRESA(Civitas::Tipo_sorpresa::PORCASAHOTEL, -100, "Hay que pagar el mantenimiento de los edificios"))
    end
    
    def contabilizar_pasos_por_salida(jugadorActual)
      if @tablero.por_salida > 0 and @jugadores[@indiceJugadorActual].numCasillaActual == 0
        jugadorActual.pasa_por_salida
      end
    end
    
  
    def pasar_turno
      @indiceJugadorActual = (@indiceJugadorActual + 1)%@jugadores.lenght
    end
    
    def siguiente_paso_completo(operacion)
      @gestorEstados.siguiente_estado(@jugadores[@indiceJugadorActual], @estado, operacion)
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
      for i in jugadores
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
    
    def avanza_jugador
      jugador_actual = @jugadores[@indiceJugadorActual]
      posicion_actual = jugador_actual.numCasillaActual
      dado = Dado.instance
      tirada = dado.tirar
      posicion_nueva = @tablero.nueva_posicion(posicion_actual, tirada)
      casilla = @tablero.casilla(posicion_nueva)
      contabilizar_pasos_por_salida(jugador_actual)
      jugador_actual.mover_a_casilla(posicion_nueva)
      casilla.recibe_jugador(posicion_actual, @jugadores)
      contabilizar_pasos_por_salida(jugador_actual)
    end
    
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
        siguiente_paso_completado(operacion)
      when Civitas::Operaciones_juego::AVANZAR
        avanza_jugador
        siguiente_paso_completado(operacion)
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
    
    #private_class_method :avanza_jugador, :contabilizar_pasos_por_salida, :pasar_turno, :inicializa_mazo_sorpresas, :inicializa_tablero
  end
end