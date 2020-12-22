# encoding:utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  class SorpresaConvertirJugador < Sorpresa
    
    def initialize(texto, fianza)
      super(texto)
      @fianza = fianza
    end
    
    def aplicar_a_jugador(actual, todos)
      if jugador_correcto(actual,todos)
        informe(actual,todos)
        todos[actual] = JugadorEspeculador.new_Especulador(todos[actual], @fianza)
      end
    end
    
       
    public_class_method  :new
  end
end
