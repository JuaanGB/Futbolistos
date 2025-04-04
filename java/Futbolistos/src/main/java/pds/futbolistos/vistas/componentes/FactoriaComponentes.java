package pds.futbolistos.vistas.componentes;

import javax.swing.*;
import java.awt.*;

public class FactoriaComponentes {

	private static final Font LABEL_FONT = new Font("Arial", Font.PLAIN, 16);
	private static final Font FIELD_FONT = new Font("Arial", Font.PLAIN, 16);
	private static final Color BORDER_COLOR = new Color(0, 204, 102);
	private static final Color BUTTON_COLOR = new Color(0, 204, 102);
	private static final Color TEXT_COLOR = Color.WHITE;
	private static final Color BACKGROUND_COLOR = new Color(30, 30, 30);

	public static void utilizarNimbusLookAndFeel() {
		// Intentar utilizar Nimbus Look and Feel para un estilo moderno
		try {
			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// Si Nimbus no está disponible, se empleará la apariencia por defecto
		}

	}

	public static JLabel crearLabel(String texto) {
		return crearLabel(texto, 0);
	}

	public static JLabel crearLabel(String texto, int horizontalAlignment) {
		JLabel label = new JLabel(texto, horizontalAlignment);
		label.setForeground(TEXT_COLOR);
		label.setFont(LABEL_FONT);
		return label;
	}

	public static JTextField crearTextField() {
		JTextField textField = new JTextField();
		textField.setFont(FIELD_FONT);
		textField.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 2));
		return textField;
	}

	public static JPasswordField crearPasswordField() {
		JPasswordField passwordField = new JPasswordField();
		passwordField.setFont(FIELD_FONT);
		passwordField.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 2));
		return passwordField;
	}

	public static JTextArea crearTextArea(int filas, int columnas) {
		JTextArea textArea = new JTextArea(filas, columnas);
		textArea.setFont(FIELD_FONT);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 2));
		return textArea;
	}

	public static JButton crearBoton(String texto) {
		JButton boton = new JButton(texto);
		boton.setFont(new Font("Arial", Font.BOLD, 16));
		boton.setBackground(BUTTON_COLOR);
		boton.setForeground(TEXT_COLOR);
		boton.setFocusPainted(false);
		return boton;
	}
	
	public static JCheckBox crearCheckBox(String texto) {
        JCheckBox checkBox = new JCheckBox(texto);
        checkBox.setBackground(new Color(30, 30, 30));
        checkBox.setForeground(Color.WHITE);
        checkBox.setFocusPainted(false);
        checkBox.setFont(new Font("Arial", Font.PLAIN, 12));
        checkBox.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 2));
        return checkBox;
    }
}
