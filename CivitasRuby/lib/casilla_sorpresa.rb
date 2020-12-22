# encoding:utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  class CasillaSorpresa < Casilla
    
    def initialize(mazo, nombre)
        super(nombre)
        @mazo = mazo
    end
    
     def recibe_jugador(iactual, todos)
      if jugador_correcto(iactual,todos)
        informe(iactual,todos)
        @sorpresa = @mazo.siguiente();
        @sorpresa.aplicar_a_jugador(iactual, todos)
      end
    end
    
    def to_s
      "Nombre: #{@nombre} \n"
    end
    
  end
end