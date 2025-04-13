package pds.futbolistos.modelado;

import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import pds.futbolistos.modelado.convertidores.ConversorBufferedImage;

@Entity
@Table(name = "CURSOS")
public class Curso {

	// Atributos
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Lob
	private String nombre;
	@Lob
	private String descripcion;
	@Lob
	private String imagenURL;
	@Convert(converter = ConversorBufferedImage.class)
	private BufferedImage imagen;
	@OneToMany
	@JoinColumn(name = "curso_id")
	private final Set<BloqueDeContenido> bloquesDeContenido;

	// Constructor
	public Curso() {
		this.bloquesDeContenido = new HashSet<>();
	}
	
	public Curso(String nombre, String descripcion, String imagenURL) {
		this();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.imagenURL = imagenURL;
		this.imagen = null; // Cargar imagen
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
