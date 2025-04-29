package pds.futbolistos.bd;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import pds.futbolistos.modelado.Curso;
import pds.futbolistos.modelado.SesionCurso;
import pds.futbolistos.modelado.Usuario;

public class BaseDeDatos {

	public static final String BBDD = "futbolistos.db";
	public static final String BBDD_TEST = "test-futbolistos.db";

	private final Map<String, String> properties = new HashMap<>();
	private final EntityManagerFactory emf;
	private final EntityManager em;

	public BaseDeDatos(String bbdd) {
		properties.put("hibernate.connection.url", "jdbc:sqlite:" + bbdd);
		emf = Persistence.createEntityManagerFactory("ejemplo", properties);
		em = emf.createEntityManager();
	}

	public BaseDeDatos() {
		this(BBDD);
	}

	private void iniciarTransaccion() {
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
	}

	private void cerrarTransaccion() {
		if (em.getTransaction().isActive()) {
			em.getTransaction().commit();
		}
	}

	private void manejarError() {
		if (em.getTransaction().isActive()) {
			em.getTransaction().rollback();
		}
	}

	private void cerrarEntityManager() {
		if (em.isOpen()) {
			em.close();
		}
	}

	/* ----------- OPERACIONES CON LA BASE DE DATOS ----- */
	public Usuario addUsuario(String nombre, String contraseña) {
		Usuario u = new Usuario(nombre, contraseña);
		iniciarTransaccion();
		em.persist(u);
		cerrarTransaccion();
		return u;
	}

	public boolean existeUsuario(String nombre) {
		iniciarTransaccion();
		Usuario u = em.find(Usuario.class, nombre);
		cerrarTransaccion();
		return u != null;
	}

	public Usuario getUsuario(String nombre, String contraseña) {
		iniciarTransaccion();
		Usuario u = em.find(Usuario.class, nombre);
		if (u != null && u.checkContraseña(contraseña)) {
			u.actualizarRachaDeDias();
			u.reiniciarFechaUltimoAcceso();
		}
		cerrarTransaccion();
		return u;
	}

	public void guardarProgresoCurso(Usuario u, SesionCurso sc) {
		iniciarTransaccion();
		em.persist(u);
		em.persist(sc);
		cerrarTransaccion();
	}

	public void usuarioImportaCurso(Usuario u, Curso c) {
		iniciarTransaccion();
		u.addCursoImportado(c);
		em.persist(c);
		cerrarTransaccion();
	}

	public void actualizarEstadisticasDeUsuario(Usuario u, SesionCurso sc) {
		iniciarTransaccion();
		u.actualizarEstadisticas(sc);
		u.removeSesion(sc);
		if (em.contains(sc))
			em.remove(sc);
		em.persist(u);
		cerrarTransaccion();
	}

	public void actualizarEstadisticasDeTiempo(Usuario u) {
		iniciarTransaccion();
		em.merge(u);
		u.actualizarEstadisticasDeTiempo();
		cerrarTransaccion();
	}

	// Este método se llama desde Controlador -> Actualizar estadísticas de tiempo.
	// ¿Por qué? El bug de actualización automática de sesiones al cerrar la
	// aplicación.
	// Al buscar la sesión en el usuario para sugerir estrategia o reanudar añade al
	// contexto la sesión
	// Así que vamos a desvincularlas del contexto al cerrar la aplicación.
	public void detachSesion(SesionCurso sc) {
		System.out.println("Llamada a detachSesiones()");
		iniciarTransaccion();
		if (em.contains(sc)) // Solo desligamos la sesión si estuviese en el contexto
			em.detach(sc);
		cerrarTransaccion();
	}

}
