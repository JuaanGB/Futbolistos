package PDS.Futbolistos;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import PDS.Futbolistos.modelado.RepositorioUsuario;
import PDS.Futbolistos.modelado.Usuario;

/**
 * Pruebas unitarias para la clase RepositorioUsuario usando JUnit 5.
 */
public class RepositorioUsuarioTest {

    private RepositorioUsuario repositorio;

    @BeforeEach
    public void setUp() {
        repositorio = RepositorioUsuario.getUnicainstancia();
        limpiarUsuarios();
    }

    @Test
    public void testUnicainstancia() {
        RepositorioUsuario otraReferencia = RepositorioUsuario.getUnicainstancia();
        assertSame(repositorio, otraReferencia, 
                   "La instancia recuperada debería ser la misma (singleton).");
    }

    @Test
    public void testAñadirUsuario() {
        String nombre = "testUser";
        String pass = "testPass";

        Usuario usuario = repositorio.añadirUsuario(nombre, pass);
        
        assertNotNull(usuario, "El usuario devuelto no debe ser nulo");
        assertEquals(nombre, usuario.getNombreUsuario(), 
                     "El nombre de usuario debe coincidir");
        assertTrue(repositorio.existeNombre(nombre), 
                   "El repositorio debe indicar que el nombre existe");
    }

    @Test
    public void testGetUsuario() {
        String nombre = "testUser2";
        String pass = "testPass2";
        repositorio.añadirUsuario(nombre, pass);

        Usuario obtenido = repositorio.getUsuario(nombre);
        assertNotNull(obtenido, "Debería poder recuperar el usuario");
        assertEquals(nombre, obtenido.getNombreUsuario(), 
                     "El nombre de usuario debe coincidir");
    }

    @Test
    public void testExisteNombre() {
        String nombre = "existingUser";
        String pass = "somePass";
        repositorio.añadirUsuario(nombre, pass);

        assertTrue(repositorio.existeNombre(nombre), 
                   "Debería indicar que el nombre existe");
        assertFalse(repositorio.existeNombre("otroUsuario"), 
                    "No debería existir un usuario distinto");
    }

    /**
     * Método auxiliar para limpiar la lista de usuarios antes de cada prueba.
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
