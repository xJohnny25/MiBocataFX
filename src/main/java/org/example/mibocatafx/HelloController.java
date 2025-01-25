package org.example.mibocatafx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.Objects;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

//    @FXML
//    private Button loginButton;
//    private TextField userInput;
//    private PasswordField passwField;
//
//    @FXML
//    protected void onLoginButtonClick() {
//        String validUserMail = "user@gmail.com";
//        String validUserPassword = "1234";
//
//        String userMail = userInput.getText();
//        String userPassword = passwField.getText();
//
//        if (userMail.equals(validUserMail) && userPassword.equals(validUserPassword)) {
//            System.out.println("Loged");
//        } else {
//            System.exit(-1);
//        }
//    }
}