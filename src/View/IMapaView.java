package View;

import java.util.List;
import java.util.Set;

import Model.Arista;
import Model.Vertice;

public interface IMapaView {
	void mostrarMensaje(String mensaje);
	void actualizarMapaConRegiones(List<Set<Integer>> regiones, List<Vertice> provincias);
	void agregarMarcador(Vertice provincia, int indice);
	void agregarArista(int origenIdx, int destinoIdx, Vertice origen, Vertice destino);
	void eliminarArista(int origenIdx, int destinoIdx);
	void cargarPais(List<Vertice> provincias, List<Arista> aristas);
}
