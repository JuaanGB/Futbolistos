package pds.futbolistos.modelado;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.imageio.ImageIO;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import pds.futbolistos.modelado.convertidores.ConversorImagenJPA;

@Entity
@DynamicUpdate
@Table(name = "CURSOS")
public class Curso {

	// Atributos
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;
	@Lob
	@JsonProperty
	private String nombre;
	@Lob
	@JsonProperty
	private String descripcion;
	@Transient // No me hace falta. Quiero persistir la imagen en sí.
	@JsonProperty("imagen_url")
	private String imagenURL;
	@Convert(converter = ConversorImagenJPA.class)
	@JsonIgnore
	private ImagenJPA imagen;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "curso_id")
	@JsonProperty("bloques_de_contenido")
	private final List<BloqueDeContenido> bloquesDeContenido;

	// Constructor
	public Curso() {
		this.bloquesDeContenido = new ArrayList<>();
		this.imagen = new ImagenJPA(); // Cargar imagen
	}

	public Curso(String nombre, String descripcion, String imagenURL) {
		this();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.imagenURL = imagenURL;
	}

	// Getters y setters
	public Long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getImagenURL() {
		return imagenURL;
	}
	
	public void setImagen(BufferedImage imagen) {
		this.imagen.setImagen(imagen);
	}
	
	public boolean hasImagen() {
		return this.imagen != null && this.imagen.getImagen() != null;
	}

	public BufferedImage getImagen() {
		return imagen.getImagen();
	}

	public List<BloqueDeContenido> getBloquesDeContenido() {
		return Collections.unmodifiableList(bloquesDeContenido);
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
		return Objects.equals(id, other.id) && Objects.equals(descripcion, other.descripcion)
				&& Objects.equals(nombre, other.nombre);
	}
	// Comparación por ID por si el usuario importa varias veces el mismo curso y queremos distinguir las sesiones
	// Si no, reanudaría la primera sesión en la lista de sesiones del usuario.
	
	public boolean checkParsing() {

		boolean res = nombre != null && !nombre.isBlank() && descripcion != null && !descripcion.isBlank();

		res = res && this.bloquesDeContenido.size() >= 1
				&& bloquesDeContenido.stream().allMatch(bc -> bc.checkParsing());

		return res;
	}


}
