package pds.futbolistos.modelado;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pds.futbolistos.modelado.Curso;
import pds.futbolistos.modelado.Pregunta;
import pds.futbolistos.modelado.SesionCurso;
import pds.futbolistos.modelado.Usuario;
import pds.futbolistos.modelado.estrategias.EstrategiaAprendizaje;
import pds.futbolistos.modelado.estrategias.EstrategiaSecuencial;

/**
 * Pruebas unitarias para la clase Usuario usando JUnit 5.
 */
public class UsuarioTest {

    private Usuario usuario;
    private final String NOMBRE_USUARIO = "testUser";
    private final String CONTRASEÑA = "password";

    @BeforeEach
    public void setUp() {
        usuario = new Usuario(NOMBRE_USUARIO, CONTRASEÑA);
    }

    @Test
    public void testCheckContraseña_Valida() {
        assertTrue(usuario.checkContraseña(CONTRASEÑA), "La contraseña debería coincidir");
    }

    @Test
    public void testCheckContraseña_Invalida() {
        assertFalse(usuario.checkContraseña("passErroneo"), "La contraseña no debería coincidir");
    }

    @Test
    public void testGetters() {
        assertEquals(NOMBRE_USUARIO, usuario.getNombreUsuario(), "El nombre de usuario debe coincidir.");
        assertEquals(CONTRASEÑA, usuario.getContraseña(), "La contraseña debe coincidir.");
        assertNotNull(usuario.getEstadisticas(), "Las estadísticas no deben ser nulas tras instanciar el usuario.");
    }

    @Test
    public void testEmpezarCurso() {
        Curso cursoTest = new Curso("Prueba", "Descripción", "URLimagen");
        EstrategiaAprendizaje estrategiaFalsa = new EstrategiaSecuencial();

        SesionCurso sesion = usuario.empezarCurso(cursoTest, estrategiaFalsa);
        assertNotNull(sesion, "La sesión creada no debe ser nula.");
        assertEquals(cursoTest, sesion.getCurso(), "El curso de la sesión debe coincidir.");
        assertEquals(estrategiaFalsa, sesion.getEstrategia(), "La estrategia de la sesión debe coincidir.");
    }

    @Test
    public void testProcesoCompletoUsuario() {
    	Curso cursoTest = new Curso("Prueba", "Descripción", "URLimagen");
        EstrategiaAprendizaje estrategiaFalsa = new EstrategiaSecuencial();

        SesionCurso sesion = usuario.empezarCurso(cursoTest, estrategiaFalsa);
        assertNotNull(sesion, "La sesión no debe ser nula al iniciar el curso.");

        usuario.actualizarEstadisticas(sesion);

        assertTrue(usuario.getEstadisticas().getCursosRealizados() >= 1,
                   "La estadística de cursos realizados debe ser >= 1");
    }
    
	@Test
	public void testGetSesionComenzada() {
		Curso cursoTest = new Curso("Curso A", "Descripción A", "URL_A");
		EstrategiaAprendizaje estrategia = new EstrategiaSecuencial();

		SesionCurso sesion = usuario.empezarCurso(cursoTest, estrategia);

		SesionCurso encontrada = usuario.getSesionComenzada(cursoTest);

		assertNotNull(encontrada, "La sesión debería encontrarse.");
		assertEquals(sesion, encontrada, "La sesión encontrada debe ser la misma que la iniciada.");
	}

	@Test
	public void testRemoveSesion() {
		Curso cursoTest = new Curso("Curso B", "Descripción B", "URL_B");
		EstrategiaAprendizaje estrategia = new EstrategiaSecuencial();

		SesionCurso sesion = usuario.empezarCurso(cursoTest, estrategia);

		assertTrue(usuario.getSesionesCurso().contains(sesion), "La sesión debe estar antes de ser eliminada.");

		usuario.removeSesion(sesion);

		assertFalse(usuario.getSesionesCurso().contains(sesion), "La sesión no debe estar después de ser eliminada.");
	}


}
