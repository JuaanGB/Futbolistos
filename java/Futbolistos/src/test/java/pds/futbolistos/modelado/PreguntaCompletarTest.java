package pds.futbolistos.modelado;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PreguntaCompletarTest {

	PreguntaCompletar p;

	@BeforeEach
	public void setup() {
		this.p = new PreguntaCompletar("¿Quién es el mejor jugador de todos los tiempos?",
				"Lionel Messi", "Es un jugador argentino", 10);
	}

	@ParameterizedTest
	@CsvSource({ "'Lionel Messi', true",
		"'  Lionel Messi  ', true",
		"'lionel messi', true",
		"'LIONEL MESSI', true",
		"'Lionel  Messi', true",
		"'L io nel Messi', false",
		"'LionelMessi', false",
		"'Messi Lionel', false",
		"'Cristiano Ronaldo', false",
		"'Lionél Messi', false",
		"'Lionel    Messi', true",
		"'   lIoNeL   MeSsI  ', true",
		"'Lionel   messi', true",
		"'lionel    messi  ', true",
		"'Li o nel     Me   ssi', false"
	})
	public void testValidarRespuesta(String respuesta, boolean correcto) {
		
		boolean resultado = p.isRespuestaValida(respuesta);
		assertEquals(correcto, resultado);
	}

}
