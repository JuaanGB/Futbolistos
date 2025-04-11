package pds.futbolistos.modelado;

import javax.swing.JPanel;

import pds.futbolistos.vistas.componentes.PanelPreguntaFlashcard;

public class PreguntaFlashcard extends Pregunta {
	
	private String anverso, reverso;
	
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
