package PDS.Futbolistos.controlador;

import java.util.LinkedList;
import java.util.List;

import PDS.Futbolistos.modelado.CatalogoCursos;
import PDS.Futbolistos.modelado.Curso;
import PDS.Futbolistos.modelado.Pregunta;
import PDS.Futbolistos.modelado.SesionCurso;
import PDS.Futbolistos.modelado.Usuario;
import PDS.Futbolistos.modelado.estrategias.EstrategiaAprendizaje;

public class Controlador {

	private static Controlador instancia;
	
	private Usuario usuarioAct;
	private SesionCurso sesionCursoAct;

	public Controlador() {
		anadirCursos();
	}

	// Getters y setters
	public static Controlador getInstancia() {
		if (instancia == null)
			instancia = new Controlador();
		return instancia;
	}
	public Usuario getUsuarioAct() { return usuarioAct; }
	public SesionCurso getSesionCursoAct() { return sesionCursoAct; }
	

	// Funcionalidad
	public void empezarCurso(Curso c, EstrategiaAprendizaje e) {
		sesionCursoAct = usuarioAct.empezarCurso(c, e);
	}
	
	public boolean validarRespuesta(Pregunta p, String text) {
		boolean res = p.isRespuestaValida(text);
		sesionCursoAct.removePrimeraPregunta();
		return res;
	}
	
	public void actualizarPregunta() {
		Pregunta p = sesionCursoAct.getPreguntaActual();
		// Notificar a la ventana de curso del cambio de pregunta y mostrar el nuevo panel. Observer?
	}
	
	public List<Curso> getCursosDisponibles() {
		return CatalogoCursos.getInstancia().obtenerCursos();
	}

	// Métodos de prueba
	private void anadirCursos() {
		List<Curso> cursos = new LinkedList<>();
		cursos.add(new Curso("Técnicas Básicas de Fútbol",
				"Aprende las técnicas esenciales para jugar al fútbol, \ncomo el pase, el regate y el disparo.",
				"https://example.com/images/futbol_basico.jpg"));

		cursos.add(new Curso("Estrategias Tácticas en Fútbol",
				"Conoce las tácticas de juego más importantes, como el 4-4-2, el 4-3-3 y las formaciones defensivas.",
				"https://example.com/images/estrategia_futbol.jpg"));

		cursos.add(new Curso("Entrenamiento Físico para Futbolistas",
				"Un curso enfocado en el acondicionamiento físico para futbolistas, que abarca resistencia, velocidad y fuerza.",
				"https://example.com/images/entrenamiento_futbol.jpg"));

		cursos.add(new Curso("Psicología Deportiva en el Fútbol",
				"Aprende a gestionar la mentalidad y la motivación de los futbolistas para mejorar su rendimiento en el campo.",
				"https://example.com/images/psicologia_deportiva.jpg"));
		
		for (Curso c : cursos) CatalogoCursos.getInstancia().agregarCurso(c);
	}

}
