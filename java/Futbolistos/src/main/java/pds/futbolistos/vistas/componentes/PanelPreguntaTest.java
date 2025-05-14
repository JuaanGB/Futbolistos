package pds.futbolistos.vistas.componentes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import pds.futbolistos.controlador.Controlador;
import pds.futbolistos.modelado.PreguntaTest;

public class PanelPreguntaTest extends PanelPregunta {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<JButton> botones;
	private PreguntaTest pt;

	public PanelPreguntaTest(PreguntaTest p) {
		super(p);
		this.pt = p;
		inicializarComponentes();
		añadirAcciones();
	}

	private void añadirAcciones() {
		for (JButton boton : botones) {
			boton.addActionListener(e -> {
				detenerTemporizador(true);
				if (Controlador.getInstancia().validarRespuesta(pt, boton.getText())) {
					JOptionPane.showMessageDialog(this, "Respuesta correcta", "Éxito", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(this,
							"Respuesta incorrecta.\nLa respuesta correcta era:\n" + pt.getRespuestaCorrecta(), "Fallo.",
							JOptionPane.ERROR_MESSAGE);
				}
				this.manejarTiempoTerminado(true);
			});
		}
	}

	private void inicializarComponentes() {

		// Configuración del panel de respuestas
		panelRespuestas.setLayout(new GridLayout(2, 2, 10, 10));
		panelRespuestas.setBackground(new Color(30, 30, 30));

		botones = new ArrayList<>();
		for (int i = 0; i < pt.getNumRespuestas(); i++) {
			JButton boton = FactoriaComponentes.crearBoton(pt.getRespuesta(i));
			boton.setPreferredSize(new Dimension(200,50));
			panelRespuestas.add(boton);
			botones.add(boton);
		}

	}

	@Override
	protected void gestionarPreguntaRespondida(boolean respondida) {
		if (!respondida) {
			JOptionPane.showMessageDialog(this,
					"¡Tiempo agotado! La respuesta correcta era:\n" + pt.getRespuestaCorrecta());
		}
	}

}