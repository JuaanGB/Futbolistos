package pds.futbolistos.modelado;

import javax.swing.JPanel;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import pds.futbolistos.vistas.componentes.PanelPreguntaFlashcard;

@Entity
@Table(name="PREGUNTAS_FLASHCARD")
public class PreguntaFlashcard extends Pregunta {
	
	@Lob
	@JsonProperty
	private String anverso;
	@Lob
	@JsonProperty
	private String reverso;
	
	public PreguntaFlashcard() {
		
	}
	
	public PreguntaFlashcard(int segundos, String anverso, String reverso) {
		super(" ", null, segundos);
		this.anverso = anverso;
		this.reverso = reverso;
	}
	
	public String getAnverso() {
		return anverso;
	}
	
	public String getReverso() {
		return reverso;
	}

	@Override
	public boolean isRespuestaValida(String respuesta) {
		return true;
	}

	@Override
	public JPanel getPanel() {
		return new PanelPreguntaFlashcard(this);
	}

}
