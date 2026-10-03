package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ArbolGeneradorMinimo {
	private Grafo arbol;
	
	public ArbolGeneradorMinimo(Grafo original) {
		List<Arista> aristasAGM = new Kruskal().calcular(original);
		if(aristasAGM.size() != original.tamano() -1) {
			throw new IllegalArgumentException("El grafo debe ser conexo");
		}
		arbol = new Grafo(original.tamano());
		for(Arista a : aristasAGM) {
			arbol.agregarArista(a.getOrigen(), a.getDestino(), a.getPeso());
		}
	}

	public ResultadoRegiones generarRegiones(int k) {
		if(k < 1 || k > arbol.tamano()) {
			throw new IllegalArgumentException("Las posibles regiones que se pueden generar van de: 1 a " + arbol.tamano());
		}
		
		List<Arista> aristas = arbol.aristas();
		Collections.sort(aristas);
		int aConservar = aristas.size() - (k - 1);
		UnionFind unionFind = new UnionFind(arbol.tamano());
		for(int i = 0; i < aConservar; i++) {
			unionFind.unir(aristas.get(i).getOrigen(), aristas.get(i).getDestino());
		}
		Map<Integer, Set<Integer>> porRaiz = new HashMap<>();
		for(int v = 0; v < arbol.tamano(); v++) {
			porRaiz.computeIfAbsent(unionFind.buscar(v), r -> new HashSet<>()).add(v);
		}
		List<Arista>conservadas = new ArrayList<>(aristas.subList(0, aConservar));
		System.out.println("aristas en el arbol: " + aristas.size());
		System.out.println("aConservar: " + aConservar);
		return new ResultadoRegiones (new ArrayList<>(porRaiz.values()), conservadas);
	}

}
