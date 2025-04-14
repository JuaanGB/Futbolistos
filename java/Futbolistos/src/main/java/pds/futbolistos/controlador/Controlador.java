package pds.futbolistos.controlador;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import pds.futbolistos.factorias.FactoriaEstrategiasAprendizaje;
import pds.futbolistos.modelado.BloqueDeContenido;
import pds.futbolistos.modelado.CatalogoCursos;
import pds.futbolistos.modelado.Curso;
import pds.futbolistos.modelado.Pregunta;
import pds.futbolistos.modelado.PreguntaCompletar;
import pds.futbolistos.modelado.PreguntaFlashcard;
import pds.futbolistos.modelado.PreguntaTest;
import pds.futbolistos.modelado.RepositorioUsuario;
import pds.futbolistos.modelado.SesionCurso;
import pds.futbolistos.modelado.Usuario;
import pds.futbolistos.modelado.estrategias.EstrategiaAprendizaje;

public class Controlador {

	private static Controlador instancia;

	// Variable global para el repositorio de usuarios
	private final RepositorioUsuario repositorio;
	private final CatalogoCursos catalogoCursos;
	private final FactoriaEstrategiasAprendizaje factoriaEstrategias;
	private final EntityManagerFactory emf;
	private final EntityManager em;

	private Usuario usuarioAct;
	private SesionCurso sesionCursoAct;

	private Controlador() {
		// Se inicializa el repositorio una sola vez, evitando su definición en cada
		// método
		emf = Persistence.createEntityManagerFactory("ejemplo");
		em = emf.createEntityManager();
		repositorio = RepositorioUsuario.getUnicainstancia();
		repositorio.setEmf(emf);
		factoriaEstrategias = FactoriaEstrategiasAprendizaje.getInstancia();
		catalogoCursos = CatalogoCursos.getInstancia();
		anadirCursos();
	}

	public Controlador(RepositorioUsuario repoUsuarios, CatalogoCursos repoCursos) {
		this.repositorio = repoUsuarios;
		this.catalogoCursos = repoCursos;
		this.factoriaEstrategias = FactoriaEstrategiasAprendizaje.getInstancia();
		this.emf = Persistence.createEntityManagerFactory("ejemplo");
		this.em = emf.createEntityManager();
	}

	// Getters y setters
	public static Controlador getInstancia() {
		if (instancia == null)
			instancia = new Controlador();
		return instancia;
	}

	public void setUsuarioAct(Usuario u) {
		this.usuarioAct = u;
	}

	public Usuario getUsuarioAct() {
		return usuarioAct;
	}

	public SesionCurso getSesionCursoAct() {
		return sesionCursoAct;
	}

	public void setSesionCursoAct(SesionCurso sc) {
		this.sesionCursoAct = sc;
	}
	
	public void iniciarTransaccion() {
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
	}

	public void cerrarTransaccion() {
		if (em.getTransaction().isActive()) {
			em.getTransaction().commit();
		}
	}

	public void manejarError() {
		if (em.getTransaction().isActive()) {
			em.getTransaction().rollback();
		}
	}

	public void cerrarEntityManager() {
		if (em.isOpen()) {
			em.close();
		}
	}

	// CASO DE USO: INICIAR SESIÓN EN EL SISTEMA
	public Usuario autenticar(String nombreUsuario, String contraseña) {
		Usuario u = repositorio.getUsuario(nombreUsuario);
		if (u != null && u.checkContraseña(contraseña)) {
			this.usuarioAct = u;
			try {
				iniciarTransaccion();
				em.find(Usuario.class, usuarioAct.getNombreUsuario());
				cerrarTransaccion();
			} catch (Exception e) {
				manejarError();
				System.err.println("Error al autenticar.");
				e.printStackTrace();
			}
			return u;
		}
		return null;
	}

	// CASO DE USO: REGISTRARSE EN EL SISTEMA
	public boolean registrar(String usuario, String contraseña) {
		if (repositorio.existeNombre(usuario)) {
			return false;
		}
		usuarioAct = repositorio.añadirUsuario(usuario, contraseña);
		return true;
	}

	// CASO DE USO: CARGAR CURSOS DISPONIBLES
	public List<Curso> getCursosDisponibles() {
		return catalogoCursos.obtenerCursos();
	}

	// CASO DE USO: SELECCIONAR CURSO
	public Set<String> getEstrategias() {
		return factoriaEstrategias.getEstrategias();
	}

	public EstrategiaAprendizaje getEstrategia(String estrategiaSeleccionada) {
		return factoriaEstrategias.obtenerEstrategia(estrategiaSeleccionada);
	}

	public void empezarCurso(Curso c, EstrategiaAprendizaje e) {
		sesionCursoAct = usuarioAct.empezarCurso(c, e);
	}

	// CASO DE USO: REALIZAR CURSO
	public boolean quedanPistasDisponibles() {
		return sesionCursoAct.quedanPistasDisponibles();
	}

	public void disminuirPistasDisponibles() {
		sesionCursoAct.disminuirPistasDisponibles();
	}

	public boolean validarRespuesta(Pregunta p, String text) {
		boolean correcta = p.isRespuestaValida(text);
		if (correcta)
			sesionCursoAct.incrementarPuntuacion(1);
		return correcta;
	}

	public Pregunta pasarASiguientePregunta() {
		return sesionCursoAct.pasarASiguientePregunta();
	}

	// TODO: CASO DE USO: GUARDAR PROGRESO DEL CURSO
	public void guardarProgresoCurso(SesionCurso sc) {
		try {
			iniciarTransaccion();
			em.persist(sc); // Persistimos la sesión
			em.detach(sc); // Desacoplamos para que no se persista automáticamente en el futuro
			cerrarTransaccion();
		} catch (Exception e) {
			manejarError();
			System.err.println("Error al guardar progreso del curso");
			e.printStackTrace();
		}
	}
	
	public boolean usuarioHasSesion(Curso c) {
		return usuarioAct.hasSesion(c, em);
	}
	
	// TODO: CASO DE USO: MOSTRAR ESTADÍSTICAS DE USUARIO
	
	// TODO: CASO DE USO: IMPORTAR CURSO
	public boolean importarCurso(File f) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Curso c = objectMapper.readValue(f, Curso.class);
			// Persistir el curso al completo
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	// CASO DE USO: ACTUALIZAR ESTADÍSTICAS DE USUARIO (al acabar el curso)
	public void actualizarEstadisticasUsuario(boolean completado) {
		try {
			iniciarTransaccion();
			usuarioAct.actualizarEstadisticas(sesionCursoAct, completado); // El usuario ya formaba parte del contexto al autenticar
			usuarioAct.removeSesion(sesionCursoAct);
			cerrarTransaccion();
		} catch (Exception e) {
			manejarError();
			System.err.println("Error al terminar curso (actualizando estadísticas de usuario)");
			e.printStackTrace();
		}
	}

	// Métodos de prueba para añadir cursos de ejemplo
	private void anadirCursos() {
        List<Curso> cursos = new ArrayList<>();

        // Crear curso
        Curso curso = new Curso(
            "Técnicas Básicas de Fútbol",
            "Aprende las técnicas esenciales para jugar al fútbol, \ncomo el pase, el regate y el disparo.",
            "https://example.com/images/futbol_basico.jpg"
        );

        // Crear bloque de contenido
        BloqueDeContenido bloque = new BloqueDeContenido();

		bloque.addPregunta(new PreguntaFlashcard(15, "¿Qué número de camiseta lleva Lionel Messi en el PSG?",
				"Lionel Messi lleva el número 30 en el PSG."));
        
        // Agregar preguntas al bloque
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
                null,
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
        
			bloque.addPregunta(new PreguntaCompletar("¿Quién ganó la Copa del Mundo de la FIFA 2018?", // Enunciado
					"Francia", // Respuesta correcta
					"Es un país europeo con una rica historia futbolística", // Pista
					10 // Tiempo límite en segundos
			));
			
			bloque.addPregunta(new PreguntaCompletar("¿Quién es el mejor jugador de todos los tiempos?", // Enunciado
					"Lionel Messi", // Respuesta correcta
					"Es un jugador argentino", // Pista
					10 // Tiempo límite en segundos
			));

        // Agregar bloque al curso
        curso.addBloqueDeContenido(bloque);
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
        
        for (Curso c : cursos) {
            CatalogoCursos.getInstancia().agregarCurso(c);
        }
    }

}