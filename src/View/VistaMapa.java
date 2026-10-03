package View;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import org.openstreetmap.gui.jmapviewer.Style;

import Model.Arista;
import Model.Vertice;

public class VistaMapa implements IMapaView {
    private JMapViewer mapa;
    private Map<Integer, MapMarkerDot> marcadores;
    private Map<String, MapPolygonImpl> aristasDibujadas;
    private JFrame frame;

    private static final int[] COLORES_REGION = {
        0xE41A1C, 0x377EB8, 0x4DAF4A,
        0x984EA3, 0xFF7F00, 0xFFFF33,
        0xA65628, 0xF781BF, 0x999999,
        0x66C2A5
    };

    public VistaMapa(JFrame frame) {
        this.frame = frame;
        marcadores = new HashMap<>();
        aristasDibujadas = new HashMap<>();
        inicializarMapa();
    }

    private void inicializarMapa() {
        mapa = new JMapViewer();
        mapa.setDisplayPosition(new Coordinate(-34.6, -64.0), 4);
        mapa.setBounds(10, 10, 650, 600);
    }

    public JMapViewer getMapa() {
        return mapa;
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(frame, mensaje);
    }

    @Override
    public void agregarMarcador(Vertice provincia, int indice) {
        MapMarkerDot marcador = new MapMarkerDot(provincia.getNombre(), 
        		new Coordinate(provincia.getLatitud(),provincia.getLongitud()));
        mapa.addMapMarker(marcador);
        marcadores.put(indice, marcador);
    }

    @Override
    public void agregarArista(int origenIdx, int destinoIdx, Vertice origen, Vertice destino) {
        MapPolygonImpl poligono = new MapPolygonImpl(List.of(
            new Coordinate(origen.getLatitud(), origen.getLongitud()),
            new Coordinate(destino.getLatitud(), destino.getLongitud()),
            new Coordinate(origen.getLatitud(), origen.getLongitud())));
        
        mapa.addMapPolygon(poligono);

        aristasDibujadas.put(keyFrontera(origenIdx, destinoIdx), poligono);
    }

    @Override
    public void eliminarArista(int origenIdx, int destinoIdx) {
        MapPolygonImpl poligono = aristasDibujadas.remove(keyFrontera(origenIdx, destinoIdx));

        if (poligono != null) {
            mapa.removeMapPolygon(poligono);
        }
    }

    @Override
    public void actualizarMapaConRegiones(List<Set<Integer>> regiones, List<Arista> aristasArbol, List<Vertice> provincias) {
    	for(MapPolygonImpl poligono : aristasDibujadas.values()) {
    		mapa.removeMapPolygon(poligono);
    	}
    	aristasDibujadas.clear();
    	for(Arista a : aristasArbol) {
    		agregarArista(a.getOrigen(), a.getDestino(), provincias.get(a.getOrigen()), provincias.get(a.getDestino()));
    	}
    	
        int colorIdx = 0;

        for (Set<Integer> region : regiones) {
        	Color color = new Color(COLORES_REGION[colorIdx % COLORES_REGION.length]);
        	
            for (int indice : region) {
                MapMarkerDot viejo = marcadores.get(indice);
                mapa.removeMapMarker(viejo);
                Vertice provincia = provincias.get(indice);
                MapMarkerDot nuevo = new MapMarkerDot(null, provincia.getNombre(),
                		new Coordinate(provincia.getLatitud(),provincia.getLongitud()),
                    new Style(Color.BLACK, color, null, null));
                
                marcadores.put(indice, nuevo);
                mapa.addMapMarker(nuevo);
            }
            colorIdx++;
        }
        mostrarMensaje("Se calcularon " + regiones.size() + " regiones");
    }

    @Override
    public void cargarPais(List<Vertice> provincias, List<Arista> aristas) {
        mapa.removeAllMapMarkers();
        mapa.removeAllMapPolygons();

        marcadores.clear();
        aristasDibujadas.clear();

        for (int i = 0; i < provincias.size(); i++) {
            agregarMarcador(provincias.get(i), i);
        }

        for (Arista arista : aristas) {
            agregarArista(arista.getOrigen(),arista.getDestino(),
            		provincias.get(arista.getOrigen()),provincias.get(arista.getDestino())
            );
        }
    }

    private String keyFrontera(int a, int b) {
        return Math.min(a, b) + "_" + Math.max(a, b);
    }
    
    public void setAccionClickMapa(Consumer<Coordinate> accion) {
        mapa.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    Coordinate coordenada = (Coordinate) mapa.getPosition(e.getPoint());
                    accion.accept(coordenada);
                }
            }
        });
    }
    
}