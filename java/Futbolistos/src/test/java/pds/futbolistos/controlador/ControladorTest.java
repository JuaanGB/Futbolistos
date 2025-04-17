package pds.futbolistos.controlador;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import pds.futbolistos.bd.BaseDeDatos;
import pds.futbolistos.controlador.Controlador;
import pds.futbolistos.modelado.BloqueDeContenido;
import pds.futbolistos.modelado.Curso;
import pds.futbolistos.modelado.Pregunta;
import pds.futbolistos.modelado.PreguntaTest;
import pds.futbolistos.modelado.SesionCurso;
import pds.futbolistos.modelado.Usuario;
import pds.futbolistos.modelado.estrategias.EstrategiaAprendizaje;
import pds.futbolistos.modelado.estrategias.EstrategiaSecuencial;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Stream;


/**
 * Pruebas de la clase Controlador utilizando JUnit 5.11.4.
 */
public class ControladorTest {

	private static final String USUARIO_EXISTENTE = "usuario1";
	private static final String CONTRASEÑA_USUARIO_EXISTENTE = "contraseña1";
	private static final String NUEVO_USUARIO = "nuevo";
	private static final String CONTRASEÑA_NUEVO_USUARIO = "nuevapass";

	private Controlador controlador;
	
	// Servicios con mock
	private BaseDeDatos bbddMock;
	
	// Instancias del modelo de dominio "falsas" para pruebas
	private Usuario usuarioFalso;
	private SesionCurso sesionCursoFalsa;
	private Curso cursoFalso;
	private EstrategiaAprendizaje estrategiaFalsa;

	@BeforeEach
	public void setUp() {

		// Mocks de servicios
		bbddMock = Mockito.mock(BaseDeDatos.class);
		
		controlador = new Controlador(bbddMock);
		usuarioFalso = new Usuario(USUARIO_EXISTENTE, CONTRASEÑA_USUARIO_EXISTENTE);
		estrategiaFalsa = new EstrategiaSecuencial();

		cursoFalso = new Curso("Curso 1", "Descripción del Curso 1", null);
		BloqueDeContenido b = new BloqueDeContenido();
		cursoFalso.addBloqueDeContenido(b);

		controlador.setUsuarioAct(usuarioFalso); // Necesito un usuario para añadirle la sesion
		
		// Simulamos que hay un usuario en el repositorio
		when(bbddMock.existeUsuario(USUARIO_EXISTENTE)).thenReturn(true);
		when(bbddMock.getUsuario(USUARIO_EXISTENTE))
				.thenReturn(usuarioFalso);

		// simulamos el comportamiento del repositorio ante el registro de un usuario
		// nuevo
		when(bbddMock.existeUsuario(NUEVO_USUARIO)).thenReturn(false);
		when(bbddMock.addUsuario(NUEVO_USUARIO, CONTRASEÑA_NUEVO_USUARIO))
				.thenReturn(new Usuario(NUEVO_USUARIO, CONTRASEÑA_NUEVO_USUARIO));
		when(bbddMock.getUsuario(NUEVO_USUARIO)).thenReturn(null);
		
	}

	/* ----------- TEST DE AUTENTICAR ----------------- */
	@Test
	public void testAutenticar_UsuarioExistente_Correcta() {

		Usuario autenticado = controlador.autenticar(USUARIO_EXISTENTE, CONTRASEÑA_USUARIO_EXISTENTE);
		assertNotNull(autenticado, "El usuario debería poder autenticarse si existe y la contraseña es correcta");
		assertEquals(USUARIO_EXISTENTE, autenticado.getNombreUsuario(),
				"El nombre de usuario autenticado debería coincidir");
		verify(bbddMock, times(1)).getUsuario(USUARIO_EXISTENTE);
	}

	@Test
	public void testAutenticar_UsuarioExistente_Incorrecta() {

		Usuario autenticado = controlador.autenticar(USUARIO_EXISTENTE, "contraseñaIncorrecta");
		assertNull(autenticado, "El usuario no debería poder autenticarse con una contraseña incorrecta");
		verify(bbddMock, times(1)).getUsuario(USUARIO_EXISTENTE);
	}

	@Test
	public void testAutenticar_UsuarioNoExistente() {

		Usuario autenticado = controlador.autenticar(NUEVO_USUARIO, "");
		assertNull(autenticado, "El usuario no debería poder autenticarse si no existe");
		verify(bbddMock, times(1)).getUsuario(NUEVO_USUARIO);
	}

	/* ----------- TESTS DE REGISTRO ---------- */

	@Test
	public void testRegistrar_UsuarioExistente() {

		boolean res = controlador.registrar(USUARIO_EXISTENTE, "");
		assertFalse(res, "Ya existe un usuario con ese nombre.");
		verify(bbddMock, times(1)).existeUsuario(USUARIO_EXISTENTE);
	}

	@Test
	public void testRegistrar_UsuarioNoExistente() {

		boolean res = controlador.registrar(NUEVO_USUARIO, "");
		assertTrue(res, "No existe un usuario con ese nombre.");
		verify(bbddMock, times(1)).existeUsuario(NUEVO_USUARIO);
	}

	@Test
	public void testEmpezarCurso() {
		
		controlador.empezarCurso(cursoFalso, estrategiaFalsa);

		assertNotNull(controlador.getSesionCursoAct(), "La sesión de curso no debe ser nula después de empezar el curso");

		assertEquals(cursoFalso, controlador.getSesionCursoAct().getCurso(), "El curso de la sesión debería ser el correcto");
		assertEquals(estrategiaFalsa, controlador.getSesionCursoAct().getEstrategia(),
				"La estrategia de la sesión debería ser la correcta");

	}
	
	@ParameterizedTest
	@CsvSource({
	    "0, false",
	    "1, true"
	})
	public void testQuedanPistasDisponibles(int num, boolean esperado) {
		
		sesionCursoFalsa = new SesionCurso(cursoFalso, estrategiaFalsa, usuarioFalso);
		sesionCursoFalsa.setPistasRestantes(num);
		
		controlador.setSesionCursoAct(sesionCursoFalsa);
		boolean res = controlador.quedanPistasDisponibles();
		
		assertEquals(esperado, res);
		
	}
	
	@Test
	public void testDisminuirPistasDisponibles() {
		
		sesionCursoFalsa = new SesionCurso(cursoFalso, estrategiaFalsa, usuarioFalso);
		sesionCursoFalsa.setPistasRestantes(1);
		controlador.setSesionCursoAct(sesionCursoFalsa);
		
		controlador.disminuirPistasDisponibles();
		
		assertEquals(0, controlador.getSesionCursoAct().getPistasRestantes());
		
	}
	
	private static Stream<Arguments> proveedorPreguntas() {
		PreguntaTest pt = new PreguntaTest("pregunta1", "correcta", "", 100, "1", "2", "3", "correcta");
		return Stream.of(
				Arguments.of(pt, "incorrecta", false),
				Arguments.of(pt, "correcta", true));
	}
	
	@ParameterizedTest
	@MethodSource("proveedorPreguntas")
	public void testValidarRespuesta(Pregunta p, String respuesta, boolean esperado) {
		
		sesionCursoFalsa = new SesionCurso(cursoFalso, estrategiaFalsa, usuarioFalso);
		controlador.setSesionCursoAct(sesionCursoFalsa);
		
		boolean res = controlador.validarRespuesta(p, respuesta);
		assertEquals(esperado, res);
	}
	
	@Test
	public void testPasarASiguientePregunta() {
		
		BloqueDeContenido b = new BloqueDeContenido();
		cursoFalso.addBloqueDeContenido(b);
		b.addPregunta(new PreguntaTest("pregunta1", "correcta", "", 100, "1", "2", "3", "correcta"));
		b.addPregunta(new PreguntaTest("pregunta2", "correcta", "", 100, "1", "2", "3", "correcta"));
		
		sesionCursoFalsa = new SesionCurso(cursoFalso, estrategiaFalsa, usuarioFalso);
		controlador.setSesionCursoAct(sesionCursoFalsa);
		
		controlador.pasarASiguientePregunta();
		
		Pregunta p = controlador.getSesionCursoAct().getPreguntaActual();
		
		assertEquals("pregunta2", p.getEnunciado()); // Funciona porque estrategiaFalse es la secuencial
		
	}
	
	public void testActualizarEstadisticasUsuario() {
		
		sesionCursoFalsa = new SesionCurso(cursoFalso, estrategiaFalsa, usuarioFalso);
		sesionCursoFalsa.setEstadisticas(3, 4, 2);
		controlador.setSesionCursoAct(sesionCursoFalsa);
		controlador.setUsuarioAct(usuarioFalso);
		
		usuarioFalso.actualizarEstadisticas(sesionCursoFalsa);
		// controlador.actualizarEstadisticasUsuario(completado);
		
		assertEquals(3, controlador.getUsuarioAct().getEstadisticas().getPreguntasAcertadas());
		assertEquals(4, controlador.getUsuarioAct().getEstadisticas().getPreguntasRespondidas());
		assertEquals(3-2, controlador.getUsuarioAct().getEstadisticas().getPistasConsultadas());
		assertEquals(1, controlador.getUsuarioAct().getEstadisticas().getCursosRealizados());
	}

}
