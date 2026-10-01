package View;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Model.Vertice;

public class PanelControles extends JPanel {

    private final int ANCHO = 300;

    private int y = 10;

    private JTextField txtNombre;
    private JTextField txtLat;
    private JTextField txtLon;
    private JTextField txtPeso;
    private JTextField txtK;

    private JComboBox<Vertice> comboOrigen;
    private JComboBox<Vertice> comboDestino;

    private JButton btnAgregarProvincia;
    private JButton btnAgregarFrontera;
    private JButton btnCalcular;
    private JButton btnGuardar;
    private JButton btnCargar;
    private JButton btnCargarArchivo;
    private JButton btnEliminarFrontera;

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

        agregarEtiqueta("Nombre:");
        txtNombre = agregarCampoTexto(80, 22);
        y += 30;

        agregarEtiqueta("Lat (click en mapa):");
        txtLat = agregarCampoTexto(150, 22);
        y += 30;

        agregarEtiqueta("Lon (click en mapa):");
        txtLon = agregarCampoTexto(150, 22);
        y += 30;

        btnAgregarProvincia = new JButton("Agregar provincia");
        btnAgregarProvincia.setBounds(0, y, ANCHO, 25);
        add(btnAgregarProvincia);

        y += 45;
    }

    private void agregarRelacion() {

        agregarEtiqueta("Nueva Relación");

        agregarEtiqueta("Origen:");

        comboOrigen = new JComboBox<>();
        comboOrigen.setBounds(80, y, ANCHO - 80, 25);
        add(comboOrigen);

        y += 30;

        agregarEtiqueta("Destino:");

        comboDestino = new JComboBox<>();
        comboDestino.setBounds(80, y, ANCHO - 80, 25);
        add(comboDestino);

        y += 30;

        agregarEtiqueta("Similaridad:");

        txtPeso = agregarCampoTexto(100, 22);
        y += 30;

        btnAgregarFrontera = new JButton("Agregar Relación");
        btnAgregarFrontera.setBounds(0, y, ANCHO, 25);
        add(btnAgregarFrontera);

        y += 45;
    }

    private void calcularRegiones() {

        agregarEtiqueta("Cantidad de regiones (k):");

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

        btnEliminarFrontera = new JButton("Eliminar Relación");
        btnEliminarFrontera.setBounds(0, y, ANCHO, 25);
        add(btnEliminarFrontera);

        y += 35;
    }

    private void agregarEtiqueta(String texto) {

        JLabel etiqueta = new JLabel(texto);
        etiqueta.setBounds(0, y, 200, 20);
        add(etiqueta);

        y += 25;
    }

    private JTextField agregarCampoTexto(int x, int ancho) {

        JTextField campo = new JTextField();
        campo.setBounds(x, y, ancho, 22);
        add(campo);

        return campo;
    }

    // -------------------------------------------------
    // Métodos para que el Presenter pueda escuchar
    // -------------------------------------------------

    public void setAccionAgregarProvincia(ActionListener listener) {
        btnAgregarProvincia.addActionListener(listener);
    }

    public void setAccionAgregarFrontera(ActionListener listener) {
        btnAgregarFrontera.addActionListener(listener);
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

    public void setAccionEliminarFrontera(ActionListener listener) {
        btnEliminarFrontera.addActionListener(listener);
    }

    // -------------------------------------------------
    // Métodos para obtener los datos introducidos
    // -------------------------------------------------

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

    // -------------------------------------------------
    // Métodos para actualizar los combos
    // -------------------------------------------------

    public void agregarProvincia(Vertice provincia) {
        comboOrigen.addItem(provincia);
        comboDestino.addItem(provincia);
    }

    public void limpiarProvincias() {
        comboOrigen.removeAllItems();
        comboDestino.removeAllItems();
    }

    public void limpiarNombre() {
        txtNombre.setText("");
    }

    public void limpiarPeso() {
        txtPeso.setText("");
    }
}

