/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;

/**
 *
 * @author luis
 */
public class CasillaCalle extends Casilla{
    private TituloPropiedad titulo;
    
    CasillaCalle(TituloPropiedad titulo){
        super(titulo.getNombre());
        this.titulo = titulo;
    }
    
    TituloPropiedad getTituloPropiedad(){
        return titulo;
    } 
    
    @Override
    void recibeJugador(int iactual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(iactual, todos)){
            this.informe(iactual,todos);
            Jugador jugador = todos.get(iactual);
            if(titulo.tienePropietario()){
                titulo.tramitarAlquiler(jugador);
            }else{
                 jugador.puedeComprarCasilla();
            }
            
        } 
    }
    
    @Override
    public String toString(){
        String e = "Nombre: " + this.getNombre() + "\n";
        return e;
    }
}
