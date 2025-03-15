package PDS.Futbolistos.modelado;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

public class PreguntaTest extends Pregunta {

	// Atributos
	private Set<String> respuestas;
	
	public PreguntaTest(String enunciado, String respuestaCorrecta, String pista, int segundos, String ...respuestas) {
		super(enunciado, respuestaCorrecta, pista, segundos);
		this.respuestas = new HashSet<>();
		for (String r : respuestas) this.respuestas.add(r);
	}

	@Override
	public boolean isRespuestaValida(String respuesta) {
		return respuesta.equals(this.getRespuestaCorrecta());
	}

	@Override
	public JPanel getPanel() {
		// Algo como:
		// return new PanelVentanaTest(this)
		return null;
	}

}
