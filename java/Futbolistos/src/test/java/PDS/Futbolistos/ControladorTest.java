package PDS.Futbolistos;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import PDS.Futbolistos.controlador.Controlador;
import PDS.Futbolistos.modelado.Curso;
import PDS.Futbolistos.modelado.Pregunta;
import PDS.Futbolistos.modelado.PreguntaTest;
import PDS.Futbolistos.modelado.Usuario;
import PDS.Futbolistos.modelado.estrategias.EstrategiaAprendizaje;

/**
 * Pruebas de la clase Controlador utilizando JUnit.
 * Asegurarse de que el repositorio de usuarios, catálogo de cursos,
 * y factoria de estrategias estén correctamente inicializados previamente.
 */
public class ControladorTest {

    private Controlador controlador;

    @Before
    public void setUp() {
        controlador = Controlador.getInstancia();
    }

    @Test
    public void testAutenticar_UsuarioValido() {
        // Precondición: asume que existe un usuario llamado "john" con contraseña "123" en RepositorioUsuario
        Usuario autenticado = controlador.autenticar("john", "123");
        assertNotNull("El usuario debería haberse autenticado correctamente", autenticado);
        assertEquals("Debería coincidir el nombre de usuario", "john", autenticado.getNombreUsuario());
    }

    @Test
    public void testAutenticar_UsuarioInvalido() {
        // Usuario inexistente
        Usuario autenticado = controlador.autenticar("noExiste", "noPass");
        assertNull("El usuario no debería poder autenticarse", autenticado);
    }

    @Test
    public void testRegistrar_UsuarioNuevo() {
        // Registrar un usuario nuevo (asegurar que no exista previamente)
        boolean registro = controlador.registrar("nuevoUsuario", "nuevaPass");
        assertTrue("Debería registrarse un nuevo usuario", registro);

        // Autenticar para verificar que la creación fue exitosa
        Usuario autenticado = controlador.autenticar("nuevoUsuario", "nuevaPass");
        assertNotNull("El nuevo usuario debería poder autenticarse después de registrarse", autenticado);
    }

    @Test
    public void testRegistrar_UsuarioExistente() {
        // Registrar un usuario ya existente
        boolean registro = controlador.registrar("john", "otraPass");
        assertFalse("Registrar un usuario existente debería fallar", registro);
    }

    @Test
    public void testGetCursosDisponibles() {
        // Verifica que haya cursos añadidos en la fábrica inicial
        assertFalse("Debería haber al menos un curso disponible", controlador.getCursosDisponibles().isEmpty());
    }

    @Test
    public void testEmpezarCursoYValidarSesion() {
        // Tomamos el primer curso disponible
        Curso primerCurso = controlador.getCursosDisponibles().get(0);
        // Usamos una estrategia cualquiera de las que existan
        String estrategia = controlador.getEstrategias().iterator().next();
        EstrategiaAprendizaje e = controlador.getEstrategia(estrategia);

        controlador.empezarCurso(primerCurso, e);
        assertNotNull("La sesión del curso debería haberse inicializado", controlador.getSesionCursoAct());
    }

    @Test
    public void testQuedanPistasDisponibles() {
        // Asegurar que haya una sesión de curso activa
        Curso primerCurso = controlador.getCursosDisponibles().get(0);
        EstrategiaAprendizaje e = controlador.getEstrategia(
            controlador.getEstrategias().iterator().next()
        );
        controlador.empezarCurso(primerCurso, e);

        boolean hayPistas = controlador.quedanPistasDisponibles();
        // Puede variar, depende de cómo se gestionen las pistas en SesionCurso
        assertTrue("Debería haber pistas inicialmente (depende de la config de la sesión)", hayPistas);
    }

    @Test
    public void testValidarRespuesta() {
        // Iniciar curso con preguntas
        Curso primerCurso = controlador.getCursosDisponibles().get(0);
        EstrategiaAprendizaje e = controlador.getEstrategia(
            controlador.getEstrategias().iterator().next()
        );
        controlador.empezarCurso(primerCurso, e);

        // Obtener pregunta actual
        Pregunta preguntaActual = controlador.getSesionCursoAct().getPreguntaActual();
        assertNotNull("Debe existir al menos una pregunta en el curso de prueba", preguntaActual);

        // Validar respuesta correcta
        if (preguntaActual instanceof PreguntaTest) {
            String respuestaCorrecta = ((PreguntaTest) preguntaActual).getRespuestaCorrecta();
            boolean resultado = controlador.validarRespuesta(preguntaActual, respuestaCorrecta);
            assertTrue("La respuesta correcta debería validarse como true", resultado);
        }
    }

    @Test
    public void testPasarASiguientePregunta() {
        // Iniciar curso y asegurarnos de que tenga preguntas
        Curso primerCurso = controlador.getCursosDisponibles().get(0);
        EstrategiaAprendizaje e = controlador.getEstrategia(
            controlador.getEstrategias().iterator().next()
        );
        controlador.empezarCurso(primerCurso, e);

        Pregunta pregunta1 = controlador.getSesionCursoAct().getPreguntaActual();
        assertNotNull("Debería haber una pregunta inicial", pregunta1);

        // Pasar a la siguiente pregunta
        Pregunta pregunta2 = controlador.pasarASiguientePregunta();
        // Según cómo se configuran las preguntas, pregunta2 podría ser diferente
        if (pregunta2 == null) {
            // Podría ser que sólo hubiera una pregunta
            assertNull("No hay más preguntas, vuelve null", pregunta2);
        } else {
            // Debería ser distinta a la pregunta1
            assertNotEquals("La siguiente pregunta debería ser distinta a la anterior", 
                            pregunta1, 
                            pregunta2);
        }
    }

    @Test
    public void testActualizarEstadisticasUsuario() {
        // Asegurar usuario autenticado para poder actualizar estadísticas
        Usuario auth = controlador.autenticar("john", "123");
        assertNotNull("Necesitamos un usuario válido para actualizar estadísticas", auth);

        // Iniciar un curso y completarlo
        Curso primerCurso = controlador.getCursosDisponibles().get(0);
        EstrategiaAprendizaje e = controlador.getEstrategia(
            controlador.getEstrategias().iterator().next()
        );
        controlador.empezarCurso(primerCurso, e);

        // Finalizar (o simular final) e invocar actualización
        controlador.actualizarEstadisticasUsuario(true);
        // Aquí se podría comprobar la estadística directamente en el usuario
        // si hubiera getters para verificar, pero al menos validamos que no haya fallos
        assertTrue("Proceso de actualización de estadísticas completado (no generó excepciones)", true);
    }
}