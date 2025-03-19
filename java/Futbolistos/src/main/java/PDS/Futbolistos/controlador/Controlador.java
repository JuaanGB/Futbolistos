package PDS.Futbolistos.controlador;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import PDS.Futbolistos.modelado.BloqueDeContenido;
import PDS.Futbolistos.modelado.CatalogoCursos;
import PDS.Futbolistos.modelado.Curso;
import PDS.Futbolistos.modelado.Pregunta;
import PDS.Futbolistos.modelado.PreguntaObserver;
import PDS.Futbolistos.modelado.PreguntaTest;
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
		sesionCursoAct = new SesionCurso(c, e);
		// sesionCursoAct = usuarioAct.empezarCurso(c, e);
	}
	
	public boolean validarRespuesta(Pregunta p, String text) {
		boolean res = p.isRespuestaValida(text);
		// usuarioAct.registrarRespuesta(res); // No importa modificar las estadísticas porque no se persisten en BBDD hasta que guarda o termina el curso.
		return res;
	}
	
	public Pregunta pasarASiguientePregunta() {
		sesionCursoAct.removePrimeraPregunta();
		if (sesionCursoAct.quedanPreguntas()) {
			Pregunta p = sesionCursoAct.getPreguntaActual();
			return p;
		}
		return null;
	}
	
	public boolean quedanPistasDisponibles() { return sesionCursoAct.quedanPistasDisponibles(); }
	
	public void disminuirPistasDisponibles() { sesionCursoAct.disminuirPistasDisponibles(); }
	
	public List<Curso> getCursosDisponibles() {
		return CatalogoCursos.getInstancia().obtenerCursos();
	}

	// Métodos de prueba
	private void anadirCursos() {
		List<Curso> cursos = new LinkedList<>();

		// Crear curso
		Curso curso = new Curso(
		    "Técnicas Básicas de Fútbol",
		    "Aprende las técnicas esenciales para jugar al fútbol, \ncomo el pase, el regate y el disparo.",
		    "https://example.com/images/futbol_basico.jpg"
		);

		// Crear bloque de contenido
		BloqueDeContenido bloque = new BloqueDeContenido();

		// Agregar pregunta al bloque
		bloque.addPregunta(new PreguntaTest(
			    "¿Cuántos jugadores tiene un equipo de fútbol en el campo al inicio del partido?",
			    "11",
			    "Piensa en los titulares sin contar suplentes.",
			    10,
			    "10",
			    "11",
			    "12",
			    "9"
			));

			bloque.addPregunta(new PreguntaTest(
			    "¿Qué parte del cuerpo no pueden usar los jugadores de campo, excepto el portero?",
			    "Las manos",
			    "Es la principal diferencia entre el portero y el resto.",
			    10,
			    "Las manos",
			    "Los pies",
			    "La cabeza",
			    "El pecho"
			));

			bloque.addPregunta(new PreguntaTest(
			    "¿Cuántos minutos dura un partido de fútbol profesional sin contar el tiempo añadido?",
			    "90",
			    "Se divide en dos mitades iguales.",
			    10,
			    "80",
			    "90",
			    "100",
			    "120"
			));

			bloque.addPregunta(new PreguntaTest(
			    "¿Cómo se llama el tiro libre directo desde los once metros en el área rival?",
			    "Penalti",
			    "Se concede por una falta grave dentro del área.",
			    10,
			    "Falta",
			    "Córner",
			    "Penalti",
			    "Saque de banda"
			));

		// Agregar bloque al curso
		curso.addBloqueDeContenido(bloque);

		// Agregar curso a la lista
		cursos.add(curso);
		

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
