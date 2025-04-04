package pds.futbolistos.factorias;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import pds.futbolistos.modelado.estrategias.EstrategiaAleatoria;
import pds.futbolistos.modelado.estrategias.EstrategiaAprendizaje;
import pds.futbolistos.modelado.estrategias.EstrategiaSecuencial;

public class FactoriaEstrategiasAprendizaje {

	private static FactoriaEstrategiasAprendizaje instancia;
	private Map<String, EstrategiaAprendizaje> estrategias;

	private FactoriaEstrategiasAprendizaje() {
		estrategias = new LinkedHashMap<>();
		inicializarEstrategias();
	}

	public static FactoriaEstrategiasAprendizaje getInstancia() {
		if (instancia == null) {
			instancia = new FactoriaEstrategiasAprendizaje();
		}
		return instancia;
	}

	private void inicializarEstrategias() {

		estrategias.put("Orden secuencial", new EstrategiaSecuencial());
		estrategias.put("Orden aleatorio", new EstrategiaAleatoria());
	}

	public EstrategiaAprendizaje obtenerEstrategia(String nombre) {
		return estrategias.get(nombre);
	}

	public Set<String> getEstrategias() {
		return estrategias.keySet();
	}
}
