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
import org.example.mibocatafx.models.Curso;
import org.example.mibocatafx.models.Pedido;
import org.example.mibocatafx.service.CursoService;
import org.example.mibocatafx.service.PedidoService;

import java.net.URL;
import java.time.LocalDateTime;
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
    private TableColumn tableCompleteOrderColumn;

    @FXML
    private ComboBox<Integer> numPages;

    @FXML
    private Button previusPage;

    @FXML
    private Button nextPage;

    @FXML
    private Label pagesCount;

    @FXML
    private Label resultsCount;

    int offset = 13;
            //numPages.getValue().toString().equals("10") ? 10 : numPages.getValue();
    private HashMap<String, String> filtros = new HashMap<>();
    private long totalPedidos;
    PedidoService pedidoService = new PedidoService();


    public void rellenarTabla(List<Pedido> pedidos) {
        try {
            table.setItems(FXCollections.observableArrayList(pedidos));

            //tableNameColumn.setCellValueFactory(new PropertyValueFactory<>("alumno.nombre"));
            //tableBocadilloColumn.setCellValueFactory(new PropertyValueFactory<>("bocadillo.tipo"));
            //tableCurseColumn.setCellValueFactory(new PropertyValueFactory<>("alumno.curso"));
            tableDateColumn.setCellValueFactory(new PropertyValueFactory<>("fecha"));

            tableCompleteOrderColumn.setCellFactory(columna -> {
                TableCell<Pedido, Void> cell = new TableCell<>() {
                    private final Button botonRetirar = new Button("Retirar");

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null); // No mostrar el botón si la celda está vacía
                        } else {
                            // Obtener el pedido correspondiente a la fila actual
                            Pedido pedido = getTableView().getItems().get(getIndex());

                            if (pedido.getFechaRetirada() != null) {
                                botonRetirar.setText("Retirado");
                                botonRetirar.setDisable(true);
                                botonRetirar.setStyle("-fx-cursor: not-allowed;");
                            } else {
                                // Si el pedido no está retirado, asignar acción al botón
                                botonRetirar.setOnAction(actionEvent -> {
                                    pedidoService.updateFechaRetirada(pedido);
                                    botonRetirar.setText("Retirado");
                                    botonRetirar.setDisable(true);
                                    botonRetirar.setStyle("-fx-cursor: not-allowed;");
                                });
                                botonRetirar.setStyle("");
                            }

                            setGraphic(botonRetirar); // Mostrar el botón
                        }
                    }
                };

                return cell;
            });


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
                            pedido.getAlumno().getCurso().getNombre()
                    );
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        numPages.getItems().addAll(10, 20, 30);
        numPages.setValue(10);

//        offset = 0;

        CursoService cursoService = new CursoService();
        List<Curso> cursos = cursoService.getAll();
        for (Curso curso : cursos) {
            cursesBox.getItems().add(curso.getNombre());
        }

        tipeBox.getItems().addAll("Frio", "Caliente");

        PedidoService pedidoService = new PedidoService();
        List<Pedido> pedidos = pedidoService.getPaginated(1, offset, null);
        rellenarTabla(pedidos);
        totalPedidos = pedidoService.countPedidos(null);

        resultsCount.setText("1/" + offset + " de " + totalPedidos);
    }
}
