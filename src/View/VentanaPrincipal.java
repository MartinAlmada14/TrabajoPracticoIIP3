package View;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class VentanaPrincipal {
	
	private JFrame frame;
//	private VistaMapa vistaMapa;
	private PanelControles controles;
	private String nombre = "Diseñando Regiones";
	
	public VentanaPrincipal() {
		inicializar();
	}

	private void inicializar() {
		frame = new JFrame(nombre);
		
		
//		vistaMapa = new VistaMapa();
		controles = new PanelControles();
		
//		frame.add(vistaMapa);
		frame.add(controles);
	}
	
	public void mostrar() {
		frame.setVisible(true);
	}

	public static void main(String[] args) {
	    EventQueue.invokeLater(() -> {
	        VentanaPrincipal ventana = new VentanaPrincipal();
	        ventana.mostrar();
	    });
	}

}
