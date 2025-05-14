package pds.futbolistos.vistas.componentes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import pds.futbolistos.controlador.Controlador;
import pds.futbolistos.modelado.PreguntaCompletar;

public class PanelPreguntaCompletar extends PanelPregunta {

	private JLabel lblCadenaOculta;
	private JButton botonValidar;
	private JTextField entradaTexto;
	private PreguntaCompletar pc;

	public PanelPreguntaCompletar(PreguntaCompletar p) {
		super(p);
		pc = p;
		inicializarComponentes();
		anadirAcciones();
	}

	private void inicializarComponentes() {

		this.panelRespuestas.setLayout(new GridLayout(2, 1));

		this.lblCadenaOculta = FactoriaComponentes.crearLabel(pc.getCadenaOculta());
		lblCadenaOculta.setFont(new Font("Arial", Font.PLAIN, 20));
		this.panelRespuestas.add(lblCadenaOculta);

		JPanel panelCampoYBoton = new JPanel();
		panelCampoYBoton.setLayout(new FlowLayout());
		panelCampoYBoton.setBackground(new Color(30, 30, 30));

		this.entradaTexto = FactoriaComponentes.crearTextField();
		this.entradaTexto.setPreferredSize(new Dimension(200, 40));
		SwingUtilities.invokeLater( () -> this.entradaTexto.requestFocusInWindow());
		panelCampoYBoton.add(entradaTexto);

		this.botonValidar = FactoriaComponentes.crearBoton("OK");
		this.botonValidar.setPreferredSize(new Dimension(80, 40));
		panelCampoYBoton.add(botonValidar);

		this.panelRespuestas.add(panelCampoYBoton);
	}
	
	private void anadirAcciones() {
		ActionListener accion = e -> {
            detenerTemporizador(true);
            if (Controlador.getInstancia().validarRespuesta(pc, entradaTexto.getText())) {
                JOptionPane.showMessageDialog(this, "Respuesta correcta", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Respuesta incorrecta.\nLa respuesta correcta era:\n" + pc.getRespuestaCorrecta(),
                        "Fallo.", JOptionPane.ERROR_MESSAGE);
            }
            this.manejarTiempoTerminado(true);
        };
		
		botonValidar.addActionListener(accion);
		entradaTexto.addActionListener(accion); // Validar al pulsar enter en lugar del clic en el botón
	}
	
	@Override
	protected void gestionarPreguntaRespondida(boolean respondida) {
		if (!respondida) {
			JOptionPane.showMessageDialog(this,
					"¡Tiempo agotado! La respuesta correcta era:\n" + pc.getRespuestaCorrecta());
		}

	}

}