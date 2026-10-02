package View;

import java.awt.EventQueue;

import javax.swing.JFrame;

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
		presenter = new Presenter(vistaMapa, new GSONPersistencia());
		controles = new PanelControles();
		
		frame.add(vistaMapa.getMapa());
		frame.add(controles);
		
		conectarEventos();
	}
	
	public void mostrar() {
		frame.setVisible(true);
	}
	
    private void conectarEventos() {

        controles.setAccionGuardar(e -> presenter.guardarGrafo());
        
        controles.setAccionCargar(e -> presenter.cargarGrafo());

        controles.setAccionCargarArchivo(e -> {});

        controles.setAccionEliminarFrontera(e -> presenter.eliminarFrontera
        		(controles.getIndiceOrigen(),controles.getIndiceDestino()) );
    }

	public static void main(String[] args) {
	    EventQueue.invokeLater(() -> {
	        VentanaPrincipal ventana = new VentanaPrincipal();
	        ventana.mostrar();
	    });
	}

}
