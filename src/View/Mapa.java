package View;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import org.openstreetmap.gui.jmapviewer.Style;

import Model.Arista;
import Model.Vertice;
import Persistence.GSONPersistencia;
import Presenter.Presenter;

public class Mapa implements IMapaView {

	private JFrame frame;
	private JMapViewer mapa;
	private Presenter presenter;

	private JTextField txtNombre, txtLat, txtLon, txtK, txtPeso;
	private JComboBox<Vertice> comboOrigen, comboDestino;
	private JButton btnAgregarProvincia, btnAgregarFrontera, btnCalcular;

	// índice de provincia -> marcador, para poder recolorear al calcular regiones
	private Map<Integer, MapMarkerDot> marcadores = new HashMap<>();
	private Map<String, MapPolygonImpl> fronterasDibujadas = new HashMap<>();
	
	private int x = 670, w = 300, y = 10;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			Mapa vista = new Mapa();
			vista.frame.setVisible(true);
		});
	}

	public Mapa() {
//		presenter = new Presenter(this, new GSONPersistencia());
		initialize();
	}

	private void initialize() {
		frame = new JFrame("Diseñando regiones");
		frame.setBounds(100, 100, 1000, 650); // dentro del límite 1366x768
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		mapa = new JMapViewer();
		mapa.setDisplayPosition(new Coordinate(-34.6, -64.0), 4); // centrado en Argentina
		mapa.setBounds(10, 10, 650, 600);
		frame.getContentPane().add(mapa);

		mapa.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					Coordinate c = (Coordinate) mapa.getPosition(e.getPoint());
					txtLat.setText(String.valueOf(c.getLat()));
					txtLon.setText(String.valueOf(c.getLon()));
				}
			}
		});

		armarPanelControles();
	}

	private void armarPanelControles() {

		agregarZona();
		agregarRelacion();
		eliminarRelacion();
		calcularRegiones();
		guardarGrafo();
		cargarGrafo();
		cargarArchivo();


	}

	private JLabel etiqueta(String texto, int x, int y) {
		JLabel l = new JLabel(texto);
		l.setBounds(x, y, 200, 20);
		return l;
	}

	private JTextField campoTexto(int x, int y, int ancho) {
		JTextField t = new JTextField();
		t.setBounds(x, y, ancho, 22);
		frame.getContentPane().add(t);
		return t;
	}
	
	private void onAgregarProvincia(ActionEvent e) {
		try {
			String nombre = txtNombre.getText().trim();
			double lat = Double.parseDouble(txtLat.getText().trim());
			double lon = Double.parseDouble(txtLon.getText().trim());
			presenter.agregarVertice(nombre, lat, lon);
			txtNombre.setText("");
		} catch (NumberFormatException ex) {
			mostrarMensaje("Hacé click en el mapa para tomar la coordenada, y completá el nombre.");
		}
	}

	private void onAgregarFrontera(ActionEvent e) {
		Vertice origen = (Vertice) comboOrigen.getSelectedItem();
		Vertice destino = (Vertice) comboDestino.getSelectedItem();
		if (origen == null || destino == null) {
			mostrarMensaje("Necesitás al menos dos provincias cargadas.");
			return;
		}
		try {
			double peso = Double.parseDouble(txtPeso.getText().trim());
			presenter.agregarArista(comboOrigen.getSelectedIndex(), comboDestino.getSelectedIndex(), peso);
			txtPeso.setText("");
		} catch (NumberFormatException ex) {
			mostrarMensaje("La similaridad tiene que ser un número.");
		}
	}

	private void onCalcularRegiones(ActionEvent e) {
		try {
			int k = Integer.parseInt(txtK.getText().trim());
			presenter.regionizador(k);
		} catch (NumberFormatException ex) {
			mostrarMensaje("k tiene que ser un número entero.");
		}
	}
	
	private void onCargarDesdeArchivo(ActionEvent e) {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(new FileNameExtensionFilter("Archivos JSON", "json"));
		if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
			presenter.cargarGrafoDesde(chooser.getSelectedFile().toPath());
		}
	}

	// ---- IMapaView ----
	
	private static final int[] COLORES_REGION = {
			0xE41A1C, 0x377EB8, 0x4DAF4A, 0x984EA3, 0xFF7F00,
			0xFFFF33, 0xA65628, 0xF781BF, 0x999999, 0x66C2A5
		};
	
	@Override
	public void mostrarMensaje(String mensaje) {
		JOptionPane.showMessageDialog(frame, mensaje);
	}

	@Override
	public void agregarMarcador(Vertice provincia, int indice) {
		MapMarkerDot marcador = new MapMarkerDot(provincia.getNombre(),
				new Coordinate(provincia.getLatitud(), provincia.getLongitud()));
		mapa.addMapMarker(marcador);
		marcadores.put(indice, marcador);

		comboOrigen.addItem(provincia);
		comboDestino.addItem(provincia);
	}

	@Override
	public void agregarArista(int origenIdx, int destinoIdx, Vertice origen, Vertice destino) {
		MapPolygonImpl poligono  = new MapPolygonImpl(List.of(
				new Coordinate(origen.getLatitud(), origen.getLongitud()),
				new Coordinate(destino.getLatitud(), destino.getLongitud()),
				new Coordinate(origen.getLatitud(), origen.getLongitud()) // cerrar la línea
		));
		mapa.addMapPolygon(poligono);
		fronterasDibujadas.put(keyFrontera(origenIdx, destinoIdx), poligono);
		
	}

	@Override
	public void eliminarArista(int origenIdx, int destinoIdx) {
		MapPolygonImpl poligono = fronterasDibujadas.remove(keyFrontera(origenIdx, destinoIdx));
		if(poligono != null) {
			mapa.removeMapPolygon(poligono);
		}
	}
	
	@Override
	public void actualizarMapaConRegiones(List<Set<Integer>> regiones, List<Arista> aristasArbol, List<Vertice> provincias) {
    	for(MapPolygonImpl poligono : fronterasDibujadas.values()) {
    		mapa.removeMapPolygon(poligono);
    	}
    	fronterasDibujadas.clear();
    	for(Arista a : aristasArbol) {
    		agregarArista(a.getOrigen(), a.getDestino(), provincias.get(a.getOrigen()), provincias.get(a.getDestino()));
    	}
		int colorIdx = 0;		
		for(Set<Integer> region : regiones) {
			Color color = new Color(COLORES_REGION[colorIdx % COLORES_REGION.length]);
			for(int indice : region) {
				MapMarkerDot viejo = marcadores.get(indice);
				mapa.removeMapMarker(viejo);
				Vertice provincia = provincias.get(indice);
				MapMarkerDot nuevo = new MapMarkerDot(null, provincia.getNombre(), new Coordinate(provincia.getLatitud(), 
						provincia.getLongitud()), new Style(Color.BLACK, color, null, null));
				marcadores.put(indice, nuevo);
				mapa.addMapMarker(nuevo);
			}
			colorIdx ++;
		}
		mostrarMensaje("Se calcularon " + regiones.size() + " regiones");
	}
	
	@Override
	public void cargarPais(List<Vertice> provincias, List<Arista> aristas) {
		mapa.removeAllMapMarkers();
		mapa.removeAllMapPolygons();
		marcadores.clear();
		fronterasDibujadas.clear();
		comboOrigen.removeAllItems();
		comboDestino.removeAllItems();
		
		for(int i = 0; i < provincias.size(); i++) {
			agregarMarcador(provincias.get(i), i);
		}
		for(Arista a : aristas) {
			agregarArista(a.getOrigen(), a.getDestino(), provincias.get(a.getOrigen()), provincias.get(a.getDestino()));
		}
		
	}
	
	private String keyFrontera(int a, int b) {
		return Math.min(a, b) + "_" + Math.max(a, b);
	}
	
	private void agregarZona() {
		frame.getContentPane().add(etiqueta("Nueva Zona", x, y)); y += 25;
		frame.getContentPane().add(etiqueta("Nombre:", x, y));
		txtNombre = campoTexto(x + 80, y, w - 80); y += 30;
		frame.getContentPane().add(etiqueta("Lat (click en mapa):", x, y));
		txtLat = campoTexto(x + 150, y, w - 150); y += 30;
		frame.getContentPane().add(etiqueta("Lon (click en mapa):", x, y));
		txtLon = campoTexto(x + 150, y, w - 150); y += 30;

		btnAgregarProvincia = new JButton("Agregar Zona");
		btnAgregarProvincia.setBounds(x, y, w, 25);
		btnAgregarProvincia.addActionListener(this::onAgregarProvincia);
		frame.getContentPane().add(btnAgregarProvincia); y += 45;
	}
	
	private void agregarRelacion() {
		frame.getContentPane().add(etiqueta("Nueva Relacion", x, y)); y += 25;
		frame.getContentPane().add(etiqueta("Origen:", x, y));
		comboOrigen = new JComboBox<>();
		comboOrigen.setBounds(x + 80, y, w - 80, 25);
		frame.getContentPane().add(comboOrigen); y += 30;

		frame.getContentPane().add(etiqueta("Destino:", x, y));
		comboDestino = new JComboBox<>();
		comboDestino.setBounds(x + 80, y, w - 80, 25);
		frame.getContentPane().add(comboDestino); y += 30;

		frame.getContentPane().add(etiqueta("Similaridad:", x, y));
		txtPeso = campoTexto(x + 100, y, w - 100); y += 30;

		btnAgregarFrontera = new JButton("Agregar Relacion");
		btnAgregarFrontera.setBounds(x, y, w, 25);
		btnAgregarFrontera.addActionListener(this::onAgregarFrontera);
		frame.getContentPane().add(btnAgregarFrontera); y += 35;
	}
	
	private void eliminarRelacion() {
		JButton btnEliminarFrontera = new JButton("Eliminar Relacion");
		btnEliminarFrontera.setBounds(x, y, w, 25); 	y += 25;
		btnEliminarFrontera.addActionListener(e ->
			presenter.eliminarArista(comboOrigen.getSelectedIndex(), comboDestino.getSelectedIndex()));
		frame.getContentPane().add(btnEliminarFrontera);
	}
	
	private void calcularRegiones() {
		frame.getContentPane().add(etiqueta("Cantidad de regiones (k):", x, y)); y += 25;
		txtK = campoTexto(x, y, 60);

		btnCalcular = new JButton("Calcular regiones");
		btnCalcular.setBounds(x + 70, y, w - 70, 25);
		btnCalcular.addActionListener(this::onCalcularRegiones);
		frame.getContentPane().add(btnCalcular); y += 35;
	}
	
	private void guardarGrafo() {
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(x, y, (w - 10) / 2, 25);
		btnGuardar.addActionListener(e -> presenter.guardarGrafo());
		frame.getContentPane().add(btnGuardar);
	}
	
	private void cargarGrafo() {
		JButton btnCargar = new JButton("Cargar");
		btnCargar.setBounds(x + (w + 10) / 2, y, (w - 10) / 2, 25);
		btnCargar.addActionListener(e -> presenter.cargarGrafo());
		frame.getContentPane().add(btnCargar); y += 35;	
	}
	
	private void cargarArchivo() {
		JButton btnCargarDesde = new JButton("Cargar desde archivo...");
		btnCargarDesde.setBounds(x, y, w, 25);
		btnCargarDesde.addActionListener(this::onCargarDesdeArchivo);
		frame.getContentPane().add(btnCargarDesde); y += 35;
	}
	
}