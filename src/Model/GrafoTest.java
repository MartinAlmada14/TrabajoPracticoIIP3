package Model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class GrafoTest {

	private Grafo grafo;

	@Before
	public void setUp() {
		grafo = new Grafo(4);
	}
	
	@Test
	public void tamanoGrafoTest() {
		assertEquals(4, grafo.tamano());
	}

	@Test
	public void grafoRecienCreadoSinAristasTest() {
		for (int i = 0; i < grafo.tamano(); i++) {
			for (int j = 0; j < grafo.tamano(); j++) {
				if (i != j) {
					assertFalse(grafo.existeArista(i, j));
				}
			}
		}
		assertEquals(0, grafo.aristas().size());
	}

	@Test
	public void agregarYConsultarAristaTest() {
		grafo.agregarArista(0, 1, 5.5);

		assertTrue(grafo.existeArista(0, 1));
		assertTrue(grafo.existeArista(1, 0));
		assertEquals(5.5, grafo.pesoArista(0, 1), 0.0001);
		assertEquals(5.5, grafo.pesoArista(1, 0), 0.0001);
		assertFalse(grafo.existeArista(0, 2));
	}

	@Test
	public void sobrescribirAristaExistenteTest() {
		grafo.agregarArista(0, 1, 2.0);
		grafo.agregarArista(0, 1, 8.5);

		assertTrue(grafo.existeArista(0, 1));
		assertEquals(8.5, grafo.pesoArista(0, 1), 0.0001);
		assertEquals(1, grafo.aristas().size());
	}

	@Test
	public void eliminarAristaTest() {
		grafo.agregarArista(1, 2, 4.0);
		assertTrue(grafo.existeArista(1, 2));

		grafo.eliminarArista(1, 2);
		assertFalse(grafo.existeArista(1, 2));
		assertFalse(grafo.existeArista(2, 1));
	}

	@Test
	public void eliminarAristaInexistenteNoGeneraErrorTest() {
		grafo.eliminarArista(1, 2);
		assertFalse(grafo.existeArista(1, 2));
	}

	@Test
	public void listaAristasSinDuplicadosTest() {
		grafo.agregarArista(0, 1, 1.0);
		grafo.agregarArista(1, 2, 2.0);
		grafo.agregarArista(2, 3, 3.0);

		List<Arista> lista = grafo.aristas();
		assertEquals(3, lista.size());
	}

	@Test
	public void vecinosDeUnVerticeTest() {
		grafo.agregarArista(0, 1, 3.0);
		grafo.agregarArista(0, 2, 7.0);

		Set<Integer> vecinos = grafo.vecinos(0);
		assertEquals(2, vecinos.size());
		assertTrue(vecinos.contains(1));
		assertTrue(vecinos.contains(2));
		assertFalse(vecinos.contains(3));
	}

	@Test
	public void vecinosVerticeAisladoTest() {
		Set<Integer> vecinos = grafo.vecinos(3);
		assertTrue(vecinos.isEmpty());
	}

	@Test(expected = IllegalArgumentException.class)
	public void agregarLoopLanzaExcepcionTest() {
		grafo.agregarArista(2, 2, 4.0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void verticeOrigenNegativoLanzaExcepcionTest() {
		grafo.agregarArista(-1, 2, 3.0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void verticeDestinoFueraDeRangoLanzaExcepcionTest() {
		grafo.agregarArista(0, 4, 3.0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void consultarVecinosVerticeFueraDeRangoLanzaExcepcionTest() {
		grafo.vecinos(5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void pesoAristaInexistenteLanzaExcepcionTest() {
		grafo.pesoArista(0, 3);
	}
}
