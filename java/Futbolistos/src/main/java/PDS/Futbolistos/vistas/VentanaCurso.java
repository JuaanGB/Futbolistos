package PDS.Futbolistos.vistas;

import PDS.Futbolistos.controlador.Controlador;
import PDS.Futbolistos.modelado.Pregunta;
import PDS.Futbolistos.modelado.PreguntaObserver;
import PDS.Futbolistos.modelado.SesionCurso;
import javax.swing.*;
import java.awt.*;

public class VentanaCurso extends JFrame implements PreguntaObserver {
	private JLabel lblTitulo;
	private JPanel panelPregunta;
	private JLabel lblPistasRestantes;
	private JLabel lblProgreso;
	private JButton btnGuardar;
	private SesionCurso sesionCurso;

	public VentanaCurso(SesionCurso sesionCurso) {
		
		Controlador.getInstancia().addPreguntaObserver(this);
		
		getContentPane().setBackground(new Color(255, 255, 255));
		this.sesionCurso = sesionCurso;

		setTitle("Curso de Preguntas");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(345, 500);
		getContentPane().setLayout(new BorderLayout());

		// Título con número de pregunta actual
		lblTitulo = new JLabel("Pregunta " + (sesionCurso.getNumeroPreguntasRespondidas() + 1), SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
		getContentPane().add(lblTitulo, BorderLayout.NORTH);

		// Panel de la pregunta actual
		panelPregunta = sesionCurso.getPreguntaActual().getPanel();
		getContentPane().add(panelPregunta, BorderLayout.CENTER);

		// Panel inferior con progreso y pistas restantes
		JPanel panelInferior = new JPanel();
		panelInferior.setBackground(new Color(255, 255, 255));
		GridBagLayout gbl_panelInferior = new GridBagLayout();
		gbl_panelInferior.columnWidths = new int[] { 108, 126, 19, 0 };
		gbl_panelInferior.rowHeights = new int[] { 27, 0 };
		gbl_panelInferior.columnWeights = new double[] { 1.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panelInferior.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panelInferior.setLayout(gbl_panelInferior);

		lblPistasRestantes = new JLabel("Pistas restantes: " + sesionCurso.getPistasRestantes());
		GridBagConstraints gbc_lblPistasRestantes = new GridBagConstraints();
		gbc_lblPistasRestantes.anchor = GridBagConstraints.WEST;
		gbc_lblPistasRestantes.insets = new Insets(0, 0, 0, 5);
		gbc_lblPistasRestantes.gridx = 0;
		gbc_lblPistasRestantes.gridy = 0;
		panelInferior.add(lblPistasRestantes, gbc_lblPistasRestantes);

		btnGuardar = new JButton("Guardar estado");
		// btnGuardar.addActionListener(e ->
		// Controlador.getInstancia().guardarSesion());
		GridBagConstraints gbc_btnGuardar = new GridBagConstraints();
		gbc_btnGuardar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnGuardar.anchor = GridBagConstraints.NORTH;
		gbc_btnGuardar.insets = new Insets(0, 0, 0, 5);
		gbc_btnGuardar.gridx = 1;
		gbc_btnGuardar.gridy = 0;
		panelInferior.add(btnGuardar, gbc_btnGuardar);

		getContentPane().add(panelInferior, BorderLayout.SOUTH);

		lblProgreso = new JLabel(
				(sesionCurso.getNumeroPreguntasRespondidas() + 1) + "/" + sesionCurso.getNumTotalPreguntas(),
				SwingConstants.RIGHT);
		GridBagConstraints gbc_lblProgreso = new GridBagConstraints();
		gbc_lblProgreso.anchor = GridBagConstraints.EAST;
		gbc_lblProgreso.gridx = 2;
		gbc_lblProgreso.gridy = 0;
		panelInferior.add(lblProgreso, gbc_lblProgreso);
		setVisible(true);
	}

	/**
	 * Actualiza la vista cuando se avanza a la siguiente pregunta.
	 */
	@Override
	public void actualizarPregunta(Pregunta nuevaPregunta) {
		
		int preguntaActual = sesionCurso.getNumeroPreguntasRespondidas() + 1;

		// Actualizar título y progreso
		lblTitulo.setText("Pregunta " + preguntaActual);
		lblProgreso.setText(preguntaActual + " / " + sesionCurso.getNumTotalPreguntas());
		lblPistasRestantes.setText("Pistas restantes: " + sesionCurso.getPistasRestantes());

		// Cambiar el panel de la pregunta
		remove(panelPregunta);
		panelPregunta = sesionCurso.getPreguntaActual().getPanel();
		getContentPane().add(panelPregunta, BorderLayout.CENTER);

		// Refrescar la ventana
		revalidate();
		repaint();
		
	}
	
}
