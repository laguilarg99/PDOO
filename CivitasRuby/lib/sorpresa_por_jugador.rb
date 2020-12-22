# encoding:utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  class SorpresaPorJugador < Sorpresa
    
    def initialize(valor)
      super("SorpresaPorJugador")
      @valor = valor
    end
    
       
    def aplicar_a_jugador(actual, todos)
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
    
         
    def to_s
      "\nNombre: #{@texto}"
    end
    
       
    public_class_method  :new
    
  end
end
