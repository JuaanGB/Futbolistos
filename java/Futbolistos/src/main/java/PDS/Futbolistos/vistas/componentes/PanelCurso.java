package PDS.Futbolistos.vistas.componentes;

import javax.swing.*;
import java.awt.*;

import PDS.Futbolistos.controlador.Controlador;
import PDS.Futbolistos.modelado.Curso;
import PDS.Futbolistos.modelado.estrategias.EstrategiaSecuencial;
import PDS.Futbolistos.vistas.VentanaCurso;

public class PanelCurso extends JPanel {
    
    public PanelCurso(Curso curso) {
        // Utilizamos fondo oscuro que sigue la estética de VentanaPrincipal
        setBackground(new Color(30, 30, 30));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.WHITE, 1)); // Borde blanco
        setPreferredSize(new Dimension(200, 100)); // Tamaño fijo para que las celdas sean iguales

        // Panel para la imagen (si existe)
        JLabel imagenLabel = new JLabel();
        // En caso de tener una imagen, ésta se podría ajustar aquí
       
        // Nombre del curso con estilo acorde a la estética
        JLabel nombreLabel = new JLabel(curso.getNombre());
        nombreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nombreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nombreLabel.setForeground(Color.WHITE);

        // Botón "i" para información con transparencia y sin relleno
        JButton infoButton = new JButton("");
        infoButton.setIcon(new ImageIcon(PanelCurso.class.getResource("/PDS/Futbolistos/imagenes/letra-i.png")));
        infoButton.addActionListener(e -> mostrarInformacion(curso));
        infoButton.setContentAreaFilled(false);
        infoButton.setBorderPainted(false);
        
        // Botón "Comenzar" con estilo estandarizado y de mayor tamaño
        JButton comenzarButton = new JButton("Comenzar");
        comenzarButton.setFont(new Font("Arial", Font.BOLD, 14));
        comenzarButton.setBackground(new Color(0, 204, 102));
        comenzarButton.setForeground(Color.WHITE);
        comenzarButton.setFocusPainted(false);
        comenzarButton.setBorder(null);
        comenzarButton.addActionListener(e -> iniciarCurso(curso));
        // Incrementamos el tamaño para que el fondo verde se vea más grande
        comenzarButton.setPreferredSize(new Dimension(150, 50));

        // Botón "Compartir" con estilo similar
        JButton shareButton = new JButton("");
        shareButton.setIcon(new ImageIcon(PanelCurso.class.getResource("/PDS/Futbolistos/imagenes/share.png")));
        shareButton.setContentAreaFilled(false);
        shareButton.setBorderPainted(false);
        shareButton.addActionListener(e -> compartirCurso());
        
        // Panel para los botones con fondo acorde
        JPanel botonesPanel = new JPanel();
        botonesPanel.setBackground(new Color(30, 30, 30));
        botonesPanel.add(infoButton);
        botonesPanel.add(comenzarButton);
        botonesPanel.add(shareButton);

        // Agregar componentes al panel
        add(imagenLabel, BorderLayout.WEST);
        add(nombreLabel, BorderLayout.CENTER);
        add(botonesPanel, BorderLayout.SOUTH);
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
            // Aquí se puede agregar la lógica para compartir el curso con el usuario ingresado
        } else {
            JOptionPane.showMessageDialog(this, 
                "No ingresaste un nombre de usuario válido.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}