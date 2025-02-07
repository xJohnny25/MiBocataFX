package org.example.mibocatafx.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class ChooseSandwichController {
    @FXML
    private Pane backgroundPane;

    public void initialize() {
        backgroundPane.getStylesheets().add(getClass().getResource("/css/chooseSandwichStyle.css").toExternalForm());

    }
}
