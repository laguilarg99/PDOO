# encoding:utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative 'diario.rb'
require_relative "tipo_casilla.rb"
require_relative "mazo_sorpresas.rb"
require_relative "titulo_propiedad.rb"
module Civitas
  

  class Casilla
   
    
    def initialize(nombre, tituloPropiedad, importe, tipo, mazo, sorpresa)
      init()
      @nombre = nombre
      @importe = importe
      @tituloPropiedad = tituloPropiedad
      @tipo = tipo
      @mazo = mazo
      @sorpresa = sorpresa
    end
    
    attr_reader :nombre , :tituloPropiedad
    
    def self.new_DESCANSO(nombre)
      new(nombre,nil,0,Civitas::Tipo_casilla::DESCANSO, nil, nil)
    end
    
    def self.new_CALLE(titulo)
      new(nil,titulo,0,Civitas::Tipo_casilla::CALLE,nil,nil)
    end
    
    def self.new_IMPUESTO(cantidad, nombre)
      new(nombre,nil,cantidad,Civitas::Tipo_casilla::IMPUESTO, nil,nil)
    end
    
    def self.new_JUEZ(numCasillaCarcel, nombre)
      new(nombre,nil,0,Civitas::Tipo_casilla::JUEZ,nil,nil)
      @@carcel = numCasillaCarcel
    end
    
    def self.new_SORPRESA(mazo,nombre)
      new(nombre,nil,0,Civitas::Tipo_casilla::SORPRESA,mazo,nil)
    end
    
    private
    def init()
      @nombre = nil
      @importe = -1
      @@carcel = -1
      @tipo = nil
      @tituloPropiedad = nil
      @mazo = MazoSorpresas.new_mazo_sorpresas2
    end
    
    private
    def informe(iactual, todos)
      diary = Diario.instance
      diary.ocurre_evento("Jugador #{todos[iactual].nombre} ha caido en la casilla #{@nombre}")
    end
    
    private
    def recibe_jugador_impuesto(iactual,todos)
      if jugador_correcto(iactual, todos)
        informe(iactual,todos)
        todos[iactual].paga_impuesto(@importe)
      end
    end
    
    private
    def recibe_jugador_juez(iactual, todos)
      if jugador_correcto(iactual,todos)
        informe(iactual,todos)
        todos[iactual].encarcelar(@@carcel)
      end
    end
    
    def to_s
      "Nombre: #{@nombre} \n"
    end
    
    def jugador_correcto(iactual, todos)
      if iactual >= 0 && iactual < todos.length
        return true
      else
        return false
      end
    end
    
    
    private_class_method :new
    
    
  end
end
