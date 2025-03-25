package PDS.Futbolistos.lanzador;

import java.awt.EventQueue;
import javax.swing.UIManager;
import PDS.Futbolistos.vistas.Login;


public class Lanzador {
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
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
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Si Nimbus no está disponible, se usará la apariencia por defecto
        }
    }
}
