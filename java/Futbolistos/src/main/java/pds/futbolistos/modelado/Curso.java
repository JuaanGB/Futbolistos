package pds.futbolistos.modelado;

import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Curso {

	// Atributos
	private final String nombre, descripcion, imagenURL;
	private final BufferedImage imagen;
	private final Set<BloqueDeContenido> bloquesDeContenido;

	// Constructor
	public Curso(String nombre, String descripcion, String imagenURL) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.imagenURL = imagenURL;
		this.imagen = null; // Cargar imagen
		this.bloquesDeContenido = new HashSet<>();
	}

	// Getters y setters
	public String getNombre() {
		return nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getImagenURL() {
		return imagenURL;
	}

	public BufferedImage getImagen() {
		return imagen;
	}

	public Set<BloqueDeContenido> getBloquesDeContenido() {
		return Collections.unmodifiableSet(bloquesDeContenido);
	}

	public boolean addBloqueDeContenido(BloqueDeContenido c) {
		return bloquesDeContenido.add(c);
	}

	@Override
	public int hashCode() {
		return Objects.hash(descripcion, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Curso other = (Curso) obj;
		return Objects.equals(descripcion, other.descripcion) && Objects.equals(nombre, other.nombre);
	}

}
