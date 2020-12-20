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
public class SorpresaIrCarcel extends Sorpresa{
    private Tablero tablero;
    
    SorpresaIrCarcel(Tablero tablero){
        super("SopresaIrCarcel");
        this.tablero = tablero;
    }
    
    @Override
    public void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual,todos)){
            informe(actual,todos);
            todos.get(actual).encarcelar(tablero.getCarcel());
        }
        
    }
    
    @Override
    public String toString(){
        String e = "\nNombre: SorpresaIrCarcel" ;
        return e;
    }
}
