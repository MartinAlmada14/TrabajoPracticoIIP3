package Model;

public class UnionFind {
	private int[] padre;
	private int[] rango;
	
	public UnionFind(int cantidadElementos) {
		
	}
	
	public int buscar(int elemento) {
		if (padre[elemento] != elemento) {
			padre[elemento] = buscar(padre[elemento]);
		}
		return padre[elemento];
	}
	
	public void unir(int elemento1, int elemento2) {

	    int raiz1 = buscar(elemento1);
	    int raiz2 = buscar(elemento2);

	    if (raiz1 == raiz2) {
	        return;
	    }
	    // unir según rango
	}
	
	public boolean mismoConjunto(int elemento1, int elemento2) {
		return true;
	}
}
