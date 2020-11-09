# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  class TituloPropiedad
    
    @@factorInteresesHipoteca = 1.1
    
    def initialize(nom, fr, ab, hb, pc, pe)
      @nombre = nom
      @alquilerBase = ab
      @fatorRevalorizacion = fr
      @hipotecaBase = hb
      @precioCompra = pc
      @precioEdificar = pe
      @numCasas = 0
      @numHoteles = 0
      @hipotecado = false
      @propietario = nil
    end
    
    
    def to_s
      "TituloPropiedad: \n\tNombre:  #{@nombre}  \n\t
                                              Alquiler Base:  #{@alquilerBase}  \n\t
                                              Factor Revalorizacion:  #{@factorRevalorizacion} + \n\t
                                              Hipoteca Base:  #{@hipotecaBase}  \n\t
                                              Precio Compra:  #{@precioCompra}  \n\t
                                              Precio Edificar:  #{@precioEdificar}  \n\t
                                              Numero de casas:  #{@numCasas}  \n\t
                                              Numero de hoteles: #{@numHoteles}  \n\t
                                              Hipotecado:  #{@hipotecado}  \n\t";
    end
    
    def actualiza_propietario_por_conversion(jugador)
      @propietario = jugador
    end
    
    private 
    def precio_alquiler
     
    end
    
    def importe_cancelar_hipoteca
      result = @hipotecaBase * @@factorInteresesHipoteca
      return result
    end
    
    private
    def es_este_el_propietario(jugador)
      return jugador == @propietario
    end
    
    def tramitar_alquiler(jugador)
  
    end
    
    private
    def propietarioEncarcelado
         
    end
    
    def cantidad_casas_hoteles
      result = @numCasas + @numHoteles
      return result
    end
    
    private
    def precio_venta
      result = @precioCompra + (@numCasas+5*@numHoteles)*@precioEdificar*@factorRevalorizacion
      return result
    end
    
    def derruir_casas(n, jugador)
      if @propietario == jugador && @numCasas >= n
        @numCasas = @numCasas - n
        return true
      else
        return false
      end
    end
    
    def vender(jugador)
         if es_este_el_propietario(jugador) && !@hipotecado
           jugador.recibe(@precioCompra)
           @propietario = nil
           @numCasas = 0
           @numHoteles = 0
           return true
         else
           return false
         end
    end
    
    def tiene_propietario
      return @propietario != nil
    end
    
    private
    def importe_hipoteca
      return @hipotecaBase
    end
    
    attr_reader :precioEdificar, :numCasas, :numHoteles, :hipotecado, :nombre, :precioCompra, :propietario
    
  end
end