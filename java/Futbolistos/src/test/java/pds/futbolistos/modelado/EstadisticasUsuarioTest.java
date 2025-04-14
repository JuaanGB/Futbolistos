package pds.futbolistos.modelado;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import pds.futbolistos.modelado.Curso;
import pds.futbolistos.modelado.EstadisticasUsuario;
import pds.futbolistos.modelado.SesionCurso;
import pds.futbolistos.modelado.estrategias.EstrategiaSecuencial;

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
	public void testIncrementarCursosCreados() {

		estadisticas.incrementarCursosCreados();
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
		assertEquals(30, estadisticas.getTiempoTotalDeUso());

		estadisticas.sumarTiempo(90);
		assertEquals(120, estadisticas.getTiempoTotalDeUso());
	}

	@Test
	public void testActualizarConSesionCurso() {
		SesionCurso sesionMock = new SesionCurso(new Curso("", "", null), new EstrategiaSecuencial(), Mockito.mock(Usuario.class));
		sesionMock.setEstadisticas(2, 2, 0);
		
		estadisticas.actualizar(sesionMock, true);

		assertEquals(1, estadisticas.getCursosRealizados(),
				"Debe incrementar en 1 los cursos realizados si completado.");
		assertEquals(2, estadisticas.getPreguntasAcertadas(), "Se suman los aciertos de la sesión.");
		assertEquals(2, estadisticas.getPreguntasRespondidas(), "Se suman las preguntas respondidas de la sesión.");
		assertEquals(3, estadisticas.getPistasConsultadas(), "Se incrementan pistasConsultadas (3 - pistasRestantes).");
	}

}
