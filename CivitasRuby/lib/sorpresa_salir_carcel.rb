# encoding:utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  class SorpresaSalirCarcel < Sorpresa

    def initialize(mazo)
      super("SorpresaSalirCarcel")
      @mazo = mazo
    end

    def aplicar_a_jugador(actual, todos)
      if jugador_correcto(actual, todos)
        informe(actual, todos)
        contador = 0
        for i in todos
          if i.tiene_salvo_conducto
            contador = contador + 1
          end
        end
        if contador == 0
          todos[actual].obtener_salvo_conducto(self)
          self.salir_del_mazo
        end
      end
    end

        
    def salir_del_mazo
        @mazo.inhabilitar_carta_especial(self)
    end
    
    def usada 
        @mazo.habilitar_carta_especial(self)
    end
    
    def to_s
      "\nNombre: #{@texto}"
    end
    
       
    public_class_method  :new
    
  end
end
