package org.example.mibocatafx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.mibocatafx.HelloApplication;
import org.example.mibocatafx.models.Usuario;
import org.example.mibocatafx.service.AlumnoService;
import org.example.mibocatafx.service.UsuarioService;

import java.io.IOException;
import java.util.AbstractList;

public class LoginController {
    private final AlumnoService alumnoService = new AlumnoService();
    private Usuario usuario;
    
    @FXML
    private TextField userInput;

    @FXML
    private PasswordField passField;

    private FXMLLoader fxmlLoader;
    private Scene scene;
    private Stage stage;

    @FXML
    protected void onLoginButtonClick() {
       UsuarioService usuarioService = new UsuarioService();

        usuario = usuarioService.getUsuarioByMail(userInput.getText(), passField.getText());

        if (usuario != null) {
            try {
                abrirVentana(usuario.getRol().name());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login");
            alert.setContentText("Usuario no encontrado");
            alert.show();
        }
    }

    @FXML
    protected void abrirVentana(String rol) throws IOException {
        switch (rol) {
            case "alumno":
                fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/screens/chooseSandwichScreen.fxml"));
                scene = new Scene(fxmlLoader.load());

                ChooseSandwichController chooseSandwichController = fxmlLoader.getController();

                System.out.println(usuario);

                stage = new Stage();
                stage.setTitle("Selección Bocadillo");
                stage.setMaximized(true);
                stage.setScene(scene);

                stage.setOnShown(windowEvent -> {
                    chooseSandwichController.setUsuario(usuario);
                    chooseSandwichController.cargarBocadillos();
                    chooseSandwichController.makeOrder();
                });

                stage.show();
            break;

            case "cocina":
                fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/screens/cocina.fxml"));
                scene = new Scene(fxmlLoader.load());

                stage = new Stage();
                stage.setTitle("Selección Bocadillo");
                stage.setMaximized(true);
                stage.setScene(scene);

                stage.show();
            break;

            case "admin":
                fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/screens/adminAlumnoCrud.fxml"));
                scene = new Scene(fxmlLoader.load());

                AdminAlumnosCrudController adminAlumnosCrudController = fxmlLoader.getController();

                stage = new Stage();
                stage.setTitle("Selección Bocadillo");
                stage.setMaximized(true);
                stage.setScene(scene);

                stage.setOnShown(windowEvent -> {
                    adminAlumnosCrudController.initializeInfo();
                });

                stage.show();
            break;
        }

        Stage anteriorVentana = (Stage) userInput.getScene().getWindow();
        anteriorVentana.close();
    }
}