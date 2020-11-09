/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author luis
 */
public class MazoSorpresas {
    private ArrayList<Sorpresa> sorpresas;
    private boolean barajada;
    private int usadas;
    private boolean debug;
    private ArrayList<Sorpresa> cartasEspeciales;
    private Sorpresa ultimaSorpresa;
    
    private void init(){
        sorpresas = new ArrayList<Sorpresa>();
        cartasEspeciales = new ArrayList<Sorpresa>();
        barajada = false;
        usadas = 0;
    }
    
    MazoSorpresas(boolean debug){
        this.debug = debug;
        init();
        
        if(debug){
            Diario diary = Diario.getInstance();
            diary.ocurreEvento("debug activado");
        }
    }
    
    MazoSorpresas(){
        init();
        debug = false;
    }
    
    void alMazo(Sorpresa s){
        if(!barajada)
            this.sorpresas.add(s);
    }
    
    Sorpresa siguiente(){
        if(!barajada || this.sorpresas.size() == usadas && !debug)
        {
            barajada = true;
            Collections.shuffle(sorpresas);
            usadas = 0;
        }
        
        usadas++;
        this.ultimaSorpresa = this.sorpresas.get(0);
        this.sorpresas.remove(0);
        this.sorpresas.add(ultimaSorpresa);
        
        return ultimaSorpresa;
    }
    
    Sorpresa getUltimaSorpresa(){
        return ultimaSorpresa;
    }
    
    void inhabilitarCartaEspecial(Sorpresa sorpresa){
        if(sorpresas.contains(sorpresa)){
            this.sorpresas.remove(sorpresa);
            this.cartasEspeciales.add(sorpresa);
            Diario diary = Diario.getInstance();
            diary.ocurreEvento("Sorpresa añadida a cartas especiales");
        }
    }
    
    void habilitarCartaEspecial(Sorpresa sorpresa){
         if(cartasEspeciales.contains(sorpresa)){
            this.cartasEspeciales.remove(sorpresa);
            this.sorpresas.add(sorpresa);
            Diario diary = Diario.getInstance();
            diary.ocurreEvento("Sorpresa añadida a mazo sorpresas");
        }
    }
}
