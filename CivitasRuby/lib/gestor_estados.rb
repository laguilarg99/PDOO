require_relative 'diario'
require_relative 'estados_juego'
require_relative 'jugador.rb'
require_relative 'operaciones_juego.rb'
module Civitas
  class Gestor_estados

    def estado_inicial
      return (Civitas::Estados_juego::INICIO_TURNO)
    end

    def operaciones_permitidas(jugador,estado)
      op = nil

      case estado

      when Civitas::Estados_juego::INICIO_TURNO
        if (jugador.encarcelado)
          op = Civitas::Operaciones_juego::SALIR_CARCEL
        else
          op = Civitas::Operaciones_juego::AVANZAR
        end

      when Civitas::Estados_juego::DESPUES_CARCEL
        op = Civitas::Operaciones_juego::PASAR_TURNO

      when Civitas::Estados_juego::DESPUES_AVANZAR
        if (jugador.encarcelado)
          op = Civitas::Operaciones_juego::PASAR_TURNO
        else
          if (jugador.puedeComprar)
            op = Civitas::Operaciones_juego::COMPRAR
          else
            if (jugador.tiene_algo_que_gestionar)
              op = Civitas::Operaciones_juego::GESTIONAR
            else
              puts jugador.inspect
                   puts jugador.puedeComprar.inspect
              op = Civitas::Operaciones_juego::PASAR_TURNO
            end
          end
        end

      when Civitas::Estados_juego::DESPUES_COMPRAR
        if (jugador.tiene_algo_que_gestionar)
          op = Civitas::Operaciones_juego::GESTIONAR
        else
          op = Civitas::Operaciones_juego::PASAR_TURNO
        end

      when Civitas::Estados_juego::DESPUES_GESTIONAR
        op = Civitas::Operaciones_juego::PASAR_TURNO
      end

      return op
    end



    def siguiente_estado(jugador,estado,operacion)
      siguiente = nil

      case estado

      when Civitas::Estados_juego::INICIO_TURNO
        if (operacion== Civitas::Operaciones_juego::SALIR_CARCEL)
          siguiente = Civitas::Estados_juego::DESPUES_CARCEL
        else
          if (operacion== Civitas::Operaciones_juego::AVANZAR)
            siguiente = Civitas::Estados_juego::DESPUES_AVANZAR
          end
        end


      when Civitas::Estados_juego::DESPUES_CARCEL
        if (operacion== Civitas::Operaciones_juego::PASAR_TURNO)
          siguiente = Civitas::Estados_juego::INICIO_TURNO
        end

      when Civitas::Estados_juego::DESPUES_AVANZAR
        case operacion
          when Civitas::Operaciones_juego::PASAR_TURNO
            siguiente = Civitas::Estados_juego::INICIO_TURNO
          when Civitas::Operaciones_juego::COMPRAR
              siguiente = Civitas::Estados_juego::DESPUES_COMPRAR
          when Civitas::Operaciones_juego::GESTIONAR
              siguiente = Civitas::Estados_juego::DESPUES_GESTIONAR
        end


      when Civitas::Estados_juego::DESPUES_COMPRAR
        #if (jugador.tiene_algo_que_gestionar)
        if (operacion==Civitas::peraciones_juego::GESTIONAR)
          siguiente = Civitas::Estados_juego::DESPUES_GESTIONAR
        #  end
        else
          if (operacion== Civitas::Operaciones_juego::PASAR_TURNO)
            siguiente = Civitas::Estados_juego::INICIO_TURNO
          end
        end

      when Civitas::Estados_juego::DESPUES_GESTIONAR
        if (operacion== Civitas::Operaciones_juego::PASAR_TURNO)
          siguiente = Civitas::Estados_juego::INICIO_TURNO
        end
      end

      Diario.instance.ocurre_evento("De: "+estado.to_s+ " con "+operacion.to_s+ " sale: "+siguiente.to_s)

      return siguiente
    end

  end
end
