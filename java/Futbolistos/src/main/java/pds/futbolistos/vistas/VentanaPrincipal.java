package pds.futbolistos.vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import pds.futbolistos.controlador.Controlador;
import pds.futbolistos.modelado.Curso;
import pds.futbolistos.vistas.componentes.FactoriaComponentes;
import pds.futbolistos.vistas.componentes.PanelCurso;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel panelCursos;

	public VentanaPrincipal() {

		FactoriaComponentes.utilizarNimbusLookAndFeel();
		initialize();
	}

	private void initialize() {

		setBounds(100, 100, 700, 773);
		setMinimumSize(new Dimension(700, 773));
		setTitle("FUTBOLISTOS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Controlador.getInstancia().actualizarEstadisticaDeTiempo();
			};
		});

		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBackground(new Color(30, 30, 30));
		setContentPane(mainPanel);

		JPanel panelNombreApp = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panelNombreApp.setBackground(new Color(30, 30, 30));
		mainPanel.add(panelNombreApp, BorderLayout.NORTH);

		JLabel fotoLogo = new JLabel();
		fotoLogo.setIcon(
				new ImageIcon(VentanaPrincipal.class.getResource("/pds/futbolistos/imagenes/logo-futbolistos.png")));
		panelNombreApp.add(fotoLogo);

		JPanel panelUsuarioYCursos = new JPanel(new BorderLayout());
		panelUsuarioYCursos.setBackground(new Color(30, 30, 30));
		mainPanel.add(panelUsuarioYCursos, BorderLayout.CENTER);

		JPanel panelBotonesUsuario = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		panelBotonesUsuario.setBackground(new Color(30, 30, 30));

		Controlador ctrl = Controlador.getInstancia();
		String currentUserName = (ctrl.getUsuarioAct() != null) ? ctrl.getUsuarioAct().getNombreUsuario() : "Usuario";

		// Botón "Usuario" con icono escalado (por ejemplo, 32x32)
		JLabel lblUsuario = FactoriaComponentes.crearLabel(
				"<html><div style='text-align: center;'>¡Bienvenido,<br>" + currentUserName + "!</div></html>");

		lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);

		// Botón "Estadísticas"
		JButton btnEstadsticas = FactoriaComponentes.crearBoton("Estadísticas");
		btnEstadsticas.setPreferredSize(new Dimension(200, 50));
		btnEstadsticas.setIcon(new ImageIcon(getClass().getResource("/pds/futbolistos/imagenes/tendencia.png")));
		btnEstadsticas.addActionListener(e -> new VentanaEstadisticasUsuario().setVisible(true));

		// Botón "Cargar Curso"
		JButton btnCargarCurso = FactoriaComponentes.crearBoton("Cargar curso");
		btnCargarCurso.setPreferredSize(new Dimension(200, 50));
		btnCargarCurso.setIcon(new ImageIcon(getClass().getResource("/pds/futbolistos/imagenes/subir.png")));
		btnCargarCurso.addActionListener(e -> cargarCursoDesdeFichero());

		panelBotonesUsuario.add(lblUsuario);
		panelBotonesUsuario.add(btnEstadsticas);
		panelBotonesUsuario.add(btnCargarCurso);
		panelUsuarioYCursos.add(panelBotonesUsuario, BorderLayout.NORTH);

		panelCursos = new JPanel(new GridLayout(0, 2, 10, 10));
		panelCursos.setBackground(new Color(30, 30, 30));
		List<Curso> cursos = ctrl.getCursosDisponibles();
		for (Curso c : cursos) {
			panelCursos.add(new PanelCurso(c));
		}

		JPanel panelWrapper = new JPanel(new BorderLayout());
		panelWrapper.setBackground(new Color(30, 30, 30));
		panelWrapper.add(panelCursos, BorderLayout.NORTH);

		JScrollPane scrollPane = new JScrollPane(panelWrapper);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panelUsuarioYCursos.add(scrollPane, BorderLayout.CENTER);
	}

	private void cargarCursoDesdeFichero() {
		JFileChooser fileChooser = new JFileChooser();
		String[] extensiones = Controlador.getInstancia().getExtensionesValidas();
		FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos de serialización " + String.join(", ", extensiones),
				extensiones);
		fileChooser.setFileFilter(filtro);

		int seleccion = fileChooser.showOpenDialog(this);
		if (seleccion == JFileChooser.APPROVE_OPTION) {
			File archivoSeleccionado = fileChooser.getSelectedFile();
			String extension = obtenerExtension(archivoSeleccionado);

			System.out.println("Archivo seleccionado: " + archivoSeleccionado.getAbsolutePath());
			System.out.println("Extensión: " + extension);

			if (Controlador.getInstancia().importarCurso(archivoSeleccionado, extension)) {
				JOptionPane.showMessageDialog(null, "¡Curso importado correctamente!", "Importación exitosa",
						JOptionPane.INFORMATION_MESSAGE);
				actualizarCursosDisponibles();
			} else {
				JOptionPane.showMessageDialog(null,
						"Ocurrió un error al importar el curso.\nVerifica que el archivo sea válido.",
						"Error de importación", JOptionPane.ERROR_MESSAGE);
			}

		}
	}

	private String obtenerExtension(File archivo) {
		String nombre = archivo.getName();
		int lastIndex = nombre.lastIndexOf(".");
		return (lastIndex == -1) ? "" : nombre.substring(lastIndex + 1).toLowerCase();
	}

	private void actualizarCursosDisponibles() {

		panelCursos.removeAll();
		List<Curso> cursos = Controlador.getInstancia().getCursosDisponibles();
		for (Curso c : cursos) {
			panelCursos.add(new PanelCurso(c));
		}

		panelCursos.revalidate();
		panelCursos.repaint();
	}

}