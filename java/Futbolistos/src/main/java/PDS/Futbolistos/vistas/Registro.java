package PDS.Futbolistos.vistas;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class Registro extends JFrame {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JLabel lblImage;
    private File selectedImageFile = null;
    
    // Simulación de almacenamiento de usuarios ya registrados (cuando haya persistencia, se reemplazará con la base de datos)
    private static Set<String> registeredUsernames = new HashSet<>();
    
    // Patrón para fecha en formato dd/mm/yyyy
    private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{2}/\\d{2}/\\d{4}$");
    
    public Registro() {
        setTitle("Futbolistos - Registro");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(550, 500);
        setLocationRelativeTo(null); // Centrar la ventana
        
        // Establecer tamaño mínimo para que siempre se muestren todos los componentes
        setMinimumSize(new Dimension(800, 600));

        // Intentar usar Nimbus Look and Feel para un estilo moderno
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Si Nimbus no está disponible, se usará el look and feel por defecto
        }
        
        // Panel principal con fondo oscuro
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(30, 30, 30));
        
        // Etiqueta de título para la app
        JLabel lblTitle = new JLabel("Futbolistos", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitle.setForeground(new Color(0, 204, 102));
        mainPanel.add(lblTitle, BorderLayout.NORTH);
        
        // Panel del formulario usando GridBagLayout para un diseño moderno
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
        
        // Usuario (nombre de usuario)
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setForeground(Color.WHITE);
        lblUsuario.setFont(labelFont);
        JTextField txtUsuario = new JTextField();
        txtUsuario.setFont(fieldFont);
        txtUsuario.setBorder(BorderFactory.createLineBorder(new Color(0, 204, 102), 2));
        
        // Contraseña
        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setForeground(Color.WHITE);
        lblPassword.setFont(labelFont);
        JPasswordField txtPassword = new JPasswordField();
        txtPassword.setFont(fieldFont);
        txtPassword.setBorder(BorderFactory.createLineBorder(new Color(0, 204, 102), 2));
        
        // Confirmar Contraseña
        JLabel lblConfirmPassword = new JLabel("Confirmar Contraseña:");
        lblConfirmPassword.setForeground(Color.WHITE);
        lblConfirmPassword.setFont(labelFont);
        JPasswordField txtConfirmPassword = new JPasswordField();
        txtConfirmPassword.setFont(fieldFont);
        txtConfirmPassword.setBorder(BorderFactory.createLineBorder(new Color(0, 204, 102), 2));
        
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
        
        // Imagen (opcional)
        JLabel lblImagen = new JLabel("Imagen (opcional):");
        lblImagen.setForeground(Color.WHITE);
        lblImagen.setFont(labelFont);
        JButton btnSeleccionarImagen = new JButton("Seleccionar Imagen");
        btnSeleccionarImagen.setFont(new Font("Arial", Font.BOLD, 14));
        btnSeleccionarImagen.setBackground(new Color(0, 204, 102));
        btnSeleccionarImagen.setForeground(Color.WHITE);
        btnSeleccionarImagen.setFocusPainted(false);
        lblImage = new JLabel();
        lblImage.setPreferredSize(new Dimension(100, 100));
        lblImage.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        // Botón Registrar
        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 16));
        btnRegistrar.setBackground(new Color(0, 204, 102));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFocusPainted(false);

        // Agregar componentes al panel del formulario (fila por fila)
        // Fila 0 - Nombre
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(lblNombre, gbc);
        gbc.gridx = 1;
        formPanel.add(txtNombre, gbc);
        
        // Fila 1 - Apellido
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(lblApellido, gbc);
        gbc.gridx = 1;
        formPanel.add(txtApellido, gbc);
        
        // Fila 2 - Usuario
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(lblUsuario, gbc);
        gbc.gridx = 1;
        formPanel.add(txtUsuario, gbc);
        
        // Fila 3 - Contraseña
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(lblPassword, gbc);
        gbc.gridx = 1;
        formPanel.add(txtPassword, gbc);
        
        // Fila 4 - Confirmar Contraseña
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(lblConfirmPassword, gbc);
        gbc.gridx = 1;
        formPanel.add(txtConfirmPassword, gbc);
        
        // Fila 5 - Fecha de Nacimiento
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(lblFechaNacimiento, gbc);
        gbc.gridx = 1;
        formPanel.add(txtFechaNacimiento, gbc);
        
        // Fila 6 - Saludo
        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(lblSaludo, gbc);
        gbc.gridx = 1;
        formPanel.add(scrollSaludo, gbc);
        
        // Fila 7 - Imagen: botón y previsualización
        gbc.gridx = 0;
        gbc.gridy = 7;
        formPanel.add(lblImagen, gbc);
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        imagePanel.setBackground(new Color(30, 30, 30));
        imagePanel.add(btnSeleccionarImagen);
        imagePanel.add(lblImage);
        gbc.gridx = 1;
        formPanel.add(imagePanel, gbc);
        
        // Fila 8 - Botón Registrar centrado
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        formPanel.add(btnRegistrar, gbc);
        gbc.gridwidth = 1;
        
        // Ensamblar el panel principal
        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel);
        
        // Acción para seleccionar una imagen usando JFileChooser
        btnSeleccionarImagen.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(Registro.this);
            if(result == JFileChooser.APPROVE_OPTION) {
                selectedImageFile = fileChooser.getSelectedFile();
                ImageIcon icon = new ImageIcon(selectedImageFile.getAbsolutePath());
                // Escalar la imagen para ajustarla al label
                Image img = icon.getImage().getScaledInstance(lblImage.getWidth(), lblImage.getHeight(), Image.SCALE_SMOOTH);
                lblImage.setIcon(new ImageIcon(img));
            }
        });
        
        // Acción para el botón 'Registrar'
        btnRegistrar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            String usuario = txtUsuario.getText().trim();
            String password = new String(txtPassword.getPassword());
            String confirmPassword = new String(txtConfirmPassword.getPassword());
            String fechaNacimiento = txtFechaNacimiento.getText().trim();
            String saludo = txtSaludo.getText().trim();
            
            // Validación básica de campos obligatorios
            if(nombre.isEmpty() || apellido.isEmpty() || usuario.isEmpty() || password.isEmpty() 
                    || confirmPassword.isEmpty() || fechaNacimiento.isEmpty()){
                JOptionPane.showMessageDialog(Registro.this, "Por favor, complete todos los campos obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Validar que las contraseñas coincidan
            if(!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(Registro.this, "Las contraseñas no coinciden. Por favor, verifique.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Validar el formato de la fecha
            if(!DATE_PATTERN.matcher(fechaNacimiento).matches()){
                JOptionPane.showMessageDialog(Registro.this, "El formato de la fecha es incorrecto. Use dd/mm/yyyy.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Validar que no exista un usuario con el mismo nombre ya registrado
            if(registeredUsernames.contains(usuario)){
                JOptionPane.showMessageDialog(Registro.this, "El nombre de usuario ya existe. Por favor elija otro.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Simulación de registro (aquí se guardaría en la base de datos en el futuro)
            registeredUsernames.add(usuario);
            JOptionPane.showMessageDialog(Registro.this, "Registro exitoso para el usuario: " + usuario, "Información", JOptionPane.INFORMATION_MESSAGE);
            
            // Cerrar la ventana de registro y abrir la de Login
            dispose();
            Login login = new Login();
            login.setVisible(true);
        });
    }
    
}