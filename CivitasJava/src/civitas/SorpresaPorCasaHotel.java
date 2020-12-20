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
public class SorpresaPorCasaHotel extends Sorpresa{
    private int valor;
    
    SorpresaPorCasaHotel(int valor){
        super("SorpresaPorCasaHotel");
        this.valor = valor;
    }
    
    @Override
    public void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual,todos)){
            informe(actual,todos);
            int casasHoteles = 0;
            
            for(int i = 0; i < todos.get(actual).getPropiedades().size(); i++){
                casasHoteles = casasHoteles + todos.get(actual).getPropiedades().get(i).cantidadCasasHoteles();
            }
            
            todos.get(actual).modificarSaldo(this.valor*casasHoteles);
        }
    }
    
    @Override
    public String toString(){
        String e = "\nNombre: SorpresaPorCasaHotel";
        return e;
    }
}
