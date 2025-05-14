package pds.futbolistos.vistas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import pds.futbolistos.controlador.Controlador;
import pds.futbolistos.modelado.Usuario;
import pds.futbolistos.vistas.componentes.FactoriaComponentes;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;

	public Login() {
		setTitle("Futbolistos - Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450, 580);
		setMinimumSize(new Dimension(450, 580));
		setLocationRelativeTo(null); // Centrar la ventana

		FactoriaComponentes.utilizarNimbusLookAndFeel();

		JPanel mainPanel = new JPanel(new GridBagLayout());
		mainPanel.setBackground(new Color(30, 30, 30));
		GridBagConstraints gbcMain = new GridBagConstraints();
		gbcMain.insets = new Insets(20, 20, 20, 20);
		gbcMain.gridx = 0;
		gbcMain.fill = GridBagConstraints.BOTH;
		gbcMain.anchor = GridBagConstraints.CENTER;
		gbcMain.weightx = 1.0;
		gbcMain.weighty = 1.0;

		// Logo normal
		ImageIcon icon = new ImageIcon(getClass().getResource("/pds/futbolistos/imagenes/futbolistos-50.png"));
		JLabel lblImage = new JLabel(icon);
		lblImage.setHorizontalAlignment(SwingConstants.CENTER);
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
		formPanel.add(txtUsuario, gbcForm);

		// Campo de contraseña
		JLabel lblPassword = FactoriaComponentes.crearLabel("Contraseña:", SwingConstants.RIGHT);
		JPasswordField txtPassword = FactoriaComponentes.crearPasswordField();
		txtPassword.setEchoChar('*');
		gbcForm.gridx = 0;
		gbcForm.gridy = 1;
		formPanel.add(lblPassword, gbcForm);
		gbcForm.gridx = 1;
		formPanel.add(txtPassword, gbcForm);

		// Checkbox para mostrar/ocultar contraseña
		JCheckBox chkMostrarPassword = FactoriaComponentes.crearCheckBox("👁");
		gbcForm.gridx = 2;
		formPanel.add(chkMostrarPassword, gbcForm);

		chkMostrarPassword.addActionListener(e -> {
			txtPassword.setEchoChar(chkMostrarPassword.isSelected() ? '\0' : '*');
		});

		// Botón de Registrarse (izquierda)
		JButton btnRegister = FactoriaComponentes.crearBoton("Registrarse");
		btnRegister.setPreferredSize(new Dimension(150, 50));
		gbcForm.gridx = 0;
		gbcForm.gridy = 2;
		formPanel.add(btnRegister, gbcForm);

		// Botón de Ingresar (derecha)
		JButton btnLogin = FactoriaComponentes.crearBoton("Ingresar");
		btnLogin.setPreferredSize(new Dimension(150, 50));
		gbcForm.gridx = 1;
		gbcForm.gridy = 2;
		formPanel.add(btnLogin, gbcForm);

		// Acción para abrir ventana de registro
		btnRegister.addActionListener(e -> {
			new Registro().setVisible(true);
			this.dispose();
		});

		// Añadir formulario al panel principal
		GridBagConstraints gbcFormContainer = new GridBagConstraints();
		gbcFormContainer.gridx = 0;
		gbcFormContainer.gridy = 1;
		gbcFormContainer.fill = GridBagConstraints.HORIZONTAL;
		gbcFormContainer.anchor = GridBagConstraints.CENTER;
		mainPanel.add(formPanel, gbcFormContainer);

		// Agregar el panel principal a la ventana
		add(mainPanel);

		// Acción del botón de login
		ActionListener accionLogin = e -> {
			String usuario = txtUsuario.getText().trim();
			String password = new String(txtPassword.getPassword());

			if (usuario.isEmpty() || password.isEmpty()) {
				JOptionPane.showMessageDialog(Login.this, "¡Debe llenar todos los campos!", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			Controlador controlador = Controlador.getInstancia();
			Usuario u = controlador.autenticar(usuario, password);
			if (u != null) {
				JOptionPane.showMessageDialog(Login.this, "Inicio de sesión exitoso", "Información",
						JOptionPane.INFORMATION_MESSAGE);
				new VentanaPrincipal().setVisible(true);
				dispose();
			} else {
				JOptionPane.showMessageDialog(Login.this, "Credenciales incorrectas", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		};
		btnLogin.addActionListener(accionLogin);
		txtUsuario.addActionListener(accionLogin);
		txtPassword.addActionListener(accionLogin);
	}
}
