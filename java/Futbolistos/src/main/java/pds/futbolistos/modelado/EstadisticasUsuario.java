package pds.futbolistos.modelado;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;

@Embeddable
public class EstadisticasUsuario {

	// Atributos
	private int preguntasRespondidas, preguntasAcertadas, cursosRealizados, cursosCreados, mejorRachaDias,
			pistasConsultadas;
	private int tiempoTotalDeUso; // en segundos
	private LocalDateTime fechaUltimoAcceso;
	private LocalDateTime fechaAlCerrarAplicacion;
	private int rachaDiasActual;


	// Constructor
	public EstadisticasUsuario() {
		this.preguntasRespondidas = 0;
		this.preguntasAcertadas = 0;
		this.cursosRealizados = 0;
		this.cursosCreados = 0;
		this.mejorRachaDias = 0;
		this.pistasConsultadas = 0;
		this.tiempoTotalDeUso = 0;
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

	public int getPistasConsultadas() {
		return pistasConsultadas;
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
