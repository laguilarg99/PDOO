# encoding:utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require "singleton"
require_relative 'diario.rb'
module Civitas
  
  class Dado
    include Singleton
    
    def initialize
      @dado
      @@SalidaCarcel = 5
      @debug = false
      @ultimoResultado
    end
    
    attr_reader :ultimoResultado
    
    
    def tirar()
      if !@debug
        numero = rand(1..6)
      else
        numero = 1
      end      
      
      @ultimoResultado = numero
      return numero
    end
    
    def salgo_de_la_carcel()
      numDado = tirar()
      if numDado == @@SalidaCarcel
        true
      else
        false
      end
    end
    
    def quienEmpieza(n)
      jugador = rand(1..n)
      return jugador
    end
    
    def set_debug(d)
      @debug = d
      diary = Diario.instance
      
      if d
        diary.ocurre_evento("debug activado")
      else
        diary.ocurre_evento("debug desactivado")
      end
    end
  end

end