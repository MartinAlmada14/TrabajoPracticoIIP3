package Model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class KruskalTest {

	private Kruskal kruskal;

	@Before
	public void setUp() {
		kruskal = new Kruskal();
	}

	@Test
	public void arbolGeneradorMinimoCantidadAristasTest() {
		Grafo grafo = new Grafo(4);
		grafo.agregarArista(0, 1, 1.0);
		grafo.agregarArista(1, 2, 2.0);
		grafo.agregarArista(2, 3, 3.0);
		grafo.agregarArista(3, 0, 4.0);
		grafo.agregarArista(0, 2, 5.0);

		List<Arista> arbol = kruskal.calcular(grafo);

		assertEquals(3, arbol.size());
	}

	@Test
	public void arbolGeneradorMinimoCostoMinimoTest() {
		Grafo grafo = new Grafo(3);
		grafo.agregarArista(0, 1, 10.0);
		grafo.agregarArista(1, 2, 1.0);
		grafo.agregarArista(0, 2, 2.0);

		List<Arista> arbol = kruskal.calcular(grafo);

		double costoTotal = 0;
		for (Arista arista : arbol) {
			costoTotal += arista.getPeso();
		}

		assertEquals(2, arbol.size());
		assertEquals(3.0, costoTotal, 0.0001);
	}

	@Test
	public void arbolEvitaCiclosTest() {
		Grafo grafo = new Grafo(3);
		grafo.agregarArista(0, 1, 1.0);
		grafo.agregarArista(1, 2, 1.0);
		grafo.agregarArista(0, 2, 1.0);

		List<Arista> arbol = kruskal.calcular(grafo);

		assertEquals(2, arbol.size());
	}

	@Test
	public void grafoDeUnSoloVerticeRetornaArbolVacioTest() {
		Grafo grafo = new Grafo(1);

		List<Arista> arbol = kruskal.calcular(grafo);

		assertTrue(arbol.isEmpty());
	}

	@Test
	public void grafoNoConexoRetornaMenosAristasTest() {
		Grafo grafo = new Grafo(4);
		grafo.agregarArista(0, 1, 1.0);
		grafo.agregarArista(2, 3, 2.0);

		List<Arista> arbol = kruskal.calcular(grafo);

		assertEquals(2, arbol.size());
	}
}