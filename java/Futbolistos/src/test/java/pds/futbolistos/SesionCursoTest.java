package pds.futbolistos;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pds.futbolistos.modelado.Curso;
import pds.futbolistos.modelado.Pregunta;
import pds.futbolistos.modelado.SesionCurso;
import pds.futbolistos.modelado.estrategias.EstrategiaAprendizaje;

/**
 * Pruebas unitarias para la clase SesionCurso usando JUnit 5.
 */
public class SesionCursoTest {

    private SesionCurso sesion;
    private Curso curso;
    private EstrategiaAprendizaje estrategiaFalsa;

    @BeforeEach
    public void setUp() {
        curso = new Curso("Curso Pruebas", "Descripción de prueba", "URLImagen");

        List<Pregunta> preguntas = new ArrayList<>();
        preguntas.add(new Pregunta("¿Pregunta 1?", "1", "Pista Pregunta 1", 10) {
            @Override
            public boolean isRespuestaValida(String respuesta) {
                return getRespuestaCorrecta().equals(respuesta);
            }

            @Override
            public JPanel getPanel() {
                return new JPanel();
            }
        });
        preguntas.add(new Pregunta("¿Pregunta 2?", "2", null, 15) {
            @Override
            public boolean isRespuestaValida(String respuesta) {
                return getRespuestaCorrecta().equals(respuesta);
            }

            @Override
            public JPanel getPanel() {
                return new JPanel();
            }
        });

        estrategiaFalsa = new EstrategiaAprendizaje() {
            public String getNombre() {
                return "EstrategiaFalsa";
            }

            @Override
            public List<Pregunta> calcularOrden(Curso c) {
                return preguntas;
            }
        };

        sesion = new SesionCurso(curso, estrategiaFalsa);
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
        
        Pregunta pregunta1 = preguntasSesion.get(0);
        assertEquals("¿Pregunta 1?", pregunta1.getEnunciado(), "El enunciado debe coincidir.");
        assertEquals("1", pregunta1.getRespuestaCorrecta(), "La respuesta correcta debe coincidir.");
        assertEquals("Pista Pregunta 1", pregunta1.getPista(), "La pista debe coincidir.");
        assertEquals(10, pregunta1.getSegundos(), "El tiempo de respuesta debe coincidir.");
    }
}
