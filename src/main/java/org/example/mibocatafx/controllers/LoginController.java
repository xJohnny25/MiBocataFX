package org.example.mibocatafx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.mibocatafx.HelloApplication;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField userInput;

    @FXML
    private PasswordField passField;

    @FXML
    protected void onLoginButtonClick() {
        String validUserMail = "user@gmail.com";
        String validUserPassword = "1234";

        String userMail = userInput.getText();
        String userPassword = passField.getText();

        if (userMail.equals(validUserMail) && userPassword.equals(validUserPassword)) {
            try {
                abrirVentana();
            } catch (IOException e) {
                System.out.println("Error al entrar");
            }
        } else {
            System.exit(-1);
        }
    }

    @FXML
    protected void abrirVentana() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/screens/chooseSandwichScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = new Stage();
        stage.setTitle("Selección Bocadillo");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();

        Stage anteriorVentana = (Stage) userInput.getScene().getWindow();
        anteriorVentana.close();
    }
}