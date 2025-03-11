package PDS.Futbolistos.modelado;

import java.util.HashSet;
import java.util.Set;

public class BloqueDeContenido {

	// Atributos
	private final Set<Pregunta> preguntas;
	
	// Constructor
	public BloqueDeContenido() {
		this.preguntas = new HashSet<>();
	}
	
	// Getters y setters
	public Set<Pregunta> getPreguntas() { return preguntas; }
	public boolean addPregunta(Pregunta p) { return preguntas.add(p); }
}
