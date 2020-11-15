/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import juegoTexto.Controlador;
import juegoTexto.VistaTextual;
        
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author luis
 */
public class JuegoPrueba {
    
    public static void main(String[] args){
       ArrayList<String> jugadores = new ArrayList<>(Arrays.asList("Luis", "Fran", "Miguel")); 
       Dado dado = Dado.getInstance();
       dado.setDebug(Boolean.TRUE);
       
       CivitasJuego nuevo = new CivitasJuego(jugadores);
       VistaTextual vista = new VistaTextual();
       Controlador control = new Controlador(nuevo,vista);
       control.juega();
       
    }
}
