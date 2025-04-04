package pds.futbolistos.vistas.componentes;

import javax.swing.*;

import pds.futbolistos.controlador.Controlador;
import pds.futbolistos.modelado.Pregunta;
import pds.futbolistos.vistas.VentanaCurso;
import pds.futbolistos.vistas.VentanaPrincipal;

import java.awt.*;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;

public abstract class PanelPregunta extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Elementos comunes a todas las preguntas
	private JTextArea txtrEnunciado;
	private JLabel lblFoto;
	protected JPanel panelRespuestas; // Para poder añadir elementos en los tipos concretos de pregunta
	private JButton btnPista;
	private JLabel lblTiempoRestante;
	private Timer timer;

	// Actualización del tiempo
	private int tiempoRestante;
	
	// VentanaCurso
	protected VentanaCurso ventanaCurso;

	public PanelPregunta(Pregunta p) {

		this.tiempoRestante = p.getSegundos();
		
		// Necesario para estar seguros de que se llama al método getWindowAncestor después de que se añada
		// el panel a la ventana. Si no, devuelve null.
		this.addHierarchyListener( e -> {
			if ((e.getChangeFlags() & HierarchyEvent.PARENT_CHANGED) != 0) {
				ventanaCurso = (VentanaCurso)SwingUtilities.getWindowAncestor(PanelPregunta.this);
            }
		});
		inicializarComponentes();
		personalizarDisplay(p);
		empezarTemporizador();

	}

	// Gestión del tiempo
	private void empezarTemporizador() {
		timer = new Timer(1000, e -> actualizarTiempo());
		timer.start();
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
	protected final void manejarTiempoTerminado(boolean respondida) {
		gestionarPreguntaRespondida(respondida);
		
		// Siempre se ejecuta el código de pasar a siguiente pregunta, independientemente del tipo de pregunta.
		Pregunta p = Controlador.getInstancia().pasarASiguientePregunta();
        if (p == null) {
            JOptionPane.showMessageDialog(this, "¡Curso completado!");
            this.ventanaCurso.mostrarEstadisticas();
        } else {
            this.ventanaCurso.actualizarPregunta(p);
        }
	}
	
	protected abstract void gestionarPreguntaRespondida(boolean respondida);

	// Dibujar el panel
	protected void personalizarDisplay(Pregunta p) {
		txtrEnunciado.setText(p.getEnunciado());
		if (!Controlador.getInstancia().quedanPistasDisponibles() || !p.hasPista() ) 
			btnPista.setEnabled(false);
		btnPista.addActionListener( e -> {
			JOptionPane.showMessageDialog(this, p.getPista());
			Controlador.getInstancia().disminuirPistasDisponibles();
			btnPista.setEnabled(false);
			this.ventanaCurso.actualizarPistasRestantes();
		});
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
		lblFoto.setIcon(new ImageIcon(PanelPregunta.class.getResource("/pds/futbolistos/imagenes/flag-football.png")));
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

	
}
