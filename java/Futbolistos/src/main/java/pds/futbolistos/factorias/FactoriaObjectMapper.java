package pds.futbolistos.factorias;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

// Me parece bien tener esta factoría para desacoplar del Controlador la elección de un ObjectMapper u otro.
// Además, si se desea agregar nuevos formatos, está claro el trozo de código en el que hay que hacerlo.
public class FactoriaObjectMapper {

	private static FactoriaObjectMapper instancia;
	private Map<String, ObjectMapper> mappers;
	
	public FactoriaObjectMapper() {
		inicializarMapa();
	}

	public static FactoriaObjectMapper getInstancia() {
		if (instancia == null) {
			instancia = new FactoriaObjectMapper();
		}
		return instancia;
	}
	
	private void inicializarMapa() {
		mappers = new HashMap<>();
		mappers.put("json", new ObjectMapper());
		mappers.put("yaml", new ObjectMapper(new YAMLFactory()));
		mappers.put("yml", new ObjectMapper(new YAMLFactory()));
	}

	public ObjectMapper getMapper(String extension) {
		return mappers.get(extension);
	}
	
	public String[] getExtensionesValidas() {
		return mappers.keySet().stream()
				.toArray(String[]::new);
	}

}
