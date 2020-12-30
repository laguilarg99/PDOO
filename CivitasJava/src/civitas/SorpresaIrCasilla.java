/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import GUI.Dado;
import java.util.ArrayList;

/**
 *
 * @author luis
 */
public class SorpresaIrCasilla extends Sorpresa{
    
    private Tablero tablero;
    private int valor;
    
    SorpresaIrCasilla(Tablero tablero, int valor){
        super("SorpresaIrCasilla");
        this.tablero = tablero;
        this.valor = valor;
    }
    
    @Override
    public void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual,todos)){
            informe(actual,todos);
            Dado dado = Dado.getInstance();
            int tirada = tablero.calcularTirada(todos.get(actual).getNumCasillaActual(), dado.tirar());
            int posicion = tablero.nuevaPosicion(todos.get(actual).getNumCasillaActual(), tirada);
            todos.get(actual).moverACasilla(posicion);
            tablero.getCasilla(todos.get(actual).getNumCasillaActual()).recibeJugador(actual, todos);
        }
    }
    
    @Override
    public String toString(){
        String e = "\nNombre: SorpresaIrCasilla";
        return e;
    }
}
