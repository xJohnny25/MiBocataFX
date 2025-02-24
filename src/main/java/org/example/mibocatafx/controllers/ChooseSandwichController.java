package org.example.mibocatafx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.mibocatafx.models.Alumno;
import org.example.mibocatafx.models.Bocata;
import org.example.mibocatafx.models.Pedido;
import org.example.mibocatafx.models.Usuario;
import org.example.mibocatafx.service.AlumnoService;
import org.example.mibocatafx.service.BocataService;
import org.example.mibocatafx.service.PedidoService;
import org.example.mibocatafx.util.TipoBocadillo;

import java.time.LocalDate;
import java.util.List;


public class ChooseSandwichController {
    private final BocataService bocataService = new BocataService();
    private final PedidoService pedidoService = new PedidoService();
    private final AlumnoService alumnoService = new AlumnoService();
    private Usuario usuario;
    private Alumno alumno;
    private Pedido pedidoActual;
    private Pedido pedidoExistente;

    @FXML
    private Button orderButton;

    @FXML
    private VBox bocadilloFrio;

    @FXML
    private VBox bocadilloCaliente;

    @FXML
    private HBox alergenosFrioBox;

    @FXML
    private HBox alergenosCalienteBox;

    @FXML
    private Label labelBocadilloFrio;

    @FXML
    private Label labelBocadilloCaliente;

    @FXML
    private Label bottomLabel;

    @FXML
    private Label descripcionBocadilloFrio;

    @FXML
    private Label descripcionBocadilloCaliente;

    @FXML
    private Label precioFrio;

    @FXML
    private Label precioCaliente;

    public void makeOrder() {
        alumno = alumnoService.getAlumnoByUsuario(usuario);
        pedidoExistente = pedidoService.getPedidoHoy(alumno);

        if (pedidoExistente != null){
            if (pedidoExistente.getBocadillo().getTipo().equals(TipoBocadillo.frio)) {
                bocadilloFrio.setStyle("-fx-background-color: #25A4F4; -fx-border-color: white; -fx-border-radius: 20; -fx-background-radius: 20");
                labelBocadilloFrio.setStyle("-fx-text-fill: white");
                descripcionBocadilloFrio.setStyle("-fx-text-fill: white");
                precioFrio.setStyle("-fx-text-fill: white");
            } else {
                bocadilloCaliente.setStyle("-fx-background-color: #25A4F4; -fx-border-color: white; -fx-border-radius: 20; -fx-background-radius: 20");
                labelBocadilloCaliente.setStyle("-fx-text-fill: white");
                descripcionBocadilloCaliente.setStyle("-fx-text-fill: white");
                precioCaliente.setStyle("-fx-text-fill: white");
            }
        }

        bocadilloFrio.setOnMouseClicked(mouseEvent -> seleccionarBocadillo("frio"));
        bocadilloCaliente.setOnMouseClicked(mouseEvent -> seleccionarBocadillo("caliente"));

        orderButton.setOnMouseClicked(event -> realizarPedido());
    }

    private void seleccionarBocadillo(String tipo) {
        String nombreBocadillo = tipo.equals("frio") ? labelBocadilloFrio.getText() : labelBocadilloCaliente.getText();
        Bocata bocadillo = bocataService.getBocataByName(nombreBocadillo);
        double precio = bocadillo.getPrecio();

        if (pedidoExistente != null) {
            if (pedidoExistente.getBocadillo().getTipo() == bocadillo.getTipo()) {
                bottomLabel.setText(nombreBocadillo);
                pedidoActual = pedidoExistente;

                System.out.println("El pedido ya contiene este bocadillo");
                System.out.println(pedidoActual.getBocadillo().getNombre());

                return;
            } else {
                Pedido pedidoNuevo = new Pedido(
                        pedidoExistente.getId(),
                        pedidoExistente.getAlumno(),
                        bocadillo,
                        null,
                        pedidoExistente.getFecha(),
                        precio,
                        null
                );

                pedidoActual = pedidoNuevo;

                System.out.println("Pedido actualizado: " + pedidoNuevo.getId());
                System.out.println(pedidoNuevo.getBocadillo().getNombre());
            }
        } else {
            // Si no tiene pedido, creamos uno nuevo
            pedidoActual = new Pedido(alumno, bocadillo, null, LocalDate.now(), precio, null);
            System.out.println("Nuevo pedido creado: " + pedidoActual);
        }

        bottomLabel.setText(nombreBocadillo);
    }

    private void realizarPedido() {
        if (pedidoActual == null) {
            System.out.println("No hay bocadillo seleccionado.");
            return;
        } else if (pedidoExistente != null && pedidoActual.getBocadillo().getNombre().equals(pedidoExistente.getBocadillo().getNombre())) {
            mostrarAlerta("Pedido ya existente", "Ya tienes un pedido creado con este bocadillo", Alert.AlertType.INFORMATION);
            return;
        }

        // Guardar o actualizar el pedido en la BD
        pedidoService.save(pedidoActual);
        mostrarAlerta("Pedido creado", "Tu pedido ha sido guardado satisfactoriamente", Alert.AlertType.CONFIRMATION);
        System.out.println("Pedido guardado en la base de datos: " + pedidoActual.getId());
    }

    public void cargarBocadillos() {
        List<Bocata> listaBocatasHoy = bocataService.getBocataToday();

        Bocata bocataCaliente = listaBocatasHoy.get(0);
        Bocata bocataFrio = listaBocatasHoy.get(1);

        labelBocadilloCaliente.setText(bocataCaliente.getNombre());
        labelBocadilloFrio.setText(bocataFrio.getNombre());

        descripcionBocadilloCaliente.setText(bocataCaliente.getDescripcion());
        descripcionBocadilloFrio.setText(bocataFrio.getDescripcion());

        precioCaliente.setText(String.format("%.2f€", bocataCaliente.getPrecio()));
        precioFrio.setText(String.format("%.2f€", bocataFrio.getPrecio()));
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipoAlerta) {
        Alert alert = new Alert(tipoAlerta);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
