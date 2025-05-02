package pds.futbolistos.modelado;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;

@Embeddable
public class EstadisticasUsuario {

	// Atributos
	private int preguntasRespondidas, preguntasAcertadas, cursosRealizados, cursosCreados, mejorRachaDias,
			pistasConsultadas;
	private int tiempoTotalDeUso; // en segundos
	private LocalDateTime fechaUltimoAcceso;
	private LocalDateTime fechaAlCerrarAplicacion;
	private int rachaDiasActual;

	@ElementCollection
	@MapKeyColumn(name = "fecha") // La clave es la fecha localdate
	@Column(name = "racha") // Columna que almacena el valor de la racha para ese dia
	@CollectionTable(name = "HISTORIAL_RACHA", joinColumns = @JoinColumn(name = "nombre_usuario"))
	private Map<LocalDate, Integer> historialRachas;

	// Constructor
	public EstadisticasUsuario() {
		this.preguntasRespondidas = 0;
		this.preguntasAcertadas = 0;
		this.cursosRealizados = 0;
		this.cursosCreados = 0;
		this.mejorRachaDias = 0;
		this.pistasConsultadas = 0;
		this.tiempoTotalDeUso = 0;
		this.historialRachas = new LinkedHashMap<>();
	}

	// Getters
	public int getPreguntasRespondidas() {
		return preguntasRespondidas;
	}

	public int getPreguntasAcertadas() {
		return preguntasAcertadas;
	}

	public int getCursosRealizados() {
		return cursosRealizados;
	}

	public int getCursosCreados() {
		return cursosCreados;
	}

	public int getMejorRachaDias() {
		return mejorRachaDias;
	}

	public int getTiempoTotalDeUso() {
		return tiempoTotalDeUso;
	}
	
	public String getMediaPistasPorCursoRedondeado() {
		if (cursosRealizados == 0) {
			return "0.00";
		}
		float media = (float) pistasConsultadas / cursosRealizados;
		return String.format("%.2f", media);
	}

	public String getTiempoTotalDeUsoFormateado() {
		int horas = tiempoTotalDeUso / 3600;
		int minutos = (tiempoTotalDeUso % 3600) / 60;
		return horas + "h " + minutos + "min";
	}

	public int getPistasConsultadas() {
		return pistasConsultadas;
	}

	public int getRachaDiasActual() {
		return rachaDiasActual;
	}

	// Devolvemos las 10 últimas entradas para no saturar el gráfico de la vista
	public Map<LocalDate, Integer> getHistorialRachas(int numDias) {
		List<Map.Entry<LocalDate, Integer>> entradas = new ArrayList<>(historialRachas.entrySet());

		// Ordenamos por fecha ascendente (cronológico)
		entradas.sort(Map.Entry.comparingByKey());

		int total = entradas.size();
		int desde = Math.max(0, total - numDias);

		List<Map.Entry<LocalDate, Integer>> ultimasEntradas = entradas.subList(desde, total);

		// Preservamos el orden en un LinkedHashMap
		Map<LocalDate, Integer> resultado = new LinkedHashMap<>();
		for (Map.Entry<LocalDate, Integer> entry : ultimasEntradas) {
			resultado.put(entry.getKey(), entry.getValue());
		}

		return resultado;
	}



	// Funcionalidad
	public void incrementarCursosCreados() {
		cursosCreados++;
	}

	public void actualizarMejorRacha(int rachaActual) {
		if (rachaActual > mejorRachaDias)
			mejorRachaDias = rachaActual;
	}

	public void sumarTiempo(long segundos) {
		tiempoTotalDeUso += segundos;
	}

	@Override
	public String toString() {
		return "EstadisticasUsuario [preguntasRespondidas=" + preguntasRespondidas + ", preguntasAcertadas="
				+ preguntasAcertadas + ", cursosRealizados=" + cursosRealizados + ", cursosCreados=" + cursosCreados
				+ ", mejorRachaDias=" + mejorRachaDias + ", pistasConsultadas=" + pistasConsultadas
				+ ", tiempoTotalDeUso=" + tiempoTotalDeUso + "]";
	}

	public void actualizarTrasAcabarSesion(SesionCurso s) {
		this.cursosRealizados++;
		this.preguntasAcertadas += s.getPuntuacion();
		this.preguntasRespondidas += s.getNumeroPreguntasRespondidas();
		this.pistasConsultadas += 3 - s.getPistasRestantes();
	}

	public void registrarAcceso() {
		registrarAcceso(LocalDateTime.now());
	}

	// Con parámetro para realizar tests
	public void registrarAcceso(LocalDateTime fechaActual) {
		LocalDate hoy = fechaActual.toLocalDate();
		if (fechaUltimoAcceso != null) {
			LocalDate ultimo = fechaUltimoAcceso.toLocalDate();
			if (ultimo.plusDays(1).isEqual(hoy)) {
				rachaDiasActual++;
			} else if (!ultimo.isEqual(hoy)) {
				rachaDiasActual = 1;
			}
		} else { // Primer dia despues del primer login
			rachaDiasActual = 1;
		}
		fechaUltimoAcceso = fechaActual;
		actualizarMejorRacha(rachaDiasActual);
		historialRachas.put(hoy, rachaDiasActual);
	}

	public void registrarCierre() {
		registrarCierre(LocalDateTime.now());
	}

	// Con parámetro para realizar tests
	public void registrarCierre(LocalDateTime fechaCierre) {
		this.fechaAlCerrarAplicacion = fechaCierre;
		if (fechaUltimoAcceso != null && fechaAlCerrarAplicacion.isAfter(fechaUltimoAcceso)) {
			long segundos = Duration.between(fechaUltimoAcceso, fechaAlCerrarAplicacion).getSeconds();
			sumarTiempo(segundos);
		}
	}

	public void reiniciarFechaDeUltimoAcceso() {
		this.fechaUltimoAcceso = LocalDateTime.now();
	}

}
