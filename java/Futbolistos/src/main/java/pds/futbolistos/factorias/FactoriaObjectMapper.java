package pds.futbolistos.factorias;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

// Me parece bien tener esta factoría para desacoplar del Controlador la elección de un ObjectMapper u otro.
// Además, si se desea agregar nuevos formatos, está claro el trozo de código en el que hay que hacerlo.
public class FactoriaObjectMapper {

	private static FactoriaObjectMapper instancia;

	public static FactoriaObjectMapper getInstancia() {
		if (instancia == null) {
			instancia = new FactoriaObjectMapper();
		}
		return instancia;
	}

	public ObjectMapper getMapper(String extension) {
		switch (extension.toLowerCase()) {
		case "json":
			return new ObjectMapper();
		case "yaml":
		case "yml":
			return new ObjectMapper(new YAMLFactory());
		default:
			throw new IllegalArgumentException("Formato no soportado: " + extension);
		}
	}

}
