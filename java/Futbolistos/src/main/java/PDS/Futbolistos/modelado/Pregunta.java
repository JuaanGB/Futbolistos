package PDS.Futbolistos.modelado;

import java.util.HashSet;
import java.util.Set;

public class Pregunta {

	// Atributos
	private final String enunciado;
	private final Set<String> respuestas;
	private final String respuestaCorrecta;
	private final String pista;
	private final int segundos;
	
	// Constructor
	public Pregunta(String enunciado, String respuestaCorrecta, String pista, int segundos, String ... respuestas) {
		this.enunciado = enunciado;
		this.respuestaCorrecta = respuestaCorrecta;
		this.respuestas = new HashSet<>();
		this.respuestas.add(respuestaCorrecta);
		for (String r : respuestas) {
			this.respuestas.add(r);
		}
		this.pista = pista;
		this.segundos = segundos;
	}
	
	// Getters y setters
	public String getEnunciado() { return enunciado; }  
	public Set<String> getRespuestas() { return respuestas; }  
	public String getRespuestaCorrecta() { return respuestaCorrecta; }  
	public String getPista() { return pista; }  
	public int getSegundos() { return segundos; }  

}
