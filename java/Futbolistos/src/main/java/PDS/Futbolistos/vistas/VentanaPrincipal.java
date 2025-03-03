package PDS.Futbolistos.vistas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import PDS.Futbolistos.vistas.componentes.PanelCurso;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;

public class VentanaPrincipal {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal window = new VentanaPrincipal();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VentanaPrincipal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 549, 773);
		frame.setMinimumSize(new Dimension(549, 773));
		frame.setTitle("FUTBOLISTOS");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panelNombreApp = new JPanel();
		frame.getContentPane().add(panelNombreApp, BorderLayout.NORTH);
		panelNombreApp.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel fotoFutbol = new JLabel("");
		fotoFutbol.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/PDS/Futbolistos/imagenes/flag-football.png")));
		panelNombreApp.add(fotoFutbol);
		
		JLabel lblFutbolistos = new JLabel("FUTBOLISTOS");
		lblFutbolistos.setFont(new Font("Dialog", Font.BOLD, 32));
		panelNombreApp.add(lblFutbolistos);
		
		JLabel fotoFutbol_1 = new JLabel("");
		fotoFutbol_1.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/PDS/Futbolistos/imagenes/cerebro.png")));
		panelNombreApp.add(fotoFutbol_1);
		
		JPanel panelUsuarioYCursos = new JPanel();
		frame.getContentPane().add(panelUsuarioYCursos, BorderLayout.CENTER);
		panelUsuarioYCursos.setLayout(new BorderLayout(0, 0));
		
		JPanel panelUsuario = new JPanel();
		panelUsuario.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelUsuarioYCursos.add(panelUsuario, BorderLayout.NORTH);
		
		JLabel imagenUsuario = new JLabel("");
		imagenUsuario.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/PDS/Futbolistos/imagenes/usuario.png")));
		panelUsuario.add(imagenUsuario);
		
		JLabel lblNoAutenticadousuario = new JLabel("Usuario");
		panelUsuario.add(lblNoAutenticadousuario);
		
		JButton btnIniciarSesin = new JButton("Iniciar sesión");
		panelUsuario.add(btnIniciarSesin);
		
		JButton btnEstadsticas = new JButton("Estadísticas");
		btnEstadsticas.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/PDS/Futbolistos/imagenes/tendencia.png")));
		panelUsuario.add(btnEstadsticas);
		
		JButton btnCargarCurso = new JButton("Cargar Curso");
		btnCargarCurso.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/PDS/Futbolistos/imagenes/subir.png")));
		btnCargarCurso.addActionListener( e -> cargarCursoDesdeFichero());
		panelUsuario.add(btnCargarCurso);

		JPanel panelCursos = new JPanel(new GridLayout(0, 2, 10, 10)); // 2 columnas, celdas del mismo tamaño
		for (int i = 1; i <= 11; i++) {
		    PanelCurso p = new PanelCurso("Curso " + i, null);
		    panelCursos.add(p);
		}

		// Envolverlo en un panel contenedor para que JScrollPane funcione bien
		JPanel panelWrapper = new JPanel(new BorderLayout());
		panelWrapper.add(panelCursos, BorderLayout.NORTH); // Esto permite que el scroll funcione bien

		// Agregar JScrollPane
		JScrollPane scrollPane = new JScrollPane(panelWrapper);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		// Agregar al panel principal
		panelUsuarioYCursos.add(scrollPane, BorderLayout.CENTER);
	}

	 private void cargarCursoDesdeFichero() {
	        JFileChooser fileChooser = new JFileChooser();
	        
	        // Permitir solo archivos JSON y YAML
	        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos JSON y YAML (*.json, *.yaml, *.yml)", "json", "yaml", "yml");
	        fileChooser.setFileFilter(filtro);

	        int seleccion = fileChooser.showOpenDialog(frame);

	        if (seleccion == JFileChooser.APPROVE_OPTION) {
	            File archivoSeleccionado = fileChooser.getSelectedFile();
	            String extension = obtenerExtension(archivoSeleccionado);

	            System.out.println("Archivo seleccionado: " + archivoSeleccionado.getAbsolutePath());
	            System.out.println("Extensión: " + extension);
	            
	            // TODO: Delegar en el controlador que cogerá un parser u otro en función de la extensión y creará el objeto curso, que añadirá a la lista del usuario
	            // y devolverá a esta llamada para actualizar la ventana
	        }
	    }

	    private String obtenerExtension(File archivo) {
	        String nombre = archivo.getName();
	        int lastIndex = nombre.lastIndexOf(".");
	        return (lastIndex == -1) ? "" : nombre.substring(lastIndex + 1).toLowerCase();
	    }

}
