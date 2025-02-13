package org.example.mibocatafx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.skin.LabeledSkinBase;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.example.mibocatafx.models.Alumno;
import org.example.mibocatafx.models.Bocata;
import org.example.mibocatafx.models.Pedido;
import org.example.mibocatafx.models.Usuario;
import org.example.mibocatafx.service.AlumnoService;
import org.example.mibocatafx.service.BocataService;
import org.example.mibocatafx.service.PedidoService;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class ChooseSandwichController {
    private final BocataService bocataService = new BocataService();
    private final PedidoService pedidoService = new PedidoService();
    private final AlumnoService alumnoService = new AlumnoService();
    private Usuario usuario;
    private Pedido pedidoActual;

    @FXML
    private Button orderButton;

    @FXML
    private VBox bocadilloFrio;

    @FXML
    private VBox bocadilloCaliente;

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
        bocadilloFrio.setOnMouseClicked(mouseEvent -> seleccionarBocadillo("frio"));
        bocadilloCaliente.setOnMouseClicked(mouseEvent -> seleccionarBocadillo("caliente"));

        orderButton.setOnMouseClicked(event -> realizarPedido());
    }

    private void seleccionarBocadillo(String tipo) {
        String nombreBocadillo = tipo.equals("frio") ? labelBocadilloFrio.getText() : labelBocadilloCaliente.getText();
        Bocata bocadillo = bocataService.getBocataByName(nombreBocadillo);
        double precio = bocadillo.getPrecio();
        Alumno alumno = alumnoService.getAlumnoByUsuario(usuario);

        // Verificar si ya tiene un pedido hoy
        Pedido pedidoExistente = pedidoService.getPedidoHoy(alumno);


        if (pedidoExistente != null) {
            // Si ya tiene un pedido, actualizamos el bocadillo
            pedidoExistente.setBocadillo(bocadillo);
            pedidoExistente.setPrecioFinal(precio);
            pedidoActual = pedidoExistente;
            System.out.println("Pedido actualizado: " + pedidoExistente);
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
        }

        // Guardar o actualizar el pedido en la BD
        pedidoService.save(pedidoActual);
        System.out.println("Pedido guardado en la base de datos: " + pedidoActual);
    }


    public void cargarBocadillos() {
        List<Bocata> listaBocatasHoy = bocataService.getBocataToday();

        Bocata bocataCaliente = listaBocatasHoy.get(0);
        Bocata bocataFrio = listaBocatasHoy.get(1);

        labelBocadilloCaliente.setText(bocataCaliente.getNombre());
        labelBocadilloFrio.setText(bocataFrio.getNombre());

        descripcionBocadilloCaliente.setText(bocataCaliente.getDescripcion());
        descripcionBocadilloFrio.setText(bocataFrio.getDescripcion());

        precioCaliente.setText(bocataCaliente.getPrecio() + "€");
        precioFrio.setText(bocataFrio.getPrecio() + "€");
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
