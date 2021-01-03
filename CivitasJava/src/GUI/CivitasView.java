/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import civitas.CivitasJuego;
import civitas.Casilla;
import civitas.OperacionesJuego;
import javax.swing.JOptionPane;
import civitas.SalidasCarcel;

/**
 *
 * @author luis
 */
public class CivitasView extends javax.swing.JFrame {

    CivitasJuego juego;
    JugadorPanel jugadorPanel;
    CasillaPanel casillaPanel;
    GestionarDialog gestionarD;
   
    
    /**
     * Creates new form CivitasView
     */
    public CivitasView() {
        initComponents();
        jugadorPanel = new JugadorPanel();
        this.contenedorVistaJugador.add(jugadorPanel);
        
        repaint();
        revalidate();
    }
    
    public void actualizarVista(){
        jugadorPanel.setJugador(juego.getJugadorActual());
        rellenaCasilla(juego.getCasillaActual());
        mostrarSiguienteOperacion(juego.siguientePaso());
        this.jTextArea1.setVisible(false);
        this.jLabel3.setVisible(false);
        if(juego.finalDelJuego()){
            this.jTextArea1.setVisible(true);
            this.jLabel3.setVisible(true);
            System.out.println("Final");
            String result = " ";
            for(int i = 0; i < juego.ranking().size(); i++)
                result = result + juego.ranking().get(i).getNombre() + "\n";
            
            this.jTextArea1.setText(result);  
        }
        repaint();
        revalidate();
    }
    
    

    public void setCivitasJuego(CivitasJuego juego){
        this.juego = juego;
        setVisible(true);
    }
    
    private void rellenaCasilla(Casilla casilla) {
        CasillaActual.removeAll();
        CasillaPanel vistaCasilla = new CasillaPanel();
        vistaCasilla.setCasilla(casilla);
        CasillaActual.add(vistaCasilla);
        vistaCasilla.setVisible(true);
      
        repaint();
        revalidate();
    }
    
    private void mostrarSiguienteOperacion(OperacionesJuego operacion){
        String texto = operacion.toString();
        this.jTextField1.setText(texto);
        setVisible(true);
        repaint();
        revalidate();
    }
    
    public void mostrarEventos(){
        DiarioDialog diarioD = new DiarioDialog(this,true);
        diarioD.repaint();
        diarioD.revalidate();
    }
    
    public Respuestas comprar(){
        int opcion = JOptionPane.showConfirmDialog(null,  "¿Quieres comprar la calle actual?", "Compra", JOptionPane.YES_NO_OPTION);
        return (Respuestas.values()[opcion]);
    }
    
    public void gestionar(){
        gestionarD = new GestionarDialog(this,true);
        gestionarD.gestionar(this.juego.getJugadorActual());
        gestionarD.pack();
        gestionarD.repaint();
        gestionarD.revalidate();
        gestionarD.setVisible(true);
    }
    
     public int getGestion(){
        return gestionarD.getGestion();
    }
    
    public int getPropiedad(){
        return gestionarD.getPropiedad();
    }

    public SalidasCarcel salirCarcel(){
        String[] opciones = {"Pagando","Tirando el dado"};
        int respuesta = JOptionPane.showOptionDialog(null, "¿Cómo quieres salir de la cárcel?", "Salir de la Cárcel", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        return (SalidasCarcel.values()[respuesta]);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        contenedorVistaJugador = new javax.swing.JPanel();
        CasillaActual = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Civitas");
        jLabel1.setEnabled(false);

        contenedorVistaJugador.setEnabled(false);
        contenedorVistaJugador.setLayout(new javax.swing.BoxLayout(contenedorVistaJugador, javax.swing.BoxLayout.LINE_AXIS));

        jLabel2.setText("SiguienteOperacion");

        jTextField1.setText("jTextField1");

        jLabel3.setText("Ranking");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 437, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CasillaActual, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(contenedorVistaJugador, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1))
                    .addComponent(contenedorVistaJugador, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CasillaActual, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CivitasView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CivitasView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CivitasView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CivitasView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CivitasView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel CasillaActual;
    private javax.swing.JPanel contenedorVistaJugador;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
