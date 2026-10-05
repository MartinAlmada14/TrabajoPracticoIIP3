package Model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class VerticeTest {

	@Test
	public void constructorVacioTest() {
		Vertice v = new Vertice();
		assertNotNull(v);
	}

	@Test
	public void constructorConParametrosYGettersTest() {
		Vertice v = new Vertice("Mendoza", -32.8895, -68.8458);

		assertEquals("Mendoza", v.getNombre());
		assertEquals(-32.8895, v.getLatitud(), 0.0001);
		assertEquals(-68.8458, v.getLongitud(), 0.0001);
	}

	@Test
	public void settersModificanAtributosCorrectamenteTest() {
		Vertice v = new Vertice();
		v.setNombre("San Juan");
		v.setLatitud(-31.5375);
		v.setLongitud(-68.5364);

		assertEquals("San Juan", v.getNombre());
		assertEquals(-31.5375, v.getLatitud(), 0.0001);
		assertEquals(-68.5364, v.getLongitud(), 0.0001);
	}

	@Test
	public void toStringRetornaNombreTest() {
		Vertice v = new Vertice("Salta", -24.7821, -65.4232);
		assertEquals("Salta", v.toString());
	}
}