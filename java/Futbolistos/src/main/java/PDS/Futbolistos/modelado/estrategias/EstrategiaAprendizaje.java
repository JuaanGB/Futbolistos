package PDS.Futbolistos.modelado.estrategias;

import java.util.List;
import java.util.Set;

import PDS.Futbolistos.modelado.BloqueDeContenido;
import PDS.Futbolistos.modelado.Pregunta;

public interface EstrategiaAprendizaje {

	List<Pregunta> calcularOrden(Set<BloqueDeContenido> bloquesDeContenido);

	
}
