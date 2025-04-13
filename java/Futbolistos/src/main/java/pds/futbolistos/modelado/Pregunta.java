package pds.futbolistos.modelado;

import javax.swing.JPanel;

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
public abstract class Pregunta {

	// Atributos
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Lob
	private String enunciado;
	@Lob
	private String pista;
	private int segundos;
	
	// Constructor
	public Pregunta(String enunciado, String pista, int segundos) {
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
