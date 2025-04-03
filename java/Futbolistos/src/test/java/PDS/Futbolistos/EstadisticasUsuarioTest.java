package PDS.Futbolistos;


import static org.junit.Assert.*;

import java.time.Duration;

import org.junit.Before;
import org.junit.Test;

import PDS.Futbolistos.modelado.EstadisticasUsuario;
import PDS.Futbolistos.modelado.SesionCurso;

/**
 * Clase de prueba para la clase EstadisticasUsuario.
 * Cubre la verificación de métodos de registro, actualización y suma de tiempos.
 */
public class EstadisticasUsuarioTest {

    private EstadisticasUsuario estadisticas;

    @Before
    public void setUp() {
        estadisticas = new EstadisticasUsuario();
    }

    @Test
    public void testValoresIniciales() {
        assertEquals("PreguntasRespondidas inicial debe ser 0.", 0, estadisticas.getPreguntasRespondidas());
        assertEquals("PreguntasAcertadas inicial debe ser 0.", 0, estadisticas.getPreguntasAcertadas());
        assertEquals("CursosRealizados inicial debe ser 0.", 0, estadisticas.getCursosRealizados());
        assertEquals("CursosCreados inicial debe ser 0.", 0, estadisticas.getCursosCreados());
        assertEquals("MejorRachaDias inicial debe ser 0.", 0, estadisticas.getMejorRachaDias());
        assertEquals("PistasConsultadas inicial debe ser 0.", 0, estadisticas.getPistasConsultadas());
        assertEquals("TiempoTotalDeUso inicial debe ser 0.", Duration.ZERO, estadisticas.getTiempoTotalDeUso());
    }

    @Test
    public void testRegistrarRespuesta() {
        // Registrar respuesta correcta
        estadisticas.registrarRespuesta(true);
        // Registrar respuesta incorrecta
        estadisticas.registrarRespuesta(false);

        assertEquals("Se han respondido 2 preguntas.", 2, estadisticas.getPreguntasRespondidas());
        assertEquals("Se ha acertado sólo 1 pregunta.", 1, estadisticas.getPreguntasAcertadas());
    }

    @Test
    public void testIncrementarCursosRealizadosYCreados() {
        estadisticas.incrementarCursosRealizados();
        estadisticas.incrementarCursosCreados();

        assertEquals("Se ha realizado 1 curso.", 1, estadisticas.getCursosRealizados());
        assertEquals("Se ha creado 1 curso.", 1, estadisticas.getCursosCreados());
    }

    @Test
    public void testActualizarMejorRacha() {
        estadisticas.actualizarMejorRacha(10);
        assertEquals("La mejor racha debería ser 10 tras la primera actualización.", 10, estadisticas.getMejorRachaDias());

        // No debe cambiar si la nueva racha es menor
        estadisticas.actualizarMejorRacha(5);
        assertEquals("La mejor racha no debería cambiar si el valor nuevo es 5.", 10, estadisticas.getMejorRachaDias());

        // Debe cambiar si la nueva racha es mayor
        estadisticas.actualizarMejorRacha(12);
        assertEquals("La mejor racha debe actualizarse a 12.", 12, estadisticas.getMejorRachaDias());
    }

    @Test
    public void testSumarTiempo() {
        estadisticas.sumarTiempo(30);
        assertEquals("Debería añadirse 30 segundos al tiempo total de uso.", 
                     Duration.ofSeconds(30), 
                     estadisticas.getTiempoTotalDeUso());

        estadisticas.sumarTiempo(90);
        assertEquals("Ahora deberían ser 120 segundos (2 minutos).",
                     Duration.ofSeconds(120), 
                     estadisticas.getTiempoTotalDeUso());
    }

    @Test
    public void testActualizarConSesionCursoCompletada() {
        SesionCurso sesionMock = new SesionCurso(null, null) {
            {
                // Ajustes manuales simulados
                setPuntuacion(2);                 // Aciertos
                // Se elimina la primera pregunta dos veces, simulando 2 respondidas
                removePrimeraPregunta();
                removePrimeraPregunta();
            }
            @Override
            public boolean quedanPreguntas() { return false; }
        };

        estadisticas.actualizar(sesionMock, true);

        assertEquals("Debe incrementar en 1 los cursos realizados si completado.", 
                     1, 
                     estadisticas.getCursosRealizados());
        assertEquals("Se suman los aciertos de la sesión.",
                     2, 
                     estadisticas.getPreguntasAcertadas());
        assertEquals("Se suman las preguntas respondidas de la sesión.",
                     2, 
                     estadisticas.getPreguntasRespondidas());
        assertEquals("Se incrementan pistasConsultadas (3 - pistasRestantes).",
                     3, 
                     estadisticas.getPistasConsultadas());
    }

    @Test
    public void testActualizarConSesionCursoNoCompletada() {
        SesionCurso sesionMock = new SesionCurso(null, null) {
            {
                setPuntuacion(1);
                removePrimeraPregunta();  // 1 pregunta respondida
            }
            @Override
            public boolean quedanPreguntas() { return true; }
        };

        estadisticas.actualizar(sesionMock, false);

        assertEquals("No debe incrementar cursosRealizados si no se completó.",
                     0, 
                     estadisticas.getCursosRealizados());
        assertEquals("Se suman los aciertos de la sesión (1).",
                     1, 
                     estadisticas.getPreguntasAcertadas());
        assertEquals("Se suman las preguntas respondidas (1).",
                     1, 
                     estadisticas.getPreguntasRespondidas());
        assertEquals("Se incrementan pistasConsultadas en 3 - pistasRestantes (3).",
                     3, 
                     estadisticas.getPistasConsultadas());
    }
}
