package PDS.Futbolistos.modelado;

import java.util.List;

import PDS.Futbolistos.modelado.estrategias.EstrategiaAprendizaje;

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
		this.preguntasRestantes = this.estrategia.calcularOrden(c.getBloquesDeContenido());
		this.puntuacion = 0;
		this.pistasRestantes = 3;
		this.numeroPreguntasRespondidas = 0;
		this.numTotalPreguntas = preguntasRestantes.size();
	}
	
	// Getters y setters
	public Curso getCurso() { return curso; }  
	public EstrategiaAprendizaje getEstrategia() { return estrategia; }
	public Pregunta getPreguntaActual() { return preguntasRestantes.get(0); }
	public List<Pregunta> getPreguntasRestantes() { return preguntasRestantes; }  
	public int getPuntuacion() { return puntuacion; }  
	public void setPuntuacion(int puntuacion) { this.puntuacion = puntuacion; }  
	public int getPistasRestantes() { return pistasRestantes; }
	public int getNumeroPreguntasRespondidas() { return numeroPreguntasRespondidas; }
	public int getNumTotalPreguntas() { return numTotalPreguntas; }
	
	// Funcionalidad
	public void incrementarPuntuacion(int i) { this.puntuacion += i; }
	public void removePrimeraPregunta() { preguntasRestantes.remove(0); numeroPreguntasRespondidas++; }
	public boolean quedanPreguntas() { return preguntasRestantes.size() > 0; }

	
}
