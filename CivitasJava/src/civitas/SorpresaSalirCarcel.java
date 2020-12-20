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
public class SorpresaSalirCarcel extends Sorpresa{
    
    private MazoSorpresas mazo;
    
    SorpresaSalirCarcel(MazoSorpresas mazo){
        super("SorpresaSalirCarcel");
        this.mazo = mazo;
    }
    
    @Override
    public void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual,todos)){
            informe(actual,todos);
            int contador = 0;
            for(int i = 0; i < todos.size(); i++){
                if(todos.get(i).tieneSalvoconducto()){
                    contador++;
                }
            }
            if(contador == 0){
                todos.get(actual).obtenerSalvoconducto(this);
                this.salirDelMazo();
            }
        }
    }
    
    void salirDelMazo(){
        mazo.inhabilitarCartaEspecial(this);
    }
    
    void usada(){
        mazo.habilitarCartaEspecial(this);
    }
    
    @Override
    public String toString(){
        String e = "\nNombre: SorpresaSalirCarcel";
        return e;
    }
}
