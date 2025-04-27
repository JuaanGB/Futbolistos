package pds.futbolistos.modelado;

import java.awt.image.BufferedImage;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Hibernate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import pds.futbolistos.modelado.estrategias.EstrategiaAprendizaje;

@Entity
@Table(name = "USUARIOS")
public class Usuario {

	// Atributos
	@Id
	@Lob
	private String nombreUsuario;
	@Lob
	private String contraseña;
	@OneToMany(mappedBy = "usuario", cascade = {})
	private List<SesionCurso> sesionesCurso;
	@Embedded
	private EstadisticasUsuario estadisticas;
	@OneToMany(cascade = CascadeType.ALL) // Realmente sería un ManyToMany pero requeriría comprobar si el curso ya está en base de datos y es idéntico a uno que ya exista
	private List<Curso> cursosImportados;

	// Constructor
	public Usuario() {
		this.estadisticas = new EstadisticasUsuario();
		this.sesionesCurso = new ArrayList<>();
		this.cursosImportados = new ArrayList<>();
	}
	
	public Usuario(String nombreUsuario, String contraseña) {
		this();
		this.nombreUsuario = nombreUsuario;
		this.contraseña = contraseña;		
	}
	

	// Getters y setters
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public String getContraseña() {
		return contraseña;
	}

	public EstadisticasUsuario getEstadisticas() {
		return estadisticas;
	}
	
	public List<SesionCurso> getSesionesCurso() {
		return Collections.unmodifiableList(sesionesCurso);
	}
	
	public List<Curso> getCursosImportados() {
		return Collections.unmodifiableList(cursosImportados);
	}
	
	public boolean addCursoImportado(Curso c) {
		estadisticas.incrementarCursosCreados();
		return this.cursosImportados.add(c);
	}

	// Funcionalidad
	public boolean checkContraseña(String otra) {
		return this.contraseña.equals(otra);
	}
    
    public SesionCurso empezarCurso(Curso c, EstrategiaAprendizaje a) { 
    	SesionCurso sc = new SesionCurso(c, a, this);
    	sesionesCurso.add(sc); 
    	return sc;
    }
    
    public void actualizarEstadisticas(SesionCurso s) { 
    	estadisticas.actualizarTrasAcabarSesion(s); 
    }

	
	public void removeSesion(SesionCurso sesionCursoAct) {
		SesionCurso aBorrar = sesionesCurso.stream()
			.filter( sc -> sc.equals(sesionCursoAct))
			.findFirst()
			.orElse(null); // Se realiza esta búsqueda por si tuviesen OID diferentes
		sesionesCurso.remove(aBorrar);
	}
	
	public SesionCurso getSesionComenzada(Curso c) {
		return sesionesCurso.stream()
				.filter( sc -> sc.hasCurso(c))
				.findFirst()
				.orElse(null);
	}
	
	public boolean hasSesion(Curso c) {
		return sesionesCurso.stream()
				.anyMatch( sc -> sc.hasCurso(c));
	}
	
	public void sustituirSesionEvadirContextoPersistencia(SesionCurso aEliminar, SesionCurso nueva) {
		removeSesion(aEliminar);
		sesionesCurso.add(nueva);
	}
	
	// Llamado al iniciar sesión
	public void actualizarRachaDeDias() {
		estadisticas.registrarAcceso();
	}
	
	// Llamado al cerrar la aplicación (realmente solo es en dos ventanas: la principal y la del curso)
	public void actualizarEstadisticasDeTiempo() {
		estadisticas.registrarCierre();
	}
	
	public void reiniciarFechaUltimoAcceso() {
		estadisticas.reiniciarFechaDeUltimoAcceso();
	}


}
