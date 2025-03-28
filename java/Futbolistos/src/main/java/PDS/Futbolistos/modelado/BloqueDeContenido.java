package PDS.Futbolistos.modelado;

import java.util.LinkedList;
import java.util.List;

public class BloqueDeContenido {

	// Atributos
	private final List<Pregunta> preguntas;
	
	// Constructor
	public BloqueDeContenido() {
		this.preguntas = new LinkedList<>();
	}
	
	// Getters y setters
	public List<Pregunta> getPreguntas() { return preguntas; }
	public boolean addPregunta(Pregunta p) { return preguntas.add(p); }
}
