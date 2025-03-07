package PDS.Futbolistos.vistas.componentes;

import javax.swing.*;
import java.awt.*;

public class PanelCurso extends JPanel {
	
	private String nombre;
	private ImageIcon imagen;
	
    public PanelCurso(String nombre, ImageIcon imagen) {
    	
    	this.nombre = nombre;
    	this.imagen = imagen;
    	
    	setBackground(new Color(255, 255, 255));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 1)); // Borde para separar los cursos
        setPreferredSize(new Dimension(200, 100)); // Tamaño fijo para que las celdas sean iguales

        // Panel para la imagen (si existe)
        JLabel imagenLabel = new JLabel();
        if (imagen != null) {
            Image img = imagen.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            imagenLabel.setIcon(new ImageIcon(img));
        }

        // Nombre del curso
        JLabel nombreLabel = new JLabel(nombre);
        nombreLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Botón "i" para información
        JButton infoButton = new JButton("");
        infoButton.setIcon(new ImageIcon(PanelCurso.class.getResource("/PDS/Futbolistos/imagenes/letra-i.png")));
        infoButton.addActionListener(e -> mostrarInformacion() );
        infoButton.setContentAreaFilled(false);
        infoButton.setBorderPainted(false);
        
        // Botón "Comenzar"
        JButton comenzarButton = new JButton("Comenzar");
        comenzarButton.addActionListener(e -> iniciarCurso() );

        // Panel para los botones
        JPanel botonesPanel = new JPanel();
        botonesPanel.setBackground(new Color(255, 255, 255));
        botonesPanel.add(infoButton);
        botonesPanel.add(comenzarButton);

        // Agregar componentes
        add(imagenLabel, BorderLayout.WEST);
        add(nombreLabel, BorderLayout.CENTER);
        add(botonesPanel, BorderLayout.SOUTH);
        
        JButton shareButton = new JButton("");
        shareButton.setIcon(new ImageIcon(PanelCurso.class.getResource("/PDS/Futbolistos/imagenes/share.png")));
        shareButton.setContentAreaFilled(false);
        shareButton.setBorderPainted(false);
        shareButton.addActionListener( e -> compartirCurso());
        botonesPanel.add(shareButton);
    }
    
    private void iniciarCurso() {
    	JOptionPane.showMessageDialog(this, "Iniciando " + nombre);
    }
    
    private void mostrarInformacion() {
    	JOptionPane.showMessageDialog(this, "Información sobre " + nombre);
    }
    
    private void compartirCurso() {
        String usuario = JOptionPane.showInputDialog(this, 
            "¿Con quién quieres compartir el curso?", 
            "Compartir Curso", 
            JOptionPane.QUESTION_MESSAGE);
        
        if (usuario != null && !usuario.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Curso compartido con " + usuario + " exitosamente.", 
                "Compartir Curso", 
                JOptionPane.INFORMATION_MESSAGE);
            // Aquí puedes agregar la lógica para compartir el curso con el usuario ingresado
        } else {
            JOptionPane.showMessageDialog(this, 
                "No ingresaste un nombre de usuario válido.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
}

