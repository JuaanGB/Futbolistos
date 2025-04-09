package pds.futbolistos.modelado;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import pds.futbolistos.controlador.Controlador;

public class RepositorioUsuario {

	private static RepositorioUsuario unicaInstancia = new RepositorioUsuario();

	private Map<String, Usuario> usuarios;

	// Private constructor to initialize the list only once.
	private RepositorioUsuario() {
		usuarios = new HashMap<>();
	}

	public static RepositorioUsuario getUnicainstancia() {
		return unicaInstancia;
	}

	public void añadirUsuario(Usuario u) {
		usuarios.put(u.getNombreUsuario(), u);
	}

	public Usuario getUsuario(String nombreUsuario) {
		return usuarios.get(nombreUsuario);
	}

	public boolean existeNombre(String nombreUsuario) {
		return usuarios.containsKey(nombreUsuario);
	}

	public Usuario añadirUsuario(String nombreUsuario, String contraseña) {
		Usuario u = new Usuario(nombreUsuario, contraseña);
		usuarios.put(nombreUsuario, u);
		return u;
	}
}