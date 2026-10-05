package Model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AristaTest {

	@Test
	public void constructorVacioTest() {
		Arista arista = new Arista();
		assertNotNull(arista);
	}

	@Test
	public void constructorConParametrosYGettersTest() {
		Arista arista = new Arista(0, 1, 15.5);

		assertEquals(0, arista.getOrigen());
		assertEquals(1, arista.getDestino());
		assertEquals(15.5, arista.getPeso(), 0.0001);
	}

	@Test
	public void compareToMenorMayorEIgualTest() {
		Arista liviana = new Arista(0, 1, 2.0);
		Arista pesada = new Arista(1, 2, 8.0);
		Arista misma = new Arista(2, 3, 2.0);

		assertTrue(liviana.compareTo(pesada) < 0);
		assertTrue(pesada.compareTo(liviana) > 0);
		assertEquals(0, liviana.compareTo(misma));
	}
}