package org.example.mibocatafx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.example.mibocatafx.models.Bocata;
import org.example.mibocatafx.models.Pedido;
import org.example.mibocatafx.service.BocataService;
import org.example.mibocatafx.service.PedidoService;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class ChooseSandwichController implements Initializable {
    private final BocataService bocataService = new BocataService();
    private final PedidoService pedidoService = new PedidoService();
    private String correoUsuario;

    @FXML
    private Pane backgroundPane;

    @FXML
    private Button orderButton;

    @FXML
    private HBox bocadilloFrio;

    @FXML
    private HBox bocadilloCaliente;

    public ChooseSandwichController(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backgroundPane.getStylesheets().add(getClass().getResource("/css/chooseSandwichStyle.css").toExternalForm());
    }

    public void makeOrder() {
        AtomicBoolean tipoBocadillo = new AtomicBoolean();
        AtomicBoolean precioBocadillo = new AtomicBoolean();

        //Recogemos los bocadillos
        Label labelBocadilloFrio = (Label) bocadilloFrio.getChildren().get(0);
        String nombreBocadilloFrio = labelBocadilloFrio.getText();

        Label labelBocadilloCaliente = (Label) bocadilloCaliente.getChildren().get(0);
        String nombreBocadilloCaliente = labelBocadilloCaliente.getText();

        bocadilloFrio.setOnMouseClicked(mouseEvent -> {
            //TODO -> Comprobar horario y si ya tiene un pedido creado
            //TODO -> Cambiar pedido bottomBar por nombre bocadillo

            //¿Sólo aplico estilos?
            //¿Cómo consigo el usuario logeado?

            tipoBocadillo.set(true);
            precioBocadillo.set(true);
        });

        bocadilloCaliente.setOnMouseClicked(mouseEvent -> {





            tipoBocadillo.set(false);
            precioBocadillo.set(false);
        });

        int idAlumno = 0;//obtener id Alumno
        Bocata bocadillo = tipoBocadillo.get() ? bocataService.getBocataByName(nombreBocadilloFrio) : bocataService.getBocataByName(nombreBocadilloCaliente);
        double precio = precioBocadillo.get() ? bocataService.getBocataByName(nombreBocadilloFrio).getPrecio() : bocataService.getBocataByName(nombreBocadilloCaliente).getPrecio();

        Pedido pedido = new Pedido(idAlumno, bocadillo.getId(), null, LocalDateTime.now(), precio, 0);

        orderButton.setOnMouseClicked(mouseEvent -> {
            pedidoService.save(pedido);
        });
    }
}