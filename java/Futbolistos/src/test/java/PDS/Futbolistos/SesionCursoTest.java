package PDS.Futbolistos;


import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import PDS.Futbolistos.modelado.Curso;
import PDS.Futbolistos.modelado.Pregunta;
import PDS.Futbolistos.modelado.SesionCurso;
import PDS.Futbolistos.modelado.estrategias.EstrategiaAprendizaje;

/**
 * Pruebas para la clase SesionCurso, teniendo en cuenta la definición actualizada
 * de Pregunta (con enunciado, respuestaCorrecta, pista, segundos).
 */
public class SesionCursoTest {

    private SesionCurso sesion;
    private Curso curso;
    private EstrategiaAprendizaje estrategiaFalsa;

    @Before
    public void setUp() {
        // Simular un curso con varias preguntas
        curso = new Curso("Curso Pruebas", "Descripción de prueba", "URLImagen");

        // Crear una lista de preguntas con los nuevos argumentos del constructor
        List<Pregunta> preguntas = new ArrayList<>();
        preguntas.add(new Pregunta("¿Pregunta 1?", "1", "Pista Pregunta 1", 10) {
            @Override
            public boolean isRespuestaValida(String respuesta) {
                return getRespuestaCorrecta().equals(respuesta);
            }

            @Override
            public JPanel getPanel() {
                return new JPanel(); // Lógica mínima para el test
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

        // Estrategia de aprendizaje simulada (devuelve la lista sin alterar)
        estrategiaFalsa = new EstrategiaAprendizaje() {
            public String getNombre() {
                return "EstrategiaFalsa";
            }

            @Override
            public List<Pregunta> calcularOrden(Curso c) {
                // Simplemente devuelve la lista de preguntas que creamos
                return preguntas;
            }
        };

        // Instanciar SesionCurso con el curso y la estrategia simulada
        sesion = new SesionCurso(curso, estrategiaFalsa);
    }

    /**
     * Verifica la correcta inicialización de los atributos en el constructor,
     * incluyendo la lista de preguntas generada por la estrategia.
     */
    @Test
    public void testConstructorValoresIniciales() {
        assertEquals("El curso debe coincidir con el asignado.", curso, sesion.getCurso());
        assertEquals("La estrategia debe coincidir con la asignada.", estrategiaFalsa, sesion.getEstrategia());
        assertNotNull("La lista de preguntas restantes no debe ser nula.", sesion.getPreguntasRestantes());
        assertEquals("La puntuación inicial debe ser 0.", 0, sesion.getPuntuacion());
        assertEquals("Las pistas iniciales deben ser 3.", 3, sesion.getPistasRestantes());
        assertEquals("Debe coincidir con el total de preguntas generadas por la estrategia.", 2, sesion.getNumTotalPreguntas());
        assertEquals("No se ha respondido ninguna pregunta todavía.", 0, sesion.getNumeroPreguntasRespondidas());
    }

    /**
     * Prueba de caja blanca para incrementar la puntuación.
     */
    @Test
    public void testIncrementarPuntuacion() {
        int puntuacionInicial = sesion.getPuntuacion();
        sesion.incrementarPuntuacion(2);
        assertEquals("La puntuación debería incrementarse en 2.", puntuacionInicial + 2, sesion.getPuntuacion());
    }

    /**
     * Verifica que removePrimeraPregunta() actualice preguntasRestantes y el contador de preguntas respondidas.
     */
    @Test
    public void testRemovePrimeraPregunta() {
        int sizeInicial = sesion.getPreguntasRestantes().size();
        int respondidasInicial = sesion.getNumeroPreguntasRespondidas();

        sesion.removePrimeraPregunta();

        assertEquals("Debe quedar una pregunta menos en la lista.", sizeInicial - 1, sesion.getPreguntasRestantes().size());
        assertEquals("Debe aumentar en 1 la contadora de preguntas respondidas.", 
                     respondidasInicial + 1, 
                     sesion.getNumeroPreguntasRespondidas());
    }

    /**
     * Prueba de caja negra para el método quedanPreguntas().
     */
    @Test
    public void testQuedanPreguntas() {
        // Al inicio, deben quedar preguntas
        assertTrue("Al principio, deben existir preguntas.", sesion.quedanPreguntas());
        
        // Eliminar todas las preguntas
        sesion.removePrimeraPregunta();
        sesion.removePrimeraPregunta();

        // Ahora no debe quedar ninguna
        assertFalse("Después de remover todas las preguntas, no deben quedar más.", sesion.quedanPreguntas());
    }

    /**
     * Verifica el comportamiento de las pistas y el método disminuirPistasDisponibles().
     */
    @Test
    public void testDisminuirPistasDisponibles() {
        int pistasIniciales = sesion.getPistasRestantes();
        sesion.disminuirPistasDisponibles();
        assertEquals("Debe decrementar en 1 la cantidad de pistas disponibles.", 
                     pistasIniciales - 1, 
                     sesion.getPistasRestantes());
        assertTrue("Debe seguir habiendo pistas (al menos 2) excepto si se han consumido todas.", 
                   sesion.getPistasRestantes() >= 0);
    }

    /**
     * Prueba de integración mínima: confirmamos que la estrategia retorne
     * la lista de preguntas esperada, y que SesionCurso la use correctamente.
     */
    @Test
    public void testIntegracionEstrategiaPreguntas() {
        List<Pregunta> preguntasSesion = sesion.getPreguntasRestantes();
        assertNotNull("La lista de preguntas no debe ser nula.", preguntasSesion);
        assertEquals("La sesión debe tener el número de preguntas esperado.", 
                     2, 
                     preguntasSesion.size());
        
        // Comprobamos que la primera pregunta tenga los valores constructor correctos
        Pregunta pregunta1 = preguntasSesion.get(0);
        assertEquals("¿Pregunta 1?", pregunta1.getEnunciado());
        assertEquals("1", pregunta1.getRespuestaCorrecta());
        assertEquals("Pista Pregunta 1", pregunta1.getPista());
        assertEquals(10, pregunta1.getSegundos());
    }
}
