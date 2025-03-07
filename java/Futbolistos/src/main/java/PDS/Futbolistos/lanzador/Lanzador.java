package PDS.Futbolistos.lanzador;

import java.awt.EventQueue;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.jtattoo.plaf.aluminium.AluminiumLookAndFeel;

import PDS.Futbolistos.vistas.VentanaPrincipal;

public class Lanzador {
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal window = new VentanaPrincipal();
					window.setVisible(true);
					// cambiarLookAndFeel();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void cambiarLookAndFeel() {
        try {
            // Configuración opcional del tema
            Properties props = new Properties();
            props.put("logoString", "MiApp"); // Cambia el texto en la barra de título
            AluminiumLookAndFeel.setCurrentTheme(props);

            // Aplicar el Look and Feel
            UIManager.setLookAndFeel(new AluminiumLookAndFeel());

            // Actualizar la interfaz de usuario de todos los componentes visibles
            SwingUtilities.updateComponentTreeUI(JFrame.getFrames()[0]);

        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
