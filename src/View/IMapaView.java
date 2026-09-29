package View;

import java.util.List;
import java.util.Set;

import org.openstreetmap.gui.jmapviewer.Coordinate;

import Model.Provincia;

public interface IMapaView {
	void mostrarMensaje(String mensaje);
	void actualizarMapaConRegiones(List<Set<Integer>> regiones, List<Provincia> provincias);
}
