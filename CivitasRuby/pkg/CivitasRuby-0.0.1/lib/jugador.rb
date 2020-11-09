# encoding:utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  
  class Jugador
    
    @@casaMax = 4
    @@casasPorHotel = 4
    @@pasoPorSalida = 1000
    @@precioLibertad = 200
    @@saldoInicial = 7500
    
    def initialize(nombre)
      @nombre = nombre
      @encarcelado = false
      @numCasillaActual = 0
      @puedeComprar = true
      @saldo = @@saldoInicial
      @salvoconducto = nil
      @propiedades = Array.new
    end
    
    def self.new_JUGADOR(nombre)
      new(nombre)
    end
    
    def self.new_JUGADOR_COPIA(otro)
      @nombre = otro.nombre
      @encarcelado = otro.is_encarcelado
      @numCasillaActual = otro.numCasillaActual
      @puedeComprar = otro.puedeComprar
      @saldo = otro.saldo
      @salvoconducto = otro.tiene_salvo_conducto
      @propiedades = otro.propiedades
    end
    
    attr_reader :nombre, :casaMax, :hotelesMax, :saldo, :casasPorHotel, :numCasillaActual, :precioLibertad, :pasoPorSalida, :pasoPorSalida, :puedeComprar, :propiedades
    
    
    def <=>(otro)
      return @saldo <=> otro.saldo;
    end
    
    def cantidad_casas_hoteles
      return @propiedades.length
    end
    
    def is_encarcelado
      return @encarcelado
    end
    
    def encarcelar(numCasillaCarcel)
      if debe_ser_encarcelado(numCasillaCarcel)
        mover_a_casilla(numCasillaCarcel)
        @encarcelado = true
        diary = Diario.instance
        diary.ocurre_evento("Jugador: #{@nombre} encarcelado")
      end
      
      return @encarcelado
    end
    
    def mover_a_casilla(numCasilla)
      if @encarcelado
        return false
      else
        @numCasillaActual = numCasilla
        @puedeComprar = false
        diary = Diario.instance
        diary.ocurre_evento("Jugador: #{@nombre} mueve a casilla #{@numCasillaActual}")
        return true
      end
    end
    
    def en_bancarrota
      if saldo < 0
        return true
      else
        return false
      end
    end
    
    def exite_la_propiedad(ip)
      if propiedades[ip] != nil
        return true
      else
        return false
      end
    end
    
    def debe_ser_encarcelado(numCasillaCarcel)
      if is_encarcelado
        return true
      else
        if !tiene_salvo_conducto
          return true
        else
          perder_salvo_conducto
          diary = Diario.instance
          diary.ocurre_evento("Jugador: #{@nombre} evita la carcel")
          return false
        end
      end
    end
    
    def obtener_salvo_conducto(s)
      if @encarcelado
        return false
      else
        @salvoconducto = s
        return true
      end
    end
    
    def perder_salvo_conducto
      if is_encarcelado
        return false
      else
        @salvoconducto = s
        return true
      end
    end
    
    def tiene_salvo_conducto
      if @salvoconducto != nil
        return true
      else
        return false
      end
    end
    
    def modificar_saldo(cantidad)
      @saldo = @saldo + cantidad
      diary = Diario.instance
      diary.ocurre_evento("Saldo aumentado: #{cantidad} para #{@nombre}")
      return true
    end
    
    def paga(cantidad)
      return modificarSaldo(-cantidad)
    end
    
    def paga_impuesto(cantidad)
      if is_encarcelado
        return false
      else
        return paga(cantidad)
      end
    end
    
    def paga_alquiler(cantidad)
      if is_encarcelado
        return flase
      else
        return paga(cantidad)
      end
    end
    
    def pasa_por_salida
      modificar_saldo(1000)
      diary = Diario.instance
      diary.ocurre_evento("Jugador: #{@nombre} pasa por casilla de salida y cobra 1000.")
      return true
    end
    
    def puede_comprar_casilla
      if is_encarcelado
        @puedeComprar = false
      else
        @puedeComprar = true
      end
      
      return @puedeComprar
    end
    
    def puede_salir_carcel_pagando
      if saldo > @@precioLibertad
        return true
      else
        return false
      end
    end
    
    def puedo_gastar
      if is_encarcelado
        return false
      else
        return @saldo
      end
    end
    
    def puedo_edificar_casa(propiedad)
      if is_encarcelado
        return false
      else
        if @propiedades.include?(propiedad) && @saldo >= propiedad.precioEdificar && @propiedad.cantidad_casas_hoteles < 8
          return true
        else
          return false
        end
      end
    end
    
    def puedo_edificar_hotel(propiedad)
      if is_encarcelado
        return false
      else
        if @propiedades.include?(propiedad) && @saldo >= propiedad.precioEdificar && @propiedad.cantidad_casas_hoteles < 8 && @propiedad.numCasas >= 4
          return true
        else
          return false
        end    
      end
    end
    
    def recibe(cantidad)
      if is_encarcelado
        return false
      else
        return modificarSaldo(cantidad)
      end
    end
    
    def salir_carcel_pagando
      if is_encarcelado && puede_salir_pagando
        paga(@@precioLibertad)
        @encarcelado = false
        diary = Diario.instance
        diary.ocurre_evento("Jugador: #{@nombre} sale de la carcel pagando")
        return true
      else
        return false
      end
    end
    
    def salir_carcel_tirando
      dado = Dado.instance
      if dado.salgo_de_la_carcel
        @encarcelado = false
        diary = Diario.instance
        diary.ocurre_evento("Jugador: #{@nombre} sale de la carcel tirando")
        return true
      else
        return false
      end
    end
    
    def tiene_algo_que_gestionar
      return @propiedades.length > 0
    end
    
    public
    def to_s
      "Jugador:  #{@nombre}  \n Saldo:  #{@saldo}  \nCasilla:   #{@numCasillaActual}  \nEncarcelado:  #{@encarcelado}"
    end
    
    
    def vender(ip)  
      if is_encarcelado
        result = false
      else
        if existe_la_propiedad(ip)
          if @propiedades[ip].vender(self)
            diary = Diario.instance
            diary.ocurre_evento("Propiedad: #{@propiedades[ip].nombre} \n Es vendida por: #{@nombre}")
            @propiedades.delete_at(ip)
            result = true
          else
            result = false
          end
        else
          result = false
        end
      end
      
      return result
    end
    
    private_class_method :new
    
  end
  
end