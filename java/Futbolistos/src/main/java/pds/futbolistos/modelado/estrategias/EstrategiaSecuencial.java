package pds.futbolistos.modelado.estrategias;

import java.util.List;
import java.util.stream.Collectors;

import pds.futbolistos.modelado.Curso;
import pds.futbolistos.modelado.Pregunta;

public class EstrategiaSecuencial implements EstrategiaAprendizaje {

	@Override
	public List<Pregunta> calcularOrden(Curso c) {
		return c.getBloquesDeContenido().stream()
				.flatMap( b -> b.getPreguntas().stream() )
				.collect( Collectors.toList() );
	}
}
