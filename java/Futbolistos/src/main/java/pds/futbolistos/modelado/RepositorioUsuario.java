package pds.futbolistos.modelado;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import pds.futbolistos.controlador.Controlador;

public class RepositorioUsuario {

	private static RepositorioUsuario unicaInstancia = new RepositorioUsuario();
	private EntityManagerFactory emf;
	
	public void setEmf(EntityManagerFactory emf) {
		this.emf = emf;
	}
	
	private RepositorioUsuario() {
	}

	public static RepositorioUsuario getUnicainstancia() {
		return unicaInstancia;
	}

	public void añadirUsuario(Usuario u) {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(u);
			em.getTransaction().commit();
		} catch (Exception e) {
			System.err.println("Error al añadir usuario: " + u);
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	public Usuario getUsuario(String nombreUsuario) {
		EntityManager em = emf.createEntityManager();
		Usuario u = null;
		try {
			u = em.find(Usuario.class, nombreUsuario);
		} catch (Exception e) {
			System.err.println("Error al recuperar usuario " + nombreUsuario);
			e.printStackTrace();
		} finally {
			em.close();
		}
		return u;
	}

	public boolean existeNombre(String nombreUsuario) {
		EntityManager em = emf.createEntityManager();
		boolean existe = false;
		try {
			existe = em.find(Usuario.class, nombreUsuario) != null;
		} finally {
			em.close();
		}
		return existe;
	}

	public Usuario añadirUsuario(String nombreUsuario, String contraseña) {
		Usuario u = new Usuario(nombreUsuario, contraseña);
		añadirUsuario(u);
		return u;
	}
}