package PDS.Futbolistos.modelado;

import java.util.List;

import PDS.Futbolistos.modelado.estrategias.EstrategiaAprendizaje;

public class SesionCurso {

	// Atributos
	private final Curso curso;
	private final EstrategiaAprendizaje estrategia;
	private final List<Pregunta> preguntasRestantes; // Inicializada al orden concreto de la estrategia. Conforme respondemos las eliminamos.
	private int puntuacion; // 0 mal, 1 bien
	private int pistasRestantes;
	
	// Constructor
	public SesionCurso(Curso c, EstrategiaAprendizaje e) {
		this.curso = c;
		this.estrategia = e;
		this.preguntasRestantes = this.estrategia.calcularOrden(c.getBloquesDeContenido());
		this.puntuacion = 0;
		this.pistasRestantes = 3;
	}
	
	// Getters y setters
	public Curso getCurso() { return curso; }  
	public EstrategiaAprendizaje getEstrategia() { return estrategia; }  
	public List<Pregunta> getPreguntasRestantes() { return preguntasRestantes; }  
	public int getPuntuacion() { return puntuacion; }  
	public void setPuntuacion(int puntuacion) { this.puntuacion = puntuacion; }  
	public int getPistasRestantes() { return pistasRestantes; }

	
}
