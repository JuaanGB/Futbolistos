package pds.futbolistos.modelado;

import javax.swing.JPanel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "PREGUNTAS")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "tipo_pregunta")
@JsonSubTypes({
	@JsonSubTypes.Type(value = PreguntaTest.class, name = "test"),
	@JsonSubTypes.Type(value = PreguntaCompletar.class, name = "completar"),
	@JsonSubTypes.Type(value = PreguntaFlashcard.class, name = "flashcard")
})
public abstract class Pregunta {

	// Atributos
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;
	@Lob
	@JsonProperty
	private String enunciado;
	@Lob
	@JsonProperty
	private String pista;
	@JsonProperty
	private int segundos;
	
	// Constructor
	public Pregunta() {
		
	}
	
	public Pregunta(String enunciado, String pista, int segundos) {
		this();
		this.enunciado = enunciado;
		this.pista = pista;
		this.segundos = segundos;
	}
	
	// Getters y setters
	public String getEnunciado() {
		return enunciado;
	}

	public String getPista() {
		return pista;
	}

	public int getSegundos() {
		return segundos;
	}

	// Funcionalidad
	public abstract boolean isRespuestaValida(String respuesta);

	public abstract JPanel getPanel();

	public boolean hasPista() {
		return pista != null;
	}


}
