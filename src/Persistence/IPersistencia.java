package Persistence;

import java.util.Optional;

import Model.Pais;

public interface IPersistencia {
	Optional<Pais> cargar();
	void guardar(Pais pais);
}
