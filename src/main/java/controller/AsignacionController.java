package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Asignacion;
import utils.ConnectionDB;

import java.sql.*;
import java.time.LocalDate;

public class AsignacionController {
    public ComboBox cmbAlumno;
    public ComboBox cmbEmpresa;
    public ComboBox cmbTutor;
    public TableColumn idAsignacionColumn;
    public TableColumn nombreAlumnoColumn;
    public TableColumn nombreEmpresaColumn;
    public TableColumn nombreTutorColumn;
    public TableColumn fechaInicioColumn;
    public TableColumn fechaFinColumn;
    public Button btnAsignar;
    public Label lblStatus;

    private ObservableList<Asignacion> asignaciones = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        setupTable();
        loadTable();
        cargarAlumnos();
        cargarEmpresas();
        cargarTutores();
    }

    private void cargarAlumnos() {
        try {
            Connection connection = ConnectionDB.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT nombre FROM alumnos");

            while (resultSet.next()) {
                String nombreAlumno = resultSet.getString("nombre");
                cmbAlumno.getItems().add(nombreAlumno);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al cargar los alumnos: " + e.getMessage()); // Depuración
        }
    }


    private void cargarEmpresas() {
        try {
            Connection connection = ConnectionDB.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT nombre_empresa FROM empresas");

            while (resultSet.next()) {
                cmbEmpresa.getItems().add(resultSet.getString("nombre_empresa"));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cargarTutores() {
        try {
            Connection connection = ConnectionDB.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT nombre FROM tutores");

            while (resultSet.next()) {
                cmbTutor.getItems().add(resultSet.getString("nombre"));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void setupTable(){
        idAsignacionColumn.setCellValueFactory(new PropertyValueFactory<>("id_asignacion"));
        nombreAlumnoColumn.setCellValueFactory(new PropertyValueFactory<>("nombre_alumno"));
        nombreEmpresaColumn.setCellValueFactory(new PropertyValueFactory<>("nombre_empresa"));
        nombreTutorColumn.setCellValueFactory(new PropertyValueFactory<>("nombre_tutor"));
        fechaInicioColumn.setCellValueFactory(new PropertyValueFactory<>("fecha_inicio"));
        fechaFinColumn.setCellValueFactory(new PropertyValueFactory<>("fecha_fin"));
    }

    private void loadTable(){
        asignaciones.clear();
        try (Connection connection = ConnectionDB.getConnection()) {
            String sql = "SELECT * FROM asignaciones";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int id_asignacion = resultSet.getInt("id_asignacion");
                    String nombre_alumno = resultSet.getString("nombre_alumno");
                    String nombre_empresa = resultSet.getString("nombre_empresa");
                    String nombre_tutor = resultSet.getString("nombre_tutor");
                    String fecha_inicio = resultSet.getString("fecha_inicio");
                    String fecha_fin = resultSet.getString("fecha_fin");
                    Asignacion asignacion = new Asignacion(id_asignacion, nombre_alumno, nombre_empresa, nombre_tutor, fecha_inicio, fecha_fin);
                    asignaciones.add(asignacion);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void asignar() {
        String nombreAlumno = (String) cmbAlumno.getValue();
        String nombreEmpresa = (String) cmbEmpresa.getValue();
        String nombreTutor = (String) cmbTutor.getValue();
        LocalDate fechaInicio = LocalDate.now();
        LocalDate fechaFin = fechaInicio.plusMonths(6);

        if (nombreAlumno == null || nombreEmpresa == null || nombreTutor == null || fechaInicio == null) {
            lblStatus.setText("Por favor, complete todos los campos obligatorios.");
            return;
        }

        try (Connection connection = ConnectionDB.getConnection()) {
            // Obtener id_alumno
            int idAlumno = obtenerId(connection, "alumnos", "nombre", nombreAlumno);
            // Obtener id_empresa
            int idEmpresa = obtenerId(connection, "empresas", "nombre_empresa", nombreEmpresa);
            // Obtener id_tutor
            int idTutor = obtenerId(connection, "tutores", "nombre", nombreTutor);

            // Insertar la nueva asignación en la base de datos
            String sql = "INSERT INTO asignaciones (id_alumno, id_empresa, id_tutor, fecha_inicio, fecha_fin) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, idAlumno);
                preparedStatement.setInt(2, idEmpresa);
                preparedStatement.setInt(3, idTutor);
                preparedStatement.setDate(4, java.sql.Date.valueOf(fechaInicio));
                if (fechaFin != null) {
                    preparedStatement.setDate(5, java.sql.Date.valueOf(fechaFin));
                } else {
                    preparedStatement.setNull(5, java.sql.Types.DATE);
                }
                preparedStatement.executeUpdate();
                lblStatus.setText("Asignación realizada con éxito.");

                loadTable();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            lblStatus.setText("Error al realizar la asignación.");
        }
    }

    // Otros métodos

    private int obtenerId(Connection connection, String tabla, String columnaNombre, String valorNombre) throws SQLException {
        if (connection == null || tabla == null || columnaNombre == null || valorNombre == null) {
            throw new IllegalArgumentException("None of the arguments can be null");
        }

        String idColumn = "id_" + tabla.substring(0, tabla.length() - 1); // Remove the 's' at the end of the table name
        if (tabla.equals("tutores")) {
            idColumn = "id_tutor"; // Use the correct column name for the 'tutores' table
        }
        String sql = "SELECT " + idColumn + " FROM " + tabla + " WHERE " + columnaNombre + " = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, valorNombre);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                throw new SQLException("No record found for " + columnaNombre + " in table " + tabla);
            }
        }
    }



}