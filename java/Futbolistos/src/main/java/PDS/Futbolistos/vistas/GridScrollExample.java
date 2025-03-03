package PDS.Futbolistos.vistas;

import javax.swing.*;
import java.awt.*;

public class GridScrollExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Tabla con GridLayout y Scroll");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Panel con GridLayout
        JPanel panelCursos = new JPanel(new GridLayout(0, 2, 10, 10)); // 2 columnas, espaciado de 10px
        for (int i = 1; i <= 20; i++) { // Agregamos 20 elementos como ejemplo
            JButton btn = new JButton("Botón " + i);
            panelCursos.add(btn);
        }

        // **EN EL SCROLLPANE, NECESITAMOS UN PANEL ENVOLVENTE PARA QUE FUNCIONE**
        JPanel panelWrapper = new JPanel(new BorderLayout());
        panelWrapper.add(panelCursos, BorderLayout.NORTH); // Ajusta el tamaño sin expandir

        // Agregamos el scrollpane
        JScrollPane scrollPane = new JScrollPane(panelWrapper);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        frame.add(scrollPane);
        frame.setVisible(true);
    }
}
