package PDS.Futbolistos.vistas.componentes;

import javax.swing.*;
import java.awt.*;

public class PanelCurso extends JPanel {
    public PanelCurso(String nombre, ImageIcon imagen) {
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
        infoButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Información sobre " + nombre));

        // Botón "Comenzar"
        JButton comenzarButton = new JButton("Comenzar");
        comenzarButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Iniciando " + nombre));

        // Panel para los botones
        JPanel botonesPanel = new JPanel();
        botonesPanel.add(infoButton);
        botonesPanel.add(comenzarButton);

        // Agregar componentes
        add(imagenLabel, BorderLayout.WEST);
        add(nombreLabel, BorderLayout.CENTER);
        add(botonesPanel, BorderLayout.SOUTH);
    }
}

