# encoding:utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  class SorpresaIrCasilla < Sorpresa
    
    def initialize(tablero, valor)
      super("SorpresaIrCasilla")
      @tablero = tablero
      @valor = valor
    end
    
        
    def aplicar_a_jugador(actual, todos)
      if jugador_correcto(actual, todos)
        informe(actual,todos)
        dado = Dado.instance
        tirada = @tablero.calcular_tirada(todos[actual].numCasillaActual, dado.tirar)
        posicion = @tablero.nueva_posicion(todos[actual].numCasillaActual, tirada)
        todos[actual].mover_a_casilla(posicion)
        @tablero.casilla(posicion).recibe_jugador(actual, todos)
      end
    end
    
    
    def to_s
      "\nNombre: #{@texto}"
    end
    
    public_class_method  :new
  end
end
