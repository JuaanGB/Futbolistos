package pds.futbolistos.modelado;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import pds.futbolistos.modelado.convertidores.ConversorListaDeCadenas;
import pds.futbolistos.vistas.componentes.PanelPreguntaTest;

@Entity
@Table(name = "PREGUNTAS_TEST")
public class PreguntaTest extends Pregunta /* implements Visitable */ {

	// Atributos
	@Column(name = "respuestas")
	@Convert(converter = ConversorListaDeCadenas.class)
	@JsonProperty
	private List<String> respuestas;
	@Lob
	@JsonProperty("respuesta_correcta")
	private String respuestaCorrecta;

	public PreguntaTest() {
		this.respuestas = new ArrayList<>();
	}

	public PreguntaTest(String enunciado, String respuestaCorrecta, String pista, int segundos, String... respuestas) {
		super(enunciado, pista, segundos);
		this.respuestaCorrecta = respuestaCorrecta;
		this.respuestas = new ArrayList<>();
		for (String r : respuestas)
			this.respuestas.add(r);
	}

	// Nuevos métodos get
	public String getRespuesta(int i) {
		return respuestas.get(i);
	}

	public String getRespuestaCorrecta() {
		return respuestaCorrecta;
	}
	
	public int getNumRespuestas() {
		return respuestas.size();
	}

	@Override
	public boolean isRespuestaValida(String respuesta) {
		return respuesta.equals(this.getRespuestaCorrecta());
	}

	@Override
	public JPanel getPanel() {
		return new PanelPreguntaTest(this);
	}

	@Override
	public boolean checkParsing() {
		boolean res = super.checkParsing();

		res = res && hasEnunciado() && getEnunciado() != null && !getEnunciado().isBlank();

		res = res && respuestaCorrecta != null && !respuestaCorrecta.isBlank();

		res = res && respuestas.size() >= 2;
		res = res && respuestas.size() <= 4;
		res = res && respuestas.stream().allMatch(r -> r != null && !r.isBlank());

		res = res && respuestas.contains(respuestaCorrecta);

		return res;
	}

	/*
	 * public void accept(Visitor v) { v.visitPreguntaTest(this); }
	 * 
	 * map.put(PreguntaTest.class, () )
	 */
}
