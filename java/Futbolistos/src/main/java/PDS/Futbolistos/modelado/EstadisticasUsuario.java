package PDS.Futbolistos.modelado;

import java.time.Duration;

public class EstadisticasUsuario {
	
	// Atributos
    private int preguntasRespondidas, preguntasAcertadas, cursosRealizados, cursosCreados, mejorRachaDias;
    private Duration tiempoTotalDeUso;

    // Constructor
    public EstadisticasUsuario() { 
    	this.preguntasRespondidas = 0;
    	this.preguntasAcertadas = 0;
    	this.cursosRealizados = 0;
    	this.cursosCreados = 0;
    	this.mejorRachaDias = 0; 
    	this.tiempoTotalDeUso = Duration.ZERO; 
    }

    // Getters
    public int getPreguntasRespondidas() { return preguntasRespondidas; }
    public int getPreguntasAcertadas() { return preguntasAcertadas; }
    public int getCursosRealizados() { return cursosRealizados; }
    public int getCursosCreados() { return cursosCreados; }
    public int getMejorRachaDias() { return mejorRachaDias; }
    public Duration getTiempoTotalDeUso() { return tiempoTotalDeUso; }

    // Funcionalidad
    public void registrarRespuesta(boolean correcta) { 
    	preguntasRespondidas++; 
    	if (correcta) preguntasAcertadas++; 
    }
    public void incrementarCursosRealizados() { cursosRealizados++; }
    public void incrementarCursosCreados() { cursosCreados++; }
    public void actualizarMejorRacha(int rachaActual) { 
    	if (rachaActual > mejorRachaDias) 
    		mejorRachaDias = rachaActual; 
    }
    public void sumarTiempo(int segundos) { tiempoTotalDeUso = tiempoTotalDeUso.plusSeconds(segundos); }
    
    @Override
    public String toString() { return "Estadísticas: Preguntas Respondidas: " + preguntasRespondidas + ", Preguntas Acertadas: " + preguntasAcertadas + ", Cursos Realizados: " + cursosRealizados + ", Cursos Creados: " + cursosCreados + ", Mejor Racha de Días: " + mejorRachaDias + ", Tiempo Total de Uso: " + tiempoTotalDeUso.toMinutes() + " min"; }
}
