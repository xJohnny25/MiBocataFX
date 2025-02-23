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
import org.example.mibocatafx.models.Curso;
import org.example.mibocatafx.service.AlumnoService;
import org.example.mibocatafx.service.CursoService;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class AdminAlumnosCrudController {
    private final AlumnoService alumnoService = new AlumnoService();

    int offset = 16;
    int currentPage = 1;
    private long totalAlumnos = alumnoService.countAlumnos(null);
    long totalPages = Math.round(Math.ceil((float) totalAlumnos / (float) offset));

    private HashMap<String, String> filtros = new HashMap<>();

    @FXML
    private TextField nameInput;

    @FXML
    private TextField emailInput;

    @FXML
    private ComboBox<String> cursesBox;

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
    private Label pagesCount;

    @FXML
    private Label resultsCount;

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
                            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/screens/editAlumnoForm.fxml"));

                            try {
                                Scene scene = new Scene(fxmlLoader.load(), 400, 520);

                                EditAlumnoFormController editAlumnoFormController = fxmlLoader.getController();


                                Stage stage = new Stage();
                                stage.setTitle("Editar alumno");
                                stage.setMaximized(false);
                                stage.setResizable(false);
                                stage.setScene(scene);

                                stage.setOnShown(windowEvent -> {
                                    editAlumnoFormController.setAlumno(alumnos.get(getIndex()));
                                    editAlumnoFormController.initializeInfo();
                                });

                                stage.show();

                                stage.setOnHidden(hiddenEvent -> {
                                    table.setItems(FXCollections.observableList(alumnoService.getPaginated(currentPage, offset, filtros)));
                                    table.refresh();
                                });
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
                                    table.setItems(FXCollections.observableList(alumnoService.getPaginated(currentPage, offset, filtros)));
                                    table.refresh();
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

    public void initializeInfo() {
        CursoService cursoService = new CursoService();

        List<Curso> cursos = cursoService.getAll();
        for(Curso curso : cursos) {
            cursesBox.getItems().add(curso.getNombre());
        }

        List<Alumno> alumnos = alumnoService.getPaginated(1, offset, null);
        fillTable(alumnos);

        pagesCount.setText(currentPage + " / " + (int) Math.ceil((float) totalAlumnos / (float) offset));

        resultsCount.setText("1 - " + offset + " de " + totalAlumnos);
    }

    @FXML
    public void nextPage() {
        totalAlumnos = alumnoService.countAlumnos(filtros);
        totalPages = Math.round(Math.ceil((float) totalAlumnos / (float) offset));

        if (currentPage < totalPages) {
            currentPage++;

            List<Alumno> alumnos = alumnoService.getPaginated(currentPage, offset, filtros);
            fillTable(alumnos);

            int firstRow = ((currentPage - 1) * offset) + 1;
            long lastRow = Math.min((long) currentPage * offset, totalAlumnos);

            pagesCount.setText(currentPage + " / " + (int) Math.ceil((float) totalAlumnos / (float) offset));

            resultsCount.setText(firstRow + " - " + lastRow + " de " + totalAlumnos);
        }
    }

    @FXML
    public void previusPage() {
        totalAlumnos = alumnoService.countAlumnos(filtros);
        totalPages = Math.round(Math.ceil((float) totalAlumnos / (float) offset));

        if (currentPage > 1) {
            currentPage--;

            List<Alumno> alumnos = alumnoService.getPaginated(currentPage, offset, filtros);
            fillTable(alumnos);

            int firstRow = ((currentPage - 1) * offset) + 1;
            long lastRow = Math.min((long) currentPage * offset, totalAlumnos);

            pagesCount.setText(currentPage + " / " + (int) Math.ceil((float) totalAlumnos / (float) offset));

            resultsCount.setText(firstRow + " - " + lastRow + " de " + totalAlumnos);
        }
    }

    public void filterTable() {
        currentPage = 1;

        filtros.clear();

        if (nameInput != null) {
            filtros.put("nombre", nameInput.getText());
        }

        if (emailInput != null) {
            filtros.put("correo", emailInput.getText());
        }

        if (cursesBox.getValue() != null) {
            filtros.put("curso", cursesBox.getValue());
        }

        List<Alumno> alumnos = alumnoService.getPaginated(currentPage, offset, filtros);
        fillTable(alumnos);

        totalAlumnos = alumnoService.countAlumnos(filtros);

        int firstRow = ((currentPage - 1) * offset) + 1;
        long lastRow = Math.min((long) currentPage * offset, totalAlumnos);


        pagesCount.setText(currentPage + " / " + (int) Math.ceil((float)totalAlumnos/(float)offset));

        resultsCount.setText(firstRow + " - " + lastRow + " de " + totalAlumnos);
    }

    public void clearFilters() {
        filtros.clear();

        nameInput.clear();
        emailInput.clear();
        cursesBox.setValue(null);

        List<Alumno> alumnos = alumnoService.getPaginated(1, offset, filtros);
        fillTable(alumnos);

        totalAlumnos = alumnoService.countAlumnos(filtros);

        int firstRow = ((currentPage - 1) * offset) + 1;
        long lastRow = Math.min((long) currentPage * offset, totalAlumnos);


        pagesCount.setText(currentPage + " / " + (int) Math.ceil((float)totalAlumnos/(float)offset));

        resultsCount.setText(firstRow + " - " + lastRow + " de " + totalAlumnos);
    }

    public void addNewAlumno() {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/screens/addNewAlumnoForm.fxml"));

        try {
            Scene scene = new Scene(fxmlLoader.load(), 400, 620);

            AddNewAlumnoForm addNewAlumnoForm = fxmlLoader.getController();

            Stage stage = new Stage();
            stage.setTitle("Editar alumno");
            stage.setMaximized(false);
            stage.setResizable(false);
            stage.setScene(scene);

            stage.setOnShown(windowEvent -> addNewAlumnoForm.initializeInfo());

            stage.show();

            stage.setOnHidden(windowEvent -> {
                table.setItems(FXCollections.observableList(alumnoService.getAll()));
                table.refresh();
            });
        } catch (IOException e) {
            System.out.println("No se ha pododido cargar el formulario");
        }
    }


    @FXML
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
