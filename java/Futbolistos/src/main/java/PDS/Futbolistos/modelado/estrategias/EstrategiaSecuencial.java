package PDS.Futbolistos.modelado.estrategias;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import PDS.Futbolistos.modelado.BloqueDeContenido;
import PDS.Futbolistos.modelado.Curso;
import PDS.Futbolistos.modelado.Pregunta;

public class EstrategiaSecuencial implements EstrategiaAprendizaje {

	@Override
	public List<Pregunta> calcularOrden(Curso c) {
		return c.getBloquesDeContenido().stream()
				.flatMap( b -> b.getPreguntas().stream() )
				.collect( Collectors.toList() );
	}
}
