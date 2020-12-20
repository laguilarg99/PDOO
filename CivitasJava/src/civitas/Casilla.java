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
public class Casilla {
    private String nombre;
    
    Casilla(String nombre){
       this.nombre = nombre;
    }

    public String getNombre(){
        return nombre;
    }
    
    void informe(int iactual, ArrayList<Jugador> todos){
        Diario diary = Diario.getInstance();
        diary.ocurreEvento("Jugador: "+ todos.get(iactual).getNombre() + " ha caido en la casilla " + this.getNombre());
    }
    
    
    void recibeJugador(int iactual, ArrayList<Jugador> todos){
        this.informe(iactual, todos);       
    }
   
    @Override
    public String toString(){
        String e = "Nombre: " + this.getNombre() + "\n";
        return e;
    }
    
    public boolean jugadorCorrecto(int iactual, ArrayList<Jugador> todos){
        if(iactual >= 0 && iactual  < todos.size())
            return true;
        else
            return false;
    }
    
}
