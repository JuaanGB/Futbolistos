package PDS.Futbolistos.modelado;

import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import PDS.Futbolistos.modelado.estrategias.EstrategiaAprendizaje;

public class Usuario {

	// Atributos
	private final String nombreUsuario, nombre, apellidos, contraseña;
	private String saludo, imagenURL;
	private final LocalDate fecha;
	private BufferedImage imagen;
	
	private final List<SesionCurso> sesionesCurso;
	private final EstadisticasUsuario estadisticas;

	// Constructor
	public Usuario(String nombreUsuario, String nombre, String apellidos, String contraseña, String saludo,
			String imagenURL, LocalDate fecha) {
		this.nombreUsuario = nombreUsuario;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.contraseña = contraseña;
		this.saludo = saludo;
		this.imagenURL = imagenURL;
		this.fecha = fecha;
		// Cargar imagen
		this.estadisticas = new EstadisticasUsuario();
		this.sesionesCurso = new LinkedList<>();
	}

	// Getters y setters
	public String getSaludo() { return saludo; }
    public void setSaludo(String saludo) { this.saludo = saludo; }
    public String getImagenURL() { return imagenURL; }
    public void setImagenURL(String imagenURL) { this.imagenURL = imagenURL; }
    public BufferedImage getImagen() { return imagen; }
    public void setImagen(BufferedImage imagen) { this.imagen = imagen; }
    public String getNombreUsuario() { return nombreUsuario; }
    public String getNombre() { return nombre; }
    public String getApellidos() { return apellidos; }
    public String getContraseña() { return contraseña; }
    public LocalDate getFecha() { return fecha; }
    
    // Funcionalidad
    public boolean empezarCurso(Curso c, EstrategiaAprendizaje a) { 
    	return sesionesCurso.add(new SesionCurso(c, a)); 
    }
	
	
	

}
