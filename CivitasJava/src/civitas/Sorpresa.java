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
public abstract class Sorpresa {
    private String texto;
    
    Sorpresa(String texto){
        this.texto = texto;
    }
    
    public abstract void aplicarAJugador(int actual, ArrayList<Jugador> todos);
            
    void informe(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual,todos)){
            Diario diary = Diario.getInstance();
            diary.ocurreEvento("Aplicando sorpresa a: " + todos.get(actual).getNombre());
        }   
    }
            
    public boolean jugadorCorrecto(int actual, ArrayList<Jugador> todos){
        if(actual >= 0 && actual  < todos.size())
            return true;
        else
            return false;
    }
    
    @Override
    public String toString(){
        String e = "\nNombre: " + this.texto;
        return e;
    }
}
