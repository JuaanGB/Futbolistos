package pds.futbolistos.vistas.componentes;

import javax.swing.*;

import pds.futbolistos.controlador.Controlador;
import pds.futbolistos.modelado.SesionCurso;
import pds.futbolistos.vistas.VentanaPrincipal;

import static pds.futbolistos.vistas.componentes.FactoriaComponentes.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelEstadisticasCurso extends JPanel {
	private static final long serialVersionUID = 1L;

	private JLabel lblPuntuacion;
	private JLabel lblPreguntasRestantes;
	private JLabel lblPistasRestantes;
	private JLabel lblPreguntasRespondidas;
	private JButton btnRegresar;

	public PanelEstadisticasCurso() {
		setLayout(new GridLayout(5, 2, 10, 10));
		setBackground(new Color(30, 30, 30));
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE),
				null, 0, 0, new Font("Arial", Font.BOLD, 14), Color.WHITE));

		// Crear labels con estilo
		lblPuntuacion = crearLabel("");
		lblPreguntasRestantes = crearLabel("");
		lblPistasRestantes = crearLabel("");
		lblPreguntasRespondidas = crearLabel("");

		// Crear botón "Regresar"
		btnRegresar = new JButton("Regresar");
		btnRegresar.setBackground(new Color(200, 50, 50));
		btnRegresar.setForeground(Color.WHITE);
		btnRegresar.setFont(new Font("Arial", Font.BOLD, 14));
		btnRegresar.setFocusPainted(false);
		btnRegresar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

		// Acción del botón "Regresar"
		btnRegresar.addActionListener( e -> {
			Controlador.getInstancia().actualizarEstadisticasUsuario(true);
			JFrame ventanaCurso = (JFrame) SwingUtilities.getWindowAncestor(PanelEstadisticasCurso.this);
            if (ventanaCurso != null) ventanaCurso.dispose();
            new VentanaPrincipal().setVisible(true);
		});

		// Añadir componentes al panel
		add(crearLabel("<html>Puntuación:<br></html>"));
		add(lblPuntuacion);
		add(crearLabel("<html>Preguntas<br>restantes:</html>"));
		add(lblPreguntasRestantes);
		add(crearLabel("<html>Pistas<br>disponibles:</html>"));
		add(lblPistasRestantes);
		add(crearLabel("<html>Preguntas<br>respondidas:</html>"));
		add(lblPreguntasRespondidas);
		add(new JLabel()); // Espacio vacío para la alineación
		add(btnRegresar);

		// Cargar estadísticas iniciales
		actualizarEstadisticas();
	}

	public void actualizarEstadisticas() {
		SesionCurso sesion = Controlador.getInstancia().getSesionCursoAct();
		if (sesion != null) {
			lblPuntuacion.setText(String.valueOf(sesion.getPuntuacion()));
			lblPreguntasRestantes.setText(String.valueOf(sesion.getPreguntasRestantes().size()));
			lblPistasRestantes.setText(String.valueOf(sesion.getPistasRestantes()));
			lblPreguntasRespondidas.setText(String.valueOf(sesion.getNumeroPreguntasRespondidas()));
		}
	}
}
