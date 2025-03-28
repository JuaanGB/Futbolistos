package PDS.Futbolistos.modelado.estrategias;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import PDS.Futbolistos.modelado.BloqueDeContenido;
import PDS.Futbolistos.modelado.Curso;
import PDS.Futbolistos.modelado.Pregunta;

public class EstrategiaAleatoria implements EstrategiaAprendizaje {

	@Override
	public List<Pregunta> calcularOrden(Curso c) {
		List<Pregunta> preguntas = c.getBloquesDeContenido().stream()
										.flatMap( b -> b.getPreguntas().stream() )
										.collect( Collectors.toList() );
		Collections.shuffle(preguntas);
		return preguntas;
	}
}
