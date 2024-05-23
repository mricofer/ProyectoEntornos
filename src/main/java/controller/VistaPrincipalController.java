package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Modality;

public class VistaPrincipalController {
    public Button btnRedireccionarEmpresas;
    public Button btnRedireccionarAlumnos;
    public Button btnRedireccionarTutores;
    public Button btnRedireccionarAsignar;


    @FXML
    private void redireccionarEmpresas(ActionEvent event) {
        redireccionar("/view/VistaEmpresa.fxml", "EmpresasController");
    }

    @FXML
    private void redireccionarAlumnos(ActionEvent event) {
        redireccionar("/view/VistaAlumnos.fxml", "AlumnosController");
    }

    @FXML
    private void redireccionarTutores(ActionEvent event) {
        redireccionar("/view/VistaTutor.fxml", "TutoresController");
    }

    @FXML
    private void redireccionarAsignar(ActionEvent event) {
        redireccionar("/view/VistaAsignacion.fxml", "AsignacionController");
    }

    private void redireccionar(String fxmlPath, String controllerName) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        try {
            Parent root = loader.load();

            Object controller = loader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
