package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Persistence.GSONPersistencia;
import Presenter.Presenter;

public class VentanaPrincipal {
	
	private Presenter presenter;
	private JFrame frame;
	private VistaMapa vistaMapa;
	private PanelControles controles;
	private String nombre = "Diseñando Regiones";
	
	public VentanaPrincipal() {
		inicializar();
	}

	private void inicializar() {
		frame = new JFrame(nombre);		
		frame.setBounds(100, 100, 1000, 650); // dentro del límite 1366x768
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		vistaMapa = new VistaMapa(frame);
		presenter = new Presenter(vistaMapa, new GSONPersistencia(), controles);
		controles = new PanelControles();
		
		frame.add(vistaMapa.getMapa());
		frame.add(controles);
		
		conectarEventos();
		
	    vistaMapa.setAccionClickMapa(coordenada -> {
	        controles.establecerLatitud(
	            coordenada.getLat()
	        );
	        controles.establecerLongitud(
	            coordenada.getLon()
	        );
	    });
	}
	
	public void mostrar() {
		frame.setVisible(true);
	}
	
    private void conectarEventos() {

        controles.setAccionAgregarVertice(
                e -> agregarVertice()
            );
        
        controles.setAccionAgregarVertice(
                e -> agregarArista()
            );
    }

    private Object agregarArista() {
		// TODO Auto-generated method stub
		return null;
	}

	private void agregarVertice() {

        try {
            String nombre = controles.getNombre();
            double latitud = Double.parseDouble(controles.getLatitud());
            double longitud = Double.parseDouble(controles.getLongitud());

            presenter.agregarVertice(nombre, latitud, longitud);
            controles.limpiarNombre();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                frame,
                "La latitud y longitud deben ser números."
            );
        }
    }

	public static void main(String[] args) {
	    EventQueue.invokeLater(() -> {
	        VentanaPrincipal ventana = new VentanaPrincipal();
	        ventana.mostrar();
	    });
	}

}
