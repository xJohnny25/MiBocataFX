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

import java.time.LocalDate;
import java.util.List;

public class EditAlumnoFormController {
    private final UsuarioService usuarioService = new UsuarioService();
    private final AlumnoService alumnoService = new AlumnoService();
    private final CursoService cursoService = new CursoService();
    private Alumno alumno;
    private Usuario usuario;

    @FXML
    private TextField alumnoNameInput;

    @FXML
    private TextField alumnoMailInput;

    @FXML
    private ComboBox<String> cursesBox;

    @FXML
    private ComboBox<String> stateBox;

    @FXML
    private TextArea deactivateReasonText;

    public void initializeInfo() {
        alumnoNameInput.setText(alumno.getNombre());
        alumnoMailInput.setText(alumno.getUsuario().getCorreo());

        cursesBox.setValue(alumno.getCurso().getNombre());

        if (alumno.getFechaBaja() == null) {
            stateBox.setValue("Activo");
        } else {
            stateBox.setValue("Inactivo");
        }

        List<Curso> cursos = cursoService.getAll();
        for (Curso curso : cursos) {
            cursesBox.getItems().add(curso.getNombre());
        }

        stateBox.getItems().addAll("Activo", "Inactivo");

        stateBox.setOnAction(event -> {
            String value = stateBox.getValue();

            deactivateReasonText.setDisable(!"Inactivo".equals(value));
        });

        usuario = alumno.getUsuario();
    }

    public void editAlumno() {
        String alumnoName = alumno.getNombre();
        String alumnoMail = usuario.getCorreo();
        String alumnoCurse = cursoService.getCursoByName(alumno.getCurso().getNombre()).getNombre();

        if (!alumnoNameInput.getText().isEmpty() && !alumnoNameInput.getText().equals(alumnoName)) {
            alumno.setNombre(alumnoNameInput.getText());
        }

        if (!alumnoMail.isEmpty() && !alumnoMailInput.getText().equals(alumnoMail)) {
            alumno.getUsuario().setCorreo(alumnoMailInput.getText());

            usuarioService.update(alumno.getUsuario());
        }

        if (!cursesBox.getValue().isEmpty() && !cursesBox.getValue().equals(alumnoCurse)) {
            alumno.setCurso(cursoService.getCursoByName(cursesBox.getValue()));
        }

        if (!deactivateReasonText.isDisabled() && !deactivateReasonText.getText().isEmpty()) {
            alumno.setFechaBaja(LocalDate.now());
            alumno.setMotivoBaja(deactivateReasonText.getText());
        }

        if (!stateBox.getValue().isEmpty() && stateBox.getValue().equals("Activo")) {
            alumno.setFechaBaja(null);
            alumno.setMotivoBaja(null);
        }

        alumnoService.update(alumno);

        Stage formWindow = (Stage) alumnoNameInput.getScene().getWindow();
        formWindow.close();
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }
}
