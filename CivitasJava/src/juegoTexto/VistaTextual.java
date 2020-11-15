package juegoTexto;

import civitas.CivitasJuego;
import civitas.Diario;
import civitas.OperacionesJuego;
import civitas.SalidasCarcel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import civitas.Casilla;
import civitas.Jugador;
import civitas.TituloPropiedad;
import civitas.Respuestas;

public class VistaTextual { //Cambiada visibilidad Package -> public para importar la clase en la prueba
  
  CivitasJuego juegoModel; 
  int iGestion=-1;
  int iPropiedad=-1;
  private static String separador = "=====================";
  
  private Scanner in;
  
  public VistaTextual () { //Cambiada visibilidad Package -> public para importar la clase en la prueba
    in = new Scanner (System.in);
  }
  
  void mostrarEstado(String estado) {
    System.out.println (estado);
  }
              
  void pausa() {
    System.out.print ("Pulsa una tecla");
    in.nextLine();
  }

  int leeEntero (int max, String msg1, String msg2) {
    Boolean ok;
    String cadena;
    int numero = -1;
    do {
      System.out.print (msg1);
      cadena = in.nextLine();
      try {  
        numero = Integer.parseInt(cadena);
        ok = true;
      } catch (NumberFormatException e) { // No se ha introducido un entero
        System.out.println (msg2);
        ok = false;  
      }
      if (ok && (numero < 0 || numero >= max)) {
        System.out.println (msg2);
        ok = false;
      }
    } while (!ok);

    return numero;
  }

  int menu (String titulo, ArrayList<String> lista) {
    String tab = "  ";
    int opcion;
    System.out.println (titulo);
    for (int i = 0; i < lista.size(); i++) {
      System.out.println (tab+i+"-"+lista.get(i));
    }

    opcion = leeEntero(lista.size(),
                          "\n"+tab+"Elige una opción: ",
                          tab+"Valor erróneo");
    return opcion;
  }

  SalidasCarcel salirCarcel() {
    int opcion = menu ("Elige la forma para intentar salir de la carcel",
      new ArrayList<> (Arrays.asList("Pagando","Tirando el dado")));
    return (SalidasCarcel.values()[opcion]);
  }

  Respuestas comprar() {
    String titulo = "¿Desea comprar esta casilla?" + juegoModel.getCasillaActual().toString();
    ArrayList<String> opciones = new ArrayList<>(Arrays.asList("SI","NO"));
    int opcion = menu(titulo, opciones);
    return (Respuestas.values()[opcion]);
  }

  void gestionar() {
    String titulo = "Que desea hacer:";
    ArrayList<String> opciones = new ArrayList<>(Arrays.asList("Vender", "Hipotecar", "Cancelar hipoteca", "Contruir casa", "Construir hotel", "Terminar"));
    int opcion = menu (titulo, opciones);
    iGestion = opcion;
    String tab = "  ";
    iPropiedad = leeEntero(juegoModel.getJugadorActual().getPropiedades().size(),
                          "\n"+tab+"Elige un número de propiedad: ",
                          tab+"Valor erróneo");
  }
  
  public int getGestion(){
      return iGestion;
  }
  
  public int getPropiedad(){
      return iPropiedad;
  }
    

  void mostrarSiguienteOperacion(OperacionesJuego operacion) {
      System.out.println(operacion);
  }


  void mostrarEventos() {
      Diario diary = Diario.getInstance();
      for(int i = 0; diary.eventosPendientes(); i++){
          diary.leerEvento();
      }
  }
  
  public void setCivitasJuego(CivitasJuego civitas){ 
        juegoModel=civitas;
        this.actualizarVista();
  }
  
  void actualizarVista(){
      juegoModel.getJugadorActual().toString();
      juegoModel.getCasillaActual().toString();
  
  } 
}
