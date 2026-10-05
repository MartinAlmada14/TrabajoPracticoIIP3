package Persistence;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import Model.Pais;
import Model.Vertice;

public class GSONPersistenciaTest {

	private GSONPersistencia persistencia;

	@Before
	public void setUp() {
		persistencia = new GSONPersistencia();
	}

	@Test
	public void cargarDatosPorDefectoTest() {
		Optional<Pais> optPais = persistencia.cargar();

		assertTrue(optPais.isPresent());
		Pais pais = optPais.get();
		assertNotNull(pais);
		assertFalse(pais.getVertices().isEmpty());
		assertTrue(pais.getGrafo().tamano() > 0);
	}

	@Test
	public void cargarDatosDesdeRutaTest() {
		Path ruta = Paths.get("data", "Datos.json");
		Optional<Pais> optPais = persistencia.cargar(ruta);

		assertTrue(optPais.isPresent());
		Pais pais = optPais.get();
		assertNotNull(pais);
		assertFalse(pais.getVertices().isEmpty());
	}

	@Test
	public void cargarArchivoInexistenteRetornaOptionalVacioTest() {
		Path rutaInexistente = Paths.get("data", "archivo_inexistente.json");
		Optional<Pais> optPais = persistencia.cargar(rutaInexistente);

		assertFalse(optPais.isPresent());
	}

	@Test
	public void guardarYVolverACargarTest() {
		Pais paisNuevo = new Pais();
		paisNuevo.agregarVertice(new Vertice("ProvinciaPrueba", -34.0, -58.0));
		paisNuevo.agregarVertice(new Vertice("ProvinciaPrueba2", -35.0, -59.0));
		paisNuevo.agregarArista(0, 1, 10.5);

		persistencia.guardar(paisNuevo);

		Optional<Pais> optPaisRecuperado = persistencia.cargar();
		assertTrue(optPaisRecuperado.isPresent());
		Pais paisRecuperado = optPaisRecuperado.get();

		assertFalse(paisRecuperado.getVertices().isEmpty());
		assertTrue(paisRecuperado.getGrafo().existeArista(0, 1));
	}
}