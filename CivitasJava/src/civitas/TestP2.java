/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author luis
 */
public class TestP2 {
    
    public static void main(String[] args){
       ArrayList<String> jugadores = new ArrayList<>(Arrays.asList("Luis", "Fran", "Miguel")); 
       Dado dado = Dado.getInstance();
       
       CivitasJuego nuevo = new CivitasJuego(jugadores);
       
       System.out.print(nuevo.infoJugadorTexto() + "\n\n");
       
       nuevo.getJugadorActual().moverACasilla(dado.tirar());
       
       System.out.print(nuevo.infoJugadorTexto() + "\n\n");
       
       nuevo.getJugadorActual().encarcelar(5);
       
       System.out.print(nuevo.infoJugadorTexto() + "\n\n");
       
       System.out.print(nuevo.getJugadorActual().moverACasilla(nuevo.getJugadorActual().getNumCasillaActual() + dado.tirar()) + "\n\n");
       
       nuevo.getJugadorActual().modificarSaldo(-100);
       
       System.out.print(nuevo.infoJugadorTexto() + "\n\n");
      
       
    }
}
