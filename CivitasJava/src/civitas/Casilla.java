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
    private float importe;
    private static int carcel;
    private TipoCasilla tipo;
    private TituloPropiedad tituloPropiedad;
    private MazoSorpresas mazo;
    private Sorpresa sorpresa;
    
    Casilla (String nombre){
       init();
       this.nombre = nombre;
       tipo = TipoCasilla.DESCANSO;
    }//Descanso
    
    Casilla(TituloPropiedad titulo){
        init();
        tituloPropiedad = titulo;
        tipo = TipoCasilla.CALLE;
    }//Calle
    
    Casilla(float cantidad, String nombre){
        init();
        this.nombre=nombre;
        importe = cantidad;
        tipo = TipoCasilla.IMPUESTO;
    }//Impuesto
    
    Casilla(int numCasillaCarcel, String nombre){
        init();
        this.carcel = numCasillaCarcel;
        this.nombre = nombre;
        tipo = TipoCasilla.JUEZ;
    }//Juez
    
    Casilla(MazoSorpresas mazo, String nombre){
        init();
        this.mazo = mazo;
        this.nombre = nombre;
        tipo = TipoCasilla.SORPRESA;
    }//Sorpresa
    
    private void init(){
        nombre = null;
        importe = -1;
        carcel = -1;
        tipo = null;
        tituloPropiedad = null;
        mazo = null;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    TituloPropiedad getTituloPropiedad(){
        return tituloPropiedad;
    } 
    
    private void informe(int iactual, ArrayList<Jugador> todos){
        Diario diary = Diario.getInstance();
        diary.ocurreEvento("Jugador: "+ todos.get(iactual).getNombre() + " ha caido en la casilla " + this.getNombre());
    }
    
    
    void recibeJugador(int iactual, ArrayList<Jugador> todos){
        
        switch(tipo)
        {
            case CALLE:
                this.recibeJugador_calle(iactual, todos);
                break;
            case IMPUESTO:
                this.recibeJugador_impuesto(iactual, todos);
                break;
            case JUEZ:
                this.recibeJugador_juez(iactual, todos);
                break;
            case SORPRESA:
                this.recibeJugador_sorpresa(iactual, todos);
                break;
            default:
                this.informe(iactual, todos);
                break;
           
        }
        
    }
    private void recibeJugador_impuesto(int iactual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(iactual,todos)){
            informe(iactual, todos);
            todos.get(iactual).pagaImpuesto(this.importe);
        }
        
    }
    
    private void recibeJugador_juez(int iactual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(iactual,todos)){
            informe(iactual, todos);
            todos.get(iactual).encarcelar(carcel);
            
        }
    }
    
    private void recibeJugador_sorpresa(int iactual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(iactual,todos)){
            informe(iactual, todos);
            sorpresa = mazo.siguiente();
            sorpresa.aplicarAJugador(iactual, todos);   
        }
    }
    
    private void recibeJugador_calle(int iactual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(iactual, todos)){
            informe(iactual,todos);
            Jugador jugador = todos.get(iactual);
            if(tituloPropiedad.tienePropietario()){
                jugador.puedeComprarCasilla();
                tituloPropiedad.tramitarAlquiler(jugador);
            }
            
        }
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
