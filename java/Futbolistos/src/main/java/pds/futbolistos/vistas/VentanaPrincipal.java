package pds.futbolistos.vistas;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import pds.futbolistos.controlador.Controlador;
import pds.futbolistos.modelado.Curso;
import pds.futbolistos.vistas.componentes.FactoriaComponentes;
import pds.futbolistos.vistas.componentes.PanelCurso;

import java.awt.*;
import java.io.File;
import java.util.List;

public class VentanaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;

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

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(30, 30, 30));
        setContentPane(mainPanel);

        JPanel panelNombreApp = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panelNombreApp.setBackground(new Color(30, 30, 30));
        mainPanel.add(panelNombreApp, BorderLayout.NORTH);
        
        // Icono izquierdo (se escala a 64x64)
        JLabel fotoFutbol = new JLabel("");
        fotoFutbol.setIcon(loadScaledImage("/pds/futbolistos/imagenes/flag-football.png", 64, 64));
        panelNombreApp.add(fotoFutbol);

        // Título de la aplicación
        JLabel lblFutbolistos = new JLabel("FUTBOLISTOS");
        lblFutbolistos.setFont(new Font("Arial", Font.BOLD, 32));
        lblFutbolistos.setForeground(Color.WHITE);
        panelNombreApp.add(lblFutbolistos);

        // Icono derecho (se escala a 64x64)
        JLabel fotoFutbol_1 = new JLabel("");
        fotoFutbol_1.setIcon(loadScaledImage("/pds/futbolistos/imagenes/cerebro.png", 64, 64));
        panelNombreApp.add(fotoFutbol_1);

        JPanel panelUsuarioYCursos = new JPanel(new BorderLayout());
        panelUsuarioYCursos.setBackground(new Color(30, 30, 30));
        mainPanel.add(panelUsuarioYCursos, BorderLayout.CENTER);

        JPanel panelBotonesUsuario = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotonesUsuario.setBackground(new Color(30, 30, 30));

        Controlador ctrl = Controlador.getInstancia();
        String currentUserName = (ctrl.getUsuarioAct() != null) ? ctrl.getUsuarioAct().getNombreUsuario() : "Usuario";
        // Check if the current user has a custom image
        Icon userIcon = loadScaledImage("/pds/futbolistos/imagenes/usuario.png", 32, 32);
        JLabel lblImagen = new JLabel(userIcon);

        // Botón "Usuario" con icono escalado (por ejemplo, 32x32)
        JLabel lblUsuario = FactoriaComponentes.crearLabel("Usuario");
        
        // Botón "Estadísticas"
        JButton btnEstadsticas = FactoriaComponentes.crearBoton("Estadísticas");
        btnEstadsticas.setPreferredSize(new Dimension(200, 50));
        btnEstadsticas.setIcon(loadScaledImage("/pds/futbolistos/imagenes/tendencia.png", 32, 32));
        btnEstadsticas.addActionListener( e -> System.out.println(Controlador.getInstancia().getUsuarioAct().getEstadisticas()));

        // Botón "Cargar Curso"
        JButton btnCargarCurso = FactoriaComponentes.crearBoton("Cargar curso");
        btnCargarCurso.setPreferredSize(new Dimension(200, 50));
        btnCargarCurso.setIcon(loadScaledImage("/pds/futbolistos/imagenes/subir.png", 32, 32));
        btnCargarCurso.addActionListener(e -> cargarCursoDesdeFichero());

        panelBotonesUsuario.add(lblImagen);
        panelBotonesUsuario.add(lblUsuario);
        panelBotonesUsuario.add(btnEstadsticas);
        panelBotonesUsuario.add(btnCargarCurso);
        panelUsuarioYCursos.add(panelBotonesUsuario, BorderLayout.NORTH);

        JPanel panelCursos = new JPanel(new GridLayout(0, 2, 10, 10));
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

    /**
     * Carga una imagen desde el recurso y la escala a las dimensiones indicadas usando un escalado suave.
     *
     * @param path La ruta del recurso de la imagen.
     * @param width El ancho deseado.
     * @param height La altura deseada.
     * @return Un ImageIcon escalado.
     */
    private ImageIcon loadScaledImage(String path, int width, int height) {
        ImageIcon originalIcon = new ImageIcon(VentanaPrincipal.class.getResource(path));
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    private void cargarCursoDesdeFichero() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos JSON y YAML (*.json, *.yaml, *.yml)",
                "json", "yaml", "yml");
        fileChooser.setFileFilter(filtro);

        int seleccion = fileChooser.showOpenDialog(this);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();
            String extension = obtenerExtension(archivoSeleccionado);

            System.out.println("Archivo seleccionado: " + archivoSeleccionado.getAbsolutePath());
            System.out.println("Extensión: " + extension);

            // TODO: Delegar en el controlador para parsear según la extensión, crear el objeto Curso,
            // añadirlo a la lista del usuario y actualizar la ventana
        }
    }

    private String obtenerExtension(File archivo) {
        String nombre = archivo.getName();
        int lastIndex = nombre.lastIndexOf(".");
        return (lastIndex == -1) ? "" : nombre.substring(lastIndex + 1).toLowerCase();
    }
    
}