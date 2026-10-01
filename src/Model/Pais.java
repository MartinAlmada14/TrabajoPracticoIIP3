package Model;

import java.util.ArrayList;
import java.util.List;

public class Pais {
	private List<Vertice> vertices = new ArrayList<Vertice>();
	private Grafo grafo = new Grafo(0);
	
	public int agregarVertice(Vertice provincia) {
		vertices.add(provincia);
		redimensionarGrafo();
		return vertices.size() - 1;
	}
	
	public void agregarRelacion(int origen, int destino, double similaridad) {
		grafo.agregarArista(origen, destino, similaridad);
	}
	
	public void eliminarRelacion(int origen, int destino) {
		grafo.eliminarArista(origen, destino);
	}
	
	public void redimensionarGrafo() {
		Grafo nuevo = new Grafo(vertices.size());
		for(Arista a : grafo.aristas()) {
			nuevo.agregarArista(a.getOrigen(), a.getDestino(), a.getPeso());
		}
		grafo = nuevo;
	}

	public List<Vertice> getVertices() {
		return vertices;
	}

	public Vertice getProvincia(int indice) {
		return vertices.get(indice);
	}
	
	public Grafo getGrafo() {
		return grafo;
	}
	
}
