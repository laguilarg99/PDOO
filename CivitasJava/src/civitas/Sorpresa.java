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
public class Sorpresa {
    private String texto;
    private int valor;
    private TipoSorpresa tipo;
    private Tablero tablero;
    private MazoSorpresas mazo;
    
    
    Sorpresa(TipoSorpresa tipo, Tablero tablero){
        init();
        this.tipo = tipo;
        this.tablero = tablero;
    }//IRCARCEL
    
    Sorpresa(TipoSorpresa tipo, Tablero tablero, int valor, String texto){
        init();
        this.tipo = tipo;
        this.tablero = tablero;
        this.valor = valor;
        this.texto = texto;
    }//IRCASILLA
    
    Sorpresa(TipoSorpresa tipo, MazoSorpresas mazo){
        init();
        this.tipo = tipo;
        this.mazo = mazo;
    }//SALIRCARCEL
    
    Sorpresa(TipoSorpresa tipo, int valor, String texto){
        init();
        this.tipo = tipo;
        this.valor = valor;
        this.texto = texto;
    }//RESTO
    
    private void init(){
        valor = -1;
        mazo = null;
        this.tablero = null;
    }
    
    
    void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        switch(tipo)
        {
            case IRCARCEL:
                this.aplicarAJugador_irCarcel(actual, todos);
                break;
            case IRCASILLA:
                this.aplicarAJugador_irACasilla(actual, todos);
                break;
            case PAGARCOBRAR:
                this.aplicarAJugador_pagarCobrar(actual, todos);
                break;
            case PORCASAHOTEL:
                this.aplicarAJugador_porCasaHotel(actual, todos);
                break;
            case PORJUGADOR:
                this.aplicarAJugador_porJugador(actual, todos);
                break;
            case SALIRCARCEL:
                this.aplicarAJugador_salirCarcel(actual, todos);
                break;
        }
    }
    
    private void aplicarAJugador_irACasilla(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual,todos)){
            informe(actual,todos);
            Dado dado = Dado.getInstance();
            int tirada = tablero.calcularTirada(todos.get(actual).getNumCasillaActual(), dado.tirar());
            int posicion = tablero.nuevaPosicion(todos.get(actual).getNumCasillaActual(), tirada);
            todos.get(actual).moverACasilla(posicion);
            //CASILLA -> RECIBE JUGADOR -> P3
            
        }
    }
    private void aplicarAJugador_irCarcel(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual,todos)){
            informe(actual,todos);
            todos.get(actual).encarcelar(tablero.getCarcel());
        }
    }
    private void aplicarAJugador_pagarCobrar(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual,todos)){
            informe(actual,todos);
            todos.get(actual).modificarSaldo(this.valor);
        }
    }
    private void aplicarAJugador_porCasaHotel(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual,todos)){
            informe(actual,todos);
            int casasHoteles = 0;
            
            for(int i = 0; i < todos.get(actual).getPropiedades().size(); i++){
                casasHoteles = casasHoteles + todos.get(actual).getPropiedades().get(i).cantidadCasasHoteles();
            }
            
            todos.get(actual).modificarSaldo(this.valor*casasHoteles);
        }
    }
    
    private void aplicarAJugador_porJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual,todos)){
            informe(actual,todos);
            Sorpresa pagar = new Sorpresa(TipoSorpresa.PAGARCOBRAR, this.valor * -1, "Pagan todos los jugadores");
            for(int i = 0; i < todos.size(); i++){
                if(i != actual){
                    pagar.aplicarAJugador(i, todos);
                }
            }
            Sorpresa cobrar = new Sorpresa(TipoSorpresa.PAGARCOBRAR, this.valor * (todos.size() - 1), "Cobra de todos los jugadores");
            cobrar.aplicarAJugador(actual, todos);
        } 
    }

    private void aplicarAJugador_salirCarcel(int actual, ArrayList<Jugador> todos){
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
            
    private void informe(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual,todos)){
            Diario diary = Diario.getInstance();
            diary.ocurreEvento("Aplicando sorpresa" + this.tipo.toString() + " a: " + todos.get(actual).getNombre());
        }
                
    }
            
    public boolean jugadorCorrecto(int actual, ArrayList<Jugador> todos){
        if(actual >= 0 && actual  < todos.size())
            return true;
        else
            return false;
    }
    
    void salirDelMazo(){
        if(tipo == TipoSorpresa.SALIRCARCEL){
            mazo.inhabilitarCartaEspecial(this);
        }
    }
    
    void usada(){
        if(tipo == TipoSorpresa.SALIRCARCEL){
            mazo.habilitarCartaEspecial(this);
        }
    }
    
    @Override
    public String toString(){
        String e = "Tipo: " + this.tipo + "\nNombre: " + this.texto;
        return e;
    }
}
