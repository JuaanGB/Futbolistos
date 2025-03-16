package PDS.Futbolistos.vistas.componentes;

import javax.swing.*;

import PDS.Futbolistos.modelado.Pregunta;

import java.awt.*;

public abstract class PanelPregunta extends JPanel {

	// Elementos comunes a todas las preguntas
	private JTextArea txtrEnunciado;
	private JLabel lblFoto;
	protected JPanel panelRespuestas; // Para poder añadir elementos en los tipos concretos de pregunta
	private JButton btnPista;
	private JLabel lblTiempoRestante;
	private Timer timer;

	// Actualización del tiempo
	private int tiempoRestante;

	public PanelPregunta(Pregunta p) {

		this.tiempoRestante = p.getSegundos();
		inicializarComponentes();
		personalizarDisplay(p);
		empezarTemporizador();

	}

	private void empezarTemporizador() {

		timer = new Timer(1000, e -> actualizarTiempo());
		timer.start();
	}

	protected void personalizarDisplay(Pregunta p) {
		txtrEnunciado.setText(p.getEnunciado());
		/*
		 * if (p.hasPista()) {
		 *     btnPista.addActionListener( e -> ShowMessageDialogue...);
		 *     btnPista.setEnabled(false);
		 *     Controlador.getInstancia().disminuirNumeroPistasDisponibles();
		 * } else btnPista.setEnabled(false);
		 */
		lblTiempoRestante.setText("Tiempo: " + tiempoRestante + "s");
	}

	protected void inicializarComponentes() {

		setBackground(new Color(255, 255, 255));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		txtrEnunciado = new JTextArea();
		txtrEnunciado.setRows(4);
		txtrEnunciado.setWrapStyleWord(true);
		txtrEnunciado.setLineWrap(true);
		txtrEnunciado.setEditable(false);
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

		add(Box.createVerticalGlue());

		btnPista = new JButton("Pista");
		btnPista.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(btnPista);

		add(Box.createVerticalGlue());

		// Configuración visual del temporizador
		lblTiempoRestante = new JLabel("Tiempo: " + tiempoRestante + "s");
		lblTiempoRestante.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblTiempoRestante.setHorizontalAlignment(SwingConstants.CENTER);
		lblTiempoRestante.setFont(new Font("Arial", Font.BOLD, 16));
		add(lblTiempoRestante);

	}

	private void actualizarTiempo() {
		if (tiempoRestante > 0) {
			tiempoRestante--;
			lblTiempoRestante.setText("Tiempo: " + tiempoRestante + "s");
		} else {
			manejarTiempoTerminado(false); // Se acabó el tiempo sin respuesta
		}
	}

	/**
	 * Detiene el temporizador y maneja el resultado de la pregunta.
	 * 
	 * @param respondidaCorrectamente true si el usuario respondió antes de tiempo,
	 *                                false si el tiempo se agotó
	 */
	public void detenerTemporizador(boolean respondidaCorrectamente) {
		timer.stop();
	}

	// Método que cada tipo de pregunta implementará
	protected abstract void manejarTiempoTerminado(boolean respondida);
}
