package View;

import java.awt.EventQueue;
import java.util.zip.ZipEntry;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import Model.Vertice;
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
		controles = new PanelControles();
		presenter = new Presenter(vistaMapa, new GSONPersistencia(), controles);
		
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

        controles.setAccionAgregarVertice(e -> agregarVertice());
        controles.setAccionAgregarArista(e -> agregarArista());
        controles.setAccionEliminarArista(e -> eliminarArista());
        controles.setAccionCalcular(e -> calcularRegiones());
        controles.setAccionGuardar(e -> guardarGrafo());
        controles.setAccionCargarArchivo(e -> cargarDesdeArchivo());
        controles.setAccionCargar(e -> cargarGrafo());
        
    }

	private void agregarVertice() {

        try {
            String nombre = controles.getNombre();
            double latitud = Double.parseDouble(controles.getLatitud());
            double longitud = Double.parseDouble(controles.getLongitud());

            presenter.agregarVertice(nombre, latitud, longitud);
            controles.limpiarNombre();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "La latitud y longitud deben ser números.");
        }
    }
	
    private void agregarArista() {
    	try {
    		double peso = Double.parseDouble(controles.getPeso());
    		presenter.agregarArista(controles.getIndiceOrigen(), controles.getIndiceDestino(), peso);
    		controles.limpiarPeso();
    	} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(frame, e.getMessage());
		}
    }
	
    private void eliminarArista() {
    	presenter.eliminarArista(controles.getIndiceOrigen(), controles.getIndiceDestino());
    }
    
    private void calcularRegiones() {
    	try {
    		presenter.regionizador(Integer.parseInt(controles.getK()));
    	} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(frame, e.getMessage());
		}
    }
    
    private void guardarGrafo() {
    	presenter.guardarGrafo();
    }
    
    private void cargarGrafo() {
    	presenter.cargarGrafo();
    }
    
    private void cargarDesdeArchivo() {
    	controles.elegirArchivo().ifPresent(presenter::cargarGrafoDesde);
    }
    
	public static void main(String[] args) {
	    EventQueue.invokeLater(() -> {
	        VentanaPrincipal ventana = new VentanaPrincipal();
	        ventana.mostrar();
	    });
	}

}
