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
public class SorpresaConvertirJugador extends Sorpresa{
    
    private int fianza;
    
    SorpresaConvertirJugador(String texto, int fianza){
        super(texto);
        this.fianza = fianza;
    }
    
    @Override
    public void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual,todos)){
            informe(actual,todos);
            todos.set(actual, new JugadorEspeculador(todos.get(actual), this.fianza));
        }
    }
    
}
