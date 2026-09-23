package Model;

import java.util.HashSet;
import java.util.Set;

public class Grafo {
	private Set<Arista> aristas;
	private Arista[][] matriz;

	public Grafo(int vertices) {
		matriz = new Arista[vertices][vertices];
	}

	public void agregarArista(int origen,int destino, double peso) {
		verificarAmbosVetices(origen, destino);

		Arista arista = new Arista(origen, destino, peso);

		matriz[origen][destino] = arista;
		matriz[destino][origen] = arista;
	}

	public void eliminarArista(int origen, int destino) {
		verificarAmbosVetices(origen, destino);

		matriz[origen][destino] = null;
		matriz[destino][origen] = null;
	}

	public boolean existeArista(int origen, int destino) {
		verificarAmbosVetices(origen, destino);

		return matriz[origen][destino] != null;
	}

	public double pesoArista(int origen, int destino) {
		verificarAmbosVetices(origen, destino);

		if (!existeArista(origen, destino)) {
			throw new IllegalArgumentException(
					"No existe una arista entre " + origen + " y " + destino);
		}
		return matriz[origen][destino].getPeso();
	}

	public int tamano() {
		return matriz.length;
	}

	public Set<Integer> vecinos(int arista) {
		verificarVertice(arista);

		Set<Integer> vecinos = new HashSet<Integer>();
		for(int j = 0; j < this.tamano(); ++j) if( arista != j ) {
			if( this.existeArista(arista,j) ) {
				vecinos.add(j);
			}
		}
		return vecinos;		
	}

	//	OBTENGO LAS ARISTAS SIN REPETIDOS
	public Set<Arista> aristas(){
		aristas = new HashSet<Arista>();

		for (int fila = 0; fila < matriz.length; fila++) {
			for (int columna = fila +1 ; columna < matriz.length; columna++) {
				if(matriz[fila][columna] != null) {
					aristas.add(matriz[fila][columna]);
				}
			}

		}
		return aristas;
	}

	//	VERIFICA QUE LOS VERTICES NO SEAN IGUALES
	private void verificarDistintos(int origen, int destino) {		
		if( origen == destino) {
			throw new IllegalArgumentException("No se permiten loops: (" + origen + ", " + destino + ")");
		}
	}

	//VERIFICA QUE LOS VERTICES ESTEN EN RANGO	
	private void verificarVertice(int vertice) {
		if(vertice<0) {
			throw new IllegalArgumentException("El vertice no puede ser negativo: " + vertice);
		}
		if (vertice >= matriz.length) {
			throw new IllegalArgumentException("Los vertices deben estar entre 0 y |V|-1: " + vertice);
		}
	}

	private void verificarAmbosVetices(int origen, int destino) {
		verificarVertice(destino);
		verificarVertice(origen);
		verificarDistintos(origen, destino);
	}
}
