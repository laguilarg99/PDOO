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
public class TituloPropiedad {
    private float alquilerBase;
    private static float factorInsteresesHipoteca = 1.1f;
    private float factorRevalorizacion;
    private float hipotecaBase;
    private boolean hipotecado;
    private String nombre;
    private int numCasas;
    private int numHoteles;
    private float precioCompra;
    private float precioEdificar;
    private Jugador propietario;
    
    //nombre, precio base de alquiler, factor de revalorización, precio base de hipoteca, precio de compra y precio por edificar
    TituloPropiedad(String nom, float fr, float ab, float hb, float pc, float pe){
        nombre = nom;
        alquilerBase = ab;
        factorRevalorizacion = fr;
        hipotecaBase = hb;
        precioCompra = pc;
        precioEdificar = pe;
        numCasas = 0;
        numHoteles = 0;
        hipotecado = false;
        propietario = null;
    }
    
    @Override
    public String toString(){
        String resultado = "Nombre:" + this.nombre + "\n\t"
                           + "Alquiler Base:" + this.alquilerBase + "\n\t"
                           + "Factor Revalorizacion:" + this.factorRevalorizacion + "\n\t"
                           + "Hipoteca Base:" + this.hipotecaBase + "\n\t"
                           + "Precio Compra:" + this.precioCompra + "\n\t"
                           + "Precio Edificar:" + this.precioEdificar + "\n\t"
                           + "Numero de casas:" + this.numCasas + "\n\t"
                           + "Numero de hoteles:" + this.numHoteles + "\n\t"
                           + "Hipotecado:" + this.hipotecado + "\n\t";
        
        return resultado;
    }
    
    void actualizaPropietarioPorConversion(Jugador jugador){
        this.propietario = jugador;
    }
    
    private float getPrecioAlquiler(){
        float precioAlquiler = 0;
        if(this.hipotecado || propietarioEncarcelado())
            return precioAlquiler = 0;
        else
            return precioAlquiler = this.alquilerBase*
                                    (1+(this.numCasas*0.5f)+
                                    (this.numHoteles*2.5f));
        
    }
    
    float getImporteCancelarHipoteca(){
        float result = this.hipotecaBase*this.factorInsteresesHipoteca;
        return result;
    }
    
    private boolean esEsteElPropietario(Jugador jugador){
        return jugador == propietario;
    }
    
    void tramitarAlquiler(Jugador jugador){
        if(this.tienePropietario() && esEsteElPropietario(jugador)){
            jugador.pagaAlquiler(this.getPrecioAlquiler());
            propietario.recibe(getPrecioAlquiler());
        }
    }
    
    private boolean propietarioEncarcelado(){
        if(propietario != null)
            return propietario.isEncarcelado();
        else
            return false;
    }
    
    int cantidadCasasHoteles(){
        int result = this.numCasas + this.numHoteles;
        return result;
    }
    
    private float getPrecioVenta(){
        float result = this.precioCompra + 
                       (numCasas+5*numHoteles)*precioEdificar
                       *factorRevalorizacion;
        return result;
    }
    
    boolean derruirCasas(int n, Jugador jugador){
        if(propietario == jugador && numCasas >= n){
            numCasas = numCasas - n;
            return true;
        }else
            return false;
    }
    
    boolean vender(Jugador jugador){
        if(esEsteElPropietario(jugador) && !this.hipotecado){
            jugador.recibe(this.precioCompra);
            propietario = null;
            numCasas = 0;
            numHoteles = 0;
            return true;
        }else
            return false;
    }
    
    float getPrecioEdificar(){
        return precioEdificar;
    }
    
    public int getNumCasas(){
        return numCasas;
    }
    
    public int getNumHoteles(){
        return numHoteles;
    }
    
    public boolean getHipotecado(){
        return hipotecado;
    }
    
    private float getImporteHipoteca(){
        return hipotecaBase;
    }
    
    public String getNombre(){
        return this.nombre;
    }
    
    float getPrecioCompra(){
        return this.precioCompra;
    }
    
    
    Jugador getPropietario(){
        return propietario;
    }
    
    boolean tienePropietario(){
        return propietario != null;
    }
    
    boolean cancelarHipoteca(Jugador jugador){
        boolean result = false;
        if(this.hipotecado && this.esEsteElPropietario(jugador)){
            jugador.paga(this.getImporteCancelarHipoteca());
            hipotecado = false;
            result = true;
        }
        return result;
    }
    
    boolean comprar(Jugador jugador){
        boolean result = false;
        
        if(!this.tienePropietario()){
            propietario = jugador;
            result = true;
            jugador.paga(precioCompra);
        }
        
        return result;
    }
    
    boolean construirHotel(Jugador jugador){
        boolean result = false;
        
        if(this.esEsteElPropietario(jugador)){
            jugador.paga(precioEdificar);
            numHoteles = numHoteles +1;
            result = true;
        }
        
        return result;
    }
    
    boolean construirCasa(Jugador jugador){
        boolean result = false;
        
        if(this.esEsteElPropietario(jugador)){
            jugador.paga(precioEdificar);
            numCasas = numCasas +1;
            result = true;
        }
        
        return result;
    }
    
    boolean hipotecar(Jugador jugador){
        boolean salida = false;
        
        if(!hipotecado && this.esEsteElPropietario(jugador)){
            jugador.recibe(getImporteHipoteca());
            hipotecado = true;
            salida = true;
        }
        
        return salida;
    }
    
}
