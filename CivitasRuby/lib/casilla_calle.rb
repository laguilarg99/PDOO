# encoding:utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  class CasillaCalle < Casilla
    
    def initialize(tituloPropiedad)
      super(tituloPropiedad.nombre)
      @tituloPropiedad = tituloPropiedad
      
    end
    
    
    def recibe_jugador_calle(iactual, todos)
      if jugador_correcto(iactual,todos)
        informe(iactual,todos)
        jugador = todos[iactual]
        if @tituloPropiedad.tiene_propietario
          jugador.puede_comprar_casilla
          @tituloPropiedad.tramitar_alquiler(jugador)
        end
      end
    end
    
    def to_s
      "Nombre: #{@nombre} \n"
    end
    
    attr_reader :tituloPropiedad
  end
end
