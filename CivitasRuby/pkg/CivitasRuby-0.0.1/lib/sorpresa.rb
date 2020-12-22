# encoding:utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative 'jugador.rb'
require_relative 'jugador_especulador.rb'

module Civitas
  
  class Sorpresa
    
    
    def initialize(texto)
      @texto = texto
    end
    
    def informe(actual, todos)
      if jugador_correcto(actual,todos)
        diary = Diario.instance
        diary.ocurre_evento("Aplicando sorpresa #{@tipo.to_s} a: #{@nombre}")
      end
    end
    
    def jugador_correcto(actual, todos)
      if actual >= 0 && actual < todos.length
        return true
      else
        return false
      end
    end
   
    
    def to_s
      "\nNombre: #{@texto}"
    end
    
    private_class_method  :new
  end

end
