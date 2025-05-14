package pds.futbolistos.vistas.componentes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import pds.futbolistos.controlador.Controlador;
import pds.futbolistos.modelado.PreguntaFlashcard;

public class PanelPreguntaFlashcard extends PanelPregunta {

	private JTextArea txtFlashcard;
	private JButton botonVerReverso;
	private JButton botonContinuar;
	private PreguntaFlashcard p;
	private boolean mostrandoReverso = false;

	public PanelPreguntaFlashcard(PreguntaFlashcard p) {
		super(p);
		this.p = p;
		inicializarComponentes();
		anadirAcciones();
	}

	private void inicializarComponentes() {

		this.panelRespuestas.setLayout(new GridLayout(2, 1));

		this.txtFlashcard = FactoriaComponentes.crearTextArea(3, 20);
		txtFlashcard.setFont(new Font("Arial", Font.PLAIN, 20));
		txtFlashcard.setText(this.p.getAnverso());
		txtFlashcard.setEditable(false);
		this.panelRespuestas.add(txtFlashcard);

		JPanel panelBotones = new JPanel();
		panelBotones.setLayout(new FlowLayout());
		panelBotones.setBackground(new Color(30, 30, 30));

		this.botonVerReverso = FactoriaComponentes.crearBoton("Ver Reverso");
		this.botonVerReverso.setPreferredSize(new Dimension(150, 40));
		panelBotones.add(botonVerReverso);

		this.botonContinuar = FactoriaComponentes.crearBoton("Continuar");
		this.botonContinuar.setPreferredSize(new Dimension(150, 40));
		this.botonContinuar.setVisible(false);
		panelBotones.add(botonContinuar);

		this.panelRespuestas.add(panelBotones);
	}

	@Override
	protected void gestionarPreguntaRespondida(boolean respondida) {
	}

	private void anadirAcciones() {

		botonVerReverso.addActionListener(e -> {
			if (!mostrandoReverso) {
				txtFlashcard.setText(p.getReverso());
				botonVerReverso.setText("Ver Anverso");
				botonContinuar.setVisible(true);
				mostrandoReverso = true;
			} else {
				txtFlashcard.setText(p.getAnverso());
				botonVerReverso.setText("Ver Reverso");
				botonContinuar.setVisible(false);
				mostrandoReverso = false;
			}
		});

		botonContinuar.addActionListener(e -> {
			detenerTemporizador(true);
			Controlador.getInstancia().validarRespuesta(p);
			JOptionPane.showMessageDialog(this, "¡Continuamos con la siguiente pregunta!");
			this.manejarTiempoTerminado(true);
		});
	}
}
