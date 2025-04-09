package pds.futbolistos.modelado;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JPanel;

import pds.futbolistos.vistas.componentes.PanelPreguntaTest;

public class PreguntaTest extends Pregunta /* implements Visitable */ {

	// Atributos
	private List<String> respuestas;
	
	public PreguntaTest(String enunciado, String respuestaCorrecta, String pista, int segundos, String ...respuestas) {
		super(enunciado, respuestaCorrecta, pista, segundos);
		this.respuestas = new ArrayList<>();
		for (String r : respuestas) this.respuestas.add(r);
	}

	// Nuevos métodos get
	public String getRespuesta(int i) {
		return respuestas.get(i);
	}
	
	@Override
	public boolean isRespuestaValida(String respuesta) {
		return respuesta.equals(this.getRespuestaCorrecta());
	}

	@Override
	public JPanel getPanel() {
		return new PanelPreguntaTest(this);
	}
	/*
	public void accept(Visitor v) {
		v.visitPreguntaTest(this);
	}

	map.put(PreguntaTest.class, () )
	*/
}
