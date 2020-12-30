/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;
import GUI.Dado;
/**
 *
 * @author luis
 */
public class Jugador implements Comparable<Jugador> {
    protected int casaMax = 4;
    static  protected int casasPorHotel = 4;
    protected boolean encarcelado;
    static  protected int hotelesMax = 4;
    private String nombre;
    private int numCasillaActual;
    static  protected float pasoPorSalida = 1000f;
    static  protected float precioLibertad = 200f;
    private boolean puedeComprar;
    float saldo;
    private SorpresaSalirCarcel salvoconducto;
    static private float saldoInicial = 7500;
    ArrayList<TituloPropiedad> propiedades;
    
    
    public Jugador(String nombre){
        this.nombre = nombre;
        encarcelado = false;
        numCasillaActual = 0;
        puedeComprar = false;
        saldo = saldoInicial;
        salvoconducto = null;
        propiedades = new ArrayList<TituloPropiedad>();
    }
    
    protected Jugador(Jugador otro){
        this.nombre = otro.getNombre();
        encarcelado = otro.isEncarcelado();
        numCasillaActual = otro.getNumCasillaActual();
        puedeComprar = otro.getPuedeComprar();
        saldo = otro.getSaldo();
        salvoconducto = otro.salvoconducto;
        propiedades = new ArrayList<>(otro.propiedades);
    }
    
    @Override
    public int compareTo(Jugador otro){
        int compare = Float.compare(this.saldo, otro.getSaldo());
        return compare;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    int getCasasMax(){
        return casaMax;
    }
    
    int getHotelesMax(){
        return hotelesMax;
    }
    
    public float getSaldo(){
        return this.saldo;
    }
    
    int getCasasPorHotel(){
        return casasPorHotel;
    }
    
    int getNumCasillaActual(){
        return numCasillaActual;
    }
    
    private float getPrecioLibertad(){
        return precioLibertad;
    }
    
    private float getPremioPasoSalida(){
        return pasoPorSalida;
    }
    
    boolean getPuedeComprar(){
        return this.puedeComprar;
    }
    
    public ArrayList<TituloPropiedad> getPropiedades(){ //Cambiada la visibilidad Protected -> Public para el método gestiones de la clase VistaTextual
        return propiedades;
    }
    
    int cantidadCasasHoteles(){
        int cantidad = 0;
        
        for(int i = 0; i < getPropiedades().size(); i++){
            cantidad = cantidad + propiedades.get(i).getNumCasas() + propiedades.get(i).getNumHoteles();
        }
        
        return cantidad;
    }
    
    public boolean isEncarcelado(){
        return encarcelado;
    }
    
    boolean encarcelar(int numCasillaCarcel){
        if(debeSerEncarcelado(numCasillaCarcel)){
            moverACasilla(numCasillaCarcel);
            encarcelado = true;
            Diario diary = Diario.getInstance();
            diary.ocurreEvento("Jugador: " + this.getNombre() + " encarcelado");
        }
        
        return encarcelado;
    }
    
    boolean moverACasilla(int numCasilla){
        if(encarcelado)
            return false;
        else{
            this.numCasillaActual = numCasilla;
            this.puedeComprar = false;
            Diario diary = Diario.getInstance();
            diary.ocurreEvento("Jugador: " + this.getNombre() + " mueve a casilla " + numCasilla);
            return true;
        }
    }
    
    boolean enBancarrota(){
        if(this.saldo < 0){
            return true;
        }else{
            return false;
        }
    }

    private boolean exiteLaPropiedad(int ip){
        if(propiedades.get(ip) != null){
            return true;
        }
        else{
            return false;
        }
    }
    
    
    protected boolean debeSerEncarcelado(int numCasillaCarcel){
        if(encarcelado)
            return false;
        else{
            if(!this.tieneSalvoconducto()){
                return true;
            }
            else{
                perderSalvoConducto();
                Diario diary = Diario.getInstance();
                diary.ocurreEvento("Jugador: " + this.nombre + " evita carcel");
                return false;
            }
        }
            
    }
    
    
    boolean obtenerSalvoconducto(SorpresaSalirCarcel s){
        if(encarcelado){
            return false;
        }else{
            salvoconducto = s;
            return true;
        }
    }
    
    private void perderSalvoConducto(){
        salvoconducto.usada();
        salvoconducto = null;
    }
    
    boolean tieneSalvoconducto(){
        if(salvoconducto != null)
            return true;
        else 
            return false;
    }
    
    boolean modificarSaldo(float cantidad){
        this.saldo = saldo + cantidad;
        Diario diary = Diario.getInstance();
        if(cantidad < 0)
            diary.ocurreEvento("Saldo disminuido: " + cantidad + " para " + this.getNombre());
        else
            diary.ocurreEvento("Saldo aumentado: " + cantidad + " para " + this.getNombre());
        return true;
    }
    
    boolean paga(float cantidad){
        return this.modificarSaldo(-cantidad);
    }
    
    boolean pagaImpuesto(float cantidad){
        if(isEncarcelado()){
            return false;
        }else{
            return paga(cantidad);
        }
    }
    
    boolean pagaAlquiler(float cantidad){
        if(isEncarcelado()){
            return false;
        }else{
            return paga(cantidad);
        }//SE CAMBIA FUTURAS PRÁCTICAS
    }
    
    boolean pasaPorSalida(){
        modificarSaldo(1000);
        Diario diary = Diario.getInstance();
        diary.ocurreEvento("Jugador: " + this.getNombre() + " pasa por casilla de salida y cobra 1000.");
        return true;
    }
    
    boolean puedeComprarCasilla(){
        if(isEncarcelado()){
            puedeComprar = false;
        }
        else{
            puedeComprar = true;
        }
        return puedeComprar;
    }
    
    private boolean puedeSalirCarcelPagando(){
        if(this.getSaldo() > precioLibertad){
            return true;
        }
        else{
            return false;
        }
    }
    
    private boolean puedoGastar(float precio){
        if(isEncarcelado()){
            return false;
        }else{
            return this.saldo >= precio;
        }
    }
    
    private boolean puedoEdificarCasa(TituloPropiedad propiedad){
        if(isEncarcelado()){
            return false;
        }else{
            if(propiedades.contains(propiedad) 
           && this.saldo >= propiedad.getPrecioEdificar() 
           && propiedad.cantidadCasasHoteles() < 8){
                return true;
            }
            else{
                return false;
            }
        }
    }
    
    private boolean puedoEdificarHotel(TituloPropiedad propiedad){
        if(isEncarcelado()){
            return false;
        }else{
            if(propiedades.contains(propiedad) 
           && this.saldo >= propiedad.getPrecioEdificar() 
           && propiedad.cantidadCasasHoteles() < 8 
           && propiedad.getNumCasas()>=4){
                return true;
            }
            else{
                return false;
            }
        }    
    }
    
    
    boolean recibe(float cantidad){
        if(isEncarcelado()){
            return false;
        }
        else{
            return modificarSaldo(cantidad);
        }
    }
    
    boolean salirCarcelPagando(){
        if(isEncarcelado() && puedeSalirCarcelPagando()){
            paga(precioLibertad);
            encarcelado = false;
            Diario diary = Diario.getInstance();
            diary.ocurreEvento("Jugador: " + this.getNombre() + " sale de la cárcel pagando");
            return true;
        }else{
            return false;
        }
    }
    
    boolean salirCarcelTirando(){
        Dado dado = Dado.getInstance();
        if(dado.salgoDeLaCarcel()){
            encarcelado = false;
            Diario diary = Diario.getInstance();
            diary.ocurreEvento("Jugador: " + this.getNombre() + " sale de la cárcel tirando");
            return true;
        }
        else{
            return false;
        }
    }
    
    boolean tieneAlgoQueGestionar(){
        return propiedades.size() > 0;
    }
    
    @Override
    public String toString(){
        String e = "Jugador: " + this.getNombre() + "\nSaldo: " + this.getSaldo() + "\nCasilla: " + this.getNumCasillaActual() + "\nEncarcelado: " + this.isEncarcelado();
        return e;
    }
    
    boolean vender(int ip){
        boolean result;
        
        if(isEncarcelado()){
            result = false;
        }else{
            if(this.exiteLaPropiedad(ip)){
                if(propiedades.get(ip).vender(this)){
                    Diario diary = Diario.getInstance();
                    diary.ocurreEvento("Propiedad: " + propiedades.get(ip).getNombre() + "\nEs vendida por: " + this.nombre);
                    propiedades.remove(ip);
                    result = true;
                }else
                    result = false;
                
            }else
                result = false;
        }
        
        return result;
    }
    
    boolean cancelarHipoteca(int ip){
        boolean result = false;
        
        if(encarcelado){
            return result;
        }else{
            if(this.exiteLaPropiedad(ip)){
                TituloPropiedad propiedad = propiedades.get(ip);
                float cantidad = propiedad.getImporteCancelarHipoteca();
                boolean puedoGastar = this.puedoGastar(cantidad);
                if(puedoGastar){
                    result = propiedad.cancelarHipoteca(this);
                    if(result){
                        Diario diary = Diario.getInstance();
                        diary.ocurreEvento("El jugador " + this.nombre + " cancela la hipoteca de la propiedad " + ip);
                    }
                }
            }
        }
        
        return result;
    }
    
    
    boolean comprar(TituloPropiedad titulo){
        boolean result = false;
        if(encarcelado){
            return result;
        }else{
            if(puedeComprar){
                float precio = titulo.getPrecioCompra();
                if(puedoGastar(precio)){
                    result = titulo.comprar(this);
                    if(result){
                        propiedades.add(titulo);
                        Diario diary = Diario.getInstance();
                        diary.ocurreEvento("El jugador " + this.nombre + " compra la propiedad " + titulo.toString());
                    }
                    
                    puedeComprar = false;
                }
               
            }
        }
        
        return result;
    }
    
    boolean construirHotel(int ip){
        boolean result = false;
        boolean puedoEdificarHotel = false;
        if(encarcelado){
            return result;
        }else{
            if(this.exiteLaPropiedad(ip)){
                TituloPropiedad propiedad = propiedades.get(ip);
                puedoEdificarHotel = this.puedoEdificarHotel(propiedad);
                float precio = propiedad.getPrecioEdificar();
                if(puedoEdificarHotel){
                    result = propiedad.construirHotel(this);
                    casasPorHotel = getCasasPorHotel();
                    propiedad.derruirCasas(casasPorHotel,this);
                    if(result){
                        Diario diary = Diario.getInstance();
                        diary.ocurreEvento("El jugador " + this.nombre + " construye un hotel en la propiedad " + ip);
                    }
                }
               
            }
        }
        
        return result;
        
    }
    
    boolean construirCasa(int ip){
        boolean result = false;
        boolean puedoEdificarCasa = false;
        if(encarcelado){
            return result;
        }else{
            if(this.exiteLaPropiedad(ip)){
                TituloPropiedad propiedad = propiedades.get(ip);
                puedoEdificarCasa = this.puedoEdificarCasa(propiedad);
                float precio = propiedad.getPrecioEdificar();
                if(puedoEdificarCasa){
                    result = propiedad.construirCasa(this);
                    
                    if(result){
                        Diario diary = Diario.getInstance();
                        diary.ocurreEvento("El jugador " + this.nombre + " construye una casa en la propiedad " + ip);
                    }
                }
               
            }
        }
        
        return result;
        
    }
    
    boolean hipotecar(int ip){
        boolean result = false;
        if(encarcelado){
            return result;
        }else{
            if(this.exiteLaPropiedad(ip)){
                TituloPropiedad propiedad = propiedades.get(ip);
                result = propiedad.hipotecar(this);
            }
        }
        if(result){
            Diario diary = Diario.getInstance();
            diary.ocurreEvento("El jugador " + this.nombre + " hipoteca la propiedad " + ip);
        }
        
        return result;
    }
    
    
}