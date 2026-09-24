package Model;

import java.util.Collections;
import java.util.List;

public class ArbolGeneradorMinimo {
	private Grafo arbol;
	
	public ArbolGeneradorMinimo(Grafo arbol) {
		this.arbol = arbol;
	}
	
    public void eliminarAristasMayores(int k) {
    	
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
    }
	
	public List<Arista> aristas() {
		return arbol.aristas();
	}
	
	public void generarRegiones() {
		
	}

}
