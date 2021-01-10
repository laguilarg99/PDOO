/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import civitas.CivitasJuego;
import GUI.Dado;
import GUI.Controlador;
import GUI.CivitasView;   

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author luis
 */
public class TestP5 {
    
    public static void main(String[] args){
       CivitasView vista = new CivitasView();
       Dado.createInstance(vista);
       Dado dado = Dado.getInstance();
       dado.setDebug(true);
       
       CapturaNombres nombre = new CapturaNombres(vista,true);
       ArrayList<String> jugadores = new ArrayList<>();
       jugadores = nombre.getNombres();
       
       CivitasJuego nuevo = new CivitasJuego(jugadores);
       Controlador control = new Controlador(nuevo,vista);
       
       vista.setCivitasJuego(nuevo);
       control.juega();
       vista.actualizarVista();
    }
}