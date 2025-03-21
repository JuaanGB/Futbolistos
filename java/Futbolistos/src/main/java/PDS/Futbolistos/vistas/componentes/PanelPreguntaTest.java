package PDS.Futbolistos.vistas.componentes;

import PDS.Futbolistos.controlador.Controlador;
import PDS.Futbolistos.modelado.Pregunta;
import PDS.Futbolistos.modelado.PreguntaTest;
import javax.swing.JOptionPane;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JButton;

public class PanelPreguntaTest extends PanelPregunta {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static int NUM_RESPUESTAS = 4;

    private JButton btnRespuesta1, btnRespuesta2, btnRespuesta3, btnRespuesta4;
    private List<JButton> botones;

    public PanelPreguntaTest(Pregunta p) {
        super(p);
        setBackground(new Color(30, 30, 30)); // Fondo oscuro
        añadirAcciones(p);
    }

    private void añadirAcciones(Pregunta p) {
        for (JButton boton : botones) {
            boton.addActionListener(e -> {
                detenerTemporizador(true);
                if (Controlador.getInstancia().validarRespuesta(p, boton.getText())) {
                    JOptionPane.showMessageDialog(this, "Respuesta correcta", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Respuesta incorrecta.\nLa respuesta correcta era:\n" + p.getRespuestaCorrecta(),
                            "Fallo.", JOptionPane.ERROR_MESSAGE);
                }
                this.manejarTiempoTerminado(true);
            });
        }
    }

    @Override
    protected void personalizarDisplay(Pregunta p) {
        super.personalizarDisplay(p);
        PreguntaTest pt = (PreguntaTest) p;
        for (int i = 0; i < NUM_RESPUESTAS; i++) {
            botones.get(i).setText(pt.getRespuesta(i));
        }
    }

    @Override
    protected void inicializarComponentes() {
        super.inicializarComponentes();

        // Configuración del panel de respuestas
        panelRespuestas.setLayout(new GridLayout(2, 2, 10, 10));
        panelRespuestas.setBackground(new Color(30, 30, 30));

        // Estilo de botones
        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        Color buttonBg = new Color(0, 204, 102);
        Color buttonFg = Color.WHITE;

        btnRespuesta1 = crearBotonEstilizado("Respuesta 1", buttonFont, buttonBg, buttonFg);
        btnRespuesta2 = crearBotonEstilizado("Respuesta 2", buttonFont, buttonBg, buttonFg);
        btnRespuesta3 = crearBotonEstilizado("Respuesta 3", buttonFont, buttonBg, buttonFg);
        btnRespuesta4 = crearBotonEstilizado("Respuesta 4", buttonFont, buttonBg, buttonFg);

        panelRespuestas.add(btnRespuesta1);
        panelRespuestas.add(btnRespuesta2);
        panelRespuestas.add(btnRespuesta3);
        panelRespuestas.add(btnRespuesta4);

        botones = List.of(btnRespuesta1, btnRespuesta2, btnRespuesta3, btnRespuesta4);
    }

    private JButton crearBotonEstilizado(String texto, Font font, Color bg, Color fg) {
        JButton boton = new JButton(texto);
        boton.setFont(font);
        boton.setBackground(bg);
        boton.setForeground(fg);
        boton.setFocusPainted(false);
        boton.setBorder(null);
        boton.setPreferredSize(new Dimension(200, 50));
        return boton;
    }

    @Override
    protected void gestionarPreguntaRespondida(boolean respondida) {
    	if (!respondida) {
            JOptionPane.showMessageDialog(this, 
                "¡Tiempo agotado! La respuesta correcta era:\n");
    	}
    }
    
}