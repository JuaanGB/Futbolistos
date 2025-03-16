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

    public VentanaCurso(SesionCurso sesionCurso) {
        setTitle("Curso de Preguntas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLayout(new BorderLayout());

        // Título
        lblTitulo = new JLabel("Pregunta " + (sesionCurso.getPreguntaActual() + 1), SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        add(lblTitulo, BorderLayout.NORTH);

        // Panel de pregunta
        panelPregunta = sesionCurso.getPreguntaActual().getPanel();
        add(panelPregunta, BorderLayout.CENTER);

        // Panel inferior
        JPanel panelInferior = new JPanel(new BorderLayout());
        lblPistasRestantes = new JLabel("Pistas restantes: " + sesionCurso.getPistasRestantes());
        panelInferior.add(lblPistasRestantes, BorderLayout.WEST);

        lblProgreso = new JLabel((sesionCurso.getPreguntaActual() + 1) + " / " + sesionCurso.getTotalPreguntas(), SwingConstants.RIGHT);
        panelInferior.add(lblProgreso, BorderLayout.EAST);

        btnGuardar = new JButton("Guardar estado");
        btnGuardar.addActionListener(e -> Controlador.getInstancia().guardarSesion());
        panelInferior.add(btnGuardar, BorderLayout.CENTER);

        add(panelInferior, BorderLayout.SOUTH);
        setVisible(true);
    }

    public void actualizarVista() {
        lblTitulo.setText("Pregunta " + (Controlador.getInstancia().getSesion().getPreguntaActual() + 1));
        lblProgreso.setText((Controlador.getInstancia().getSesion().getPreguntaActual() + 1) + " / " + Controlador.getInstancia().getSesion().getTotalPreguntas());
        lblPistasRestantes.setText("Pistas restantes: " + Controlador.getInstancia().getSesion().getPistasRestantes());

        remove(panelPregunta);
        panelPregunta = Controlador.getInstancia().getSesion().getPreguntaActual().getPanel();
        add(panelPregunta, BorderLayout.CENTER);

        revalidate();
        repaint();
    }
}
