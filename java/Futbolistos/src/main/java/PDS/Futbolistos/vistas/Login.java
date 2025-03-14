package PDS.Futbolistos.vistas;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Login() {
        setTitle("Futbolistos - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Aumentar el ancho para estirar horizontalmente
        setSize(700, 450);
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla

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
        
        // Panel principal con GridBagLayout para centrar vertical y horizontalmente
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(30, 30, 30));
        GridBagConstraints gbcMain = new GridBagConstraints();
        gbcMain.insets = new Insets(20, 20, 20, 20);
        gbcMain.gridx = 0;
        gbcMain.fill = GridBagConstraints.BOTH;
        gbcMain.anchor = GridBagConstraints.CENTER;
        gbcMain.weightx = 1.0;
        gbcMain.weighty = 1.0;

        // ---------------------
        // Etiqueta con la imagen que actuará como logo
        // ---------------------
        ImageIcon icon = new ImageIcon(getClass().getResource("/PDS/Futbolistos/imagenes/futbolistos.png"));
        // Obtener la imagen original sin cambiarle el tamaño
        Image image = icon.getImage();
        // Utilizar una etiqueta personalizada que redimensiona la imagen según su contenedor sin estirarla exageradamente
        JLabel lblImage = new ScaledImageLabel(image);
        gbcMain.gridy = 0;
        mainPanel.add(lblImage, gbcMain);
        
        // ---------------------
        // Panel de formulario utilizando GridBagLayout
        // ---------------------
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(30, 30, 30));
        GridBagConstraints gbcForm = new GridBagConstraints();
        gbcForm.insets = new Insets(10, 10, 10, 10);
        gbcForm.fill = GridBagConstraints.HORIZONTAL;
        
        // Campo de Usuario
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setForeground(Color.WHITE);
        lblUsuario.setFont(new Font("Arial", Font.PLAIN, 16));
        gbcForm.gridx = 0;
        gbcForm.gridy = 0;
        formPanel.add(lblUsuario, gbcForm);
        
        JTextField txtUsuario = new JTextField();
        txtUsuario.setFont(new Font("Arial", Font.PLAIN, 16));
        txtUsuario.setBorder(BorderFactory.createLineBorder(new Color(0, 204, 102), 2));
        gbcForm.gridx = 1;
        gbcForm.gridy = 0;
        formPanel.add(txtUsuario, gbcForm);
        
        // Campo de Contraseña
        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setForeground(Color.WHITE);
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 16));
        gbcForm.gridx = 0;
        gbcForm.gridy = 1;
        formPanel.add(lblPassword, gbcForm);
        
        JPasswordField txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 16));
        txtPassword.setBorder(BorderFactory.createLineBorder(new Color(0, 204, 102), 2));
        gbcForm.gridx = 1;
        gbcForm.gridy = 1;
        formPanel.add(txtPassword, gbcForm);
        
        // Botón de Ingresar
        JButton btnLogin = new JButton("Ingresar");
        btnLogin.setFont(new Font("Arial", Font.BOLD, 16));
        btnLogin.setBackground(new Color(0, 204, 102));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorder(null);
        btnLogin.setPreferredSize(new Dimension(150, 50));
        btnLogin.addActionListener( e -> {
        	VentanaPrincipal v = new VentanaPrincipal();
        	v.setVisible(true);
        });
        gbcForm.gridx = 0;
        gbcForm.gridy = 2;
        formPanel.add(btnLogin, gbcForm);
        
        // Botón de Registrarse
        JButton btnRegister = new JButton("Registrarse");
        btnRegister.setFont(new Font("Arial", Font.BOLD, 16));
        btnRegister.setBackground(new Color(0, 204, 102));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setFocusPainted(false);
        btnRegister.setBorder(null);
        btnRegister.setPreferredSize(new Dimension(150, 50));
        gbcForm.gridx = 1;
        gbcForm.gridy = 2;
        formPanel.add(btnRegister, gbcForm);
        
        // Acción para abrir la ventana de registro
        btnRegister.addActionListener(e -> new Registro().setVisible(true));
        
        GridBagConstraints gbcFormContainer = new GridBagConstraints();
        gbcFormContainer.gridx = 0;
        gbcFormContainer.gridy = 1;
        gbcFormContainer.insets = new Insets(0, 0, 0, 0);
        gbcFormContainer.fill = GridBagConstraints.HORIZONTAL;
        gbcFormContainer.anchor = GridBagConstraints.CENTER;
        mainPanel.add(formPanel, gbcFormContainer);
        
        // Agregar el panel principal a la ventana
        add(mainPanel);
    }
    
    // Clase interna para redimensionar dinámicamente la imagen sin estirarla demasiado
    private static class ScaledImageLabel extends JLabel {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Image originalImage;
        
        public ScaledImageLabel(Image originalImage) {
            this.originalImage = originalImage;
            setHorizontalAlignment(SwingConstants.CENTER);
            setVerticalAlignment(SwingConstants.CENTER);
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (originalImage != null) {
                int compWidth = getWidth();
                int compHeight = getHeight();
                int imgWidth = originalImage.getWidth(null);
                int imgHeight = originalImage.getHeight(null);

                if (imgWidth > 0 && imgHeight > 0) {
                    // Calcular el factor de escala manteniendo la proporción
                    double scaleFactor = Math.min((double) compWidth / imgWidth, (double) compHeight / imgHeight);
                    int scaledWidth = (int) (imgWidth * scaleFactor);
                    int scaledHeight = (int) (imgHeight * scaleFactor);
                    
                    // Calcular la posición para centrar la imagen
                    int x = (compWidth - scaledWidth) / 2;
                    int y = (compHeight - scaledHeight) / 2;
                    
                    g.drawImage(originalImage, x, y, scaledWidth, scaledHeight, this);
                }
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login().setVisible(true));
    }
}