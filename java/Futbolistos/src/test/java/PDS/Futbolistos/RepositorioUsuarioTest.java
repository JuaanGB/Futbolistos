package PDS.Futbolistos;


import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import PDS.Futbolistos.modelado.RepositorioUsuario;
import PDS.Futbolistos.modelado.Usuario;

/**
 * Clase de prueba para RepositorioUsuario.
 * Se ha agregado el uso de reflection con setAccessible(true)
 * para reiniciar la lista de usuarios al comienzo de cada test.
 */
public class RepositorioUsuarioTest {

    private RepositorioUsuario repositorio;

    @Before
    public void setUp() {
        repositorio = RepositorioUsuario.getUnicainstancia();
        limpiarUsuarios();
    }

    @Test
    public void testUnicainstancia() {
        RepositorioUsuario otraReferencia = RepositorioUsuario.getUnicainstancia();
        assertSame("La instancia recuperada debería ser la misma (singleton).", repositorio, otraReferencia);
    }

    @Test
    public void testAñadirUsuario() {
        String nombre = "testUser";
        String pass = "testPass";

        Usuario usuario = repositorio.añadirUsuario(nombre, pass);
        
        assertNotNull("El usuario devuelto no debe ser nulo", usuario);
        assertEquals("El nombre de usuario debe coincidir", nombre, usuario.getNombreUsuario());
        assertTrue("El repositorio debe indicar que el nombre existe", repositorio.existeNombre(nombre));
    }

    @Test
    public void testGetUsuario() {
        String nombre = "testUser2";
        String pass = "testPass2";
        repositorio.añadirUsuario(nombre, pass);

        Usuario obtenido = repositorio.getUsuario(nombre);
        assertNotNull("Debería poder recuperar el usuario", obtenido);
        assertEquals("El nombre de usuario debe coincidir", nombre, obtenido.getNombreUsuario());
    }

    @Test
    public void testExisteNombre() {
        String nombre = "existingUser";
        String pass = "somePass";
        repositorio.añadirUsuario(nombre, pass);

        assertTrue("Debería indicar que el nombre existe", repositorio.existeNombre(nombre));
        assertFalse("No debería existir un usuario distinto", repositorio.existeNombre("otroUsuario"));
    }

    /**
     * Método auxiliar para limpiar la lista de usuarios en la configuración (@Before).
     */
    private void limpiarUsuarios() {
        try {
            Field field = RepositorioUsuario.class.getDeclaredField("usuarios");
            field.setAccessible(true);
            @SuppressWarnings("unchecked")
            List<Usuario> lista = (List<Usuario>) field.get(repositorio);
            lista.clear();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("No se pudo limpiar la lista de usuarios: " + e.getMessage());
        }
    }
}
