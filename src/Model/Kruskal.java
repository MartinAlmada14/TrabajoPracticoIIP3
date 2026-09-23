package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Kruskal {
	private List<Arista> aristas;
	private List<Arista> arbol;
	
	
	public List<Arista> calcular(Grafo grafo) {

		aristas = grafo.aristas();

		Collections.sort(aristas);

		UnionFind unionFind = new UnionFind(grafo.tamano());

		arbol = new ArrayList<Arista>();

		for (Arista arista : aristas) {

			int origen = arista.getOrigen();
			int destino = arista.getDestino();

			if (!unionFind.mismoConjunto(origen, destino)) {

				arbol.add(arista);

				unionFind.unir(origen, destino);
			}

			if (arbol.size() == grafo.tamano() - 1) {
				break;
			}
		}

		return arbol;
	}
}
