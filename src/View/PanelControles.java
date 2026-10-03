package View;

import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import Model.Vertice;

public class PanelControles extends JPanel implements IControlesView{

	private final int ANCHO = 300;

	private int y = 10;

	private JTextField txtNombre;
	private JTextField txtLat;
	private JTextField txtLon;
	private JTextField txtPeso;
	private JTextField txtK;

	private JComboBox<Vertice> comboOrigen;
	private JComboBox<Vertice> comboDestino;

	private JButton btnAgregarVertice;
	private JButton btnAgregarArista;
	private JButton btnCalcular;
	private JButton btnGuardar;
	private JButton btnCargar;
	private JButton btnCargarArchivo;
	private JButton btnEliminarArista;
	private int separacion = 30;

	public PanelControles() {
		setLayout(null);
		setBounds(670, 10, ANCHO, 600);

		construirControles();
	}

	private void construirControles() {

		agregarZona();
		agregarRelacion();
		calcularRegiones();
		agregarBotonesArchivo();
		eliminarFrontera();
	}

	private void agregarZona() {

		agregarEtiqueta("Nueva Zona");
		y += separacion;

		agregarEtiqueta("Nombre:");
		txtNombre = agregarCampoTexto(80, ANCHO-80);
		y += separacion;

		agregarEtiqueta("Lat (click en mapa):");
		txtLat = agregarCampoTexto(150, ANCHO-150);
		y += separacion;

		agregarEtiqueta("Lon (click en mapa):");
		txtLon = agregarCampoTexto(150, ANCHO-150);
		y += separacion;

		btnAgregarVertice = new JButton("Agregar Vertice");
		btnAgregarVertice.setBounds(0, y, ANCHO, 25);
		add(btnAgregarVertice);

		y += 45;
	}

	private void agregarRelacion() {

		agregarEtiqueta("Nueva Relación");
		y+=separacion;

		agregarEtiqueta("Origen:");

		comboOrigen = new JComboBox<>();
		comboOrigen.setBounds(80, y, ANCHO - 80, 25);
		add(comboOrigen);

		y += separacion;

		agregarEtiqueta("Destino:");

		comboDestino = new JComboBox<>();
		comboDestino.setBounds(80, y, ANCHO - 80, 25);
		add(comboDestino);

		y += separacion;

		agregarEtiqueta("Similaridad:");

		txtPeso = agregarCampoTexto(100, ANCHO-100);
		y += separacion;

		btnAgregarArista = new JButton("Agregar Arista");
		btnAgregarArista.setBounds(0, y, ANCHO, 25);
		add(btnAgregarArista);

		y += 45;
	}

	private void calcularRegiones() {

		agregarEtiqueta("Cantidad de regiones (k):");
		y += separacion;

		txtK = new JTextField();
		txtK.setBounds(0, y, 60, 22);
		add(txtK);

		btnCalcular = new JButton("Calcular regiones");
		btnCalcular.setBounds(70, y, ANCHO - 70, 25);
		add(btnCalcular);

		y += 35;
	}

	private void agregarBotonesArchivo() {

		btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(0, y, (ANCHO - 10) / 2, 25);
		add(btnGuardar);

		btnCargar = new JButton("Cargar");
		btnCargar.setBounds((ANCHO + 10) / 2, y, (ANCHO - 10) / 2, 25);
		add(btnCargar);

		y += 35;

		btnCargarArchivo = new JButton("Cargar desde archivo...");
		btnCargarArchivo.setBounds(0, y, ANCHO, 25);
		add(btnCargarArchivo);

		y += 35;
	}

	private void eliminarFrontera() {

		btnEliminarArista = new JButton("Eliminar Relación");
		btnEliminarArista.setBounds(0, y, ANCHO, 25);
		add(btnEliminarArista);

		y += 35;
	}

	private void agregarEtiqueta(String texto) {

		JLabel etiqueta = new JLabel(texto);
		etiqueta.setBounds(0, y, 200, 20);
		add(etiqueta);
	}

	private JTextField agregarCampoTexto(int x, int ancho) {

		JTextField campo = new JTextField();
		campo.setBounds(x, y, ancho, 22);
		add(campo);

		return campo;
	}

	//	METODOS DE ACCION

	public void setAccionAgregarVertice(ActionListener listener) {
		btnAgregarVertice.addActionListener(listener);
	}
	
	public void setAccionAgregarArista(ActionListener listener) {
		btnAgregarArista.addActionListener(listener);
	}

	public void setAccionEliminarArista(ActionListener listener) {
		btnEliminarArista.addActionListener(listener);
	}
	
	public void setAccionCalcular(ActionListener listener) {
		btnCalcular.addActionListener(listener);
	}

	public void setAccionGuardar(ActionListener listener) {
		btnGuardar.addActionListener(listener);
	}

	public void setAccionCargar(ActionListener listener) {
		btnCargar.addActionListener(listener);
	}

	public void setAccionCargarArchivo(ActionListener listener) {
		btnCargarArchivo.addActionListener(listener);
	}


	// METODOS DE LECTURA

	public String getNombre() {
		return txtNombre.getText().trim();
	}

	public String getLatitud() {
		return txtLat.getText().trim();
	}

	public String getLongitud() {
		return txtLon.getText().trim();
	}

	public String getPeso() {
		return txtPeso.getText().trim();
	}

	public String getK() {
		return txtK.getText().trim();
	}

	public int getIndiceOrigen() {
		return comboOrigen.getSelectedIndex();
	}

	public int getIndiceDestino() {
		return comboDestino.getSelectedIndex();
	}

	public Vertice getOrigen() {
		return (Vertice) comboOrigen.getSelectedItem();
	}

	public Vertice getDestino() {
		return (Vertice) comboDestino.getSelectedItem();
	}

	public Optional<Path> elegirArchivo(){
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(new FileNameExtensionFilter("Archivos JSON", "json"));
		if(chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
			return Optional.empty();
		}
		return Optional.of(chooser.getSelectedFile().toPath());
	}
	
	@Override
	public void agregarVertice(Vertice vertice) {
		comboOrigen.addItem(vertice);
		comboDestino.addItem(vertice);
	}

	@Override
	public void limpiarVertice() {
		comboOrigen.removeAllItems();
		comboDestino.removeAllItems();
	}

	@Override
	public void limpiarNombre() {
		txtNombre.setText("");
	}

	@Override
	public void limpiarPeso() {
		txtPeso.setText("");
	}

	public void establecerLatitud(double latitud) {
	    txtLat.setText(String.valueOf(latitud));
	}

	public void establecerLongitud(double longitud) {
	    txtLon.setText(String.valueOf(longitud));
	}
}

