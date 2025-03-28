package PDS.Futbolistos.factorias;

import PDS.Futbolistos.modelado.estrategias.EstrategiaAleatoria;
import PDS.Futbolistos.modelado.estrategias.EstrategiaAprendizaje;
import PDS.Futbolistos.modelado.estrategias.EstrategiaSecuencial;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FactoriaEstrategiasAprendizaje {

	private static FactoriaEstrategiasAprendizaje instancia;
	private Map<String, EstrategiaAprendizaje> estrategias;

	private FactoriaEstrategiasAprendizaje() {
		estrategias = new HashMap<>();
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
