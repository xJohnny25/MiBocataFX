package org.example.mibocatafx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.mibocatafx.HelloApplication;
import org.example.mibocatafx.models.Usuario;
import org.example.mibocatafx.service.AlumnoService;
import org.example.mibocatafx.service.UsuarioService;

import java.io.IOException;

public class LoginController {
    private Usuario usuario;
    
    @FXML
    private TextField userInput;

    @FXML
    private PasswordField passField;

    private FXMLLoader fxmlLoader;
    private Scene scene;

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
            //TODO -> poner alert
            System.out.println("El usuario no se encuentra en la base de datos");
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

                Stage stage = new Stage();
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
                fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/screens/chooseSandwichScreen.fxml"));
                scene = new Scene(fxmlLoader.load());

            break;

            case "admin":
                fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/screens/admin.fxml"));
                scene = new Scene(fxmlLoader.load());

            break;
        }

        Stage anteriorVentana = (Stage) userInput.getScene().getWindow();
        anteriorVentana.close();
    }
}