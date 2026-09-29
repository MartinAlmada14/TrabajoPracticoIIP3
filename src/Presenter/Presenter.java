package Presenter;

import java.util.List;
import java.util.Set;

import org.openstreetmap.gui.jmapviewer.Coordinate;

import Model.ArbolGeneradorMinimo;
import Model.Grafo;
import Model.Pais;
import Persistence.GSONPersistencia;
import Persistence.IPersistencia;
import View.IMapaView;

public class Presenter {

	private final IMapaView vista; 
	private final IPersistencia persistencia;
	private Pais pais;
	
	public Presenter(IMapaView vista, IPersistencia persistencia) {
		this.vista = vista;
		this.persistencia = persistencia;
	}
	
	public void regionizador(int k, List<Coordinate> coordenadas) {
		if(pais == null) {
			vista.mostrarMensaje("Primero debes caergar o armar el grafo.");
			return;
		}
		try {
			List<Set<Integer>> regiones = new ArbolGeneradorMinimo(pais.getGrafo()).generarRegiones(k);
			vista.actualizarMapaConRegiones(regiones, pais.getProvincias());
		}
		catch (Exception e) {
			vista.mostrarMensaje("Error al calcular regiones: " + e.getMessage());
		}
	}
	
	public void guardarGrafo() {
		if(pais == null) {
			vista.mostrarMensaje("No hay grafo para guardar.");
			return;
		}
		persistencia.guardar(pais);
		vista.mostrarMensaje("El grafo se guardo correctamente.");
	}
	
	public void cargarGrafo() {
		persistencia.cargar().ifPresentOrElse(p -> {
			this.pais = p; vista.mostrarMensaje("Grafo cargado");
			}, () -> vista.mostrarMensaje("No hay ningun grafo guardado todavia."));
	}
	
}
