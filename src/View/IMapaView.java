package View;

import java.util.List;
import java.util.Set;

import Model.Arista;
import Model.Provincia;

public interface IMapaView {
	void mostrarMensaje(String mensaje);
	void actualizarMapaConRegiones(List<Set<Integer>> regiones, List<Provincia> provincias);
	void agregarMarcador(Provincia provincia, int indice);
	void agregarFrontera(int origenIdx, int destinoIdx, Provincia origen, Provincia destino);
	void eliminarFrontera(int origenIdx, int destinoIdx);
	void cargarPais(List<Provincia> provincias, List<Arista> aristas);
}
