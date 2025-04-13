package pds.futbolistos.vistas.componentes;

import javax.swing.*;

import pds.futbolistos.controlador.Controlador;
import pds.futbolistos.factorias.FactoriaEstrategiasAprendizaje;
import pds.futbolistos.modelado.Curso;
import pds.futbolistos.modelado.SesionCurso;
import pds.futbolistos.modelado.estrategias.EstrategiaAprendizaje;
import pds.futbolistos.modelado.estrategias.EstrategiaSecuencial;
import pds.futbolistos.vistas.VentanaCurso;
import pds.futbolistos.vistas.componentes.FactoriaComponentes;

import java.awt.*;
import java.util.ResourceBundle.Control;
import java.util.Set;

public class PanelCurso extends JPanel {

	private static final long serialVersionUID = 1L;

	public PanelCurso(Curso curso) {
		// Utilizamos fondo oscuro que sigue la estética de VentanaPrincipal
		setBackground(new Color(30, 30, 30));
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createLineBorder(Color.WHITE, 1)); // Borde blanco
		setPreferredSize(new Dimension(200, 100)); // Tamaño fijo para que las celdas sean iguales

		// Panel para la imagen (si existe)
		JLabel imagenLabel = new JLabel();

		// Nombre del curso con estilo acorde a la estética
		JLabel nombreLabel = FactoriaComponentes.crearLabel(curso.getNombre());
		nombreLabel.setHorizontalAlignment(SwingConstants.CENTER);

		// Botón "i" para información con transparencia y sin relleno
		JButton infoButton = FactoriaComponentes.crearBoton("");
		infoButton.setIcon(new ImageIcon(PanelCurso.class.getResource("/pds/futbolistos/imagenes/letra-i.png")));
		infoButton.addActionListener(e -> mostrarInformacion(curso));
		infoButton.setContentAreaFilled(false);
		infoButton.setBorderPainted(false);

		// Botón "Comenzar" con estilo estandarizado y de mayor tamaño
		JButton comenzarButton = FactoriaComponentes.crearBoton("Comenzar");
		comenzarButton.setPreferredSize(new Dimension(150, 50));
		comenzarButton.addActionListener(e -> comprobarCursoGuardadoOIniciar(curso) );

		// Panel para los botones con fondo acorde
		JPanel botonesPanel = new JPanel();
		botonesPanel.setBackground(new Color(30, 30, 30));
		botonesPanel.add(infoButton);
		botonesPanel.add(comenzarButton);

		// Agregar componentes al panel
		add(imagenLabel, BorderLayout.WEST);
		add(nombreLabel, BorderLayout.CENTER);
		add(botonesPanel, BorderLayout.SOUTH);
	}
	
	private void comprobarCursoGuardadoOIniciar(Curso c) {
		
		if (Controlador.getInstancia().usuarioHasSesion(c)) {
			SesionCurso sc = Controlador.getInstancia().getUsuarioAct().getSesionComenzada(c);
			reanudarSesionCurso(sc);
		} else {
			iniciarCurso(c);
		}
	}
	
	private void reanudarSesionCurso(SesionCurso sc) {
		
		JOptionPane.showMessageDialog(
			    this,
			    "Se reanudará la sesión del curso previamente guardada.",
			    "Reanudando sesión",
			    JOptionPane.INFORMATION_MESSAGE
			);

			VentanaCurso vc = new VentanaCurso(sc);
			vc.setVisible(true);

			JFrame ventana = (JFrame) SwingUtilities.getWindowAncestor(PanelCurso.this);
			if (ventana != null) {
			    ventana.dispose();
			}

	}

	private void iniciarCurso(Curso c) {
		
		Set<String> estrategias = Controlador.getInstancia().getEstrategias();
		JComboBox<String> comboBox = new JComboBox<>(estrategias.toArray(new String[0]));

		JPanel panel = new JPanel();
		panel.add(new JLabel("Selecciona una estrategia:"));
		panel.add(comboBox);

		int option = JOptionPane.showConfirmDialog(this, panel, "Seleccionar Estrategia", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);

		if (option == JOptionPane.OK_OPTION) {
			String estrategiaSeleccionada = (String) comboBox.getSelectedItem();

			EstrategiaAprendizaje estrategia = Controlador.getInstancia().getEstrategia(estrategiaSeleccionada);

			if (estrategia != null) {
				Controlador.getInstancia().empezarCurso(c, estrategia);
				VentanaCurso vc = new VentanaCurso(Controlador.getInstancia().getSesionCursoAct());
				vc.setVisible(true);
				JFrame ventana = (JFrame) SwingUtilities.getWindowAncestor(PanelCurso.this);
				if (ventana != null)
					ventana.dispose();
			} else {
				JOptionPane.showMessageDialog(this, "Error al seleccionar la estrategia", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void mostrarInformacion(Curso c) {
		JOptionPane.showMessageDialog(this, c.getDescripcion());
	}

}
