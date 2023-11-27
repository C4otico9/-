package com.example.sistemaventas.controlador;

import com.example.sistemaventas.modelo.dao.AlbumDao;
import com.example.sistemaventas.modelo.dao.ClienteDao;
import com.example.sistemaventas.modelo.dao.ProductoDao;
import com.example.sistemaventas.modelo.dao.VentaDao;
import com.example.sistemaventas.modelo.dominio.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class VentaController implements Initializable {
    @FXML
    public TextField codigo;
    @FXML
    private TableView<VentaDetalle> tablaVentaDetalle = new TableView<>();
    @FXML
    private TableColumn<VentaDetalle, String> colCodigoAlbumVentaDetalle = new TableColumn<>();
    @FXML
    private TableColumn<VentaDetalle, String> colGeneroGeneroAlbumVentaDetalle = new TableColumn<>();
    @FXML
    private TableColumn<VentaDetalle, String> colNombreAlbumVentaDetalle = new TableColumn<>();
    @FXML
    private TableColumn<VentaDetalle, String> colBaseImponibleAlbumVentaDetalle = new TableColumn<>();
    @FXML
    private TableColumn<VentaDetalle, String> colIgvAlbumVentaDetalle = new TableColumn<>();
    @FXML
    private TableColumn<VentaDetalle, String> colCantidadAlbumVentaDetalle = new TableColumn<>();
    @FXML
    private TableColumn<VentaDetalle, String> colPrecioUnitarioAlbumVentaDetalle = new TableColumn<>();
    @FXML
    private TableColumn<VentaDetalle, String> colTotalAlbumVentaDetalle = new TableColumn<>();


    //


    @FXML
    private TableView<Album> tablaAlbumes = new TableView<>();




    @FXML
    private TableColumn<Album, String> colCodigoAlbum = new TableColumn<>();
    @FXML
    public TableColumn<Album, String> colNombreAlbum = new TableColumn<>();
    @FXML
    public TableColumn<Album, String> colArtistaAlbum = new TableColumn<>();
    @FXML
    public TableColumn<Album, String> colFechaCreacionAlbum = new TableColumn<>();
    @FXML
    public TableColumn<Album, String> colPrecioAlbum = new TableColumn<>();




    @FXML
    public TableColumn<Album, String> colNombreGeneroAlbum = new TableColumn<>();
    private ObservableList<Album> albumesObservableList = FXCollections.observableArrayList();


    private ObservableList<VentaDetalle> ventaDetalleObservableList = FXCollections.observableArrayList();
    // venta
    @FXML
    private TableView<Venta> tablaVentas = new TableView<>();
    @FXML
    private TableColumn<Venta, String> colSerie = new TableColumn<>();
    @FXML
    public TableColumn<Venta, String> colNumero = new TableColumn<>();
    @FXML
    public TableColumn<Venta, String> colBaseImponible = new TableColumn<>();
    @FXML
    public TableColumn<Venta, String> colIgv = new TableColumn<>();
    @FXML
    public TableColumn<Venta, String> colTotal = new TableColumn<>();
    @FXML
    public TableColumn<Venta, String> colFecha = new TableColumn<>();
    @FXML
    public TableColumn<Venta, String> colDni = new TableColumn<>();
    @FXML
    public TableColumn<Venta, String> colNombre = new TableColumn<>();
    //@FXML
    //public TableColumn<Venta, String> colApellido = new TableColumn<>();
    @FXML
    private ComboBox clienteBox;
    @FXML
    private TextField baseImponibleText;
    @FXML
    private TextField igvText;
    @FXML
    private TextField totalText;
    private ObservableList<Venta> ventasObservableList = FXCollections.observableArrayList();
    private ObservableList<Cliente> clientesObservableList = FXCollections.observableArrayList();
    int idCliente = 0;
    double baseImponible = 0.00;
    double igv = 0.00;
    double total = 0.00;
    Album albumSeleccionado = new Album();
    VentaDetalle ventaDetalleSelecionado = new VentaDetalle();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCodigoAlbumVentaDetalle.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigoAlbum()));
        colGeneroGeneroAlbumVentaDetalle.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGenero()));
        colNombreAlbumVentaDetalle.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombreAlbum()));
        colBaseImponibleAlbumVentaDetalle.setCellValueFactory(cellData -> new SimpleStringProperty(Double.toString(cellData.getValue().getBaseImponibleVentaDetalle())));
        colIgvAlbumVentaDetalle.setCellValueFactory(cellData -> new SimpleStringProperty(Double.toString(cellData.getValue().getIgvVentaDetalle())));
        colTotalAlbumVentaDetalle.setCellValueFactory(cellData -> new SimpleStringProperty(Double.toString(cellData.getValue().getTotalVentaDetalle())));
        colCodigoAlbumVentaDetalle.setCellValueFactory(cellData -> new SimpleStringProperty(Double.toString(cellData.getValue().getCantidadVentaDetalle())));
        colPrecioUnitarioAlbumVentaDetalle.setCellValueFactory(cellData -> new SimpleStringProperty(Double.toString(cellData.getValue().getPrecioUnitarioVentaDetalle())));
        tablaVentaDetalle.setEditable(true);
        colCantidadAlbumVentaDetalle.setCellFactory(TextFieldTableCell.forTableColumn());
        tablaVentaDetalle.setItems(ventaDetalleObservableList);
        tablaVentaDetalle.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        ventaDetalleSelecionado = newValue;


                    }
                }
        );
        listarVentas();
        listarAlbumes();
        listarClientes();
    }


    public void listarVentas() {
        tablaVentas.getItems().clear();
        VentaDao ventaDao = new VentaDao();
        List<Venta> ventas = ventaDao.listarVentas();
        ventasObservableList.addAll(ventas);
        colSerie.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSerieVenta()));
        colNumero.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getNumeroVenta())));
        colBaseImponible.setCellValueFactory(cellData -> new SimpleStringProperty(Double.toString(cellData.getValue().getBaseImponibleVenta())));
        colIgv.setCellValueFactory(cellData -> new SimpleStringProperty(Double.toString(cellData.getValue().getIgvVenta())));
        colTotal.setCellValueFactory(cellData -> new SimpleStringProperty(Double.toString(cellData.getValue().getTotalVenta())));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        colFecha.setCellValueFactory(cellData -> new SimpleStringProperty(formatter.format(cellData.getValue().getFechaCreacionVenta())));
        colDni.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDni()));
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        //colApellido.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido()));
        tablaVentas.setItems(ventasObservableList);
       /*tablaVentas.getSelectionModel().selectedItemProperty().addListener(
               (observable, oldValue, newValue) -> {
                   if (newValue != null) {
                       // seleccionarProducto(newValue);


                   }
               }
       );*/
    }


    public void listarAlbumes() {
        tablaAlbumes.getItems().clear();
        AlbumDao albumDao = new AlbumDao();
        List<Album> albumes = albumDao.listarAlbumes();
        albumesObservableList.addAll(albumes);
        colCodigoAlbum.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigoAlbum()));
        colNombreAlbum.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombreAlbum()));
        colArtistaAlbum.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArtistaAlbum()));
        colPrecioAlbum.setCellValueFactory(cellData -> new SimpleStringProperty(Double.toString(cellData.getValue().getPrecioAlbum())));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        colFechaCreacionAlbum.setCellValueFactory(cellData -> new SimpleStringProperty(formatter.format(cellData.getValue().getFechaCreacionAlbum())));
        colNombreGeneroAlbum.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGenero()));
        tablaAlbumes.setItems(albumesObservableList);
        tablaAlbumes.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        seleccionarAlbum(newValue);
                    }
                }
        );
    }


    public void listarClientes() {
        ClienteDao clienteDao = new ClienteDao();
        List<Cliente> Clientes = clienteDao.listarClientes();
        clientesObservableList.addAll(Clientes);
        clienteBox.setPromptText("Selecciona una cliente");
        clienteBox.setItems(clientesObservableList);
        clienteBox.setConverter(new StringConverter<Cliente>() {
            @Override
            public String toString(Cliente cliente) {
                return cliente.getNombre();
            }


            @Override
            public Cliente fromString(String string) {
                return null;
            }
        });
        clienteBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                seleccionarCliente((Cliente) newValue);
            }
        });
    }


    private void seleccionarAlbum(Album album) {
        albumSeleccionado = album;
    }


    private void seleccionarCliente(Cliente cliente) {
        idCliente = cliente.getId();
    }


    public void agregarAlbum(ActionEvent actionEvent) {
        VentaDetalle ventaDetalle = new VentaDetalle();
        ventaDetalle.setIdAlbum(albumSeleccionado.getIdAlbum());
        ventaDetalle.setGenero(albumSeleccionado.getGenero());
        ventaDetalle.setCodigoAlbum(albumSeleccionado.getCodigoAlbum());
        ventaDetalle.setCantidadVentaDetalle(1.0);
        ventaDetalle.setPrecioUnitarioVentaDetalle(albumSeleccionado.getPrecioAlbum() * ventaDetalle.getCantidadVentaDetalle());
        ventaDetalle.setNombreAlbum(albumSeleccionado.getNombreAlbum());
        ventaDetalle.setBaseImponibleVentaDetalle(albumSeleccionado.getPrecioAlbum());
        ventaDetalle.setIgvVentaDetalle((albumSeleccionado.getPrecioAlbum()) * (18.00 / 100.00));
        ventaDetalle.setTotalVentaDetalle((albumSeleccionado.getPrecioAlbum()) + ((albumSeleccionado.getPrecioAlbum()) * (18.00 / 100.00)));
        ventaDetalleObservableList.add(ventaDetalle);
        calculaTotales();




    }


    public void guardarVenta(ActionEvent actionEvent) {
        VentaDao ventaDao = new VentaDao();
        Venta venta = new Venta();
        venta.setId(idCliente);
        venta.setBaseImponibleVenta(baseImponible);
        venta.setIgvVenta(igv);
        venta.setTotalVenta(total);
        venta.setNumeroVenta(ventasObservableList.size() + 1);
        venta.setSerieVenta("F001");
        venta.setFechaCreacionVenta(new Date());
        venta.setVentaDetalles(ventaDetalleObservableList);
        ventaDao.insertarVenta(venta);
    }


    public void eliminarItemDetalle(ActionEvent actionEvent) {
        ventaDetalleObservableList.remove(ventaDetalleObservableList.indexOf(ventaDetalleSelecionado));
        calculaTotales();
    }


    private void calculaTotales() {
        baseImponible = 0;
        igv = 0;
        total = 0;
        ventaDetalleObservableList.forEach(data -> {
            baseImponible = baseImponible + data.getBaseImponibleVentaDetalle();
            igv = igv + data.getIgvVentaDetalle();
            total = total + data.getTotalVentaDetalle();
        });
        baseImponibleText.setText(Double.toString(baseImponible));
        igvText.setText(Double.toString(igv));
        totalText.setText(Double.toString(total));
    }


    public void onEditar(TableColumn.CellEditEvent<VentaDetalle, String> ventaDetalleStringCellEditEvent) {
        VentaDetalle ventaDetalle = new VentaDetalle();
        ventaDetalle = tablaVentaDetalle.getSelectionModel().getSelectedItem();
        int index = ventaDetalleObservableList.indexOf(ventaDetalle);
        ventaDetalleObservableList.get(index)
                .setCantidadVentaDetalle(Double.valueOf(ventaDetalleStringCellEditEvent.getNewValue()));
        ventaDetalleObservableList.get(index)
                .setBaseImponibleVentaDetalle(ventaDetalleObservableList.get(index).getPrecioUnitarioVentaDetalle() * ventaDetalleObservableList.get(index).getCantidadVentaDetalle());
        ventaDetalleObservableList.get(index)
                .setIgvVentaDetalle((ventaDetalleObservableList.get(index).getPrecioUnitarioVentaDetalle() * ventaDetalleObservableList.get(index).getCantidadVentaDetalle()) * (18.00 / 100.00));
        ventaDetalleObservableList.get(index)
                .setTotalVentaDetalle((ventaDetalleObservableList.get(index).getPrecioUnitarioVentaDetalle() * ventaDetalleObservableList.get(index).getCantidadVentaDetalle()) + ((ventaDetalleObservableList.get(index).getPrecioUnitarioVentaDetalle()) * (18.00 / 100.00)));
       /*ventaDetalleObservableList.forEach(data -> {
           System.out.println("base imponible");
           System.out.println(data.getBaseImponibleVentaDetalle());
       });*/
        ventaDetalle = ventaDetalleObservableList.remove(index);
        ventaDetalleObservableList.add(ventaDetalle);
        System.out.println("detalle: " + ventaDetalleObservableList.toString());
        calculaTotales();
    }


    @FXML
    public void onEnter(ActionEvent ae){

        System.out.println(codigo.getText()) ;
        albumesObservableList.forEach(album -> {
            if (album.getCodigoAlbum().equals(codigo.getText())){
                albumSeleccionado=album;
                VentaDetalle ventaDetalle = new VentaDetalle();
                ventaDetalle.setIdAlbum(albumSeleccionado.getIdAlbum());
                ventaDetalle.setGenero(albumSeleccionado.getGenero());
                ventaDetalle.setCodigoAlbum(albumSeleccionado.getCodigoAlbum());
                ventaDetalle.setCantidadVentaDetalle(1.0);
                ventaDetalle.setPrecioUnitarioVentaDetalle(album.getPrecioAlbum() * ventaDetalle.getCantidadVentaDetalle());
                ventaDetalle.setNombreAlbum(albumSeleccionado.getNombreAlbum());
                ventaDetalle.setBaseImponibleVentaDetalle(album.getPrecioAlbum());
                ventaDetalle.setIgvVentaDetalle((albumSeleccionado.getPrecioAlbum()) * (18.00 / 100.00));
                ventaDetalle.setTotalVentaDetalle((albumSeleccionado.getPrecioAlbum()) + ((albumSeleccionado.getPrecioAlbum()) * (18.00 / 100.00)));
                ventaDetalleObservableList.add(ventaDetalle);
                calculaTotales();
            }
        });
    }

}

