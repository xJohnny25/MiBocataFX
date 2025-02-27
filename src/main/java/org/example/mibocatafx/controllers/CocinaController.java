package org.example.mibocatafx.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.mibocatafx.HelloApplication;
import org.example.mibocatafx.models.Bocata;
import org.example.mibocatafx.models.Curso;
import org.example.mibocatafx.models.Pedido;
import org.example.mibocatafx.service.BocataService;
import org.example.mibocatafx.service.CursoService;
import org.example.mibocatafx.service.PedidoService;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class CocinaController implements Initializable {
    @FXML
    private TextField nameInput;

    @FXML
    private ComboBox<String> tipeBox;

    @FXML
    private ComboBox<String> cursesBox;

    @FXML
    private DatePicker calendarInput;

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
    private TextField calientesCount;

    @FXML
    private TextField friosCount;

    @FXML
    private Label pagesCount;

    @FXML
    private Label resultsCount;

    PedidoService pedidoService = new PedidoService();

    int offset = 16;
    int currentPage = 1;
    private long totalPedidos = pedidoService.countPedidos(null);
    long totalPages = Math.round(Math.ceil((float) totalPedidos / (float) offset));


    private HashMap<String, String> filtros = new HashMap<>();

    public void rellenarTabla(List<Pedido> pedidos) {
        try {
            table.setItems(FXCollections.observableArrayList(pedidos));

            tableDateColumn.setCellValueFactory(new PropertyValueFactory<>("fecha"));

            tableCompleteOrderColumn.setCellFactory(columna -> {
                TableCell<Pedido, Void> cell = new TableCell<>() {
                    private final Button botonRetirar = new Button("Retirar");

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null);
                        } else {
                            // Obtener el pedido correspondiente a la fila actual
                            Pedido pedido = getTableView().getItems().get(getIndex());

                            if (pedido.getFechaRetirada() != null) {
                                botonRetirar.setText("Retirado");
                                botonRetirar.setDisable(true);
                            } else {
                                // Si el pedido no está retirado, lo retiramos y lo deshabilitamos
                                botonRetirar.setOnAction(actionEvent -> {
                                    pedidoService.updateFechaRetirada(pedido);
                                    botonRetirar.setText("Retirado");
                                    botonRetirar.setDisable(true);
                                });
                                botonRetirar.setStyle("");
                            }

                            setGraphic(botonRetirar); // Mostrar el botón
                        }
                    }
                };

                return cell;
            });

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
        CursoService cursoService = new CursoService();
        List<Curso> cursos = cursoService.getAll();
        for (Curso curso : cursos) {
            cursesBox.getItems().add(curso.getNombre());
        }

        BocataService bocataService = new BocataService();
        List<Bocata> tiposBocata = bocataService.getBocataToday();

        for (Bocata bocata : tiposBocata) {
            tipeBox.getItems().add(bocata.getTipo().name());
        }

        calendarInput.setValue(LocalDate.now());

        PedidoService pedidoService = new PedidoService();
        List<Pedido> pedidos = pedidoService.getPaginated(1, offset, null);
        rellenarTabla(pedidos);

        long pedidosCalientes = pedidoService.countPedidosCalientes(null);
        calientesCount.setText(String.valueOf(pedidosCalientes));

        long pedidosFrios = pedidoService.countPedidosFrios(null);
        friosCount.setText(String.valueOf(pedidosFrios));

        int firstRow = ((currentPage - 1) * offset) + 1;
        long lastRow = Math.min((long) currentPage * offset, totalPedidos);

        pagesCount.setText(currentPage + " / " + Math.round(Math.ceil((float) totalPedidos / (float) offset)));

        if (totalPedidos < lastRow) {
            resultsCount.setText(firstRow + " - " + totalPedidos + " de " + totalPedidos);
        } else {
            resultsCount.setText(firstRow + " - " + lastRow + " de " + totalPedidos);
        }
    }

    @FXML
    public void nextPage() {
        totalPedidos = pedidoService.countPedidos(filtros);
        totalPages = Math.round(Math.ceil((float) totalPedidos / (float) offset));

        if (currentPage < totalPages) {
            currentPage++;

            PedidoService pedidoService = new PedidoService();
            List<Pedido> pedidos = pedidoService.getPaginated(currentPage, offset, filtros);
            rellenarTabla(pedidos);

            int firstRow = ((currentPage - 1) * offset) + 1;
            long lastRow = Math.min((long) currentPage * offset, totalPedidos);

            pagesCount.setText(currentPage + " / " + (int) Math.ceil((float) totalPedidos / (float) offset));

            resultsCount.setText(firstRow + " - " + lastRow + " de " + totalPedidos);
        }
    }

    @FXML
    public void previusPage() {
        totalPedidos = pedidoService.countPedidos(filtros);
        totalPages = Math.round(Math.ceil((float) totalPedidos / (float) offset));

        if (currentPage > 1) {
            currentPage--;

            PedidoService pedidoService = new PedidoService();
            List<Pedido> pedidos = pedidoService.getPaginated(currentPage, offset, filtros);
            rellenarTabla(pedidos);

            int firstRow = ((currentPage - 1) * offset) + 1;
            long lastRow = Math.min((long) currentPage * offset, totalPedidos);

            pagesCount.setText(currentPage + " / " + (int) Math.ceil((float) totalPedidos / (float) offset));

            resultsCount.setText(firstRow + " - " + lastRow + " de " + totalPedidos);
        }
    }

    public void filterTable() {
        currentPage = 1;

        filtros.clear();

        if (!nameInput.getText().isEmpty()) {
            filtros.put("nombre", nameInput.getText());
        }

        if (tipeBox.getValue() != null) {
            filtros.put("tipo", tipeBox.getValue());
        }

        if (cursesBox.getValue() != null) {
            filtros.put("curso", cursesBox.getValue());
        }

        if (calendarInput.getValue() != null && !calendarInput.getValue().isAfter(LocalDate.now())) {
            filtros.put("fecha", calendarInput.getValue().toString());
        }

        long pedidosCalientes = pedidoService.countPedidosCalientes(filtros);
        calientesCount.setText(String.valueOf(pedidosCalientes));

        long pedidosFrios = pedidoService.countPedidosFrios(filtros);
        friosCount.setText(String.valueOf(pedidosFrios));

        PedidoService pedidoService = new PedidoService();
        List<Pedido> pedidos = pedidoService.getPaginated(currentPage, offset, filtros);
        rellenarTabla(pedidos);
        totalPedidos = pedidoService.countPedidos(filtros);

        int firstRow = ((currentPage - 1) * offset) + 1;
        long lastRow = Math.min((long) currentPage * offset, totalPedidos);

        pagesCount.setText(currentPage + " / " + (int) Math.ceil((float)totalPedidos/(float)offset));

        if (totalPedidos < lastRow) {
            resultsCount.setText(firstRow + " - " + totalPedidos + " de " + totalPedidos);
        } else {
            resultsCount.setText(firstRow + " - " + lastRow + " de " + totalPedidos);
        }
    }

    @FXML
    public void clearFilters() {
        filtros.clear();

        nameInput.clear();

        cursesBox.getSelectionModel().clearSelection();
        cursesBox.setValue(null);
        cursesBox.setPromptText("Curso");

        tipeBox.getSelectionModel().clearSelection();
        tipeBox.setValue(null);
        tipeBox.setPromptText("Tipo");

        calendarInput.setValue(null);

        PedidoService pedidoService = new PedidoService();
        List<Pedido> pedidos = pedidoService.getPaginated(1, offset, filtros);
        rellenarTabla(pedidos);
        totalPedidos = pedidoService.countPedidos(filtros);

        long pedidosCalientes = pedidoService.countPedidosCalientes(null);
        calientesCount.setText(String.valueOf(pedidosCalientes));

        long pedidosFrios = pedidoService.countPedidosFrios(null);
        friosCount.setText(String.valueOf(pedidosFrios));

        int firstRow = ((currentPage - 1) * offset) + 1;
        long lastRow = Math.min((long) currentPage * offset, totalPedidos);

        pagesCount.setText(currentPage + " / " + (int) Math.ceil((float) totalPedidos / (float) offset));

        resultsCount.setText(firstRow + " - " + lastRow + " de " + totalPedidos);
    }

    public void logOut() {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/screens/loginScreen.fxml"));

        try {
            Scene scene = new Scene(fxmlLoader.load());

            Stage stage = new Stage();
            stage.setTitle("Selección Bocadillo");
            stage.setMaximized(true);
            stage.setScene(scene);

            stage.show();

            Stage anteriorVentana = (Stage) nameInput.getScene().getWindow();
            anteriorVentana.close();
        } catch (IOException e) {
            System.out.println("No se ha podido cerrar sesión");
        }
    }
}