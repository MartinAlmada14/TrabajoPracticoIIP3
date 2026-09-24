package Model;

public class UnionFind {

    private int[] padre;
    private int[] rango;

    public UnionFind(int cantidadElementos) {

        padre = new int[cantidadElementos];
        rango = new int[cantidadElementos];

        for (int i = 0; i < cantidadElementos; i++) {
            padre[i] = i;
            rango[i] = 0;
        }
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

        if (rango[raiz1] < rango[raiz2]) {
            padre[raiz1] = raiz2;

        } else if (rango[raiz1] > rango[raiz2]) {
            padre[raiz2] = raiz1;

        } else {
            padre[raiz2] = raiz1;
            rango[raiz1]++;
        }
    }

    public boolean mismoConjunto(int elemento1, int elemento2) {
        return buscar(elemento1) == buscar(elemento2);
    }
}
