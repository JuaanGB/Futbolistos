package pds.futbolistos.modelado;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.swing.JPanel;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import pds.futbolistos.vistas.componentes.PanelPreguntaCompletar;

@Entity
@Table(name = "PREGUNTAS_COMPLETAR")
public class PreguntaCompletar extends Pregunta {

	private static final float PORCENTAJE_REVELADO = 0.25f;
	@Lob
	@JsonProperty("respuesta_correcta")
	private String respuestaCorrecta;

	public PreguntaCompletar() {

	}

	public PreguntaCompletar(String enunciado, String respuestaCorrecta, String pista, int segundos) {
		super(enunciado, pista, segundos);
		this.respuestaCorrecta = respuestaCorrecta;
	}

	public String getRespuestaCorrecta() {
		return respuestaCorrecta;
	}

	@Override
	public boolean isRespuestaValida(String respuesta) {
		return respuesta.trim().replaceAll("\\s+", " ").equalsIgnoreCase(this.getRespuestaCorrecta().trim());
	}

	@Override
	public JPanel getPanel() {
		return new PanelPreguntaCompletar(this);
	}

	public String getCadenaOculta() {
		String respuesta = this.getRespuestaCorrecta();
		int longitud = respuesta.length();

		int letrasARevelar = Math.max(1, (int) (longitud * PORCENTAJE_REVELADO));
		Set<Integer> indicesRevelados = new HashSet<>();
		Random rand = new Random();
		while (indicesRevelados.size() < letrasARevelar) {
			int indice = rand.nextInt(longitud);
			if (Character.isLetter(respuesta.charAt(indice)))
				indicesRevelados.add(indice);
		}

		StringBuilder cadena = new StringBuilder();
		for (int i = 0; i < longitud; i++) {
			char c = respuesta.charAt(i);
			if (Character.isWhitespace(c)) {
				cadena.append("   ");
			} else if (indicesRevelados.contains(i)) {
				cadena.append(Character.toUpperCase(c)).append(" ");
			} else {
				cadena.append("_ ");
			}
		}

		return cadena.toString().trim().toUpperCase();
	}

	@Override
	public boolean checkParsing() {
		boolean res = super.checkParsing();

		res = res && getEnunciado() != null && !getEnunciado().isBlank();
		res = res && respuestaCorrecta != null && !respuestaCorrecta.isBlank();

		return res;
	}


}
