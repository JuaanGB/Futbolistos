package pds.futbolistos.modelado;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JPanel;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import pds.futbolistos.modelado.convertidores.ConversorListaDeCadenas;
import pds.futbolistos.vistas.componentes.PanelPreguntaTest;

@Entity
@Table(name="PREGUNTAS_TEST")
public class PreguntaTest extends Pregunta /* implements Visitable */ {

	// Atributos
	@Column(name = "respuestas")
	@Convert(converter = ConversorListaDeCadenas.class)
	private List<String> respuestas;
	@Lob
	private String respuestaCorrecta;
	
	public PreguntaTest() {
		this.respuestas = new ArrayList<>();
	}
	
	public PreguntaTest(String enunciado, String respuestaCorrecta, String pista, int segundos, String ...respuestas) {
		super(enunciado, pista, segundos);
		this.respuestaCorrecta = respuestaCorrecta;
		this.respuestas = new ArrayList<>();
		for (String r : respuestas) this.respuestas.add(r);
	}

	// Nuevos métodos get
	public String getRespuesta(int i) {
		return respuestas.get(i);
	}
	
	public String getRespuestaCorrecta() {
		return respuestaCorrecta;
	}
	
	@Override
	public boolean isRespuestaValida(String respuesta) {
		return respuesta.equals(this.getRespuestaCorrecta());
	}

	@Override
	public JPanel getPanel() {
		return new PanelPreguntaTest(this);
	}
	/*
	public void accept(Visitor v) {
		v.visitPreguntaTest(this);
	}

	map.put(PreguntaTest.class, () )
	*/
}
