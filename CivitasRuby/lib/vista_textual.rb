#encoding:utf-8
require_relative 'operaciones_juego'
require 'io/console'
require_relative 'respuestas.rb'
require_relative 'gestiones_inmobiliarias.rb'
require_relative 'salidas_carcel.rb'

module Civitas

  class Vista_textual
    
    Lista_respuestas = [Civitas::Respuestas::SI, Civitas::Respuestas::NO]
    Lista_gestion = [Civitas::Gestiones_inmobiliarias::VENDER, Civitas::Gestiones_inmobiliarias::HIPOTECAR, Civitas::Gestiones_inmobiliarias::CANCELAR_HIPOTECA, Civitas::Gestiones_inmobiliarias::CONSTRUIR_CASA, Civitas::Gestiones_inmobiliarias::CONSTRUIR_HOTEL, Civitas::Gestiones_inmobiliarias::TERMINAR]
    Lista_salir_carcel = [Civitas::Salidas_carcel::PAGANDO, Civitas::Salidas_carcel::TIRANDO]
    
    def initialize
      @iGestion = -1
      @iPropiedad = -1
    end
    
    def mostrar_estado(estado)
      puts estado
    end

    
    def pausa
      print "Pulsa una tecla"
      STDIN.getch
      print "\n"
    end

    def lee_entero(max,msg1,msg2)
      ok = false
      begin
        print msg1
        cadena = gets.chomp
        begin
          if (cadena =~ /\A\d+\Z/)
            numero = cadena.to_i
            ok = true
          else
            raise IOError
          end
        rescue IOError
          puts msg2
        end
        if (ok)
          if (numero >= max)
            ok = false
          end
        end
      end while (!ok)

      return numero
    end



    def menu(titulo,lista)
      tab = "  "
      puts titulo
      index = 0
      lista.each { |l|
        puts tab+index.to_s+"-"+l
        index += 1
      }

      opcion = lee_entero(lista.length,
                          "\n"+tab+"Elige una opción: ",
                          tab+"Valor erróneo")
      return opcion
    end

    def salirCarcel()
      titulo = "Elige la forma de salir de la carcel"
      salircarcel = Array.new
      for i in Lista_salir_carcel
        salircarcel << i.to_s
      end
      opcion = menu(titulo, salircarcel)
      return Lista_salir_carcel[opcion]
    end
    
    def comprar
      titulo = "¿Desea comprar esta casilla? #{@juegoModel.casilla_actual.to_s()}"
      respuestas = Array.new
      for i in Lista_respuestas
        respuestas << i.to_s
      end
      opcion = menu(titulo, respuestas)
      return Lista_respuestas[opcion] 
    end

    def gestionar
      titulo = "¿Que desea hacer?"
      gestiones = Array.new
      for i in Lista_gestion
        gestiones << i.to_s
      end
      opcion = menu(titulo, gestiones)
      @iGestion = opcion;
      if(opcion != 5)
        tab = gets.chomp
        titulo1 = "Sobre que propiedad: "
        properties = Array.new
        for i in @juegoModel.jugador_actual.propiedades
          properties << i
        end
        
        @iPropiedad = menu(titulo1, properties)
      end
      
    end

    def getGestion
      return @iGestion
    end

    def getPropiedad
      return @iPropiedad
    end

    def mostrarSiguienteOperacion(operacion)
      puts operacion
    end

    def mostrarEventos
      diary = Diario.instance
      
      while diary.eventos_pendientes
        puts "#{diary.leer_evento}\n"
      end
    end
    
    def setCivitasJuego(civitas)
         @juegoModel=civitas
         self.actualizarVista
    end

    def actualizarVista
      puts @juegoModel.jugador_actual.to_s()
      puts @juegoModel.casilla_actual.to_s()
    end

    
  end

end
