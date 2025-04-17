package pds.futbolistos.vistas;

import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import pds.futbolistos.controlador.Controlador;
import pds.futbolistos.modelado.EstadisticasUsuario;
import pds.futbolistos.vistas.componentes.FactoriaComponentes;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.List;

public class VentanaEstadisticasUsuario extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final int NUM_DIAS_HISTORIAL_RACHA = 10;

	public VentanaEstadisticasUsuario() {
		setTitle("Estadísticas del Usuario");
		setSize(1200, 450);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setMinimumSize(new Dimension(1200, 450));

		getContentPane().setBackground(new Color(30, 30, 30));
		getContentPane().setLayout(new BorderLayout());

		EstadisticasUsuario stats = Controlador.getInstancia().getEstadisticasDeUsuarioAct();

		JPanel panelGraficos = new JPanel(new GridLayout(1, 3, 10, 10));
		panelGraficos.setBackground(new Color(30, 30, 30));
		panelGraficos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		panelGraficos.add(crearGraficoTarta(stats));
		panelGraficos.add(crearGraficoBarras(stats));
		panelGraficos.add(crearGraficoLineas(stats));

		JLabel lblTiempo = FactoriaComponentes
				.crearLabel("Tiempo total de uso: " + stats.getTiempoTotalDeUsoFormateado());
		lblTiempo.setFont(new Font("Arial", Font.BOLD, 18));
		lblTiempo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTiempo.setForeground(Color.WHITE);
		lblTiempo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

		getContentPane().add(panelGraficos, BorderLayout.CENTER);
		getContentPane().add(lblTiempo, BorderLayout.SOUTH);

		setVisible(true);
	}

	private JPanel crearGraficoTarta(EstadisticasUsuario stats) {
		int acertadas = stats.getPreguntasAcertadas();
		int fallos = stats.getPreguntasRespondidas() - acertadas;

		DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
		dataset.setValue("Bien", acertadas);
		dataset.setValue("Mal", Math.max(0, fallos));

		JFreeChart chart = ChartFactory.createPieChart("Aciertos vs. Fallos", dataset, true, true, false);
		chart.setBackgroundPaint(new Color(30, 30, 30));

		// Aseguramos que la leyenda está habilitada
		LegendTitle legend = chart.getLegend();
		if (legend != null) {
			legend.setItemPaint(Color.BLACK); // Cambiamos el color de los elementos de la leyenda a blanco
		}

		// Personalizamos el título y los textos del gráfico
		chart.getTitle().setPaint(Color.WHITE);
		chart.getPlot().setBackgroundPaint(new Color(30, 30, 30));

		// Configuramos las etiquetas en el gráfico de tarta
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setLabelPaint(Color.BLACK);
		plot.setSectionPaint(0, new Color(85, 170, 255)); // Aciertos
		plot.setSectionPaint(1, new Color(255, 85, 85)); // Fallos

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setBackground(new Color(30, 30, 30));
		return chartPanel;
	}

	private JPanel crearGraficoBarras(EstadisticasUsuario stats) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.setValue(stats.getCursosRealizados(), "Cursos", "Realizados");
		dataset.setValue(stats.getCursosCreados(), "Cursos", "Creados");

		JFreeChart chart = ChartFactory.createBarChart("Actividad en Cursos", "Tipo", "Cantidad", dataset,
				PlotOrientation.VERTICAL, false, true, false);

		chart.setBackgroundPaint(new Color(30, 30, 30));

		// Personalizar título y texto del gráfico
		chart.getTitle().setPaint(Color.WHITE);
		chart.getCategoryPlot().getDomainAxis().setLabelPaint(Color.WHITE);
		chart.getCategoryPlot().getRangeAxis().setLabelPaint(Color.WHITE);
		chart.getCategoryPlot().setBackgroundPaint(new Color(40, 40, 40));

		// Estilo de las barras y texto
		chart.getCategoryPlot().getRenderer().setSeriesPaint(0, new Color(85, 170, 255));
		chart.getCategoryPlot().getRenderer().setSeriesPaint(1, new Color(255, 170, 85));

		// Configurar las etiquetas
		chart.getCategoryPlot().getDomainAxis().setTickLabelPaint(Color.WHITE);
		chart.getCategoryPlot().getRangeAxis().setTickLabelPaint(Color.WHITE);

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setBackground(new Color(30, 30, 30));
		return chartPanel;
	}

	private JPanel crearGraficoLineas(EstadisticasUsuario stats) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		Map<LocalDate, Integer> historial = stats.getHistorialRachas(NUM_DIAS_HISTORIAL_RACHA);
		for (Map.Entry<LocalDate, Integer> entry : historial.entrySet()) {
			dataset.addValue(entry.getValue(), "Racha", entry.getKey().toString()); // Fecha como String
		}

		JFreeChart chart = ChartFactory.createLineChart("Evolución de la Racha", "Fecha", "Días", dataset,
				PlotOrientation.VERTICAL, false, true, false);

		chart.setBackgroundPaint(new Color(30, 30, 30));
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(new Color(40, 40, 40));
		plot.getRenderer().setSeriesPaint(0, new Color(255, 200, 0)); // Color de la línea

		// Colores para título y ejes
		chart.getTitle().setPaint(Color.WHITE);
		plot.getDomainAxis().setLabelPaint(Color.WHITE);
		plot.getRangeAxis().setLabelPaint(Color.WHITE);
		plot.getDomainAxis().setTickLabelPaint(Color.WHITE);
		plot.getRangeAxis().setTickLabelPaint(Color.WHITE);

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setBackground(new Color(30, 30, 30));
		return chartPanel;
	}

}
