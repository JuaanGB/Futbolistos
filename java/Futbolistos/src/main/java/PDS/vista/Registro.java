package PDS.vista;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Registro extends JFrame {
    private JLabel lblImage;
    private File selectedImageFile = null;
    
    public Registro() {
        setTitle("Futbolistos - Registro");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(550, 500);
        setLocationRelativeTo(null); // Center the window

        // Try to use Nimbus Look and Feel for a modern style
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, fallback to the default look and feel
        }
        
        // Main panel with a dark background
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(30, 30, 30));
        
        // Title label for the app
        JLabel lblTitle = new JLabel("Futbolistos", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitle.setForeground(new Color(0, 204, 102));
        mainPanel.add(lblTitle, BorderLayout.NORTH);
        
        // Form panel using GridBagLayout for a modern design
        JPanel formPanel = new JPanel();
        formPanel.setBackground(new Color(30, 30, 30));
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        Font labelFont = new Font("Arial", Font.PLAIN, 16);
        Font fieldFont = new Font("Arial", Font.PLAIN, 16);

        // Nombre
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setFont(labelFont);
        JTextField txtNombre = new JTextField();
        txtNombre.setFont(fieldFont);
        txtNombre.setBorder(BorderFactory.createLineBorder(new Color(0, 204, 102), 2));
        
        // Apellido
        JLabel lblApellido = new JLabel("Apellido:");
        lblApellido.setForeground(Color.WHITE);
        lblApellido.setFont(labelFont);
        JTextField txtApellido = new JTextField();
        txtApellido.setFont(fieldFont);
        txtApellido.setBorder(BorderFactory.createLineBorder(new Color(0, 204, 102), 2));
        
        // Fecha de Nacimiento
        JLabel lblFechaNacimiento = new JLabel("Fecha de Nacimiento (dd/mm/yyyy):");
        lblFechaNacimiento.setForeground(Color.WHITE);
        lblFechaNacimiento.setFont(labelFont);
        JTextField txtFechaNacimiento = new JTextField();
        txtFechaNacimiento.setFont(fieldFont);
        txtFechaNacimiento.setBorder(BorderFactory.createLineBorder(new Color(0, 204, 102), 2));
        
        // Saludo
        JLabel lblSaludo = new JLabel("Saludo (opcional):");
        lblSaludo.setForeground(Color.WHITE);
        lblSaludo.setFont(labelFont);
        JTextArea txtSaludo = new JTextArea(3, 20);
        txtSaludo.setFont(fieldFont);
        txtSaludo.setLineWrap(true);
        txtSaludo.setWrapStyleWord(true);
        txtSaludo.setBorder(BorderFactory.createLineBorder(new Color(0, 204, 102), 2));
        JScrollPane scrollSaludo = new JScrollPane(txtSaludo);
        scrollSaludo.setBorder(null);
        
        // Imagen (optional)
        JLabel lblImagen = new JLabel("Imagen (opcional):");
        lblImagen.setForeground(Color.WHITE);
        lblImagen.setFont(labelFont);
        JButton btnSeleccionarImagen = new JButton("Seleccionar Imagen");
        btnSeleccionarImagen.setFont(new Font("Arial", Font.BOLD, 14));
        btnSeleccionarImagen.setBackground(new Color(0, 204, 102));
        btnSeleccionarImagen.setForeground(Color.WHITE);
        btnSeleccionarImagen.setFocusPainted(false);
        btnSeleccionarImagen.setBorder(BorderFactory.createLineBorder(new Color(0, 204, 102),2));
        lblImage = new JLabel();
        lblImage.setPreferredSize(new Dimension(100, 100));
        lblImage.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        // Registrar button
        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 16));
        btnRegistrar.setBackground(new Color(0, 204, 102));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setBorder(BorderFactory.createLineBorder(new Color(0, 204, 102), 2));
        
        // Add components to the form panel (Row by row)
        // Row 1 - Nombre
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(lblNombre, gbc);
        gbc.gridx = 1;
        formPanel.add(txtNombre, gbc);
        
        // Row 2 - Apellido
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(lblApellido, gbc);
        gbc.gridx = 1;
        formPanel.add(txtApellido, gbc);
        
        // Row 3 - Fecha de Nacimiento
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(lblFechaNacimiento, gbc);
        gbc.gridx = 1;
        formPanel.add(txtFechaNacimiento, gbc);
        
        // Row 4 - Saludo
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(lblSaludo, gbc);
        gbc.gridx = 1;
        formPanel.add(scrollSaludo, gbc);
        
        // Row 5 - Imagen selection with button and preview
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(lblImagen, gbc);
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        imagePanel.setBackground(new Color(30, 30, 30));
        imagePanel.add(btnSeleccionarImagen);
        imagePanel.add(lblImage);
        gbc.gridx = 1;
        formPanel.add(imagePanel, gbc);
        
        // Row 6 - Registrar button centered
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        formPanel.add(btnRegistrar, gbc);
        gbc.gridwidth = 1; // reset
        
        // Assemble the main panel
        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel);
        
        // Action for selecting an image using JFileChooser
        btnSeleccionarImagen.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(Registro.this);
            if(result == JFileChooser.APPROVE_OPTION) {
                selectedImageFile = fileChooser.getSelectedFile();
                ImageIcon icon = new ImageIcon(selectedImageFile.getAbsolutePath());
                // Scale the image to fit the label
                Image img = icon.getImage().getScaledInstance(lblImage.getWidth(), lblImage.getHeight(), Image.SCALE_SMOOTH);
                lblImage.setIcon(new ImageIcon(img));
            }
        });
        
        // The action for the 'Registrar' button will be implemented in the future
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Registro().setVisible(true));
    }
}