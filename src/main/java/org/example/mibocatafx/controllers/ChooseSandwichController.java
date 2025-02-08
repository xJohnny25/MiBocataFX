package org.example.mibocatafx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class ChooseSandwichController implements Initializable {
    @FXML
    private Pane backgroundPane;

    @FXML
    private Button orderButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backgroundPane.getStylesheets().add(getClass().getResource("/css/chooseSandwichStyle.css").toExternalForm());
    }
}
