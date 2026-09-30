package Model;

import java.util.ArrayList;
import java.util.List;

public class Pais {
	private List<Provincia> provincias = new ArrayList<Provincia>();
	private Grafo grafo = new Grafo(0);
	
	public int agregarProvincia(Provincia provincia) {
		provincias.add(provincia);
		redimensionarGrafo();
		return provincias.size() - 1;
	}
	
	public void agregarFrontera(int origen, int destino, double similaridad) {
		grafo.agregarArista(origen, destino, similaridad);
	}
	
	public void eliminarFrontera(int origen, int destino) {
		grafo.eliminarArista(origen, destino);
	}
	
	public void redimensionarGrafo() {
		Grafo nuevo = new Grafo(provincias.size());
		for(Arista a : grafo.aristas()) {
			nuevo.agregarArista(a.getOrigen(), a.getDestino(), a.getPeso());
		}
		grafo = nuevo;
	}

	public List<Provincia> getProvincias() {
		return provincias;
	}

	public Provincia getProvincia(int indice) {
		return provincias.get(indice);
	}
	
	public Grafo getGrafo() {
		return grafo;
	}
	
}
