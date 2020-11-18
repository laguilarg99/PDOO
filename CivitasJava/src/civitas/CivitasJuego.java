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
public class CivitasJuego {
    private int indiceJugadorActual;
    private ArrayList<Jugador> jugadores;
    private MazoSorpresas mazo;
    private EstadosJuego estado;
    private GestorEstados gestorEstados;
    private Tablero tablero;
    private Dado dado = Dado.getInstance();
    
    public CivitasJuego(ArrayList<String> nombres){
        jugadores = new ArrayList<Jugador>();
        for(int i = 0; i < nombres.size(); i++){
            jugadores.add(new Jugador(nombres.get(i)));
        }
        
        gestorEstados = new GestorEstados();
        estado = gestorEstados.estadoInicial();
        
        indiceJugadorActual = dado.quienEmpieza(nombres.size());
        
        mazo = new MazoSorpresas();
        
        inicializaTablero(mazo);
        inicializaMazoSorpresas(tablero);
    }
    
    private void inicializaTablero(MazoSorpresas mazo){
        tablero = new Tablero(5);
        tablero.añadeCasilla(new Casilla(new TituloPropiedad( "Calle de la Palmita", 10, 500, 250, 50, 150)));
        tablero.añadeCasilla(new Casilla(new TituloPropiedad( "Calle de la Espartera", 10, 500, 250, 50, 150)));
        tablero.añadeCasilla(new Casilla(mazo, "Sorpresa1"));
        tablero.añadeCasilla(new Casilla(new TituloPropiedad( "Calle Valle Gran Rey", 11, 600, 300, 60, 300)));
        tablero.añadeCasilla(new Casilla(new TituloPropiedad( "Calle Santo Cristo", 11, 600, 300, 60, 400)));
        tablero.añadeCasilla(new Casilla(1000f, "Impuesto"));
        tablero.añadeCasilla(new Casilla(new TituloPropiedad( "Calle Recogidas", 12, 600, 300, 60, 400)));
        tablero.añadeCasilla(new Casilla(new TituloPropiedad( "Calle Gracia", 12, 600, 350, 65, 500)));
        tablero.añadeCasilla(new Casilla("PARKING"));
        tablero.añadeCasilla(new Casilla(new TituloPropiedad( "Calle Pedro Antonio de Alarcón", 13, 600, 350, 65, 500)));
        tablero.añadeCasilla(new Casilla(mazo, "Sorpresa2"));
        tablero.añadeCasilla(new Casilla(new TituloPropiedad( "Calle Gonzalo Gallas", 13, 700, 400, 70, 600)));
        tablero.añadeCasilla(new Casilla(new TituloPropiedad( "Camino de Ronda", 14, 800, 500, 80, 700)));
        tablero.añadeCasilla(new Casilla(tablero.getCarcel(), "Juez"));
        tablero.añadeCasilla(new Casilla(new TituloPropiedad( "Gran Via de Colón", 14, 800, 500, 80, 700)));
        tablero.añadeCasilla(new Casilla(mazo, "Sorpresa3"));
        tablero.añadeCasilla(new Casilla(new TituloPropiedad( "Calle Yanguas", 15, 1000, 750, 100, 1000)));
        tablero.añadeCasilla(new Casilla(new TituloPropiedad( "Calle San Juan de los Reyes", 15, 1000, 750, 100, 1000)));
    }
    
    private void inicializaMazoSorpresas(Tablero tablero){
        mazo.alMazo(new Sorpresa(TipoSorpresa.IRCARCEL,tablero));
        mazo.alMazo(new Sorpresa(TipoSorpresa.IRCASILLA,tablero, 0, "Vuelve a la casilla salida sin cobrar"));
        mazo.alMazo(new Sorpresa(TipoSorpresa.SALIRCARCEL, mazo));
        mazo.alMazo(new Sorpresa(TipoSorpresa.PAGARCOBRAR, -1000, "Toca pagar faturas"));
        mazo.alMazo(new Sorpresa(TipoSorpresa.PORCASAHOTEL, 100, "Van bien los negocios"));
        mazo.alMazo(new Sorpresa(TipoSorpresa.PORJUGADOR, 200, "Tus amigos te devuelven tu dinero"));
        mazo.alMazo(new Sorpresa(TipoSorpresa.PAGARCOBRAR, 1700, "Cobras la beca de la universidad"));
        mazo.alMazo(new Sorpresa(TipoSorpresa.IRCASILLA, tablero, 8, "Nos vamos de viaje, ve a la casilla sin pasar por la salida"));
        mazo.alMazo(new Sorpresa(TipoSorpresa.PAGARCOBRAR, -500, "Te ha pillado el radar duplicando el límite de velocidad"));
        mazo.alMazo(new Sorpresa(TipoSorpresa.PORCASAHOTEL, -100, "Hay que pagar el mantenimiento de los edificios"));
    }
    
    private void contabilizarPasosPorSalida(Jugador jugadorActual){
        if(tablero.getPorSalida() > 0 && jugadorActual.getNumCasillaActual() == 0){
            jugadorActual.pasaPorSalida();
        }
    }
    
    private void pasarTurno(){
        indiceJugadorActual = (indiceJugadorActual + 1)%jugadores.size();    
    }
    
    public void siguientePasoCompletado(OperacionesJuego operacion){
        estado = gestorEstados.siguienteEstado(jugadores.get(indiceJugadorActual), estado, operacion);
    }
    
    public boolean vender(int ip){
        return jugadores.get(indiceJugadorActual).vender(ip);
    }
    
    public boolean salirCarcelPagando(){
        return jugadores.get(indiceJugadorActual).salirCarcelPagando();
    }
    
    public boolean salirCarcelTirando(){
        return jugadores.get(indiceJugadorActual).salirCarcelTirando();
    }
    
    public boolean finalDelJuego(){
        boolean result = false;
        
        for(int i = 0; i < jugadores.size(); i++){
            if(jugadores.get(i).enBancarrota()){
                result = true;
                break;
            }else{
                result = false;
            }
        }
        
        return result;
    }
    
    
    public Casilla getCasillaActual(){
        return tablero.getCasilla(jugadores.get(indiceJugadorActual).getNumCasillaActual());
    }
    
    public Jugador getJugadorActual(){
        return jugadores.get(indiceJugadorActual);
    }
    
    public ArrayList<Jugador> ranking(){ 
        Collections.sort(jugadores, Jugador::compareTo);
        return jugadores;
    }
    
    public String infoJugadorTexto(){
        String result = "";
        
        result = jugadores.get(indiceJugadorActual).toString();
                
        return result;
    }
    
    private void avanzaJugador(){
        Jugador jugadorActual = jugadores.get(this.indiceJugadorActual);
        int posicionActual = jugadorActual.getNumCasillaActual();
        Dado dado= Dado.getInstance();
        int tirada = dado.tirar();
        int posicionNueva = tablero.nuevaPosicion(posicionActual, tirada);
        Casilla casilla = tablero.getCasilla(posicionNueva);
        this.contabilizarPasosPorSalida(jugadorActual);
        jugadorActual.moverACasilla(posicionNueva);
        casilla.recibeJugador(indiceJugadorActual, jugadores);
        this.contabilizarPasosPorSalida(jugadorActual);
    
    }
    public boolean cancelarHipoteca(int ip){
        return jugadores.get(indiceJugadorActual).cancelarHipoteca(ip);
    }
   
    public boolean construirCasa(int ip){
        return jugadores.get(indiceJugadorActual).construirCasa(ip);
    }
    
    public boolean construirHotel(int ip){
        return jugadores.get(indiceJugadorActual).construirHotel(ip);
    }
    
    public boolean hipotecar(int ip){
        return jugadores.get(indiceJugadorActual).hipotecar(ip);
    }
    
    public OperacionesJuego siguientePaso(){
        Jugador jugadorActual = jugadores.get(indiceJugadorActual);
        OperacionesJuego operacion = gestorEstados.operacionesPermitidas(jugadorActual,estado);
        
        switch(operacion)
        {
            case PASAR_TURNO:
                this.pasarTurno();
                this.siguientePasoCompletado(operacion);
                break;
            case AVANZAR:
                this.avanzaJugador();
                this.siguientePasoCompletado(operacion);
                break;
        }
        
        return operacion;
    }
    
    public boolean comprar(){
        boolean res = false;
        Jugador jugadorActual = jugadores.get(indiceJugadorActual);
        int numCasillaActual = jugadorActual.getNumCasillaActual();
        Casilla casilla = tablero.getCasilla(numCasillaActual);
        TituloPropiedad titulo = casilla.getTituloPropiedad();
        res = jugadorActual.comprar(titulo);
        return res;
    }
}
