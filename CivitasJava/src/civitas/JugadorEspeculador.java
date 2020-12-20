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
public class JugadorEspeculador extends Jugador {
    private static int FactorEspeculador = 2;
    private float fianza;
    
    JugadorEspeculador(Jugador jugador, float fianza){
        super(jugador);
        this.fianza = fianza;
    }
    
    private void actualizaPropietarioPorConversion(){
        for(int i = 0; i < this.propiedades.size(); i++){
            propiedades.get(i).actualizaPropietarioPorConversion(this);
        }                  
    }
    
    @Override
    boolean encarcelar(int numCasillaCarcel){
        if(debeSerEncarcelado(numCasillaCarcel)){
            if(this.saldo > fianza){
                this.paga(fianza);
                Diario diary = Diario.getInstance();
                diary.ocurreEvento("Jugador: " + this.getNombre() + " no es encarcelado");
                encarcelado = false;
            }else{
                moverACasilla(numCasillaCarcel);
                encarcelado = true;
                Diario diary = Diario.getInstance();
                diary.ocurreEvento("Jugador: " + this.getNombre() + " encarcelado");
            }
        }
        
        return encarcelado;
    }
    
    @Override
    boolean pagaImpuesto(float cantidad){
        if(isEncarcelado()){
            return false;
        }else{
            float cantidadEspeculador = cantidad/this.FactorEspeculador; 
            return paga(cantidadEspeculador);
        }
    }
    
    @Override
    int getCasasMax(){
        return this.casaMax * FactorEspeculador;
    }
    
    @Override
    int getHotelesMax(){
        return hotelesMax * FactorEspeculador;
    }
    
    @Override
    public String toString(){
        String e = "Jugador Especulador: " + this.getNombre() + "\nSaldo: " + this.getSaldo() + "\nCasilla: " + this.getNumCasillaActual() + "\nEncarcelado: " + this.isEncarcelado() + "\nFianza: " +  this.fianza;
        return e;
    }
    
    
}
