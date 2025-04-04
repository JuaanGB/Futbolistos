package pds.futbolistos.modelado.estrategias;

import java.util.List;

import pds.futbolistos.modelado.Curso;
import pds.futbolistos.modelado.Pregunta;

public interface EstrategiaAprendizaje {

	List<Pregunta> calcularOrden(Curso c);

	
}
