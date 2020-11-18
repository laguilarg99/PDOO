/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.Random;
/**
 *
 * @author luis
 */
public class Dado {
    static final private Dado instance = new Dado();
    static final private int SalidaCarcel = 5;
    private Random random;
    private int ultimoResultado;
    private boolean debug;
    
    private Dado(){
        random = new Random();
        ultimoResultado = 0;
        debug = false;
    }
    
    static public Dado getInstance(){
        return instance;
    }
    
    int tirar(){
        int numero;
        
        if(!debug){
            numero = (int)(random.nextDouble() * (6)) + 1;
        }
        else{
            numero = 1;
        }
        
        ultimoResultado = numero;
        return numero;
    }
    
    boolean salgoDeLaCarcel(){
        int numDado = tirar();
        if(numDado == SalidaCarcel)
            return true;
        else
            return false;   
    }
    
    int quienEmpieza(int n){
        Random jugador = new Random();
        return (int)(jugador.nextDouble() * (n));
    }
        
    public void setDebug(Boolean d){
        debug = d;
        Diario diary = Diario.getInstance();
        if(d){ 
            diary.ocurreEvento("debug activado");
        }else{
            diary.ocurreEvento("debug desactivado");
        }
    }
    
    int getUltimoResultado(){
        return ultimoResultado;
    }
}
