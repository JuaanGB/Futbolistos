package PDS.Futbolistos.vistas;

import PDS.Futbolistos.controlador.Controlador;
import PDS.Futbolistos.modelado.SesionCurso;
import javax.swing.*;
import java.awt.*;

public class VentanaCurso extends JFrame {
    private JLabel lblTitulo;
    private JPanel panelPregunta;
    private JLabel lblPistasRestantes;
    private JLabel lblProgreso;
    private JButton btnGuardar;
    private SesionCurso sesionCurso;

    public VentanaCurso(SesionCurso sesionCurso) {
        this.sesionCurso = sesionCurso;

        setTitle("Curso de Preguntas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLayout(new BorderLayout());

        // Título con número de pregunta actual
        lblTitulo = new JLabel("Pregunta " + (sesionCurso.getNumeroPreguntasRespondidas() + 1), SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        add(lblTitulo, BorderLayout.NORTH);

        // Panel de la pregunta actual
        panelPregunta = sesionCurso.getPreguntaActual().getPanel();
        add(panelPregunta, BorderLayout.CENTER);

        // Panel inferior con progreso y pistas restantes
        JPanel panelInferior = new JPanel(new BorderLayout());

        lblPistasRestantes = new JLabel("Pistas restantes: " + sesionCurso.getPistasRestantes());
        panelInferior.add(lblPistasRestantes, BorderLayout.WEST);

        lblProgreso = new JLabel((sesionCurso.getNumeroPreguntasRespondidas() + 1) + "/" + sesionCurso.getNumTotalPreguntas(), SwingConstants.RIGHT);
        panelInferior.add(lblProgreso, BorderLayout.EAST);

        btnGuardar = new JButton("Guardar estado");
        // btnGuardar.addActionListener(e -> Controlador.getInstancia().guardarSesion());
        panelInferior.add(btnGuardar, BorderLayout.CENTER);

        add(panelInferior, BorderLayout.SOUTH);
        setVisible(true);
    }

    /**
     * Actualiza la vista cuando se avanza a la siguiente pregunta.
     */
    public void actualizarVista() {
        int preguntaActual = sesionCurso.getNumeroPreguntasRespondidas() + 1;

        // Actualizar título y progreso
        lblTitulo.setText("Pregunta " + preguntaActual);
        lblProgreso.setText(preguntaActual + " / " + sesionCurso.getNumTotalPreguntas());
        lblPistasRestantes.setText("Pistas restantes: " + sesionCurso.getPistasRestantes());

        // Cambiar el panel de la pregunta
        remove(panelPregunta);
        panelPregunta = sesionCurso.getPreguntaActual().getPanel();
        add(panelPregunta, BorderLayout.CENTER);

        // Refrescar la ventana
        revalidate();
        repaint();
    }
}
