# encoding:utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
require_relative 'dado.rb'
require_relative 'civitas_juego.rb'
require_relative 'tablero.rb'
require_relative 'jugador.rb'
require_relative 'vista_textual.rb'
require_relative 'controlador.rb'

module Civitas
  class Juego_prueba
    def initialize
      
    end
    
    def self.main
      
      jugadores = Array.new
      jugadores << "Luis"
      jugadores << "Fran"
      jugadores << "Miguel"
      
      dado = Dado.instance
      dado.set_debug(true)
      
      juego = CivitasJuego.new(jugadores)
      vista = Vista_textual.new
      controlador = Controlador.new(juego, vista)
      controlador.juega
    end
  end

  
  Juego_prueba.main
end
