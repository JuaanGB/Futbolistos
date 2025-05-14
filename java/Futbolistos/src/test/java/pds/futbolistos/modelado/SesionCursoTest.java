package pds.futbolistos.modelado;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.Mockito;

import pds.futbolistos.modelado.estrategias.EstrategiaAprendizaje;
import pds.futbolistos.modelado.estrategias.EstrategiaSecuencial;

/**
 * Pruebas unitarias para la clase SesionCurso usando JUnit 5.
 */
public class SesionCursoTest {

	@Mock
	private PreguntaCompletar preguntaMock1, preguntaMock2;

	private SesionCurso sesion;
	private Curso curso;
	private EstrategiaAprendizaje estrategiaFalsa;

	@BeforeEach
	public void setUp() {
		curso = new Curso("Curso Pruebas", "Descripción de prueba", "URLImagen");

		preguntaMock1 = Mockito.mock(PreguntaCompletar.class);
		when(preguntaMock1.getEnunciado()).thenReturn("¿Pregunta 1?");
		when(preguntaMock1.getRespuestaCorrecta()).thenReturn("1");
		when(preguntaMock1.getPista()).thenReturn("Pista Pregunta 1");
		when(preguntaMock1.getSegundos()).thenReturn(10);
		when(preguntaMock1.isRespuestaValida(anyString())).thenAnswer(invocation -> {
			String respuesta = invocation.getArgument(0);
			return "1".equals(respuesta);
		});

		preguntaMock2 = Mockito.mock(PreguntaCompletar.class);
		when(preguntaMock2.getEnunciado()).thenReturn("¿Pregunta 2?");
		when(preguntaMock2.getRespuestaCorrecta()).thenReturn("2");
		when(preguntaMock2.isRespuestaValida(anyString())).thenAnswer(invocation -> {
			String respuesta = invocation.getArgument(0);
			return "2".equals(respuesta);
		});

		BloqueDeContenido b = new BloqueDeContenido();
		b.addPregunta(preguntaMock1);
		b.addPregunta(preguntaMock2);

		curso.addBloqueDeContenido(b);

		estrategiaFalsa = new EstrategiaSecuencial();

		sesion = new SesionCurso(curso, estrategiaFalsa, Mockito.mock(Usuario.class));
	}

	@Test
	public void testConstructorValoresIniciales() {
		assertEquals(curso, sesion.getCurso(), "El curso debe coincidir con el asignado.");
		assertEquals(estrategiaFalsa, sesion.getEstrategia(), "La estrategia debe coincidir con la asignada.");
		assertNotNull(sesion.getPreguntasRestantes(), "La lista de preguntas restantes no debe ser nula.");
		assertEquals(0, sesion.getPuntuacion(), "La puntuación inicial debe ser 0.");
		assertEquals(3, sesion.getPistasRestantes(), "Las pistas iniciales deben ser 3.");
		assertEquals(2, sesion.getNumTotalPreguntas(), "Debe coincidir con el total de preguntas generadas.");
		assertEquals(0, sesion.getNumeroPreguntasRespondidas(), "No se ha respondido ninguna pregunta todavía.");
	}

	@Test
	public void testIncrementarPuntuacion() {
		int puntuacionInicial = sesion.getPuntuacion();
		sesion.incrementarPuntuacion(2);
		assertEquals(puntuacionInicial + 2, sesion.getPuntuacion(), "La puntuación debería incrementarse en 2.");
	}

	@Test
	public void testRemovePrimeraPregunta() {
		int sizeInicial = sesion.getPreguntasRestantes().size();
		int respondidasInicial = sesion.getNumeroPreguntasRespondidas();

		sesion.removePrimeraPregunta();

		assertEquals(sizeInicial - 1, sesion.getPreguntasRestantes().size(), "Debe quedar una pregunta menos.");
		assertEquals(respondidasInicial + 1, sesion.getNumeroPreguntasRespondidas(),
				"Debe aumentar en 1 el contador de preguntas respondidas.");
	}

	@Test
	public void testQuedanPreguntas() {
		assertTrue(sesion.quedanPreguntas(), "Al principio, deben existir preguntas.");

		sesion.removePrimeraPregunta();
		sesion.removePrimeraPregunta();

		assertFalse(sesion.quedanPreguntas(), "Después de remover todas, no deben quedar más.");
	}

	@Test
	public void testDisminuirPistasDisponibles() {
		int pistasIniciales = sesion.getPistasRestantes();
		sesion.disminuirPistasDisponibles();
		assertEquals(pistasIniciales - 1, sesion.getPistasRestantes(), "Debe decrementar en 1 la cantidad de pistas.");
		assertTrue(sesion.getPistasRestantes() >= 0, "Debe seguir habiendo pistas excepto si se han consumido todas.");
	}

	@Test
	public void testIntegracionEstrategiaPreguntas() {
		List<Pregunta> preguntasSesion = sesion.getPreguntasRestantes();
		assertNotNull(preguntasSesion, "La lista de preguntas no debe ser nula.");
		assertEquals(2, preguntasSesion.size(), "La sesión debe tener el número de preguntas esperado.");

		PreguntaCompletar pregunta1 = (PreguntaCompletar) preguntasSesion.get(0);
		assertEquals("¿Pregunta 1?", pregunta1.getEnunciado(), "El enunciado debe coincidir.");
		assertEquals("1", pregunta1.getRespuestaCorrecta(), "La respuesta correcta debe coincidir.");
		assertEquals("Pista Pregunta 1", pregunta1.getPista(), "La pista debe coincidir.");
		assertEquals(10, pregunta1.getSegundos(), "El tiempo de respuesta debe coincidir.");
	}
	
	@ParameterizedTest
	@CsvSource({ 
		"1, true",
		"0, false"
	})
	public void testQuedanPistasDisponibles(int pistasRestantes, boolean esperado) {
		sesion.setPistasRestantes(pistasRestantes);
		assertEquals(esperado, sesion.quedanPistasDisponibles());
	}
}
