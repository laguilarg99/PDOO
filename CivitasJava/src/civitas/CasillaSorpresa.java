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
public class CasillaSorpresa extends Casilla{
    private MazoSorpresas mazo;
    private Sorpresa sorpresa;
    
    CasillaSorpresa(MazoSorpresas mazo, String nombre){
        super(nombre);
        this.mazo = mazo;
        sorpresa = null;
    }
    
    @Override
    void recibeJugador(int iactual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(iactual,todos)){
            informe(iactual, todos);
            sorpresa = mazo.siguiente();
            sorpresa.aplicarAJugador(iactual, todos);   
        }
    }
    
    @Override
    public String toString(){
        String e = "Nombre: " + this.getNombre() + "\n";
        return e;
    }
}
