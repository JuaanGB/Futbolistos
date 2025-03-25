package PDS.Futbolistos.modelado;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import PDS.Futbolistos.controlador.Controlador;

public class RepositorioUsuario {
    
    private static RepositorioUsuario unicaInstancia = new RepositorioUsuario();
    
    private List<Usuario> usuarios;
    
    // Private constructor to initialize the list only once.
    private RepositorioUsuario() {
        usuarios = new LinkedList<>();
    }
    
    public static RepositorioUsuario getUnicainstancia() {
        return unicaInstancia;
    }
    
    public void añadirUsuario(Usuario u) {
        usuarios.add(u);
    }
    
    public Usuario getUsuario(String nombreUsuario) {
        for (Usuario u : usuarios) {
            if (u.getNombreUsuario().equals(nombreUsuario)) {
                return u;
            }
        }
        return null;
    }
    
    public boolean existeNombre(String nombreUsuario) {
        return usuarios.stream().anyMatch(u -> u.getNombreUsuario().equals(nombreUsuario));
    }
    
    public Usuario añadirUsuario(String nombreUsuario, String nombre, String apellidos, String contraseña, String saludo,
                                   String imagenURL, LocalDate fecha) {
        Usuario u = new Usuario(nombreUsuario, nombre, apellidos, contraseña, saludo, imagenURL, fecha);
        usuarios.add(u);
        return u;
    }
}