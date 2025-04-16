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
import pds.futbolistos.bd.BaseDeDatos;
import pds.futbolistos.factorias.FactoriaEstrategiasAprendizaje;
import pds.futbolistos.modelado.BloqueDeContenido;
import pds.futbolistos.modelado.Curso;
import pds.futbolistos.modelado.Pregunta;
import pds.futbolistos.modelado.PreguntaCompletar;
import pds.futbolistos.modelado.PreguntaFlashcard;
import pds.futbolistos.modelado.PreguntaTest;
import pds.futbolistos.modelado.SesionCurso;
import pds.futbolistos.modelado.Usuario;
import pds.futbolistos.modelado.estrategias.EstrategiaAprendizaje;

public class Controlador {

	private static Controlador instancia;

	private final FactoriaEstrategiasAprendizaje factoriaEstrategias;
	private final BaseDeDatos bbdd;

	private Usuario usuarioAct;
	private SesionCurso sesionCursoAct;

	private Controlador() {
		bbdd = new BaseDeDatos();
		factoriaEstrategias = FactoriaEstrategiasAprendizaje.getInstancia();
	}

	public Controlador(BaseDeDatos bbdd) {
	
		this.factoriaEstrategias = FactoriaEstrategiasAprendizaje.getInstancia();
		this.bbdd = bbdd;
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

	// CASO DE USO: INICIAR SESIÓN EN EL SISTEMA
	public Usuario autenticar(String nombreUsuario, String contraseña) {
		Usuario u = bbdd.getUsuario(nombreUsuario);
		if (u != null && u.checkContraseña(contraseña)) {
			this.usuarioAct = u;
			return u;
		}
		return null;
	}

	// CASO DE USO: REGISTRARSE EN EL SISTEMA
	public boolean registrar(String usuario, String contraseña) {
		if (bbdd.existeUsuario(usuario)) {
			return false;
		}
		usuarioAct = bbdd.addUsuario(usuario, contraseña);
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
		bbdd.guardarProgresoCurso(usuarioAct, sc);
	}
	
	public SesionCurso reanudarCurso(Curso c) {
		sesionCursoAct = usuarioAct.getSesionComenzada(c);
		return sesionCursoAct;
	}
	
	public boolean usuarioHasSesion(Curso c) {
		return bbdd.usuarioHasSesion(usuarioAct, c);
	}
	
	// TODO: CASO DE USO: MOSTRAR ESTADÍSTICAS DE USUARIO
	
	// TODO: CASO DE USO: IMPORTAR CURSO
	public boolean importarCurso(File f) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Curso c = objectMapper.readValue(f, Curso.class);
			bbdd.usuarioImportaCurso(usuarioAct, c);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	// CASO DE USO: ACTUALIZAR ESTADÍSTICAS DE USUARIO (al acabar el curso)
	public void actualizarEstadisticasUsuario(boolean completado) {
		try {
			bbdd.actualizarEstadisticasDeUsuario(usuarioAct, sesionCursoAct);
		} catch (Exception e) {
			System.err.println("Error al terminar curso (actualizando estadísticas de usuario)");
			e.printStackTrace();
		}
	}

}