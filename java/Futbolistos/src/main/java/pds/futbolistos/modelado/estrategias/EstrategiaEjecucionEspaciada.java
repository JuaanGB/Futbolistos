package pds.futbolistos.modelado.estrategias;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import pds.futbolistos.modelado.BloqueDeContenido;
import pds.futbolistos.modelado.Curso;
import pds.futbolistos.modelado.Pregunta;

public class EstrategiaEjecucionEspaciada implements EstrategiaAprendizaje {

	@Override
	public List<Pregunta> calcularOrden(Curso c) {
		List<Pregunta> originales = c.getBloquesDeContenido().stream()
				.flatMap(b -> b.getPreguntas().stream())
				.collect(Collectors.toList());

		int tamaño = originales.size() * 2;
		List<Pregunta> espaciadas = new ArrayList<>(tamaño);

		espaciadas.addAll(originales);

		for (int i = 0; i < originales.size(); i++) {
			int nuevaPos = i + 3;

			if (nuevaPos > espaciadas.size()) {
				espaciadas.add(originales.get(i));
			} else {
				espaciadas.add(nuevaPos, originales.get(i));
			}
		}

		return espaciadas;
	}
}
