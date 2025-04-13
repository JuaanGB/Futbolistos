package pds.futbolistos.modelado;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "BLOQUES_DE_CONTENIDO")
public class BloqueDeContenido {

	// Atributos
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@OneToMany
	@JoinColumn(name="pregunta_id")
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
