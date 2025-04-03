package PDS.Futbolistos;


import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import PDS.Futbolistos.modelado.Curso;
import PDS.Futbolistos.modelado.Pregunta;
import PDS.Futbolistos.modelado.SesionCurso;
import PDS.Futbolistos.modelado.Usuario;
import PDS.Futbolistos.modelado.estrategias.EstrategiaAprendizaje;

/**
 * Ejemplo de pruebas aplicando diversas técnicas (unitarias, integración, caja negra y caja blanca)
 * para la clase Usuario.
 */
public class UsuarioTest {

    private Usuario usuario;
    private final String NOMBRE_USUARIO = "testUser";
    private final String CONTRASEÑA = "password";

    /**
     * Preparación: creación de un objeto Usuario para ejecutar las pruebas.
     */
    @Before
    public void setUp() {
        usuario = new Usuario(NOMBRE_USUARIO, CONTRASEÑA);
    }

    /**
     * Prueba Unitaria (Caja Blanca):
     * Verifica la lógica interna del método checkContraseña,
     * comprobando la igualdad exacta de cadenas.
     */
    @Test
    public void testCheckContraseña_Valida() {
        assertTrue("La contraseña debería coincidir", usuario.checkContraseña(CONTRASEÑA));
    }

    @Test
    public void testCheckContraseña_Invalida() {
        assertFalse("La contraseña no debería coincidir", usuario.checkContraseña("passErroneo"));
    }

    /**
     * Prueba Unitaria (Caja Negra):
     * Verifica que los getters retornen lo esperado sin conocer la implementación interna.
     */
    @Test
    public void testGetters() {
        assertEquals("El nombre de usuario debe ser el asignado en el constructor.",
                     NOMBRE_USUARIO, usuario.getNombreUsuario());
        assertEquals("La contraseña debe ser la asignada en el constructor.",
                     CONTRASEÑA, usuario.getContraseña());
        assertNotNull("Las estadísticas no deben ser nulas tras instanciar el usuario.",
                      usuario.getEstadisticas());
    }

    /**
     * Prueba de Integración Básica:
     * Se integra la clase Usuario con un Curso y una EstrategiaAprendizaje simulada.
     * Verifica que se cree correctamente la sesión y que se almacene en la lista de sesiones del usuario.
     */
    @Test
    public void testEmpezarCurso() {
        Curso cursoTest = new Curso("Prueba", "Descripción", "URLimagen");
        EstrategiaAprendizaje estrategiaFalsa = new EstrategiaAprendizaje() {
            public String getNombre() {
                return "Estrategia Falsa";
            }

			@Override
			public List<Pregunta> calcularOrden(Curso c) {
				// TODO Auto-generated method stub
				return null;
			}
        };

        SesionCurso sesion = usuario.empezarCurso(cursoTest, estrategiaFalsa);
        assertNotNull("La sesión creada no debe ser nula.", sesion);
        assertEquals("El curso de la sesión debe coincidir con el pasado.",
                     cursoTest, sesion.getCurso());
        assertEquals("La estrategia de la sesión debe coincidir con la pasada.",
                     estrategiaFalsa, sesion.getEstrategia());
    }

    /**
     * Prueba de Sistema (Caja Negra):
     * Simula el uso de un usuario realizando acciones típicas:
     * – Iniciar un curso.
     * – Actualizar estadísticas con la sesión completada.
     * Verifica resultados visibles (estadísticas) sin conocer detalles de implementación interna.
     */
    @Test
    public void testProcesoCompletoUsuario() {
        // 1. Crear curso y estrategia
        Curso cursoTest = new Curso("Prueba de Sistema", "Curso para Test", "URLSistema");
        EstrategiaAprendizaje estrategiaFalsa = new EstrategiaAprendizaje() {
            public String getNombre() {
                return "EstrategiaSistema";
            }

			@Override
			public List<Pregunta> calcularOrden(Curso c) {
				// TODO Auto-generated method stub
				return null;
			}
        };

        // 2. Empezar el curso, se crea y añade la sesión al usuario
        SesionCurso sesion = usuario.empezarCurso(cursoTest, estrategiaFalsa);
        assertNotNull("La sesión no debe ser nula al iniciar el curso.", sesion);

        // 3. Simular proceso de completado de curso y actualizar estadísticas
        usuario.actualizarEstadisticas(sesion, true);

        // 4. Verificar las estadísticas tras el uso
        // (Se asume que 'actualizar' incrementa cursos o algo similar)
        assertTrue("La estadística de cursos realizados debe ser >= 1",
                   usuario.getEstadisticas().getCursosRealizados() >= 1);
    }

    /**
     * Prueba de Caja Blanca adicional:
     * Verifica la ruta interna del método registrarRespuesta.
     * Comprueba si la estadística se actualiza correctamente dependiendo de la respuesta.
     */
    @Test
    public void testRegistrarRespuesta() {
        int totalesAntes = usuario.getEstadisticas().getPreguntasRespondidas();
        usuario.registrarRespuesta(true);
        int totalesDespues = usuario.getEstadisticas().getPreguntasRespondidas();

        assertEquals("Debería incrementarse el total de preguntas en 1", totalesAntes + 1, totalesDespues);
    }
}
