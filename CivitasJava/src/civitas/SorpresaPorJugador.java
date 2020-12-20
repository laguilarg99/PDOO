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

public class SorpresaPorJugador extends Sorpresa{
    private int valor;
    
    SorpresaPorJugador(int valor){
        super("SorpresaPorJugador");
        this.valor = valor;
    }
    
    @Override
    public void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual,todos)){
            informe(actual,todos);
            SorpresaPagarCobrar pagar = new SorpresaPagarCobrar(this.valor * -1);
            for(int i = 0; i < todos.size(); i++){
                if(i != actual){
                    pagar.aplicarAJugador(i, todos);
                }
            }
            SorpresaPagarCobrar cobrar = new SorpresaPagarCobrar(this.valor * (todos.size() - 1));
            cobrar.aplicarAJugador(actual, todos);
        } 
    }
    
    @Override
    public String toString(){
        String e = "\nNombre: SorpresaPorJugador";
        return e;
    }
}
