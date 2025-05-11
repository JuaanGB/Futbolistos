package pds.futbolistos.modelado;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.Duration;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
		
		estadisticas.actualizarTrasAcabarSesion(sesionMock);

		assertEquals(1, estadisticas.getCursosRealizados(),
				"Debe incrementar en 1 los cursos realizados si completado.");
		assertEquals(2, estadisticas.getPreguntasAcertadas(), "Se suman los aciertos de la sesión.");
		assertEquals(2, estadisticas.getPreguntasRespondidas(), "Se suman las preguntas respondidas de la sesión.");
		assertEquals(3, estadisticas.getPistasConsultadas(), "Se incrementan pistasConsultadas (3 - pistasRestantes).");
	}
	
	@Test
	public void testRegistrarAccesoPrimeraVez() {
		LocalDateTime fecha = LocalDateTime.of(2024, 4, 15, 10, 0);
		estadisticas.registrarAcceso(fecha);

		assertEquals(1, estadisticas.getMejorRachaDias());
	}

	@Test
	public void testRegistrarAccesoDiaSiguiente() {
		LocalDateTime dia1 = LocalDateTime.of(2024, 4, 14, 10, 0);
		LocalDateTime dia2 = LocalDateTime.of(2024, 4, 15, 9, 0); // dia consecutivo

		estadisticas.registrarAcceso(dia1);
		estadisticas.registrarAcceso(dia2);

		assertEquals(2, estadisticas.getMejorRachaDias());
	}

	@Test
	public void testRegistrarAccesoSaltoDeDias() {
		LocalDateTime dia1 = LocalDateTime.of(2024, 4, 10, 10, 0);
		LocalDateTime dia3 = LocalDateTime.of(2024, 4, 12, 9, 0); // no consecutivo

		estadisticas.registrarAcceso(dia1);
		estadisticas.registrarAcceso(dia3);

		assertEquals(1, estadisticas.getMejorRachaDias());
	}

	@Test
	public void testRegistrarCierreSumaTiempo() {
		LocalDateTime inicio = LocalDateTime.of(2024, 4, 15, 10, 0);
		LocalDateTime cierre = LocalDateTime.of(2024, 4, 15, 10, 30); // 1800 segundos de sesión

		estadisticas.registrarAcceso(inicio);
		estadisticas.registrarCierre(cierre);

		assertEquals(1800, estadisticas.getTiempoTotalDeUso());
	}
	
	@ParameterizedTest
	@CsvSource({
		"0, 0, '0,00'",
		"5, 2, '2,50'",
		"2, 3, '0,67'"
	})
	public void testGetMediaPistasPorCursoRedondeado(int pistas, int cursos, String res) {
		estadisticas.setPistasConsultadas(pistas);
		estadisticas.setCursosRealizados(cursos);
		System.out.println(estadisticas.getMediaPistasPorCursoRedondeado());
		assertEquals(res, estadisticas.getMediaPistasPorCursoRedondeado());
	}


}
