package PDS.Futbolistos.vistas.componentes;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import PDS.Futbolistos.controlador.Controlador;
import PDS.Futbolistos.modelado.Pregunta;
import PDS.Futbolistos.modelado.PreguntaTest;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.GridLayout;
import java.util.List;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.Box;

public class PanelPreguntaTest extends JPanel {

	private static int NUM_RESPUESTAS = 4;

	private JLabel lblNumPregunta;
	private JTextArea txtrEnunciado;
	private JLabel lblFoto;
	private JPanel panelRespuestas;
	private JButton btnRespuesta1, btnRespuesta2, btnRespuesta3, btnRespuesta4, btnPista;
	private JLabel lblTiempoRestante;
	private List<JButton> botones;

	public PanelPreguntaTest(Pregunta p) {
		inicializarComponentes();
		personalizarDisplay(p);
		añadirAcciones(p);
	}

	private void añadirAcciones(Pregunta p) {
		PreguntaTest pt = (PreguntaTest) p;
		btnPista.addActionListener(e -> JOptionPane.showMessageDialog(this, pt.getPista()));
		for (JButton boton : botones) {
			boton.addActionListener(e -> {
				if (Controlador.getInstancia().validarRespuesta(p, boton.getText())) {
					JOptionPane.showMessageDialog(this, "Respuesta correcta", "Éxito", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(this,
							"Respuesta incorrecta.\nLa respuesta correcta era:\n" + p.getRespuestaCorrecta(), "Fallo.",
							JOptionPane.ERROR_MESSAGE);
				}
				Controlador.getInstancia().actualizarPregunta();
			});
		}
	}

	private void personalizarDisplay(Pregunta p) {
		PreguntaTest pt = (PreguntaTest) p;

		// Personalización del enunciado
		txtrEnunciado.setText(pt.getEnunciado());

		// Personalización de la foto: TODO

		// Personalización de los botones de respuesta
		for (int i = 0; i < NUM_RESPUESTAS; i++) {
			botones.get(i).setText(pt.getRespuesta(i));
		}

		// Personalización del tiempo restante
		lblTiempoRestante.setText("00:" + pt.getSegundos());

	}

	private void inicializarComponentes() {

		setBackground(new Color(255, 255, 255));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		lblNumPregunta = new JLabel("PREGUNTA Nº");
		lblNumPregunta.setFont(new Font("Dialog", Font.BOLD, 18));
		lblNumPregunta.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNumPregunta.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblNumPregunta);

		add(Box.createVerticalGlue());

		txtrEnunciado = new JTextArea();
		txtrEnunciado.setRows(4);
		txtrEnunciado.setWrapStyleWord(true);
		txtrEnunciado.setLineWrap(true);
		txtrEnunciado.setText(
				"Enunciado de la pregunta que puede ser tan grande como se deseé. Es un JTextArea para que se ponga en varias líneas.");

		JScrollPane scrollPane = new JScrollPane(txtrEnunciado);
		scrollPane.setMaximumSize(new Dimension(400, 100));
		add(scrollPane);

		add(Box.createVerticalGlue());

		lblFoto = new JLabel("");
		lblFoto.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblFoto.setIcon(
				new ImageIcon(PanelPreguntaTest.class.getResource("/PDS/Futbolistos/imagenes/flag-football.png")));
		add(lblFoto);

		add(Box.createVerticalGlue());

		panelRespuestas = new JPanel();
		add(panelRespuestas);
		panelRespuestas.setLayout(new GridLayout(2, 2, 4, 4));

		btnRespuesta1 = new JButton("Respuesta 1");
		btnRespuesta2 = new JButton("Respuesta 2");
		btnRespuesta3 = new JButton("Respuesta 3");
		btnRespuesta4 = new JButton("Respuesta 4");

		panelRespuestas.add(btnRespuesta1);
		panelRespuestas.add(btnRespuesta2);
		panelRespuestas.add(btnRespuesta3);
		panelRespuestas.add(btnRespuesta4);

		add(Box.createVerticalGlue());

		btnPista = new JButton("Pista");
		btnPista.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(btnPista);

		add(Box.createVerticalGlue());

		lblTiempoRestante = new JLabel("00:00");
		lblTiempoRestante.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblTiempoRestante.setHorizontalAlignment(SwingConstants.LEFT);
		add(lblTiempoRestante);

		botones = List.of(btnRespuesta1, btnRespuesta2, btnRespuesta3, btnRespuesta4);
	}
}