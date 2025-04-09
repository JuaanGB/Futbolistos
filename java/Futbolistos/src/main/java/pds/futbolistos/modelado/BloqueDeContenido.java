package pds.futbolistos.modelado;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class BloqueDeContenido {

	// Atributos
	private final List<Pregunta> preguntas;

	// Constructor
	public BloqueDeContenido() {
		this.preguntas = new ArrayList<>();
	}

	// Getters y setters
	public List<Pregunta> getPreguntas() {
		return Collections.unmodifiableList(preguntas);
	}

	public boolean addPregunta(Pregunta p) {
		return preguntas.add(p);
	}
}
