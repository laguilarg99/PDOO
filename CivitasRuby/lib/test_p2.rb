# encoding:utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
require_relative 'dado.rb'
require_relative 'civitas_juego.rb'
require_relative 'tablero.rb'
require_relative 'jugador.rb'

module Civitas
  class TestP2
    def initialize
      
    end
    def self.main
      
      jugadores = Array.new
      jugadores << "Luis"
      jugadores << "Fran"
      jugadores << "Miguel"
      
      dado = Dado.instance
      
      juego = CivitasJuego.new(jugadores)
      
      puts juego.info_jugador_texto
      puts"\n"
      
      juego.jugador_actual.mover_a_casilla(dado.tirar)
      
      puts juego.info_jugador_texto
      puts"\n"
      
      juego.jugador_actual.encarcelar(5)
      
      puts juego.info_jugador_texto
      puts"\n"
      
      puts juego.jugador_actual.mover_a_casilla(dado.tirar)
      
      juego.jugador_actual.modificar_saldo(-100)
      
      puts juego.info_jugador_texto
      
    end
  end

  
  TestP2.main
end
