package Persistence;

import java.nio.file.Path;
import java.util.Optional;

import Model.Pais;

public interface IPersistencia {
	Optional<Pais> cargar();
	Optional<Pais> cargar(Path archivo);
	void guardar(Pais pais);
}
