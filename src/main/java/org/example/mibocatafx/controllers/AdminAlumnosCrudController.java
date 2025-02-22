package org.example.mibocatafx.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.example.mibocatafx.HelloApplication;
import org.example.mibocatafx.models.Alumno;
import org.example.mibocatafx.service.AlumnoService;
import org.example.mibocatafx.service.UsuarioService;

import java.io.IOException;
import java.util.List;

public class AdminAlumnosCrudController {
    private final AlumnoService alumnoService = new AlumnoService();
    private final UsuarioService usuarioService = new UsuarioService();

    @FXML
    private TextField nameInput;

    @FXML
    private TextField emailInput;

    @FXML
    private ComboBox<String> cursesBox;

    @FXML
    private HBox addNewAlumnoButton;

    @FXML
    private TableView<Alumno> table;

    @FXML
    private TableColumn<Alumno, String> idTableColumn;

    @FXML
    private TableColumn<Alumno, String> nameTableColumn;

    @FXML
    private TableColumn<Alumno, String> mailTableColumn;

    @FXML
    private TableColumn<Alumno, String> curseTableColumn;

    @FXML
    private TableColumn<Alumno, String> stateTableColumn;

    @FXML
    private TableColumn<Alumno, Void> actionsTableColumn;

    @FXML
    private Button logOutButton;

    public void fillTable(List<Alumno> alumnos) {
        try {
            table.setItems(FXCollections.observableList(alumnos));

            idTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));

            mailTableColumn.setCellValueFactory(cellDataFeatures -> {
               Alumno alumno = cellDataFeatures.getValue();

               return new SimpleStringProperty(alumno.getUsuario().getCorreo());
            });

            curseTableColumn.setCellValueFactory(cellDataFeatures -> new SimpleStringProperty(
                    cellDataFeatures.getValue().getCurso().getNombre()
            ));

            stateTableColumn.setCellValueFactory(cellDataFeatures -> {
                Alumno alumno = cellDataFeatures.getValue();

                if (alumno.getFechaBaja() != null) {
                    return new SimpleStringProperty("Inactivo");
                } else {
                    return new SimpleStringProperty("Activo");
                }
            });

            actionsTableColumn.setCellFactory(tableColumn -> {
                TableCell<Alumno, Void> cell = new TableCell<>() {
                    private final Button editButton = new Button();
                    private final Button deleteButton = new Button();
                    private final HBox hBox = new HBox(10, editButton, deleteButton);

                    {
                        ImageView editImage = new ImageView(new Image(getClass().getResourceAsStream("/assets/edit.png")));
                        ImageView deleteImage = new ImageView(new Image(getClass().getResourceAsStream("/assets/delete.png")));

                        editImage.setFitHeight(20);
                        editImage.setFitWidth(20);

                        deleteImage.setFitHeight(20);
                        deleteImage.setFitWidth(20);

                        editButton.setGraphic(editImage);
                        deleteButton.setGraphic(deleteImage);

                        editButton.setStyle("-fx-background-radius: 5; -fx-background-color: transparent");
                        deleteButton.setStyle("-fx-background-radius: 5; -fx-background-color: transparent");

                        editButton.setOnMouseClicked(event -> {
                            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/screens/adminForm.fxml"));

                            try {
                                Scene scene = new Scene(fxmlLoader.load(), 400, 520);

                                FormController formController = fxmlLoader.getController();

                                Stage stage = new Stage();
                                stage.setTitle("Editar alumno");
                                stage.setMaximized(false);
                                stage.setResizable(false);
                                stage.setScene(scene);

                                stage.setOnShown(windowEvent -> {
                                    formController.setAlumno(alumnos.get(getIndex()));
                                    formController.initializeInfo();
                                });

                                stage.show();
                            } catch (IOException e) {
                                System.out.println("No se ha pododido cargar el formulario");
                            }
                        });

                        deleteButton.setOnMouseClicked(event -> {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Eliminar alumno");
                            alert.setContentText("¿Seguro que quieres eliminar a este alumno?");
                            alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
                            alert.showAndWait().ifPresent(reponse -> {
                                if (reponse == ButtonType.YES) {
                                    alumnoService.delete(alumnos.get(getIndex()));
                                    fillTable(alumnos);
                                } else {
                                    alert.close();
                                }
                            });
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(hBox);
                        }
                    }
                };

                return cell;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logOut() {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/screens/chooseSandwichScreen.fxml"));

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
