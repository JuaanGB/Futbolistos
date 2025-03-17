package PDS.Futbolistos.vistas.componentes;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import PDS.Futbolistos.controlador.Controlador;
import PDS.Futbolistos.modelado.Pregunta;
import PDS.Futbolistos.modelado.PreguntaTest;
import PDS.Futbolistos.vistas.VentanaPrincipal;

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
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.Box;

public class PanelPreguntaTest extends PanelPregunta {

	private static int NUM_RESPUESTAS = 4;

	private JButton btnRespuesta1, btnRespuesta2, btnRespuesta3, btnRespuesta4;
	private List<JButton> botones;

	public PanelPreguntaTest(Pregunta p) {
		super(p);
		añadirAcciones(p);
	}

	private void añadirAcciones(Pregunta p) {

		for (JButton boton : botones) {
			boton.addActionListener(e -> {
				detenerTemporizador(true);
				if (Controlador.getInstancia().validarRespuesta(p, boton.getText())) {
					JOptionPane.showMessageDialog(this, "Respuesta correcta", "Éxito", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(this,
							"Respuesta incorrecta.\nLa respuesta correcta era:\n" + p.getRespuestaCorrecta(), "Fallo.",
							JOptionPane.ERROR_MESSAGE);
				}
				manejarTiempoTerminado(true);
			});
		}
	}

	@Override
	protected void personalizarDisplay(Pregunta p) {

		// Personalización global (enunciado, foto, pista)
		super.personalizarDisplay(p);
		PreguntaTest pt = (PreguntaTest) p;
		// Personalización del panel de respuestas
		for (int i = 0; i < NUM_RESPUESTAS; i++) {
			botones.get(i).setText(pt.getRespuesta(i));
		}
	}

	@Override
	protected void inicializarComponentes() {

		// Inicializas lo global a cada pregunta
		super.inicializarComponentes();

		// Personalizamos el panel de respuestas
		btnRespuesta1 = new JButton("Respuesta 1");
		btnRespuesta2 = new JButton("Respuesta 2");
		btnRespuesta3 = new JButton("Respuesta 3");
		btnRespuesta4 = new JButton("Respuesta 4");

		panelRespuestas.add(btnRespuesta1);
		panelRespuestas.add(btnRespuesta2);
		panelRespuestas.add(btnRespuesta3);
		panelRespuestas.add(btnRespuesta4);

		botones = List.of(btnRespuesta1, btnRespuesta2, btnRespuesta3, btnRespuesta4);
	}

	@Override
	protected void manejarTiempoTerminado(boolean respondida) {
        if (!respondida) {
            JOptionPane.showMessageDialog(this, "¡Tiempo agotado! La respuesta correcta era:\n");
        }
        boolean hayPregunta = Controlador.getInstancia().pasarASiguientePregunta();
        if (!hayPregunta) {
        	JOptionPane.showMessageDialog(this, "¡Curso completado!");
        	VentanaPrincipal vp = new VentanaPrincipal();
        	vp.setVisible(true);
        }
    }
}