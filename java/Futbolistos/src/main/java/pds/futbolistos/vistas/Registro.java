package pds.futbolistos.vistas;

import javax.swing.*;

import pds.futbolistos.controlador.Controlador;
import pds.futbolistos.vistas.componentes.FactoriaComponentes;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Registro extends JFrame {
	private static final long serialVersionUID = 1L;

	public Registro() {
		setTitle("Futbolistos - Registro");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400, 250); // Ajuste de tamaño
		setLocationRelativeTo(null); // Centrar la ventana
		setResizable(false);

		FactoriaComponentes.utilizarNimbusLookAndFeel();

		// Panel principal
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBackground(new Color(30, 30, 30));

		// Panel del formulario
		JPanel formPanel = new JPanel(new GridBagLayout());
		formPanel.setBackground(new Color(30, 30, 30));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		// Usuario
		JLabel lblUsuario = FactoriaComponentes.crearLabel("Usuario:", SwingConstants.RIGHT);
		JTextField txtUsuario = FactoriaComponentes.crearTextField();

		// Contraseña
		JLabel lblPassword = FactoriaComponentes.crearLabel("Contraseña:", SwingConstants.RIGHT);
		JPasswordField txtPassword = FactoriaComponentes.crearPasswordField();
		JCheckBox chkMostrarPassword = FactoriaComponentes.crearCheckBox("👁");

		// Confirmar Contraseña
		JLabel lblConfirmPassword = FactoriaComponentes.crearLabel("Confirmar:", SwingConstants.RIGHT);
		JPasswordField txtConfirmPassword = FactoriaComponentes.crearPasswordField();
		JCheckBox chkMostrarConfirmPassword = FactoriaComponentes.crearCheckBox("👁");

		// Botón Registrar
		JButton btnRegistrar = FactoriaComponentes.crearBoton("Registrar");

		// Distribución de los componentes
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.3;
		formPanel.add(lblUsuario, gbc);
		gbc.gridx = 1;
		gbc.weightx = 0.7;
		formPanel.add(txtUsuario, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0.3;
		formPanel.add(lblPassword, gbc);
		gbc.gridx = 1;
		gbc.weightx = 0.6;
		formPanel.add(txtPassword, gbc);
		gbc.gridx = 2;
		gbc.weightx = 0.1;
		formPanel.add(chkMostrarPassword, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 0.3;
		formPanel.add(lblConfirmPassword, gbc);
		gbc.gridx = 1;
		gbc.weightx = 0.6;
		formPanel.add(txtConfirmPassword, gbc);
		gbc.gridx = 2;
		gbc.weightx = 0.1;
		formPanel.add(chkMostrarConfirmPassword, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 3;
		gbc.anchor = GridBagConstraints.CENTER;
		formPanel.add(btnRegistrar, gbc);

		mainPanel.add(formPanel, BorderLayout.CENTER);
		add(mainPanel);

		// Acción para mostrar/ocultar contraseñas
		ActionListener togglePasswordVisibility = e -> {
			JCheckBox source = (JCheckBox) e.getSource();
			if (source == chkMostrarPassword) {
				txtPassword.setEchoChar(chkMostrarPassword.isSelected() ? '\0' : '·');
			} else if (source == chkMostrarConfirmPassword) {
				txtConfirmPassword.setEchoChar(chkMostrarConfirmPassword.isSelected() ? '\0' : '·');
			}
		};

		chkMostrarPassword.addActionListener(togglePasswordVisibility);
		chkMostrarConfirmPassword.addActionListener(togglePasswordVisibility);

		// Acción para el botón 'Registrar'
		btnRegistrar.addActionListener(e -> {
			String usuario = txtUsuario.getText().trim();
			String password = new String(txtPassword.getPassword());
			String confirmPassword = new String(txtConfirmPassword.getPassword());

			if (usuario.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
				JOptionPane.showMessageDialog(Registro.this, "Por favor, complete todos los campos.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (!password.equals(confirmPassword)) {
				JOptionPane.showMessageDialog(Registro.this, "Las contraseñas no coinciden.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Intentar registrar el usuario
			Controlador controlador = Controlador.getInstancia();
            if (!controlador.registrar(usuario, password)) {
                JOptionPane.showMessageDialog(Registro.this, "El usuario ya existe. Elija otro.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(Registro.this, "Registro exitoso para el usuario: " + usuario, "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new Login().setVisible(true);
            }
		});
	}
}
