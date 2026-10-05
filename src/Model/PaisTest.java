package Model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class PaisTest {

	private Pais pais;

	@Before
	public void setUp() {
		pais = new Pais();
	}

	@Test
	public void paisInicialVacioTest() {
		assertTrue(pais.getVertices().isEmpty());
		assertEquals(0, pais.getGrafo().tamano());
	}

	@Test
	public void agregarProvinciasActualizaTamanoYIndicesTest() {
		Vertice bsas = new Vertice("Buenos Aires", -34.6037, -58.3816);
		Vertice cba = new Vertice("Cordoba", -31.4201, -64.1888);

		int indiceBsas = pais.agregarVertice(bsas);
		int indiceCba = pais.agregarVertice(cba);

		assertEquals(0, indiceBsas);
		assertEquals(1, indiceCba);
		assertEquals(2, pais.getVertices().size());
		assertEquals(2, pais.getGrafo().tamano());
		assertEquals(bsas, pais.getVertice(0));
		assertEquals(cba, pais.getVertice(1));
	}

	@Test
	public void redimensionarGrafoConservaAristasPreviasTest() {
		Vertice v0 = new Vertice("V0", 0.0, 0.0);
		Vertice v1 = new Vertice("V1", 1.0, 1.0);
		pais.agregarVertice(v0);
		pais.agregarVertice(v1);

		pais.agregarArista(0, 1, 4.5);
		assertTrue(pais.getGrafo().existeArista(0, 1));

		Vertice v2 = new Vertice("V2", 2.0, 2.0);
		pais.agregarVertice(v2);

		assertEquals(3, pais.getGrafo().tamano());
		assertTrue(pais.getGrafo().existeArista(0, 1));
		assertEquals(4.5, pais.getGrafo().pesoArista(0, 1), 0.0001);
		assertFalse(pais.getGrafo().existeArista(0, 2));
	}

	@Test
	public void eliminarAristaEnPaisTest() {
		pais.agregarVertice(new Vertice("V0", 0.0, 0.0));
		pais.agregarVertice(new Vertice("V1", 1.0, 1.0));

		pais.agregarArista(0, 1, 2.0);
		assertTrue(pais.getGrafo().existeArista(0, 1));

		pais.eliminarArista(0, 1);
		assertFalse(pais.getGrafo().existeArista(0, 1));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void consultarVerticeInexistenteLanzaExcepcionTest() {
		pais.getVertice(0);
	}
}