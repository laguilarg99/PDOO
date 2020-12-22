# encoding:utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  class CasillaImpuesto < Casilla
    
    def initialize(cantidad,nombre)
      super(nombre)
      @cantidad = cantidad;
    end
    
    def recibe_jugador(iactual,todos)
      if jugador_correcto(iactual, todos)
        informe(iactual,todos)
        todos[iactual].paga_impuesto(@importe)
      end
    end
    
    def to_s
      "Nombre: #{@nombre} \n"
    end
  end
end

