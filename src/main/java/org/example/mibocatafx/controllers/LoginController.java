package org.example.mibocatafx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.mibocatafx.HelloApplication;
import org.example.mibocatafx.service.AlumnoService;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField userInput;

    @FXML
    private PasswordField passField;

    @FXML
    protected void onLoginButtonClick() {
       AlumnoService alumnoService = new AlumnoService();

        String userMail = userInput.getText();
        String userPassword = passField.getText();

        try {
            abrirVentana();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void abrirVentana() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/screens/chooseSandwichScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());

        Stage stage = new Stage();
        stage.setTitle("Selección Bocadillo");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();

        Stage anteriorVentana = (Stage) userInput.getScene().getWindow();
        anteriorVentana.close();
    }
}