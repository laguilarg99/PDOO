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
                                              Alquiler Base:  #{@alquilerBase}\n\t
                                              Factor Revalorizacion:  #{@factorRevalorizacion}\n\t
                                              Hipoteca Base:  #{@hipotecaBase}\n\t
                                              Precio Compra:  #{@precioCompra}\n\t
                                              Precio Edificar:  #{@precioEdificar}\n\t
                                              Numero de casas:  #{@numCasas}\n\t
                                              Numero de hoteles: #{@numHoteles}\n\t
                                              Hipotecado:  #{@hipotecado}  \n\t";
    end
    
    def actualiza_propietario_por_conversion(jugador)
      @propietario = jugador
    end
    
    
    def precio_alquiler
     precio_alquiler = 0
     if @hipotecado or propietarioEncarcelado
       return precio_alquiler = 0
     else
       return precio_alquiler = @alquilerBase*(1+(@numCasas*0.5) + (@numHoteles*2.5))
     end
    end
    
    def importe_cancelar_hipoteca
      result = @hipotecaBase * @@factorInteresesHipoteca
      return result
    end
    
 
    def es_este_el_propietario(jugador)
      return jugador == @propietario
    end
    
    def tramitar_alquiler(jugador)
      if(tiene_propietario && es_este_el_propietario(jugador))
        jugador.paga_alquiler(precio_alquiler)
        @propietario.recibe(precio_alquiler)
      end
    end
    

    def propietarioEncarcelado
         if @propietario != nil
           return @propietario.is_encarcelado
         else
           return false
         end
    end
    
    def cantidad_casas_hoteles
      result = @numCasas + @numHoteles
      return result
    end
   
    
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
    

    def cancelar_hipteca(jugador)
      result = false
      if @hipotecado and  es_este_el_propietario(jugador)
        jugador.paga(importe_cancelar_hipoteca)
        @hipotecado = false
        result = true
      end
      return result
    end
    
    def comprar(jugador)
      result = false
      
      if !tiene_propietario
        @propietario = jugador
        result = true
        jugador.paga(precioCompra)
      end
      
      return result
    end
    
    def construir_hotel(jugador)
      result = false
      
      if es_este_el_propietario(jugador)
        jugador.paga(precioEdificar)
        @numHoteles = @numHoteles + 1
        result = true
      end
      
      return result
    end
    
    def construir_casa(jugador)
      result = false
      
      if es_este_el_propietario(jugador)
        jugador.paga(precioEdificar)
        @numCasas = @numCasas + 1
        result = true
      end
      
      return result
    end
    
    def hipotecar(jugador)
      salida = false
      
      if(!@hipotecado and es_este_el_propietario(jugador))
        jugador.recibe(hipotecaBase)
        @hipotecado = true
        salida = true
      end
      
      return salida
    end
    
    attr_reader :precioEdificar, :numCasas, :numHoteles, :hipotecado, :nombre, :precioCompra, :propietario, :hipotecaBase
    
    
  end
end