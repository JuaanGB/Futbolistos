package pds.futbolistos.controlador;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
	
	private String ficheroBd = "prueba-1.db";
	private Map<String, String> properties = new HashMap<>();
	
	private final EntityManagerFactory emf;
	private final EntityManager em;

	private Usuario usuarioAct;
	private SesionCurso sesionCursoAct;

	private Controlador() {
		// Se inicializa el repositorio una sola vez, evitando su definición en cada
		// método
		properties.put("hibernate.connection.url", "jdbc:sqlite:" + ficheroBd);
		emf = Persistence.createEntityManagerFactory("ejemplo", properties);
		em = emf.createEntityManager();
		repositorio = RepositorioUsuario.getUnicainstancia();
		repositorio.setEmf(emf);
		factoriaEstrategias = FactoriaEstrategiasAprendizaje.getInstancia();
		catalogoCursos = CatalogoCursos.getInstancia();
	}

	public Controlador(RepositorioUsuario repoUsuarios, CatalogoCursos repoCursos) {
		this.repositorio = repoUsuarios;
		this.catalogoCursos = repoCursos;
		this.factoriaEstrategias = FactoriaEstrategiasAprendizaje.getInstancia();
		this.emf = Persistence.createEntityManagerFactory("ejemplo");
		this.em = emf.createEntityManager();
		properties.put("hibernate.connection.url", "jdbc:sqlite:" + ficheroBd);
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
				usuarioAct = em.find(Usuario.class, usuarioAct.getNombreUsuario());
				Hibernate.initialize(usuarioAct.getCursosImportados());
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
		return usuarioAct.getCursosImportados();
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
			em.persist(usuarioAct);
			em.persist(sc); // Persistimos la sesión
			cerrarTransaccion();
			
		} catch (Exception e) {
			manejarError();
			System.err.println("Error al guardar progreso del curso");
			e.printStackTrace();
		}
	}
	
	public SesionCurso reanudarCurso(Curso c) {
		sesionCursoAct = usuarioAct.getSesionComenzada(c);
		return sesionCursoAct;
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
			em.getTransaction().begin();
			em.persist(usuarioAct);
			usuarioAct.addCursoImportado(c); // Persiste el curso por el CascadeType.ALL en Usuario
			em.getTransaction().commit();
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

}