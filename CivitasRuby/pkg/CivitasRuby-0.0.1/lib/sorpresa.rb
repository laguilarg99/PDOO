# encoding:utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.



module Civitas
  
  class Sorpresa
    
    
    def initialize()
      @valor = -1
      @mazo = nil
      @tablero = nil
    end
    
    def self.new_IRCARCEL(tipo, tablero)
      new
      @tipo = tipo
      @tablero = tablero
    end
    
    def self.new_IRCASILLA(tipo, tablero, valor, texto)
      new
      @tipo = tipo
      @tablero = tablero
      @valor = valor
      @texto = texto
    end
    
    def self.new_SALIRCARCEL(tipo, mazo)
      new
      @tipo = tipo
      @mazo = mazo
    end
    
    def self.new_SORPRESA(tipo, valor, texto)
      new
      @tipo = tipo
      @valor = valor
      @texto = texto
    end
    
    def aplicar_a_jugador(actual, todos)
      case @tipo
      when Civitas::Tipo_sorpresa::IRCARCEL
        self.aplicar_a_jugador_ir_carcel(actual,todos)
      when Civitas::Tipo_sorpresa::IRCASILLA
        self.aplicar_a_jugador_ir_a_casilla(actual,todos)
      when Civitas::Tipo_sorpresa::PAGARCOBRAR
        self.aplicar_a_jugador_pagar_cobrar(actual,todos)
      when Civitas::Tipo_sorpresa::PORCASAHOTEL
        self.aplicar_a_jugador_por_casa_hotel(actual,todos)
      when Civitas::Tipo_sorpresa::PORJUGADOR
        self.aplicar_a_jugador_por_jugador(actual,todos)
      when Civitas::Tipo_sorpresa::SALIRCARCEL
        self.aplicar_a_jugador_salir_carcel(actual,todos)
      end
    end
    
    def aplicar_a_jugador_ir_a_casilla(actual, todos)
      if jugador_correcto(actual, todos)
        informe(actual,todos)
        dado = Dado.instance
        tirada = @tablero.calcular_tirada(todos[actual].numCasillaActual, dado.tirar)
        posicion = @tablero.nuevaPosicion(todos[actual].numCasillaActual, tirada)
        todos[actual].mover_a_casilla(posicion)
        @tablero.casilla(posicion).recibe_jugador(actual, todos)
      end
    end
    
    def aplicar_a_jugador_ir_carcel(actual, todos)
      if jugador_correcto(actual,todos)
        informe(actual,todos)
        todos[actual].encarcelar(@tablero.numCasillaCarcel)
      end
    end
    
    def aplicar_a_jugador_pagar_cobrar(actual, todos)
      if jugador_correcto(actual,todos)
        informe(actual,todos)
        todos[actual].modificar_saldo(@valor)
      end
    end
    
    def aplicar_a_jugador_por_casa_hotel(actual, todos)
      if jugador_correcto(actual,todos)
        informe(actual,todos)
        todos[actual].modificar_saldo(todos[actual].cantidad_casa_hoteles*@valor)
      end
    end
    
    def aplicar_a_jugador_por_jugador(actual, todos)
      if jugador_correcto(actual,todos)
        informe(actual,todos)
        pagar = Sorpresa.new_SORPRESA(Civitas::Tipo_sorpresa::PAGARCOBRAR, @valor * -1, "Pagan todos los jugadores")
        for i in todos
          if i.nombre != todos[actual].nombre
            pagar.aplicar_a_jugador(i,todos)
          end
        end
        cobrar = Sorpresa.new_SORPRESA(Civitas::Tipo_sorpresa::PAGARCOBRAR,@valor * ( todos.lenght - 1), "Cobra de todos los jugadores")
        cobrar.aplicar_a_jugador(actual, todos)
      end
    end
    
    def aplicar_a_jugador_salir_carcel(actual, todos)
      if jugador_correcto(actual, todos)
        informe(actual, todos)
        contador = 0
        for i in todos
          if i.tiene_salvo_conducto(self)
            contador = contador + 1
          end
        end
        if contador == 0
          todos[actual].obtener_salvo_conducto(self)
          self.salir_del_mazo
        end
      end
    end
    
    def informe(actual, todos)
      if jugador_correcto(actual,todos)
        diary = Diario.instance
        diary.ocurre_evento("Aplicando sorpresa #{@tipo.to_s} a: #{@nombre}")
      end
    end
    
    def jugador_correcto(actual, todos)
      if actual >= 0 && actual < todos.length
        return true
      else
        return false
      end
    end
    
    def salir_del_mazo
      if tipo == Civitas::Tipo_sorpresa::SALIRCARCEL
        mazo.inhabilitar_carta_especial(self)
      end
    end
    
    def usada 
      if tipo == Civitas::Tipo_sorpresa::SALIRCARCEL
        mazo.habilitar_carta_especial(self)
      end
    end
    
    def to_s
      "Tipo: #{@tipo} \nNombre: #{@texto}"
    end
    
    
    private_class_method :new
    
  end

end
