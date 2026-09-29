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
	
	public ArbolGeneradorMinimo(Grafo arbol) {
		List<Arista> aristasAGM = new Kruskal().calcular(arbol);
		if(aristasAGM.size() != arbol.tamano() -1) {
			throw new IllegalArgumentException("El grafo debe ser conexo");
		}
		arbol = new Grafo(arbol.tamano());
		for(Arista a : aristasAGM) {
			arbol.agregarArista(a.getOrigen(), a.getDestino(), a.getPeso());
		}
		//this.arbol = arbol;
	}
	
    /*public void eliminarAristasMayores(int k) {
    	
    	if	(k > arbol.tamano()-1) {
			throw new IllegalArgumentException("Las posibles regiones que se pueden generar van de:" + 0 + "a" + arbol.tamano());
    	}
    	
        List<Arista> aristas = arbol.aristas();

        Collections.sort(aristas);

        int cantidadAEliminar = k - 1;

        for (int i = aristas.size()-1 ; i >= aristas.size() - cantidadAEliminar; i--) {

            Arista arista = aristas.get(i);

            arbol.eliminarArista(arista.getOrigen(), arista.getDestino());
        }
    }*/
	
	public List<Arista> aristas() {
		return arbol.aristas();
	}
	
	public List<Set<Integer>> generarRegiones(int k) {
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
		
		return new ArrayList<>(porRaiz.values());
	}

}
