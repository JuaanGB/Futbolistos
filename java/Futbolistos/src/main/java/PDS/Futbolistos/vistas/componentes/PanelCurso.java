package PDS.Futbolistos.vistas.componentes;

import javax.swing.*;

import PDS.Futbolistos.controlador.Controlador;
import PDS.Futbolistos.modelado.Curso;
import PDS.Futbolistos.modelado.estrategias.EstrategiaSecuencial;
import PDS.Futbolistos.vistas.VentanaCurso;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PanelCurso extends JPanel {
	
    public PanelCurso(Curso curso) {
    	
    	setBackground(new Color(255, 255, 255));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 1)); // Borde para separar los cursos
        setPreferredSize(new Dimension(200, 100)); // Tamaño fijo para que las celdas sean iguales

        // Panel para la imagen (si existe)
        JLabel imagenLabel = new JLabel();
        
        // Nombre del curso
        JLabel nombreLabel = new JLabel(curso.getNombre());
        nombreLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Botón "i" para información
        JButton infoButton = new JButton("");
        infoButton.setIcon(new ImageIcon(PanelCurso.class.getResource("/PDS/Futbolistos/imagenes/letra-i.png")));
        infoButton.addActionListener(e -> mostrarInformacion(curso) );
        infoButton.setContentAreaFilled(false);
        infoButton.setBorderPainted(false);
        
        // Botón "Comenzar"
        JButton comenzarButton = new JButton("Comenzar");
        comenzarButton.addActionListener(e -> iniciarCurso(curso) );

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
    
    private void iniciarCurso(Curso c) {
    	Controlador.getInstancia().empezarCurso(c, new EstrategiaSecuencial());
    	VentanaCurso vc = new VentanaCurso(Controlador.getInstancia().getSesionCursoAct());
    	vc.setVisible(true);
    }
    
    private void mostrarInformacion(Curso c) {
    	JOptionPane.showMessageDialog(this, c.getDescripcion());
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

