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
public class CasillaJuez extends Casilla{
    private int carcel;
    
    CasillaJuez(int carcel, String nombre){
        super(nombre);
        this.carcel = carcel;
    }
    
    @Override
    void recibeJugador(int iactual, ArrayList<Jugador> todos){ 
        if(jugadorCorrecto(iactual,todos)){
            informe(iactual, todos);
            todos.get(iactual).encarcelar(carcel);    
        }
    }
    
    @Override
    public String toString(){
        String e = "Nombre: " + this.getNombre() + "\n";
        return e;
    }
}
