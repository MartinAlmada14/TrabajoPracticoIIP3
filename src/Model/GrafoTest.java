package Model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GrafoTest {
	@Test
	public void agregarYConsultarArista() {
		Grafo grafo = new Grafo(3);
		grafo.agregarArista(0, 1, 5.0);
		
		assertTrue(grafo.existeArista(0, 1));
		assertTrue(grafo.existeArista(1, 0));
		assertEquals(5.0, grafo.pesoArista(0, 1), 0.0001);
		assertFalse(grafo.existeArista(0, 2));
	}
	
	
}
