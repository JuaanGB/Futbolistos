package pds.futbolistos.modelado;

import java.util.ArrayList;
import java.util.List;

public class CatalogoCursos {

	private static CatalogoCursos instancia;

	private List<Curso> cursos;

	private CatalogoCursos() {
		cursos = new ArrayList<>();
	}

	public static CatalogoCursos getInstancia() {
		if (instancia == null) {
			instancia = new CatalogoCursos();
		}
		return instancia;
	}

	public List<Curso> obtenerCursos() {
		return cursos;
	}

	public void agregarCurso(Curso curso) {
		cursos.add(curso);
	}

	public void eliminarCurso(Curso curso) {
		cursos.remove(curso);
	}

}
