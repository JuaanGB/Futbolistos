package PDS.Futbolistos.modelado.estrategias;

import java.util.List;

import PDS.Futbolistos.modelado.Curso;
import PDS.Futbolistos.modelado.Pregunta;

public interface EstrategiaAprendizaje {

	List<Pregunta> calcularOrden(Curso c);

	
}
