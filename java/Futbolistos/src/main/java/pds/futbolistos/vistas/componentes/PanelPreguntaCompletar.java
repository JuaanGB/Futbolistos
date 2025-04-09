package pds.futbolistos.vistas.componentes;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import pds.futbolistos.controlador.Controlador;
import pds.futbolistos.modelado.Pregunta;
import pds.futbolistos.modelado.PreguntaCompletar;
import pds.futbolistos.modelado.PreguntaTest;
import pds.futbolistos.vistas.VentanaCurso;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;

public class PanelPreguntaCompletar extends PanelPregunta {

	private JLabel lblCadenaOculta;
	private JButton botonValidar;
	private JTextField entradaTexto;
	private PreguntaCompletar pc;

	public PanelPreguntaCompletar(Pregunta p) {
		super(p);
		anadirAcciones();
		pc = (PreguntaCompletar) p;
	}

	@Override
	protected void inicializarComponentes() {
		super.inicializarComponentes();

		this.panelRespuestas.setLayout(new GridLayout(2, 1));

		this.lblCadenaOculta = FactoriaComponentes.crearLabel("<cadena_oculta>");
		lblCadenaOculta.setFont(new Font("Arial", Font.PLAIN, 20));
		this.panelRespuestas.add(lblCadenaOculta);

		JPanel panelCampoYBoton = new JPanel();
		panelCampoYBoton.setLayout(new FlowLayout());
		panelCampoYBoton.setBackground(new Color(30, 30, 30));

		this.entradaTexto = FactoriaComponentes.crearTextField();
		this.entradaTexto.setPreferredSize(new Dimension(200, 40));
		this.entradaTexto.requestFocus();
		panelCampoYBoton.add(entradaTexto);

		this.botonValidar = FactoriaComponentes.crearBoton("OK");
		this.botonValidar.setPreferredSize(new Dimension(80, 40));
		panelCampoYBoton.add(botonValidar);

		this.panelRespuestas.add(panelCampoYBoton);
	}
	
	@Override
	protected void personalizarDisplay(Pregunta p) {
		super.personalizarDisplay(p);
		this.lblCadenaOculta.setText(((PreguntaCompletar) p).getCadenaOculta());
	}

	@Override
	protected void gestionarPreguntaRespondida(boolean respondida) {
		if (!respondida) {
			JOptionPane.showMessageDialog(this,
					"¡Tiempo agotado! La respuesta correcta era:\n" + pc.getRespuestaCorrecta());
		}

	}
	
	private void anadirAcciones() {
		botonValidar.addActionListener(e -> {
            detenerTemporizador(true);
            if (Controlador.getInstancia().validarRespuesta(pc, entradaTexto.getText())) {
                JOptionPane.showMessageDialog(this, "Respuesta correcta", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Respuesta incorrecta.\nLa respuesta correcta era:\n" + pc.getRespuestaCorrecta(),
                        "Fallo.", JOptionPane.ERROR_MESSAGE);
            }
            this.manejarTiempoTerminado(true);
        });
	}

}