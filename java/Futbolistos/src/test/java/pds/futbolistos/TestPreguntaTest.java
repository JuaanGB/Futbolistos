package pds.futbolistos;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import pds.futbolistos.modelado.Pregunta;
import pds.futbolistos.modelado.PreguntaTest;

class TestPreguntaTest {

	private static Stream<Arguments> proveedorPreguntas() {
		PreguntaTest pt = new PreguntaTest("pregunta1", "correcta", "", 100, "1", "2", "3", "correcta");
		return Stream.of(
				Arguments.of(pt, "incorrecta", false),
				Arguments.of(pt, "correcta", true));
	}
	
	@ParameterizedTest
	@MethodSource("proveedorPreguntas")
	public void testValidarRespuesta(Pregunta p, String respuesta, boolean esperado) {
				
		boolean res = p.isRespuestaValida(respuesta);
		assertEquals(esperado, res);
	}
	

}
