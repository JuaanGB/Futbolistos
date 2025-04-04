package pds.futbolistos.vistas;

import javax.imageio.ImageIO;
import javax.swing.*;

import pds.futbolistos.controlador.Controlador;
import pds.futbolistos.modelado.Usuario;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PerfilUsuario extends JFrame {

    private static final long serialVersionUID = 1L;
    private JLabel lblImagenPreview;
    private JTextArea txtSaludo;
    private File selectedImageFile;

    public PerfilUsuario() {
        setTitle("Perfil de Usuario");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(30, 30, 30));
        setContentPane(mainPanel);

        // Panel para la imagen y botón de selección
        JPanel panelImagen = new JPanel(new FlowLayout());
        panelImagen.setBackground(new Color(30, 30, 30));
        lblImagenPreview = new JLabel();
        lblImagenPreview.setPreferredSize(new Dimension(120, 120));
        lblImagenPreview.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        panelImagen.add(lblImagenPreview);

        JButton btnSeleccionarImagen = new JButton("Seleccionar Imagen");
        btnSeleccionarImagen.setFont(new Font("Arial", Font.BOLD, 14));
        btnSeleccionarImagen.setBackground(new Color(0, 204, 102));
        btnSeleccionarImagen.setForeground(Color.WHITE);
        btnSeleccionarImagen.setFocusPainted(false);
        btnSeleccionarImagen.addActionListener(e -> seleccionarImagen());
        panelImagen.add(btnSeleccionarImagen);
        mainPanel.add(panelImagen, BorderLayout.NORTH);

        // Panel para editar el saludo
        JPanel panelSaludo = new JPanel(new BorderLayout());
        panelSaludo.setBackground(new Color(30, 30, 30));
        JLabel lblSaludo = new JLabel("Saludo:");
        lblSaludo.setForeground(Color.WHITE);
        lblSaludo.setFont(new Font("Arial", Font.PLAIN, 16));
        panelSaludo.add(lblSaludo, BorderLayout.NORTH);
        txtSaludo = new JTextArea(3, 20);
        txtSaludo.setLineWrap(true);
        txtSaludo.setWrapStyleWord(true);
        txtSaludo.setFont(new Font("Arial", Font.PLAIN, 16));
        JScrollPane scrollSaludo = new JScrollPane(txtSaludo);
        panelSaludo.add(scrollSaludo, BorderLayout.CENTER);
        mainPanel.add(panelSaludo, BorderLayout.CENTER);

        // Botón para guardar cambios
        JButton btnGuardar = new JButton("Guardar Cambios");
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 16));
        btnGuardar.setBackground(new Color(0, 204, 102));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);
        btnGuardar.addActionListener(e -> guardarCambios());
        mainPanel.add(btnGuardar, BorderLayout.SOUTH);

        // Cargar datos actuales del usuario
        cargarDatosUsuario();
    }

    private void seleccionarImagen() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedImageFile = chooser.getSelectedFile();
            try {
                BufferedImage bufferedImage = ImageIO.read(selectedImageFile);
                // Escalar la imagen para previsualización
                Image scaledImage = bufferedImage.getScaledInstance(lblImagenPreview.getWidth(), 
                        lblImagenPreview.getHeight(), Image.SCALE_SMOOTH);
                lblImagenPreview.setIcon(new ImageIcon(scaledImage));
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al cargar la imagen.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void guardarCambios() {
        String nuevoSaludo = txtSaludo.getText().trim();
        Controlador ctrl = Controlador.getInstancia();
        Usuario actual = ctrl.getUsuarioAct();
        if (actual != null) {
            //actual.setSaludo(nuevoSaludo);
            if (selectedImageFile != null) {
                try {
                    BufferedImage nuevaImagen = ImageIO.read(selectedImageFile);
                    //actual.setImagen(nuevaImagen);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error al cargar la nueva imagen.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            JOptionPane.showMessageDialog(this, "Cambios guardados correctamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    }

    private void cargarDatosUsuario() {
        Controlador ctrl = Controlador.getInstancia();
        Usuario actual = ctrl.getUsuarioAct();
        if (actual != null) {
            //txtSaludo.setText(actual.getSaludo());
            //BufferedImage imagen = actual.getImagen();
//            if (imagen != null) {
//                Image scaledImage = imagen.getScaledInstance(lblImagenPreview.getWidth(), 
//                        lblImagenPreview.getHeight(), Image.SCALE_SMOOTH);
//                lblImagenPreview.setIcon(new ImageIcon(scaledImage));
//            }
        }
    }
}