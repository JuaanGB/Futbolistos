package PDS.Futbolistos.modelado;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

public abstract class Pregunta {

	// Atributos
	private final String enunciado;
	private final String respuestaCorrecta;
	private final String pista;
	private final int segundos;
	
	// Constructor
	public Pregunta(String enunciado, String respuestaCorrecta, String pista, int segundos) {
		this.enunciado = enunciado;
		this.respuestaCorrecta = respuestaCorrecta;
		this.pista = pista;
		this.segundos = segundos;
	}
	
	// Getters y setters
	public String getEnunciado() { return enunciado; }  
	public String getRespuestaCorrecta() { return respuestaCorrecta; }  
	public String getPista() { return pista; }  
	public int getSegundos() { return segundos; }
	
	// Funcionalidad
	public abstract boolean isRespuestaValida(String respuesta);
	public abstract JPanel getPanel();
	public boolean hasPista() {	return pista != null; }


}
