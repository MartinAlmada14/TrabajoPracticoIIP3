package Model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class UnionFindTest {

	private UnionFind uf;

	@Before
	public void setUp() {
		uf = new UnionFind(5);
	}

	@Test
	public void elementosInicialmenteDisjuntosTest() {
		for (int i = 0; i < 5; i++) {
			assertEquals(i, uf.buscar(i));
		}
		assertFalse(uf.mismoConjunto(0, 1));
		assertFalse(uf.mismoConjunto(2, 4));
	}

	@Test
	public void unirDosElementosTest() {
		uf.unir(0, 1);

		assertTrue(uf.mismoConjunto(0, 1));
		assertEquals(uf.buscar(0), uf.buscar(1));
		assertFalse(uf.mismoConjunto(0, 2));
	}

	@Test
	public void transitividadEnUnionesTest() {
		uf.unir(0, 1);
		uf.unir(1, 2);

		assertTrue(uf.mismoConjunto(0, 2));
		assertEquals(uf.buscar(0), uf.buscar(2));
	}

	@Test
	public void unirElementosYaConectadosNoGeneraErrorTest() {
		uf.unir(0, 1);
		int raizAntes = uf.buscar(0);

		uf.unir(0, 1); 
		assertEquals(raizAntes, uf.buscar(0));
		assertTrue(uf.mismoConjunto(0, 1));
	}

	@Test
	public void unionesMultiplesYComponentesSeparadasTest() {
		uf.unir(0, 1);
		uf.unir(1, 2);
		uf.unir(3, 4);

		assertTrue(uf.mismoConjunto(0, 2));
		assertTrue(uf.mismoConjunto(3, 4));
		assertFalse(uf.mismoConjunto(0, 3));
		assertFalse(uf.mismoConjunto(2, 4));

		uf.unir(2, 3);
		assertTrue(uf.mismoConjunto(0, 4));
	}

	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void buscarIndiceFueraDeRangoLanzaExcepcionTest() {
		uf.buscar(5);
	}
}