package pds.futbolistos.vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import pds.futbolistos.vistas.componentes.FactoriaComponentes;

public class Registro extends JFrame {
	private static final long serialVersionUID = 1L;

	public Registro() {
		setTitle("Futbolistos - Registro");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setMinimumSize(new Dimension(500, 400));
		setLocationRelativeTo(null);

		FactoriaComponentes.utilizarNimbusLookAndFeel();

		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBackground(new Color(30, 30, 30));

		// Panel superior con logo e información
		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.setBackground(new Color(30, 30, 30));

		ImageIcon logo = new ImageIcon(getClass().getResource("/pds/futbolistos/imagenes/logo-futbolistos.png"));
		JLabel lblLogo = new JLabel(logo);
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
		topPanel.add(lblLogo, BorderLayout.NORTH);

		JLabel lblSubtitulo = FactoriaComponentes.crearLabel(
				"<html><div style='text-align:center;'>¡Date de alta y disfruta de<br>cursos interesantes sobre fútbol!</div></html>",
				SwingConstants.CENTER);
		lblSubtitulo.setForeground(Color.WHITE);
		topPanel.add(lblSubtitulo, BorderLayout.CENTER);

		mainPanel.add(topPanel, BorderLayout.NORTH);

		// Panel del formulario
		JPanel formPanel = new JPanel(new GridBagLayout());
		formPanel.setBackground(new Color(30, 30, 30));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
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

		// Botones
		JButton btnRegistrar = FactoriaComponentes.crearBoton("Registrar");
		JButton btnRegresar = FactoriaComponentes.crearBoton("Cerrar");

		btnRegistrar.setPreferredSize(new Dimension(150, 50));
		btnRegresar.setPreferredSize(new Dimension(150, 50));

		// Distribución del formulario
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

		// Panel de botones
		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
		panelBotones.setBackground(new Color(30, 30, 30));
		panelBotones.add(btnRegresar);
		panelBotones.add(btnRegistrar);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 3;
		gbc.anchor = GridBagConstraints.CENTER;
		formPanel.add(panelBotones, gbc);

		mainPanel.add(formPanel, BorderLayout.CENTER);
		add(mainPanel);

		// Mostrar/ocultar contraseñas
		ActionListener togglePasswordVisibility = e -> {
			JCheckBox source = (JCheckBox) e.getSource();
			if (source == chkMostrarPassword) {
				txtPassword.setEchoChar(chkMostrarPassword.isSelected() ? '\0' : '*');
			} else if (source == chkMostrarConfirmPassword) {
				txtConfirmPassword.setEchoChar(chkMostrarConfirmPassword.isSelected() ? '\0' : '*');
			}
		};

		chkMostrarPassword.addActionListener(togglePasswordVisibility);
		chkMostrarConfirmPassword.addActionListener(togglePasswordVisibility);

		// Acción Registrar
		ActionListener registrar = e -> {
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
		};
		
		btnRegistrar.addActionListener(registrar);
		txtUsuario.addActionListener(registrar);
		txtPassword.addActionListener(registrar);
		txtConfirmPassword.addActionListener(registrar);

		// Acción Regresar
		btnRegresar.addActionListener(e -> {
			dispose();
			new Login().setVisible(true);
		});
	}
}
