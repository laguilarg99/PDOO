# encoding:utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative 'diario.rb'
require_relative "mazo_sorpresas.rb"
require_relative "titulo_propiedad.rb"

module Civitas
  

  class Casilla
   
    @@Carcel = 0
    
    def initialize(nombre)
      @nombre = nombre
    end
    
    attr_reader :nombre
 
    
    def informe(iactual, todos)
      diary = Diario.instance
      diary.ocurre_evento("Jugador #{todos[iactual].nombre} ha caido en la casilla #{@nombre}")
    end
    
    def recibe_jugador(iactual, todos)
      if jugador_correcto(iactual, todos)
        informe(iactual,todos)
      end
    end
    
    def jugador_correcto(iactual, todos)
      if iactual >= 0 and iactual < todos.length
        return true
      else
        return false
      end
    end
    
    def to_s
      "Nombre: #{@nombre} \n"
    end
    
    attr_reader :nombre
  end
end
