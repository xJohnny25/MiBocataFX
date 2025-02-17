package org.example.mibocatafx.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.example.mibocatafx.dao.CursoDAO;
import org.example.mibocatafx.models.Curso;
import org.example.mibocatafx.models.Pedido;
import org.example.mibocatafx.service.CursoService;
import org.example.mibocatafx.service.PedidoService;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class CocinaController implements Initializable {
    @FXML
    private HBox logOutButton;

    @FXML
    private TextField nameInput;

    @FXML
    private ComboBox<String> tipeBox;

    @FXML
    private ComboBox<String> cursesBox;

    @FXML
    private DatePicker calendarInput;

    @FXML
    private Button searchButton;

    @FXML
    private TableView table;

    @FXML
    private TableColumn tableNameColumn;

    @FXML
    private TableColumn tableBocadilloColumn;

    @FXML
    private TableColumn tableCurseColumn;

    @FXML
    private TableColumn tableDateColumn;

    @FXML
    private ComboBox numPages;

    @FXML
    private Button previusPage;

    @FXML
    private Button nextPage;

    @FXML
    private Label pagesCount;

    @FXML
    private Label resultsCount;

    private int offset = numPages.getValue().toString().equals("10") ? 10 : (int) numPages.getValue();
    private HashMap<String, String> filtros = new HashMap<>();
    private long totalPedidos;


    public void rellenarTabla(List<Pedido> pedidos) {
        try {
            table.setItems(FXCollections.observableArrayList(pedidos));

            //tableNameColumn.setCellValueFactory(new PropertyValueFactory<>("alumno.nombre"));
            //tableBocadilloColumn.setCellValueFactory(new PropertyValueFactory<>("bocadillo.tipo"));
            //tableCurseColumn.setCellValueFactory(new PropertyValueFactory<>("alumno.curso"));
            tableDateColumn.setCellValueFactory(new PropertyValueFactory<>("fecha"));

            //tableBocadilloColumn.setCellValueFactory(data-> new SimpleStringProperty(data.getValue()));

            tableNameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                @Override
                public ObservableValue call(TableColumn.CellDataFeatures cellDataFeatures) {
                    Pedido pedido = (Pedido) cellDataFeatures.getValue();

                    return new SimpleStringProperty(
                            pedido.getAlumno().getNombre()
                    );
                }
            });

            tableBocadilloColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                @Override
                public ObservableValue call(TableColumn.CellDataFeatures cellDataFeatures) {
                    Pedido pedido = (Pedido) cellDataFeatures.getValue();

                    return new SimpleStringProperty(
                            pedido.getBocadillo().getNombre()
                    );
                }
            });

            tableCurseColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                @Override
                public ObservableValue call(TableColumn.CellDataFeatures cellDataFeatures) {
                    Pedido pedido = (Pedido) cellDataFeatures.getValue();

                    return new SimpleStringProperty(
                            pedido.getAlumno().getCurso().toString()
                    );
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CursoService cursoService = new CursoService();
        List<Curso> cursos = cursoService.getAll();
        for (Curso curso : cursos) {
            cursesBox.getItems().add(curso.getNombre());
        }

        PedidoService pedidoService = new PedidoService();
        List<Pedido> pedidos = pedidoService.getPaginated(1, offset, null);
        rellenarTabla(pedidos);
        totalPedidos = pedidoService.countPedidos(null);

        resultsCount.setText("1/" + offset + " de " + totalPedidos);
    }
}
