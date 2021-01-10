/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juegoTexto;

import civitas.CivitasJuego;
import juegoTexto.VistaTextual;
import civitas.OperacionesJuego;
import civitas.Respuestas;
import civitas.GestionesInmobiliarias;
import civitas.OperacionInmobiliaria;
import civitas.SalidasCarcel;
/**
 *
 * @author luis
 */
public class Controlador {
    VistaTextual vista;
    CivitasJuego juego;
    
    public Controlador(CivitasJuego juego, VistaTextual vista){ //visibilidad cambiada a public para que el uso en la prueba
        this.juego= juego;
        this.vista = vista;
    }
    
    public void juega(){ //visibilidad cambiada a public para que el uso
        vista.setCivitasJuego(juego);
        while(!juego.finalDelJuego()){
            vista.actualizarVista();
            vista.pausa();
            OperacionesJuego operacion = juego.siguientePaso();
            if(operacion != OperacionesJuego.PASAR_TURNO){
                vista.mostrarEventos();
            }
            if(!juego.finalDelJuego()){
                switch(operacion)
                {
                    case COMPRAR:
                        Respuestas respuesta = vista.comprar();
                        if(respuesta == Respuestas.SI){
                            juego.comprar(); 
                        }
                        juego.siguientePasoCompletado(operacion);
                        break;
                    case GESTIONAR:
                        vista.gestionar();
                        int gestion = vista.getGestion();
                        GestionesInmobiliarias g = GestionesInmobiliarias.values()[gestion];
                        int property = vista.getPropiedad();
                        OperacionInmobiliaria inmueble = new OperacionInmobiliaria(g,property);
                        switch(g)
                        {
                            case VENDER:
                                juego.vender(property);
                                break;
                         
                            case HIPOTECAR:
                                juego.hipotecar(property);
                                break;
                                
                            case CANCELAR_HIPOTECA:
                                juego.cancelarHipoteca(property);
                                break;
                                
                            case CONSTRUIR_CASA:
                                juego.construirCasa(property);
                                break;
                            
                            case CONSTRUIR_HOTEL:
                                juego.construirHotel(property);
                                break;
                             
                            case TERMINAR:
                                juego.siguientePasoCompletado(operacion);
                                break;
                        }
                        break;
                    case SALIR_CARCEL:
                        SalidasCarcel salida = vista.salirCarcel();
                        switch(salida){
                            case PAGANDO:
                                juego.salirCarcelPagando();
                                break;
                            
                            case TIRANDO:
                                juego.salirCarcelTirando();
                                break;
                        }
                        juego.siguientePasoCompletado(operacion);
                        break;
                    
                }
            }
        }
        
    }
}
