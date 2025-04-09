package pds.futbolistos.modelado;

import java.time.Duration;

public class EstadisticasUsuario {

	// Atributos
	private int preguntasRespondidas, preguntasAcertadas, cursosRealizados, cursosCreados, mejorRachaDias,
			pistasConsultadas;
	private Duration tiempoTotalDeUso;

	// Constructor
	public EstadisticasUsuario() {
		this.preguntasRespondidas = 0;
		this.preguntasAcertadas = 0;
		this.cursosRealizados = 0;
		this.cursosCreados = 0;
		this.mejorRachaDias = 0;
		this.pistasConsultadas = 0;
		this.tiempoTotalDeUso = Duration.ZERO;
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

	public Duration getTiempoTotalDeUso() {
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

	public void sumarTiempo(int segundos) {
		tiempoTotalDeUso = tiempoTotalDeUso.plusSeconds(segundos);
	}

	@Override
	public String toString() {
		return "EstadisticasUsuario [preguntasRespondidas=" + preguntasRespondidas + ", preguntasAcertadas="
				+ preguntasAcertadas + ", cursosRealizados=" + cursosRealizados + ", cursosCreados=" + cursosCreados
				+ ", mejorRachaDias=" + mejorRachaDias + ", pistasConsultadas=" + pistasConsultadas
				+ ", tiempoTotalDeUso=" + tiempoTotalDeUso + "]";
	}

	public void actualizar(SesionCurso s, boolean completado) {
		if (completado)
			this.cursosRealizados++;
		this.preguntasAcertadas += s.getPuntuacion();
		this.preguntasRespondidas += s.getNumeroPreguntasRespondidas();
		this.pistasConsultadas += 3 - s.getPistasRestantes();
	}

}
