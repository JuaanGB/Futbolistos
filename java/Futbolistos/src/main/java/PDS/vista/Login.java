package PDS.vista;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {
    public Login() {
        setTitle("Futbolistos - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 350);
        setLocationRelativeTo(null); // Centrar la ventana

        // Intenta utilizar Nimbus Look and Feel para un estilo moderno
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Si Nimbus no está disponible, se usará la apariencia por defecto
        }
        
        // Panel principal con un fondo oscuro
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(30, 30, 30)); // Fondo oscuro
        mainPanel.setLayout(new BorderLayout());
        
        // ---------------------
        // Etiqueta con la imagen en la parte superior
        // ---------------------
        // Carga la imagen (asegúrate de que la ruta sea correcta)
        ImageIcon icon = new ImageIcon("futbolistos.webp");
        // Opcional: redimensionar la imagen (descomentar si lo deseas)
        /*
        Image image = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        icon = new ImageIcon(image);
        */
        // Crea la etiqueta y añade la imagen
        JLabel lblImage = new JLabel(icon);
        lblImage.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Panel de formulario con GridBagLayout
        JPanel formPanel = new JPanel();
        formPanel.setBackground(new Color(30, 30, 30));
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espaciado entre componentes
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Campo de Usuario
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setForeground(Color.WHITE);
        lblUsuario.setFont(new Font("Arial", Font.PLAIN, 16));
        JTextField txtUsuario = new JTextField();
        txtUsuario.setFont(new Font("Arial", Font.PLAIN, 16));
        txtUsuario.setBorder(BorderFactory.createLineBorder(new Color(0, 204, 102), 2));
        
        // Campo de Contraseña
        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setForeground(Color.WHITE);
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 16));
        JPasswordField txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 16));
        txtPassword.setBorder(BorderFactory.createLineBorder(new Color(0, 204, 102), 2));
        
        // Botón de Ingresar
        JButton btnLogin = new JButton("Ingresar");
        btnLogin.setFont(new Font("Arial", Font.BOLD, 16));
        btnLogin.setBackground(new Color(0, 204, 102));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorder(null); 
        btnLogin.setPreferredSize(new Dimension(150, 50)); // Aumenta el tamaño del fondo verde
        
        // Botón de Registrarse
        JButton btnRegister = new JButton("Registrarse");
        btnRegister.setFont(new Font("Arial", Font.BOLD, 16));
        btnRegister.setBackground(new Color(0, 204, 102));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setFocusPainted(false);
        btnRegister.setBorder(null); 
        btnRegister.setPreferredSize(new Dimension(150, 50)); // Aumenta el tamaño del fondo verde
        
        // Agregar componentes al panel de formulario usando GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(lblUsuario, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(txtUsuario, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(lblPassword, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(txtPassword, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(btnLogin, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(btnRegister, gbc);
        
        // Acción para abrir la ventana de registro
        btnRegister.addActionListener(e -> new Registro().setVisible(true));
        
        // Agregar la imagen y el formulario al panel principal
        mainPanel.add(lblImage, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        // Agregar el panel principal a la ventana
        add(mainPanel);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login().setVisible(true));
    }
}
