package Presenter;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import Model.ArbolGeneradorMinimo;
import Model.Pais;
import Model.Provincia;

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
	
	public void regionizador(int k) {
		if(pais == null) {
			vista.mostrarMensaje("Primero cargá o armá el grafo.");
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
	
	public int agregarProvincia(String nombre, double latitud, double longitud) {
		if(pais == null){
			pais = new Pais();
		}
		Provincia provincia = new Provincia(nombre, latitud, longitud);
		int indice = pais.agregarProvincia(provincia);
		vista.agregarMarcador(provincia, indice);
		return indice;
	}
	
	public void agregarFrontera(int origen, int destino, double similiaridad) {
		if(pais == null) {
			vista.mostrarMensaje("Primero agregar minimo 2 provincias");
			return;
		}
		try {
			pais.agregarFrontera(origen, destino, similiaridad);
			vista.agregarFrontera(origen, destino, pais.getProvincia(origen), pais.getProvincia(destino));
		}
		catch (Exception e) {
			vista.mostrarMensaje("Error al agregar frontera: " + e.getMessage());
		}
		
	}
	
	public void eliminarFrontera(int origen, int destino) {
		if(pais == null) {
			return;
		}
		try {
			pais.eliminarFrontera(origen, destino);
			vista.eliminarFrontera(origen, destino);
			vista.mostrarMensaje("Frontera eliminada");
		}
		catch (Exception e) {
			vista.mostrarMensaje("Error al eliminar frontera " + e.getMessage());
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
		cargarDesde(persistencia.cargar());
	}
	
	public void cargarGrafoDesde(Path archivo) {
		cargarDesde(persistencia.cargar(archivo));
	}
	
	public void cargarDesde(Optional<Pais> resultado) {
		resultado.ifPresentOrElse(p -> {
			this.pais = p; 
			vista.cargarPais(p.getProvincias(), p.getGrafo().aristas());
			vista.mostrarMensaje("Grafo cargado");
			}, () -> vista.mostrarMensaje("No se encontro ningun grafo en ese archivo."));
	}
	
}
