/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

/**
 *
 * @author luis
 */
public class OperacionInmobiliaria {
    int numPropiedad;
    GestionesInmobiliarias gestion;
    
    public GestionesInmobiliarias getGestion(){
        return gestion;
    }
    
    public int getNumPropiedad(){
        return numPropiedad;
    }
    
    public OperacionInmobiliaria(GestionesInmobiliarias gest, int ip){
        gestion = gest;
        numPropiedad = ip;
    }
}
