package PDS.Futbolistos.vistas.componentes;

import javax.swing.*;
import java.awt.*;
import PDS.Futbolistos.modelado.Pregunta;
import PDS.Futbolistos.vistas.VentanaCurso;

public abstract class PanelPregunta extends JPanel {

	// Elementos comunes a todas las preguntas
	private JTextArea txtrEnunciado;
	private JLabel lblFoto;
	protected JPanel panelRespuestas; // Para poder añadir elementos en los tipos concretos de pregunta
	private JButton btnPista;
	private JLabel lblTiempoRestante;
	private Timer timer;
	
	// Ventana en la que está contenido el panel para actualizar dependencias
	private VentanaCurso ventanaCurso;

	// Actualización del tiempo
	private int tiempoRestante;

	public PanelPregunta(Pregunta p) {

		this.tiempoRestante = p.getSegundos();
		inicializarComponentes();
		personalizarDisplay(p);
		empezarTemporizador();

	}
	
	public void setVentanaCurso(VentanaCurso ventanaCurso) {
		this.ventanaCurso = ventanaCurso;
	}
	
	public VentanaCurso getVentanaCurso() {
		return ventanaCurso;
	}

	private void empezarTemporizador() {
		timer = new Timer(1000, e -> actualizarTiempo());
		timer.start();
	}

	protected void personalizarDisplay(Pregunta p) {
		txtrEnunciado.setText(p.getEnunciado());
		// Se puede implementar la lógica para el botón de pista, en base a si la pregunta tiene pista disponible.
		lblTiempoRestante.setText("Tiempo: " + tiempoRestante + "s");
	}

	protected void inicializarComponentes() {

		// Se utiliza un fondo oscuro acorde con la estética de VentanaPrincipal
		setBackground(new Color(30, 30, 30));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		txtrEnunciado = new JTextArea();
		txtrEnunciado.setRows(4);
		txtrEnunciado.setWrapStyleWord(true);
		txtrEnunciado.setLineWrap(true);
		txtrEnunciado.setEditable(false);
		txtrEnunciado.setText("Enunciado de la pregunta que puede ser tan grande como se deseé. Es un JTextArea para que se ponga en varias líneas.");
		txtrEnunciado.setBackground(new Color(30, 30, 30));
		txtrEnunciado.setForeground(Color.WHITE);
		txtrEnunciado.setFont(new Font("Arial", Font.PLAIN, 14));
		// Alineación centrada dentro del JTextArea
		txtrEnunciado.setAlignmentX(Component.CENTER_ALIGNMENT);
		txtrEnunciado.setAlignmentY(Component.CENTER_ALIGNMENT);
		// Eliminar borde por defecto
		txtrEnunciado.setBorder(null);

		JScrollPane scrollPane = new JScrollPane(txtrEnunciado);
		scrollPane.setMaximumSize(new Dimension(400, 100));
		scrollPane.getViewport().setBackground(new Color(30, 30, 30));
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		add(scrollPane);

		add(Box.createVerticalGlue());

		// Se carga la imagen escalada (se utiliza el mismo recurso que en VentanaPrincipal)
		lblFoto = new JLabel("");
		lblFoto.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblFoto.setIcon(new ImageIcon(PanelPregunta.class.getResource("/PDS/Futbolistos/imagenes/flag-football.png")));
		add(lblFoto);

		add(Box.createVerticalGlue());

		panelRespuestas = new JPanel();
		panelRespuestas.setBackground(new Color(30, 30, 30));
		add(panelRespuestas);
		panelRespuestas.setLayout(new GridLayout(2, 2, 4, 4));

		add(Box.createVerticalGlue());

		// Se aumenta el tamaño del botón "Pista"
		btnPista = new JButton("Pista");
		btnPista.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnPista.setBackground(new Color(0, 204, 102));
		btnPista.setForeground(Color.WHITE);
		btnPista.setFocusPainted(false);
		btnPista.setBorder(null);
		btnPista.setFont(new Font("Arial", Font.BOLD, 16));
		btnPista.setPreferredSize(new Dimension(200, 50));
		btnPista.setMaximumSize(new Dimension(200, 50));
		add(btnPista);

		add(Box.createVerticalGlue());

		// Configuración visual del temporizador
		lblTiempoRestante = new JLabel("Tiempo: " + tiempoRestante + "s");
		lblTiempoRestante.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblTiempoRestante.setHorizontalAlignment(SwingConstants.CENTER);
		lblTiempoRestante.setFont(new Font("Arial", Font.BOLD, 16));
		lblTiempoRestante.setForeground(Color.WHITE);
		add(lblTiempoRestante);
	}

	private void actualizarTiempo() {
		if (tiempoRestante > 0) {
			tiempoRestante--;
			lblTiempoRestante.setText("Tiempo: " + tiempoRestante + "s");
		} else {
			detenerTemporizador(false);
			manejarTiempoTerminado(false); // Se acabó el tiempo sin respuesta
		}
	}

	/**
	 * Detiene el temporizador.
	 * 
	 * @param respondida true si el usuario respondió antes de tiempo, false si el tiempo se agotó
	 */
	public void detenerTemporizador(boolean respondida) {
		timer.stop();
	}

	// Método que cada tipo de pregunta implementará para manejar cuando se termine el tiempo.
	protected abstract void manejarTiempoTerminado(boolean respondida);
}
