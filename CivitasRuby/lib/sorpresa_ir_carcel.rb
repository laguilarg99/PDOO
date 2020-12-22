# encoding:utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  class SorpresaIrCarcel < Sorpresa
    
    def initialize(tablero)
      super("SorpresaIrCarcel")
      @tablero = tablero
    end
        
    def aplicar_a_jugador(actual, todos)
      if jugador_correcto(actual,todos)
        informe(actual,todos)
        todos[actual].encarcelar(@tablero.numCasillaCarcel)
      end
    end
    
    def to_s
      "\nNombre: #{@texto}"
    end

       
    public_class_method  :new
    
  end
end
