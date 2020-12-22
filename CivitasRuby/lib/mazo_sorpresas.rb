# encoding:utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative 'sorpresa.rb'
require_relative 'sorpresa_ir_carcel.rb'
require_relative 'sorpresa_ir_casilla.rb'
require_relative 'sorpresa_pagar_cobrar.rb'
require_relative 'sorpresa_por_casa_hotel.rb'
require_relative 'sorpresa_por_jugador.rb'
require_relative 'sorpresa_salir_carcel.rb'
require_relative 'sorpresa_convertir_jugador.rb'

module Civitas
  
  class MazoSorpresas

    def initialize()
      @sorpresas = Array.new
      @cartasEspeciales = Array.new
      @barajada = false
      @usadas = 0
      @debug = false
    end
    
    def self.new_mazo_sorpresas1(debug)
      new
      @debug = debug
      
      if(debug)
        diary = Diario.instance
        diary.ocurre_evento("debug activado")
      end
      
    end
    
    def self.new_mazo_sorpresas2()
        new
    end
   
    
    def al_mazo(s)
      if !@barajada
        @sorpresas << s
      end
    end
    
    def siguiente()
      if !@barajada && @sorpresas.length == @usadas && !@debug
        @barajada = true
        @sorpresas.shuffle
        @usadas = 0
      end
      @usadas = @usadas + 1
      @ultimaSorpresa = @sorpresas[0]
      @sorpresas.delete_at(0)
      @sorpresas << @ultimaSorpresa
      
      return @ultimaSorpresa
    end
    
    def inhabilitar_carta_especial(sorpresa)
      if @sorpresas.include?(sorpresa)
        @sorpresas.delete(sorpresa)
        @cartasEspeciales << sorpresa
        diary = Diario.instance
        diary.ocurre_evento("Sorpresa añadida a cartas especiales")
      end
    end
    
    def habilitar_carta_especial(sorpresa)
      if @cartasEspeciales.include?(sorpresa)
        @cartasEspeciales.delete(sorpresa)
        @sorpresas << sorpresa
        diary = Diario.instance
        diary.ocurre_evento("Sorpresa añadida a mazo sorpresas")
      end
    end
    
    private_class_method :new
  end
  
end
