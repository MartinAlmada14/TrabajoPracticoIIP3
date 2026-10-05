package Model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class ArbolGeneradorMinimoTest {

	private Grafo grafoConexo;

	@Before
	public void setUp() {
		grafoConexo = new Grafo(4);
		grafoConexo.agregarArista(0, 1, 1.0);
		grafoConexo.agregarArista(1, 2, 2.0);
		grafoConexo.agregarArista(2, 3, 3.0);
		grafoConexo.agregarArista(0, 3, 10.0);
	}

	@Test
	public void crearArbolConGrafoConexoValidoTest() {
		ArbolGeneradorMinimo agm = new ArbolGeneradorMinimo(grafoConexo);
		ResultadoRegiones resultado = agm.generarRegiones(1);

		assertEquals(1, resultado.getRegiones().size());
		assertEquals(3, resultado.getAristasConservadas().size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void grafoNoConexoLanzaExcepcionTest() {
		Grafo grafoNoConexo = new Grafo(4);
		grafoNoConexo.agregarArista(0, 1, 1.0);
		grafoNoConexo.agregarArista(2, 3, 2.0);

		new ArbolGeneradorMinimo(grafoNoConexo);
	}

	@Test
	public void generarUnaSolaRegionConservaTodasLasAristasTest() {
		ArbolGeneradorMinimo agm = new ArbolGeneradorMinimo(grafoConexo);
		ResultadoRegiones resultado = agm.generarRegiones(1);

		assertEquals(1, resultado.getRegiones().size());
		assertEquals(3, resultado.getAristasConservadas().size());
		assertEquals(4, resultado.getRegiones().get(0).size());
	}

	@Test
	public void generarDosRegionesEliminaAristaDeMayorPesoTest() {
		ArbolGeneradorMinimo agm = new ArbolGeneradorMinimo(grafoConexo);
		ResultadoRegiones resultado = agm.generarRegiones(2);

		assertEquals(2, resultado.getRegiones().size());
		assertEquals(2, resultado.getAristasConservadas().size());

		boolean regionConUnElemento = false;
		boolean regionConTresElementos = false;

		for (Set<Integer> region : resultado.getRegiones()) {
			if (region.size() == 1 && region.contains(3)) {
				regionConUnElemento = true;
			}
			if (region.size() == 3 && region.contains(0) && region.contains(1) && region.contains(2)) {
				regionConTresElementos = true;
			}
		}

		assertTrue(regionConUnElemento);
		assertTrue(regionConTresElementos);
	}

	@Test
	public void generarKMaximoDeRegionesDejaVerticesAisladosTest() {
		ArbolGeneradorMinimo agm = new ArbolGeneradorMinimo(grafoConexo);
		ResultadoRegiones resultado = agm.generarRegiones(4);

		assertEquals(4, resultado.getRegiones().size());
		assertTrue(resultado.getAristasConservadas().isEmpty());

		for (Set<Integer> region : resultado.getRegiones()) {
			assertEquals(1, region.size());
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void kMenorAUnoLanzaExcepcionTest() {
		ArbolGeneradorMinimo agm = new ArbolGeneradorMinimo(grafoConexo);
		agm.generarRegiones(0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void kMayorACantidadDeVerticesLanzaExcepcionTest() {
		ArbolGeneradorMinimo agm = new ArbolGeneradorMinimo(grafoConexo);
		agm.generarRegiones(5);
	}
}