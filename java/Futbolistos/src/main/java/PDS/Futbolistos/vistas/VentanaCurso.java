package PDS.Futbolistos.vistas;

import PDS.Futbolistos.modelado.Pregunta;
import PDS.Futbolistos.modelado.SesionCurso;
import PDS.Futbolistos.vistas.componentes.PanelPregunta;
import javax.swing.*;
import java.awt.*;

public class VentanaCurso extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblTitulo;
    private PanelPregunta panelPregunta;
    private JLabel lblPistasRestantes;
    private JLabel lblProgreso;
    private JButton btnGuardar;
    private SesionCurso sesionCurso;

    public VentanaCurso(SesionCurso sesionCurso) {

        this.sesionCurso = sesionCurso;
        
        // Establecer estética oscura similar a VentanaPrincipal
        getContentPane().setBackground(new Color(30, 30, 30));
        setTitle("Curso de Preguntas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(345, 500);
        setLocationRelativeTo(null);
        
        getContentPane().setLayout(new BorderLayout());
        
        // Título con número de pregunta actual, con fuente y color acorde
        lblTitulo = new JLabel("Pregunta " + (sesionCurso.getNumeroPreguntasRespondidas() + 1), SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        getContentPane().add(lblTitulo, BorderLayout.NORTH);

        // Panel de la pregunta actual, se invoca el panel del objeto Pregunta
        panelPregunta = (PanelPregunta) sesionCurso.getPreguntaActual().getPanel();
        panelPregunta.setVentanaCurso(this);
        panelPregunta.setBackground(new Color(30, 30, 30));
        getContentPane().add(panelPregunta, BorderLayout.CENTER);

        // Panel inferior con progreso y pistas restantes
        JPanel panelInferior = new JPanel();
        panelInferior.setBackground(new Color(30, 30, 30));
        GridBagLayout gbl_panelInferior = new GridBagLayout();
        // Se aumenta el ancho de la columna central para que el botón "Guardar estado" luzca más grande
        gbl_panelInferior.columnWidths = new int[] { 108, 140, 19, 0 };
        gbl_panelInferior.rowHeights = new int[] { 27, 0 };
        gbl_panelInferior.columnWeights = new double[] { 1.0, 0.0, 1.0, Double.MIN_VALUE };
        gbl_panelInferior.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
        panelInferior.setLayout(gbl_panelInferior);

        // Se mantiene la etiqueta de pistas restantes (el botón de pista se encuentra en otro lugar, debajo de las respuestas)
        lblPistasRestantes = new JLabel("Pistas restantes: " + sesionCurso.getPistasRestantes());
        lblPistasRestantes.setForeground(Color.WHITE);
        GridBagConstraints gbc_lblPistasRestantes = new GridBagConstraints();
        gbc_lblPistasRestantes.anchor = GridBagConstraints.WEST;
        gbc_lblPistasRestantes.insets = new Insets(0, 0, 0, 5);
        gbc_lblPistasRestantes.gridx = 0;
        gbc_lblPistasRestantes.gridy = 0;
        panelInferior.add(lblPistasRestantes, gbc_lblPistasRestantes);

        // Botón "Guardar estado" (más grande que antes)
        btnGuardar = new JButton("Guardar estado");
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 14)); // fuente incrementada
        btnGuardar.setBackground(new Color(0, 204, 102));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setBorder(null);
        btnGuardar.setPreferredSize(new Dimension(140, 40)); // tamaño preferido mayor
        GridBagConstraints gbc_btnGuardar = new GridBagConstraints();
        gbc_btnGuardar.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnGuardar.anchor = GridBagConstraints.NORTH;
        gbc_btnGuardar.insets = new Insets(0, 0, 0, 5);
        gbc_btnGuardar.gridx = 1;
        gbc_btnGuardar.gridy = 0;
        panelInferior.add(btnGuardar, gbc_btnGuardar);

        // Etiqueta de progreso
        lblProgreso = new JLabel((sesionCurso.getNumeroPreguntasRespondidas() + 1) + "/" + sesionCurso.getNumTotalPreguntas(), SwingConstants.RIGHT);
        lblProgreso.setForeground(Color.WHITE);
        GridBagConstraints gbc_lblProgreso = new GridBagConstraints();
        gbc_lblProgreso.anchor = GridBagConstraints.EAST;
        gbc_lblProgreso.gridx = 2;
        gbc_lblProgreso.gridy = 0;
        panelInferior.add(lblProgreso, gbc_lblProgreso);
        getContentPane().add(panelInferior, BorderLayout.SOUTH);

        setVisible(true);
    }

    /**
     * Actualiza la vista cuando se avanza a la siguiente pregunta.
     */
    public void actualizarPregunta(Pregunta nuevaPregunta) {
        int preguntaActual = sesionCurso.getNumeroPreguntasRespondidas() + 1;
        // Actualizar título y progreso
        lblTitulo.setText("Pregunta " + preguntaActual);
        lblProgreso.setText(preguntaActual + " / " + sesionCurso.getNumTotalPreguntas());
        lblPistasRestantes.setText("Pistas restantes: " + sesionCurso.getPistasRestantes());

        // Cambiar el panel de la pregunta y actualizar la estética
        getContentPane().remove(panelPregunta);
        panelPregunta = (PanelPregunta) sesionCurso.getPreguntaActual().getPanel();
        panelPregunta.setVentanaCurso(this);
        panelPregunta.setBackground(new Color(30, 30, 30));
        getContentPane().add(panelPregunta, BorderLayout.CENTER);

        // Refrescar la ventana
        revalidate();
        repaint();
    }
    
    public void actualizarPistasRestantes() {
    	lblPistasRestantes.setText("Pistas restantes: " + sesionCurso.getPistasRestantes());
    }
}
