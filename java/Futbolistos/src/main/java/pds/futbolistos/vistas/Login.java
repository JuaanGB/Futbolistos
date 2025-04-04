package pds.futbolistos.vistas;

import javax.swing.*;

import pds.futbolistos.controlador.Controlador;
import pds.futbolistos.modelado.Usuario;
import pds.futbolistos.vistas.componentes.FactoriaComponentes;

import java.awt.*;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;

	public Login() {
		setTitle("Futbolistos - Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700, 450);
		setLocationRelativeTo(null); // Centrar la ventana en la pantalla

		FactoriaComponentes.utilizarNimbusLookAndFeel();

		// Panel principal con GridBagLayout para centrar la interfaz
		JPanel mainPanel = new JPanel(new GridBagLayout());
		mainPanel.setBackground(new Color(30, 30, 30));
		GridBagConstraints gbcMain = new GridBagConstraints();
		gbcMain.insets = new Insets(20, 20, 20, 20);
		gbcMain.gridx = 0;
		gbcMain.fill = GridBagConstraints.BOTH;
		gbcMain.anchor = GridBagConstraints.CENTER;
		gbcMain.weightx = 1.0;
		gbcMain.weighty = 1.0;

		// Etiqueta que contiene el logo
		ImageIcon icon = new ImageIcon(getClass().getResource("/pds/futbolistos/imagenes/futbolistos.png"));
		Image image = icon.getImage();
		JLabel lblImage = new ScaledImageLabel(image);
		gbcMain.gridy = 0;
		mainPanel.add(lblImage, gbcMain);

		// Panel del formulario de login
		JPanel formPanel = new JPanel(new GridBagLayout());
		formPanel.setBackground(new Color(30, 30, 30));
		GridBagConstraints gbcForm = new GridBagConstraints();
		gbcForm.insets = new Insets(10, 10, 10, 10);
		gbcForm.fill = GridBagConstraints.HORIZONTAL;

		// Campo de usuario
		JLabel lblUsuario = FactoriaComponentes.crearLabel("Usuario:", SwingConstants.RIGHT);
		JTextField txtUsuario = FactoriaComponentes.crearTextField();
		gbcForm.gridx = 0;
		gbcForm.gridy = 0;
		formPanel.add(lblUsuario, gbcForm);
		gbcForm.gridx = 1;
		gbcForm.gridy = 0;
		formPanel.add(txtUsuario, gbcForm);

		// Campo de contraseña
		JLabel lblPassword = FactoriaComponentes.crearLabel("Contraseña:", SwingConstants.RIGHT);
		JPasswordField txtPassword = FactoriaComponentes.crearPasswordField();
		gbcForm.gridx = 0;
		gbcForm.gridy = 1;
		formPanel.add(lblPassword, gbcForm);
		gbcForm.gridx = 1;
		gbcForm.gridy = 1;
		formPanel.add(txtPassword, gbcForm);

		// Botón de Ingresar
		JButton btnLogin = FactoriaComponentes.crearBoton("Ingresar");
		btnLogin.setPreferredSize(new Dimension(150, 50));
		gbcForm.gridx = 0;
		gbcForm.gridy = 2;
		formPanel.add(btnLogin, gbcForm);

		// Botón de Registrarse
		JButton btnRegister = FactoriaComponentes.crearBoton("Registrarse");
		btnRegister.setPreferredSize(new Dimension(150, 50));
		gbcForm.gridx = 1;
		gbcForm.gridy = 2;
		formPanel.add(btnRegister, gbcForm);

		// Acción para abrir la ventana de registro
		btnRegister.addActionListener(e -> {new Registro().setVisible(true); this.dispose(); });

		// Añadir el formulario al panel principal
		GridBagConstraints gbcFormContainer = new GridBagConstraints();
		gbcFormContainer.gridx = 0;
		gbcFormContainer.gridy = 1;
		gbcFormContainer.fill = GridBagConstraints.HORIZONTAL;
		gbcFormContainer.anchor = GridBagConstraints.CENTER;
		mainPanel.add(formPanel, gbcFormContainer);

		// Agregar el panel principal a la ventana
		add(mainPanel);

		// Lógica de login que utiliza el Controlador para acceder al repositorio
		btnLogin.addActionListener(e -> {
			String usuario = txtUsuario.getText().trim();
			String password = new String(txtPassword.getPassword());

			if (usuario.isEmpty() || password.isEmpty()) {
				JOptionPane.showMessageDialog(Login.this, "¡Debe llenar todos los campos!", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			Controlador controlador = Controlador.getInstancia();
			// Se asume que el Controlador tiene el método "autenticar" que retorna el
			// Usuario si las
			// credenciales son válidas o null en caso contrario.
			Usuario u = controlador.autenticar(usuario, password);
			if (u != null) {
				JOptionPane.showMessageDialog(Login.this, "Inicio de sesión exitoso", "Información",
						JOptionPane.INFORMATION_MESSAGE);
				new VentanaPrincipal().setVisible(true);
				dispose(); // Cerrar ventana de login
			} else {
				JOptionPane.showMessageDialog(Login.this, "Credenciales incorrectas", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});
	}

	// Clase interna para redimensionar la imagen manteniendo la proporción
	private static class ScaledImageLabel extends JLabel {
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
					double scaleFactor = Math.min((double) compWidth / imgWidth, (double) compHeight / imgHeight);
					int scaledWidth = (int) (imgWidth * scaleFactor);
					int scaledHeight = (int) (imgHeight * scaleFactor);
					int x = (compWidth - scaledWidth) / 2;
					int y = (compHeight - scaledHeight) / 2;
					g.drawImage(originalImage, x, y, scaledWidth, scaledHeight, this);
				}
			}
		}
	}
}
