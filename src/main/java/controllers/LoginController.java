package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
            System.out.println("Loged");
        } else {
            System.exit(-1);
        }
    }
}