# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
require_relative 'civitas_juego.rb'
require_relative 'vista_textual.rb'
require_relative 'operacion_inmobiliaria.rb'

module Civitas
  
  class Controlador

    Lista_gestion = [Civitas::Gestiones_inmobiliarias::VENDER, Civitas::Gestiones_inmobiliarias::HIPOTECAR, Civitas::Gestiones_inmobiliarias::CANCELAR_HIPOTECA, Civitas::Gestiones_inmobiliarias::CONSTRUIR_CASA, Civitas::Gestiones_inmobiliarias::CONSTRUIR_HOTEL, Civitas::Gestiones_inmobiliarias::TERMINAR]
   
    def initialize(juego, vista)
      @juego = juego
      @vista = vista
    end
    
    def juega()
      @vista.setCivitasJuego(@juego)
      while !@juego.final_del_juego
        @vista.actualizarVista
        @vista.pausa
        operacion = @juego.siguiente_paso
        if operacion != Civitas::Operaciones_juego::PASAR_TURNO
          @vista.mostrarEventos
        end
        if !@juego.final_del_juego
          case operacion
          when Civitas::Operaciones_juego::COMPRAR
            respuesta = @vista.comprar
            if respuesta == Civitas::Respuestas::SI
              @juego.comprar
            end
            @juego.siguiente_paso_completo(operacion)
          when Civitas::Operaciones_juego::GESTIONAR
            @vista.gestionar
            gestion = @vista.getGestion
            g = Lista_gestion[gestion]
            property = @vista.getPropiedad
            inmueble = Operacion_inmobiliaria.new(g, property)
            case g
            when Civitas::Gestiones_inmobiliarias::VENDER
              @juego.vender(property)
            when Civitas::Gestiones_inmobiliarias::HIPOTECAR
              @juego.hipotecar(property)
            when Civitas::Gestiones_inmobiliarias::CANCELAR_HIPOTECA
              @juego.cancelar_hipoteca(property)
            when Civitas::Gestiones_inmobiliarias::CONSTRUIR_CASA
              @juego.construir_casa(property)
            when Civitas::Gestiones_inmobiliarias::CONSTRUIR_HOTEL
              @juego.construir_hotel(property)
            when Civitas::Gestiones_inmobiliarias::TERMINAR
              @juego.siguiente_paso_completo(operacion)
            end
          when Civitas::Operaciones_juego::SALIR_CARCEL
            salida = @vista.salirCarcel
            case salida
            when Civitas::Salidas_carcel::PAGANDO
              @juego.salir_carcel_pagando
            when Civitas::Salidas_carcel::TIRANDO
              @juego.salir_carcel_tirando
            end
            @juego.siguiente_paso_completo(operacion)
          end
        end
      end
      
      for i in @juego.ranking
          puts "#{i}\n"
      end
      
    end

  end

end