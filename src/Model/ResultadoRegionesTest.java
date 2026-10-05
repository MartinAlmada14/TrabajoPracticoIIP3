package Model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class ResultadoRegionesTest {

	@Test
	public void gettersRetornanEstructurasAsignadasTest() {
		List<Set<Integer>> regiones = new ArrayList<>();
		Set<Integer> r1 = new HashSet<>();
		r1.add(0);
		r1.add(1);
		regiones.add(r1);

		List<Arista> aristas = new ArrayList<>();
		aristas.add(new Arista(0, 1, 5.0));

		ResultadoRegiones resultado = new ResultadoRegiones(regiones, aristas);

		assertEquals(regiones, resultado.getRegiones());
		assertEquals(aristas, resultado.getAristasConservadas());
		assertEquals(1, resultado.getRegiones().size());
		assertEquals(1, resultado.getAristasConservadas().size());
	}

	@Test
	public void resultadoConListasVaciasTest() {
		List<Set<Integer>> regiones = new ArrayList<>();
		List<Arista> aristas = new ArrayList<>();

		ResultadoRegiones resultado = new ResultadoRegiones(regiones, aristas);

		assertTrue(resultado.getRegiones().isEmpty());
		assertTrue(resultado.getAristasConservadas().isEmpty());
	}
}