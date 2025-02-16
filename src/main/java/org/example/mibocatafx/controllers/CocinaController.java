package org.example.mibocatafx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

public class CocinaController {
    @FXML
    private HBox logOutButton;

    @FXML
    private TextField nameInput;

    @FXML
    private TextField lastNameInput;

    @FXML
    private ComboBox<String> cursesBox;

    @FXML
    private DatePicker calendarInput;

    @FXML
    private Button searchButton;

    @FXML
    private TableView table;

    @FXML
    private ComboBox numPages;

    @FXML
    private Button previusPage;

    @FXML
    private Button nextPage;

    @FXML
    private Label pagesCount;

    @FXML
    private Label resultsCount;


}
