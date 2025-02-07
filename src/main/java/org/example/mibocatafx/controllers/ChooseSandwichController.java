package org.example.mibocatafx.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class ChooseSandwichController {
    @FXML
    private Pane backgroundPane;

    @FXML
    private Button orderButton;

    public void initialize() {
        backgroundPane.getStylesheets().add(getClass().getResource("/css/chooseSandwichStyle.css").toExternalForm());

        orderButton.setOnMouseEntered(mouseEvent -> {
            orderButton.setStyle("-fx-background-color: #0080D0");
        });

    }
}
