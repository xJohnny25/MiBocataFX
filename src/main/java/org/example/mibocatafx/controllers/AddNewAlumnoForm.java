package org.example.mibocatafx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.mibocatafx.models.Alumno;
import org.example.mibocatafx.models.Curso;
import org.example.mibocatafx.models.Usuario;
import org.example.mibocatafx.service.AlumnoService;
import org.example.mibocatafx.service.CursoService;
import org.example.mibocatafx.service.UsuarioService;
import org.example.mibocatafx.util.Rol;

import java.time.LocalDate;
import java.util.List;

public class AddNewAlumnoForm {
    private final AlumnoService alumnoService = new AlumnoService();
    private final CursoService cursoService = new CursoService();

    @FXML
    private TextField newAlumnoNameInput;

    @FXML
    private TextField newAlumnoMailInput;

    @FXML
    private TextField newAlumnoPasswordInput;

    @FXML
    private ComboBox<String> cursesBox;

    @FXML
    private ComboBox<String> stateBox;

    @FXML
    private TextArea deactivateReasonText;

    public void initializeInfo() {
        List<Curso> cursos = cursoService.getAll();
        for (Curso curso : cursos) {
            cursesBox.getItems().add(curso.getNombre());
        }

        cursesBox.setValue(cursoService.getAll().get(0).getNombre());

        stateBox.getItems().addAll("Activo", "Inactivo");

        stateBox.setOnAction(event -> {
            String value = stateBox.getValue();

            deactivateReasonText.setDisable(!"Inactivo".equals(value));
        });
    }

    public void addNewAlumno() {
        //Hacer comprobaciones de textos
        String newAlumnoName = null;
        String newAlumnoMail = null;
        String newAlumnoPassword = null;
        Usuario newAlumnoUser;
        Curso newAlumnoCurse = null;
        LocalDate newAlumnoDeactivateDate = null;
        String newAlumnoDeactivateReason = null;

        if (!newAlumnoNameInput.getText().isEmpty()) {
            newAlumnoName = newAlumnoNameInput.getText();
        }
        if (!newAlumnoMailInput.getText().isEmpty()) {
            newAlumnoMail = newAlumnoMailInput.getText();
        }
        if (!newAlumnoPasswordInput.getText().isEmpty()) {
            newAlumnoPassword = newAlumnoPasswordInput.getText();
        }
        if (!cursesBox.getValue().isEmpty()) {
            newAlumnoCurse = cursoService.getCursoByName(cursesBox.getValue());
        }
        if (!stateBox.getValue().isEmpty()) {
            if (stateBox.getValue().equals("Inactivo")) {
                newAlumnoDeactivateDate = LocalDate.now();
                newAlumnoDeactivateReason = deactivateReasonText.getText();
            }
        }

        newAlumnoUser = new Usuario(newAlumnoMail, newAlumnoPassword, null, null, Rol.alumno);
        Alumno alumno = new Alumno(newAlumnoName, newAlumnoUser, newAlumnoCurse, newAlumnoDeactivateDate, newAlumnoDeactivateReason);

        //usuarioService.add(newAlumnoUser);
        alumnoService.add(alumno);

        Stage formWindow = (Stage) newAlumnoNameInput.getScene().getWindow();
        formWindow.close();
    }
}
