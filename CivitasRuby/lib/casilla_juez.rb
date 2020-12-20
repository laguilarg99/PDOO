# encoding:utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  class CasillaJuez < Casilla
    
    def initialize(carcel, nombre)
      super(nombre)
      @@carcel = carcel
    end
    
    
    def recibe_jugador_juez(iactual, todos)
      if jugador_correcto(iactual,todos)
        informe(iactual,todos)
        todos[iactual].encarcelar(@@carcel)
      end
    end
    
    def to_s
      "Nombre: #{@nombre} \n"
    end
    
  end
end
