package pds.futbolistos.modelado;

import java.util.Collections;
import java.util.List;

import pds.futbolistos.modelado.estrategias.EstrategiaAprendizaje;

public class SesionCurso {

	// Atributos
	private final Curso curso;
	private final EstrategiaAprendizaje estrategia;
	private final List<Pregunta> preguntasRestantes; // Inicializada al orden concreto de la estrategia. Conforme respondemos las eliminamos.
	private int puntuacion; // 0 mal, 1 bien
	private int pistasRestantes, numeroPreguntasRespondidas, numTotalPreguntas;
	
	// Constructor
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
	

	
}
