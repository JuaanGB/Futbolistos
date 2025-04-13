package pds.futbolistos.modelado;

import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
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
	@OneToMany
	@JoinColumn(name = "nombre_usuario")
	private List<SesionCurso> sesionesCurso;
	@Embedded
	private EstadisticasUsuario estadisticas;

	// Constructor
	public Usuario() {
		this.estadisticas = new EstadisticasUsuario();
		this.sesionesCurso = new LinkedList<>();
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

	// Actualización de estadísticas (evitamos que el Controlador conozca por
	// completo las estadísticas haciendo
	// .getEstadististicas().metodoDeModificacionDeEstadistica(...)
	public void actualizarMejorRacha(int racha) {
		estadisticas.actualizarMejorRacha(racha);
	}

	public void sumarTiempoDeUso(int segundos) {
		estadisticas.sumarTiempo(segundos);
	}

	// Funcionalidad
	public boolean checkContraseña(String otra) {
		return this.contraseña.equals(otra);
	}
    
    public SesionCurso empezarCurso(Curso c, EstrategiaAprendizaje a) { 
    	SesionCurso sc = new SesionCurso(c, a);
    	sesionesCurso.add(sc); 
    	return sc;
    }
    
    public void actualizarEstadisticas(SesionCurso s, boolean completado) { 
    	estadisticas.actualizar(s, completado); 
    }

	public boolean hasSesion(Curso c) {
		return sesionesCurso.stream()
				.anyMatch( sc -> sc.hasCurso(c) );
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

}
