package PDS.Futbolistos;

import PDS.Futbolistos.controlador.Controlador;
import PDS.Futbolistos.modelado.CatalogoCursos;
import PDS.Futbolistos.modelado.Curso;
import PDS.Futbolistos.modelado.Pregunta;
import PDS.Futbolistos.modelado.PreguntaTest;
import PDS.Futbolistos.modelado.RepositorioUsuario;
import PDS.Futbolistos.modelado.SesionCurso;
import PDS.Futbolistos.modelado.Usuario;
import PDS.Futbolistos.modelado.estrategias.EstrategiaAprendizaje;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

/**
 * Pruebas de la clase Controlador utilizando JUnit 5.11.4.
 */
public class ControladorTest {

	private static final String USUARIO_EXISTENTE = "usuario1";
	private static final String CONTRASEÑA_USUARIO_EXISTENTE = "contraseña1";
	private static final String NUEVO_USUARIO = "nuevo";
	private static final String CONTRASEÑA_NUEVO_USUARIO = "nuevapass";

	private Controlador controlador;
	private RepositorioUsuario catalogoMock;
	private SesionCurso sesionCursoMock;
	private CatalogoCursos catalogoCursosMock;
	private Curso cursoMock;
	private EstrategiaAprendizaje estrategiaMock;

	@BeforeEach
	public void setUp() {

		catalogoCursosMock = mock(CatalogoCursos.class);
		catalogoMock = mock(RepositorioUsuario.class);
		controlador = new Controlador(catalogoMock, catalogoCursosMock);
		sesionCursoMock = mock(SesionCurso.class);
		estrategiaMock = mock(EstrategiaAprendizaje.class);

		// Simulamos que hay un usuario en el repositorio
		when(catalogoMock.existeNombre(USUARIO_EXISTENTE)).thenReturn(true);
		when(catalogoMock.getUsuario(USUARIO_EXISTENTE))
				.thenReturn(new Usuario(USUARIO_EXISTENTE, CONTRASEÑA_USUARIO_EXISTENTE));

		// simulamos el comportamiento del repositorio ante el registro de un usuario
		// nuevo
		when(catalogoMock.existeNombre(NUEVO_USUARIO)).thenReturn(false);
		when(catalogoMock.añadirUsuario(NUEVO_USUARIO, CONTRASEÑA_NUEVO_USUARIO))
				.thenReturn(new Usuario(NUEVO_USUARIO, CONTRASEÑA_NUEVO_USUARIO));
		when(catalogoMock.getUsuario(NUEVO_USUARIO)).thenReturn(null);

		// Comportamiento de la estrategia
		when(estrategiaMock.calcularOrden(cursoMock)).thenReturn(new LinkedList<>());
		
	}

	/* ----------- TEST DE AUTENTICAR ----------------- */
	@Test
	public void testAutenticar_UsuarioExistente_Correcta() {

		Usuario autenticado = controlador.autenticar(USUARIO_EXISTENTE, CONTRASEÑA_USUARIO_EXISTENTE);
		assertNotNull(autenticado, "El usuario debería poder autenticarse si existe y la contraseña es correcta");
		assertEquals(USUARIO_EXISTENTE, autenticado.getNombreUsuario(),
				"El nombre de usuario autenticado debería coincidir");
		verify(catalogoMock, times(1)).getUsuario(USUARIO_EXISTENTE);
	}

	@Test
	public void testAutenticar_UsuarioExistente_Incorrecta() {

		Usuario autenticado = controlador.autenticar(USUARIO_EXISTENTE, "contraseñaIncorrecta");
		assertNull(autenticado, "El usuario no debería poder autenticarse con una contraseña incorrecta");
		verify(catalogoMock, times(1)).getUsuario(USUARIO_EXISTENTE);
	}

	@Test
	public void testAutenticar_UsuarioNoExistente() {

		Usuario autenticado = controlador.autenticar(NUEVO_USUARIO, "");
		assertNull(autenticado, "El usuario no debería poder autenticarse si no existe");
		verify(catalogoMock, times(1)).getUsuario(NUEVO_USUARIO);
	}

	/* ----------- TESTS DE REGISTRO ---------- */

	@Test
	public void testRegistrar_UsuarioExistente() {

		boolean res = controlador.registrar(USUARIO_EXISTENTE, "");
		assertFalse(res, "Ya existe un usuario con ese nombre.");
		verify(catalogoMock, times(1)).existeNombre(USUARIO_EXISTENTE);
	}

	@Test
	public void testRegistrar_UsuarioNoExistente() {

		boolean res = controlador.registrar(NUEVO_USUARIO, "");
		assertTrue(res, "No existe un usuario con ese nombre.");
		verify(catalogoMock, times(1)).existeNombre(NUEVO_USUARIO);
	}

	@Test
	public void testGetCursosDisponibles() {

		Curso curso1 = new Curso("Curso 1", "Descripción del Curso 1", null);
		Curso curso2 = new Curso("Curso 2", "Descripción del Curso 2", null);
		List<Curso> cursos = List.of(curso1, curso2);

		when(catalogoCursosMock.obtenerCursos()).thenReturn(cursos);

		List<Curso> cursosDisponibles = controlador.getCursosDisponibles();

		assertNotNull(cursosDisponibles);
		assertEquals(2, cursosDisponibles.size());
		verify(catalogoCursosMock, times(1)).obtenerCursos();
	}

	@Test
	public void testEmpezarCurso() {
		
		controlador.autenticar(USUARIO_EXISTENTE, CONTRASEÑA_USUARIO_EXISTENTE); // Necesito un usuario para añadirle la sesion
		controlador.empezarCurso(cursoMock, estrategiaMock);

		assertNotNull(controlador.getSesionCursoAct(), "La sesión de curso no debe ser nula después de empezar el curso");

		assertEquals(cursoMock, controlador.getSesionCursoAct().getCurso(), "El curso de la sesión debería ser el correcto");
		assertEquals(estrategiaMock, controlador.getSesionCursoAct().getEstrategia(),
				"La estrategia de la sesión debería ser la correcta");

	}

	/*
	 * 
	 * @Test public void testEmpezarCursoYValidarSesion() { Curso primerCurso =
	 * controlador.getCursosDisponibles().get(0); EstrategiaAprendizaje e =
	 * controlador.getEstrategia(controlador.getEstrategias().iterator().next());
	 * 
	 * controlador.empezarCurso(primerCurso, e);
	 * assertNotNull(controlador.getSesionCursoAct(),
	 * "La sesión del curso debería haberse inicializado"); }
	 * 
	 * @Test public void testQuedanPistasDisponibles() { Curso primerCurso =
	 * controlador.getCursosDisponibles().get(0); EstrategiaAprendizaje e =
	 * controlador.getEstrategia(controlador.getEstrategias().iterator().next());
	 * controlador.empezarCurso(primerCurso, e);
	 * 
	 * assertTrue(controlador.quedanPistasDisponibles(),
	 * "Debería haber pistas inicialmente (depende de la configuración de la sesión)"
	 * ); }
	 * 
	 * @Test public void testValidarRespuesta() { Curso primerCurso =
	 * controlador.getCursosDisponibles().get(0); EstrategiaAprendizaje e =
	 * controlador.getEstrategia(controlador.getEstrategias().iterator().next());
	 * controlador.empezarCurso(primerCurso, e);
	 * 
	 * Pregunta preguntaActual =
	 * controlador.getSesionCursoAct().getPreguntaActual();
	 * assertNotNull(preguntaActual,
	 * "Debe existir al menos una pregunta en el curso de prueba");
	 * 
	 * if (preguntaActual instanceof PreguntaTest) { String respuestaCorrecta =
	 * ((PreguntaTest) preguntaActual).getRespuestaCorrecta(); boolean resultado =
	 * controlador.validarRespuesta(preguntaActual, respuestaCorrecta);
	 * assertTrue(resultado, "La respuesta correcta debería validarse como true"); }
	 * }
	 * 
	 * @Test public void testPasarASiguientePregunta() { Curso primerCurso =
	 * controlador.getCursosDisponibles().get(0); EstrategiaAprendizaje e =
	 * controlador.getEstrategia(controlador.getEstrategias().iterator().next());
	 * controlador.empezarCurso(primerCurso, e);
	 * 
	 * Pregunta pregunta1 = controlador.getSesionCursoAct().getPreguntaActual();
	 * assertNotNull(pregunta1, "Debería haber una pregunta inicial");
	 * 
	 * Pregunta pregunta2 = controlador.pasarASiguientePregunta(); if (pregunta2 ==
	 * null) { assertNull(pregunta2, "No hay más preguntas, vuelve null"); } else {
	 * assertNotEquals(pregunta1, pregunta2,
	 * "La siguiente pregunta debería ser distinta a la anterior"); } }
	 * 
	 * @Test public void testActualizarEstadisticasUsuario() { Usuario auth =
	 * controlador.autenticar("john", "123"); assertNotNull(auth,
	 * "Necesitamos un usuario válido para actualizar estadísticas");
	 * 
	 * Curso primerCurso = controlador.getCursosDisponibles().get(0);
	 * EstrategiaAprendizaje e =
	 * controlador.getEstrategia(controlador.getEstrategias().iterator().next());
	 * controlador.empezarCurso(primerCurso, e);
	 * 
	 * controlador.actualizarEstadisticasUsuario(true); assertTrue(true,
	 * "Proceso de actualización de estadísticas completado (no generó excepciones)"
	 * ); }
	 * 
	 */
}
