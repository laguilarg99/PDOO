# encoding:utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.


module Civitas
  class SorpresaPagarCobrar < Sorpresa
    
    def initialize(valor)
       super("SorpresaPagarCobrar")
       @valor = valor
    end
    
    
    def aplicar_a_jugador(actual, todos)
      if jugador_correcto(actual,todos)
        informe(actual,todos)
        todos[actual].modificar_saldo(@valor)
      end
    end
    
    def to_s
      "\nNombre: #{@texto}"
    end
    
       
    public_class_method  :new
    
  end
end
