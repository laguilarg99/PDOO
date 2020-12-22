# encoding:utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  class JugadorEspeculador < Jugador
    
    @@FactorEspeculador = 2
    
    def initialize(jugador,fianza)
      @fianza = fianza
      super(jugador)
    end
    
    
    def actualiza_propietario_por_conversion()
      for i in self.propiedades
        i.actualiza_propietario_por_conversion(self)
      end
    end
    
    def self.new_Especulador(jugador, fianza)
      jugador_especulador = new
      jugador_especulador.actualiza_propietario_por_conversion()
      return jugador_especulador
    end
    
    def encarcelar(numCasillaCarcel)
      if debe_ser_encarcelado(numCasillaCarcel)
        if self.saldo > @fianza
          self.paga(@fianza)
          @encarcelado = false
          diary = Diario.instance
          diary.ocurre_evento("Jugador: #{@nombre} no es encarcelado")
        else
          mover_a_casilla(numCasillaCarcel)
          @encarcelado = true
          diary = Diario.instance
          diary.ocurre_evento("Jugador: #{@nombre} encarcelado")
        end
        
      end
      
      return @encarcelado
    end
    
    def paga_impuesto(cantidad)
      if is_encarcelado
        return false
      else
        cantidad_especulador = cantidad/@@FactorEspeculador
        return paga(cantidad_especulador)
      end
    end
    
    def casaMax
      return self.casaMax * @@FactorEspeculador
    end
    
    def hotelesMax
      return self.hotelesMax * @@FactorEspeculador
    end
    
    def to_s
      "Jugador Especulador:  #{@nombre.to_s}  \n Saldo:  #{@saldo.to_s}  \nCasilla:   #{@numCasillaActual.to_s}  \nEncarcelado:  #{@encarcelado.to_s}"
    end
    
    
    #private_class_method :new
  end
end
