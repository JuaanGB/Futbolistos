package pds.futbolistos.modelado;

import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import pds.futbolistos.modelado.estrategias.EstrategiaAprendizaje;

public class Usuario {

	// Atributos
	private final String nombreUsuario, contraseña;
	
	private final List<SesionCurso> sesionesCurso;
	private final EstadisticasUsuario estadisticas;

	// Constructor
	public Usuario(String nombreUsuario, String contraseña) {
		this.nombreUsuario = nombreUsuario;
		this.contraseña = contraseña;

		// Cargar imagen
		this.estadisticas = new EstadisticasUsuario();
		this.sesionesCurso = new LinkedList<>();
	}
	

	// Getters y setters
    public String getNombreUsuario() { return nombreUsuario; }
    public String getContraseña() { return contraseña; }
    public EstadisticasUsuario getEstadisticas() { return estadisticas; }
    
    // Actualización de estadísticas (evitamos que el Controlador conozca por completo las estadísticas haciendo
    // .getEstadististicas().metodoDeModificacionDeEstadistica(...)
    public void registrarRespuesta(boolean correcta) { estadisticas.registrarRespuesta(correcta); }
    public void incrementarCursosRealizados() { estadisticas.incrementarCursosRealizados(); }
    public void incrementarCursosCreados() { estadisticas.incrementarCursosCreados(); }
    public void actualizarMejorRacha(int racha) { estadisticas.actualizarMejorRacha(racha); }
    public void sumarTiempoDeUso(int segundos) { estadisticas.sumarTiempo(segundos); }

    
    // Funcionalidad
    public boolean checkContraseña(String otra) { return this.contraseña.equals(otra); }
    
    public SesionCurso empezarCurso(Curso c, EstrategiaAprendizaje a) { 
    	SesionCurso sc = new SesionCurso(c, a);
    	sesionesCurso.add(sc); 
    	return sc;
    }
    
    public void actualizarEstadisticas(SesionCurso s, boolean completado) { 
    	estadisticas.actualizar(s, completado); 
    }


	public SesionCurso iniciarCurso(Curso c, EstrategiaAprendizaje e) {
		return new SesionCurso(c, e);
	}
	
	
	

}
