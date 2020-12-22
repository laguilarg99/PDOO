# encoding:utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative 'casilla.rb'
require_relative 'casilla_calle.rb'
require_relative 'casilla_impuesto.rb'
require_relative 'casilla_juez.rb'
require_relative 'casilla_sorpresa.rb'

module Civitas
  
  class Tablero
    
    
    def initialize(numCarcel)
      if numCarcel > 1
        @numCasillaCarcel = numCarcel
      else
        @numCasillaCarcel = 1
      end
      @casillas = Array.new
      @casillas << Casilla.new("Salida")
      @porSalida = 0
      @tieneJuez = false
    end
      
    attr_reader :numCasillaCarcel, :casillas
    
    
    def correcto()
      return @casillas.count -1 > numCasillaCarcel
    end
    
    
    def num_correcto(numCasilla)
      return correcto() && numCasilla > 0 && numCasilla < @casillas.length
    end
    
    
    def por_salida()
      aux_salida = @porSalida
      
      if @porSalida
        aux_salida = @porSalida - 1
      end
      
      return aux_salida
    end
    
    
    def añade_casilla(casilla)
      comprobar = 0
      insertar = true
      
      while comprobar < 2
        comprobar = comprobar + 1
        
        if @numCasillaCarcel == @casillas.length
          @casillas << CasillaJuez.new(@numCasillaCarcel,"Carcel")
        end
        
        if insertar
          @casillas << casilla
          insertar = false
        end  
      end
      
    end
    
    def añade_juez()
      if !@tieneJuez
        juez = Casilla.new("Juez")
        self.añade_casilla(juez)
        @tieneJuez = true
      end
    end
    
    def nueva_posicion(actual, tirada)
      if(!correcto())
        return -1
      else
        nuevaPos = actual + tirada
        nuevaPos = nuevaPos % @casillas.length
        if nuevaPos != actual + tirada
          @porSalida = @porSalida + 1
        end
        
        return nuevaPos
      end
      
    end
    
    def calcular_tirada(origen, destino)
      tirada = destino - origen
      if tirada < 0
        tirada = tirada + casillas.count
      end
      return tirada
    end
    
    def casilla(numCasilla)
      if num_correcto(numCasilla)
        return @casillas[numCasilla]
      else
        return nil
      end
    end

  end

end
