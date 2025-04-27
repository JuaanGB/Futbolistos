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
		//Hibernate.initialize(u.getCursosImportados());
		// u.getCursosImportados().forEach(em::detach); // Los desvinculo del contexto porque no se modifican dentro de la app.
		cerrarTransaccion();
		return u;
	}

//	public boolean usuarioHasSesion(Usuario u, Curso c) {
//		iniciarTransaccion();
//		List<SesionCurso> sesionesCurso = em
//				.createQuery("SELECT sc FROM SesionCurso sc WHERE sc.usuario.nombreUsuario = :usuarioNombre",
//						SesionCurso.class)
//				.setParameter("usuarioNombre", u.getNombreUsuario()).getResultList();
//		// No estoy seguro de que "usuarioAct" en Controlador tenga las sesiones por
//		// aliasing.
//		// Tras testear, sí lo tiene porque "usuarioAct" del Controlador es el obtenido
//		// a partir del método "getUsuario" y a partir de ese momento, está en el
//		// contexto del EM
//		cerrarTransaccion();
//		
//		sesionesCurso.stream()
//			.forEach( sc -> em.detach(sc));
//
//		return sesionesCurso.stream().anyMatch(sc -> sc.hasCurso(c));
//	}

	// Hay un bug:
	// Inicio una sesión y la guardo. La vuelvo a abrir sin cerrar la aplicación. Respondo una pregunta. Cierro la ventana (con la X).
	// El curso se persistía porque al hacer em.persist(sc) se guardaba en el contexto.
	public void guardarProgresoCurso(Usuario u, SesionCurso sc) {
		iniciarTransaccion();
		em.persist(u);
		em.persist(sc);
		cerrarTransaccion();
		
		em.clear();
		
		// Y si hago un clone y cambio la instancia de sesion de usuario por una clonada??? Quiero que esté en transient nuevamente
		// para que no se persista automáticamente
		//u.sustituirSesionEvadirContextoPersistencia(sc, sc.clonarParaEvadirContextoPersistencia());
		// Si clono el id, el Entity Manager se piensa que es el mismo objeto así que lo actualiza automáticamente.
		// Si no clono el id, al hacer el persist coge el primer id libre (coincide con el id de la sesión que sustituí y ocurre lo mismo)
	}

	public void usuarioImportaCurso(Usuario u, Curso c) {
		iniciarTransaccion();
		u.addCursoImportado(c); // Lo mismo, no sé si por aliasing el usuarioAct de Controlador tendrá el curso.
		em.persist(c);
		cerrarTransaccion();
	}

	public void actualizarEstadisticasDeUsuario(Usuario u, SesionCurso sc) {
		iniciarTransaccion();
		u.actualizarEstadisticas(sc);
		u.removeSesion(sc);
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
	// ¿Por qué? El bug de actualización automática de sesiones al cerrar la aplicación.
	// Al buscar la sesión en el usuario para sugerir estrategia o reanudar añade al contexto la sesión
	// Así que vamos a desvincularlas del contexto al cerrar la aplicación.
	public void detachSesiones(Usuario usuarioAct) {
		
		System.out.println("Llamada a detachSesiones()");
		
		iniciarTransaccion();
		usuarioAct.getSesionesCurso().stream()
			.forEach( sc -> em.detach(sc));
		cerrarTransaccion();
		
	}

}
