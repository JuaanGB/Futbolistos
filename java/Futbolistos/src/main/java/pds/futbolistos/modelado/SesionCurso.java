package pds.futbolistos.modelado;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import pds.futbolistos.modelado.estrategias.EstrategiaAprendizaje;

@Entity
@Table(name = "SESIONES_CURSO")
public class SesionCurso {

	// Atributos
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "curso_id")
	private Curso curso;
	@Transient // No es necesario porque la estrategia ya va implícita en el orden de las preguntas
	private EstrategiaAprendizaje estrategia;
	@ManyToMany
	@JoinTable(name = "SESIONES_CURSO_PREGUNTAS_RESTANTES")
	private List<Pregunta> preguntasRestantes; // Inicializada al orden concreto de la estrategia. Conforme respondemos las eliminamos.
	private int puntuacion; // 0 mal, 1 bien
	private int pistasRestantes, numeroPreguntasRespondidas, numTotalPreguntas;
	
	// Constructor
	public SesionCurso() {
		this.preguntasRestantes = new ArrayList<>();
	}
	
	public SesionCurso(Curso c, EstrategiaAprendizaje e) {
		this.curso = c;
		this.estrategia = e;
		this.preguntasRestantes = this.estrategia.calcularOrden(c);
		this.puntuacion = 0;
		this.pistasRestantes = 3;
		this.numeroPreguntasRespondidas = 0;
		this.numTotalPreguntas = preguntasRestantes.size(); // Por si la estrategia hace repetir preguntas
	}
	
	// Getters y setters
	public Curso getCurso() {
		return curso;
	}

	public EstrategiaAprendizaje getEstrategia() {
		return estrategia;
	}

	public Pregunta getPreguntaActual() {
		return preguntasRestantes.get(0);
	}

	public List<Pregunta> getPreguntasRestantes() {
		return Collections.unmodifiableList(preguntasRestantes);
	}

	public int getPuntuacion() {
		return puntuacion;
	}

	public int getPistasRestantes() {
		return pistasRestantes;
	}

	public void setPistasRestantes(int num) {
		this.pistasRestantes = num;
	} // Necesario para test

	public int getNumeroPreguntasRespondidas() {
		return numeroPreguntasRespondidas;
	}

	public int getNumTotalPreguntas() {
		return numTotalPreguntas;
	}

	public void setEstadisticas(int puntuacion, int numPreguntasResp, int pistasDisponibles) {
		this.pistasRestantes = pistasDisponibles;
		this.puntuacion = puntuacion;
		this.numeroPreguntasRespondidas = numPreguntasResp;
	}

	// Funcionalidad
	public Pregunta pasarASiguientePregunta() {
		removePrimeraPregunta();
		if (quedanPreguntas()) {
			return getPreguntaActual();
		}
		return null;	
	}
	
	public void incrementarPuntuacion(int i) {
		this.puntuacion += i;
	}

	public void removePrimeraPregunta() {
		preguntasRestantes.remove(0);
		numeroPreguntasRespondidas++;
	}

	public boolean quedanPreguntas() {
		return preguntasRestantes.size() > 0;
	}

	public boolean quedanPistasDisponibles() {
		return pistasRestantes > 0;
	}

	public void disminuirPistasDisponibles() {
		pistasRestantes--;
	}

	public boolean hasCurso(Curso c) {
		return this.curso.equals(c);
	}
	

	
}
