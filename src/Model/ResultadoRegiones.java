package Model;

import java.util.List;
import java.util.Set;

public class ResultadoRegiones {
	private final List<Set<Integer>>regiones;
	private final List<Arista> aristas;
	
	public ResultadoRegiones(List<Set<Integer>> regiones, List<Arista> aristas) {
		this.regiones = regiones;
		this.aristas = aristas;
	}

	public List<Set<Integer>> getRegiones() {
		return regiones;
	}

	public List<Arista> getAristasConservadas() {
		return aristas;
	}
}
