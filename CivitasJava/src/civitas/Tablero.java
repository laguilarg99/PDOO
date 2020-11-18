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
public class Tablero {
    private int numCasillaCarcel;
    private int porSalida;
    private boolean tieneJuez;
    ArrayList<Casilla> casillas;
    
    
    public Tablero(int numCasillaCarcel){
        if(numCasillaCarcel > 1)
            this.numCasillaCarcel = numCasillaCarcel;
        else
            this.numCasillaCarcel = 1;
        casillas = new ArrayList<Casilla>();
        Casilla casillaSalida = new Casilla("Salida");
        casillas.add(casillaSalida);
        porSalida = 0;
        tieneJuez = false;  
    }
    
    private boolean correcto(){
        return casillas.size() > numCasillaCarcel;
    }
    
    private boolean correcto(int numCasilla){
        return correcto() && numCasilla >= 0 && numCasilla < casillas.size();
    }
    
    int getCarcel(){
        return numCasillaCarcel;
    }
    
    int getPorSalida(){
        int auxSalida = porSalida;
        
        if(porSalida > 0)
            auxSalida = porSalida -1;
        
        return auxSalida;
    }
    
    Casilla getCasilla(int numCasilla){
        if(this.correcto(numCasilla)){
            return casillas.get(numCasilla);
        }
        else 
            return null;
    }
    
    ArrayList<Casilla> getCasillas(){
        return casillas;
    }
        
    void añadeCasilla(Casilla casilla){
        
        int comprobar = 0;
        boolean insertar = true;
       
        while(comprobar < 2){
            
            comprobar++;
            
            if(numCasillaCarcel == casillas.size()){
                Casilla numCarcel = new Casilla("Carcel");
                casillas.add(numCarcel);
            }
   
            if(insertar){
                casillas.add(casilla);
                insertar = false;
            }
        }
    }
    
    
    void añadeJuez(){//NO SE PASA COMO PARAMETRO NUMCARCEL
        if(!tieneJuez){
            Casilla numJuez = new Casilla(numCasillaCarcel,"Juez");
            this.añadeCasilla(numJuez);
            tieneJuez = true;
        }
    }
    
    
    int nuevaPosicion(int actual, int tirada){
        if(!this.correcto()){
            return -1;
        }
        else{
            int nuevaPos = actual + tirada;
            nuevaPos = nuevaPos % casillas.size();
            if(nuevaPos != actual + tirada){
                porSalida = porSalida + 1;
            }
            
            return nuevaPos;
        }
    }
    
    int calcularTirada(int origen, int destino){
        int tirada = destino - origen;
        if(tirada < 0)
            tirada = tirada + casillas.size();
        return tirada;
    }
}
