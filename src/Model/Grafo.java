package Model;

import java.util.HashSet;
import java.util.Set;

public class Grafo {

	private Arista[][] matriz;

	public Grafo(int vertices) {
		matriz = new Arista[vertices][vertices];
	}

	public void agregarArista(int origen,int destino, double peso) {
		verificarVertice(origen);
		verificarVertice(destino);
		verificarDistintos(origen,destino);
		
		Arista arista = new Arista(origen, destino, peso);
		
		matriz[origen][destino] = arista;
		matriz[destino][origen] = arista;
	}

    public void eliminarArista(int origen, int destino) {
        verificarVertice(origen);
        verificarVertice(destino);
        verificarDistintos(origen, destino);

        matriz[origen][destino] = null;
        matriz[destino][origen] = null;
    }

    public boolean existeArista(int origen, int destino) {
        verificarVertice(origen);
        verificarVertice(destino);
        verificarDistintos(origen, destino);

        return matriz[origen][destino] != null;
    }
    
   public int tamano() {
	   return matriz.length;
   }

	public Set<Integer> vecinos(int vertice) {
		verificarVertice(vertice);

		Set<Integer> vecinos = new HashSet<Integer>();
		for(int j = 0; j < this.tamano(); ++j) if( vertice != j ) {
			if( this.existeArista(vertice,j) ) {
				vecinos.add(j);
			}
		}
		return vecinos;		
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
}
