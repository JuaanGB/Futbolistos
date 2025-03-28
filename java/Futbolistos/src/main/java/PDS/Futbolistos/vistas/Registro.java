package PDS.Futbolistos.vistas;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;
import PDS.Futbolistos.controlador.Controlador;
import PDS.Futbolistos.vistas.componentes.FactoriaComponentes;

public class Registro extends JFrame {
	private static final long serialVersionUID = 1L;
	private JLabel lblImage;
	private File selectedImageFile = null;

	// Patrón para fecha en formato dd/mm/yyyy
	private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{2}/\\d{2}/\\d{4}$");

	public Registro() {
		setTitle("Futbolistos - Registro");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(550, 500);
		setLocationRelativeTo(null); // Centrar la ventana

		// Establecer tamaño mínimo para que siempre se muestren todos los componentes
		setMinimumSize(new Dimension(800, 600));

		FactoriaComponentes.utilizarNimbusLookAndFeel();

		// Panel principal con fondo oscuro
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBackground(new Color(30, 30, 30));

		// Etiqueta de título para la app
		JLabel lblTitle = FactoriaComponentes.crearLabel("Futbolistos", SwingConstants.CENTER);
		mainPanel.add(lblTitle, BorderLayout.NORTH);

		// Panel del formulario usando GridBagLayout para un diseño moderno
		JPanel formPanel = new JPanel();
		formPanel.setBackground(new Color(30, 30, 30));
		formPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		// Nombre
		JLabel lblNombre = FactoriaComponentes.crearLabel("Nombre:", SwingConstants.RIGHT);
		JTextField txtNombre = FactoriaComponentes.crearTextField();

		// Apellido
		JLabel lblApellido = FactoriaComponentes.crearLabel("Apellido:", SwingConstants.RIGHT);
		JTextField txtApellido = FactoriaComponentes.crearTextField();

		// Usuario (nombre de usuario)
		JLabel lblUsuario = FactoriaComponentes.crearLabel("Usuario:", SwingConstants.RIGHT);
		JTextField txtUsuario = FactoriaComponentes.crearTextField();

		// Contraseña
		JLabel lblPassword = FactoriaComponentes.crearLabel("Contraseña:", SwingConstants.RIGHT);
		JPasswordField txtPassword = FactoriaComponentes.crearPasswordField();

		// Confirmar Contraseña
		JLabel lblConfirmPassword = FactoriaComponentes.crearLabel("Confirmar Contraseña:", SwingConstants.RIGHT);
		JPasswordField txtConfirmPassword = FactoriaComponentes.crearPasswordField();

		// Fecha de Nacimiento
		JLabel lblFechaNacimiento = FactoriaComponentes.crearLabel("Fecha de Nacimiento (dd/mm/yyyy):",
				SwingConstants.RIGHT);
		JTextField txtFechaNacimiento = FactoriaComponentes.crearTextField();

		// Saludo
		JLabel lblSaludo = FactoriaComponentes.crearLabel("Saludo (opcional):", SwingConstants.RIGHT);
		JTextArea txtSaludo = FactoriaComponentes.crearTextArea(3, 20);

		// Imagen (opcional)
		JLabel lblImagen = FactoriaComponentes.crearLabel("Imagen (opcional):", SwingConstants.RIGHT);
		JButton btnSeleccionarImagen = FactoriaComponentes.crearBoton("Seleccionar Imagen");
		lblImage = new JLabel();
		lblImage.setPreferredSize(new Dimension(100, 100));
		lblImage.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		// Botón Registrar
		JButton btnRegistrar = FactoriaComponentes.crearBoton("Registrar");

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
			if (result == JFileChooser.APPROVE_OPTION) {
				selectedImageFile = fileChooser.getSelectedFile();
				ImageIcon icon = new ImageIcon(selectedImageFile.getAbsolutePath());
				// Escalar la imagen para ajustarla al label
				Image img = icon.getImage().getScaledInstance(lblImage.getWidth(), lblImage.getHeight(),
						Image.SCALE_SMOOTH);
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
			if (nombre.isEmpty() || apellido.isEmpty() || usuario.isEmpty() || password.isEmpty()
					|| confirmPassword.isEmpty() || fechaNacimiento.isEmpty()) {
				JOptionPane.showMessageDialog(Registro.this, "Por favor, complete todos los campos obligatorios.",
						"Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Validar que las contraseñas coincidan
			if (!password.equals(confirmPassword)) {
				JOptionPane.showMessageDialog(Registro.this, "Las contraseñas no coinciden. Por favor, verifique.",
						"Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Validar el formato de la fecha
			if (!DATE_PATTERN.matcher(fechaNacimiento).matches()) {
				JOptionPane.showMessageDialog(Registro.this, "El formato de la fecha es incorrecto. Use dd/mm/yyyy.",
						"Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			LocalDate fecha;
			try {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				fecha = LocalDate.parse(fechaNacimiento, formatter);
			} catch (DateTimeParseException ex) {
				JOptionPane.showMessageDialog(Registro.this, "Fecha inválida.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Obtener la URL de la imagen seleccionada (opcional)
			String imagenURL = "";
			if (selectedImageFile != null) {
				imagenURL = selectedImageFile.getAbsolutePath();
			}

			// Utilizar el Controlador para registrar el usuario
			Controlador controlador = Controlador.getInstancia();
			if (controlador.registrar(nombre, apellido, usuario, password, saludo, imagenURL, fecha) == null) {
				JOptionPane.showMessageDialog(Registro.this, "El nombre de usuario ya existe. Por favor, elija otro.",
						"Error", JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(Registro.this, "Registro exitoso para el usuario: " + usuario,
						"Información", JOptionPane.INFORMATION_MESSAGE);
				dispose();
				new Login().setVisible(true);
			}
		});
	}
}