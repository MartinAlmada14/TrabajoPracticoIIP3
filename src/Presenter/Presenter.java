package Presenter;

import java.nio.file.Path;
import java.util.Optional;

import Model.ArbolGeneradorMinimo;
import Model.Pais;
import Model.ResultadoRegiones;
import Model.Vertice;

import Persistence.IPersistencia;
import View.IControlesView;
import View.IMapaView;

public class Presenter {

	private final IMapaView vistaMapa; 
	private final IPersistencia persistencia;
	private final IControlesView vistaControles;
	private Pais pais;
	
	public Presenter(IMapaView vistaMapa, IPersistencia persistencia, IControlesView vistaControles) {
		this.vistaMapa = vistaMapa;
		this.persistencia = persistencia;
		this.vistaControles = vistaControles;
	}
	
	public void regionizador(int k) {
		if(pais == null) {
			vistaMapa.mostrarMensaje("Primero cargá o armá el grafo.");
			return;
		}
		try {
			ResultadoRegiones resultado = new ArbolGeneradorMinimo(pais.getGrafo()).generarRegiones(k);
			vistaMapa.actualizarMapaConRegiones(resultado.getRegiones(),resultado.getAristasConservadas(), pais.getVertices());
		}
		catch (Exception e) {
			vistaMapa.mostrarMensaje("Error al calcular regiones: " + e.getMessage());
		}
	}
	
	public int agregarVertice(String nombre, double latitud, double longitud) {
		if(pais == null){
			pais = new Pais();
		}
		Vertice vertice = new Vertice(nombre, latitud, longitud);
		int indice = pais.agregarVertice(vertice);
		vistaMapa.agregarMarcador(vertice, indice);
		vistaControles.agregarVertice(vertice);
		return indice;
	}
	
	public void agregarArista(int origen, int destino, double peso) {
		if(pais == null) {
			vistaMapa.mostrarMensaje("Primero agregar minimo 2 Vertices");
			return;
		}
		try {
			pais.agregarArista(origen, destino, peso);
			vistaMapa.agregarArista(origen, destino, pais.getVertice(origen), pais.getVertice(destino));
		}
		catch (Exception e) {
			vistaMapa.mostrarMensaje("Error al agregar la Arista: " + e.getMessage());
		}
		
	}
	
	public void eliminarArista(int origen, int destino) {
		if(pais == null) {
			return;
		}
		try {
			pais.eliminarArista(origen, destino);
			vistaMapa.eliminarArista(origen, destino);
			vistaMapa.mostrarMensaje("Arista eliminada");
		}
		catch (Exception e) {
			vistaMapa.mostrarMensaje("Error al eliminar la Arista " + e.getMessage());
		}
		
	}
	
	public void guardarGrafo() {
		if(pais == null) {
			vistaMapa.mostrarMensaje("No hay grafo para guardar.");
			return;
		}
		persistencia.guardar(pais);
		vistaMapa.mostrarMensaje("El grafo se guardo correctamente.");
	}
	
	public void cargarGrafo() {
		cargarDesde(persistencia.cargar());
	}
	
	public void cargarGrafoDesde(Path archivo) {
		cargarDesde(persistencia.cargar(archivo));
	}
	
	private void cargarDesde(Optional<Pais> resultado) {
		resultado.ifPresentOrElse(this::mostrarPais, 
				() -> vistaMapa.mostrarMensaje("No se encontro ningun grafo en ese archivo."));
		}
	
	private void mostrarPais(Pais pais) {
		this.pais = pais;
		vistaMapa.cargarPais(pais.getVertices(), pais.getGrafo().aristas());
		vistaControles.limpiarVertice();
		pais.getVertices().forEach(vistaControles::agregarVertice);
		vistaMapa.mostrarMensaje("Grafo cargado");
	}
	
}
