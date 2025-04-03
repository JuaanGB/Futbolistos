package PDS.Futbolistos;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import PDS.Futbolistos.modelado.EstadisticasUsuario;
import PDS.Futbolistos.modelado.SesionCurso;

/**
 * Clase de prueba para la clase EstadisticasUsuario en JUnit 5.11.4.
 */
public class EstadisticasUsuarioTest {

	private EstadisticasUsuario estadisticas;

	@BeforeEach
	public void setUp() {
		estadisticas = new EstadisticasUsuario();
	}

	@Test
	public void testRegistrarRespuesta() {
		estadisticas.registrarRespuesta(true);
		estadisticas.registrarRespuesta(false);

		assertEquals(2, estadisticas.getPreguntasRespondidas(), "Se han respondido 2 preguntas.");
		assertEquals(1, estadisticas.getPreguntasAcertadas(), "Se ha acertado sólo 1 pregunta.");
	}

	@Test
	public void testIncrementarCursosRealizadosYCreados() {
		estadisticas.incrementarCursosRealizados();
		estadisticas.incrementarCursosCreados();

		assertEquals(1, estadisticas.getCursosRealizados(), "Se ha realizado 1 curso.");
		assertEquals(1, estadisticas.getCursosCreados(), "Se ha creado 1 curso.");
	}

	@Test
	public void testActualizarMejorRacha() {
		estadisticas.actualizarMejorRacha(10);
		assertEquals(10, estadisticas.getMejorRachaDias(),
				"La mejor racha debería ser 10 tras la primera actualización.");

		estadisticas.actualizarMejorRacha(5);
		assertEquals(10, estadisticas.getMejorRachaDias(), "La mejor racha no debería cambiar si el valor nuevo es 5.");

		estadisticas.actualizarMejorRacha(12);
		assertEquals(12, estadisticas.getMejorRachaDias(), "La mejor racha debe actualizarse a 12.");
	}

	@Test
	public void testSumarTiempo() {
		estadisticas.sumarTiempo(30);
		assertEquals(Duration.ofSeconds(30), estadisticas.getTiempoTotalDeUso(),
				"Debería añadirse 30 segundos al tiempo total de uso.");

		estadisticas.sumarTiempo(90);
		assertEquals(Duration.ofSeconds(120), estadisticas.getTiempoTotalDeUso(),
				"Ahora deberían ser 120 segundos (2 minutos).");
	}

	@Test
	public void testActualizarConSesionCursoCompletada() {
		SesionCurso sesionMock = new SesionCurso(null, null) {
			{
				setPuntuacion(2);
				removePrimeraPregunta();
				removePrimeraPregunta();
			}

			@Override
			public boolean quedanPreguntas() {
				return false;
			}
		};

		estadisticas.actualizar(sesionMock, true);

		assertEquals(1, estadisticas.getCursosRealizados(),
				"Debe incrementar en 1 los cursos realizados si completado.");
		assertEquals(2, estadisticas.getPreguntasAcertadas(), "Se suman los aciertos de la sesión.");
		assertEquals(2, estadisticas.getPreguntasRespondidas(), "Se suman las preguntas respondidas de la sesión.");
		assertEquals(3, estadisticas.getPistasConsultadas(), "Se incrementan pistasConsultadas (3 - pistasRestantes).");
	}

	@Test
	public void testActualizarConSesionCursoNoCompletada() {
		SesionCurso sesionMock = new SesionCurso(null, null) {
			{
				setPuntuacion(1);
				removePrimeraPregunta();
			}

			@Override
			public boolean quedanPreguntas() {
				return true;
			}
		};

		estadisticas.actualizar(sesionMock, false);

		assertEquals(0, estadisticas.getCursosRealizados(), "No debe incrementar cursosRealizados si no se completó.");
		assertEquals(1, estadisticas.getPreguntasAcertadas(), "Se suman los aciertos de la sesión (1).");
		assertEquals(1, estadisticas.getPreguntasRespondidas(), "Se suman las preguntas respondidas (1).");
		assertEquals(3, estadisticas.getPistasConsultadas(),
				"Se incrementan pistasConsultadas en 3 - pistasRestantes (3).");
	}
}
