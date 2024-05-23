package controller;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import model.Tutor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import utils.ConnectionDB;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TutorController {
    public TableView tableTutor;
    public TableColumn colIdTutor;
    public TableColumn colNombre;
    public TableColumn colApellidos;
    public TableColumn colEmail;
    public TableColumn colTelefono;
    public TableColumn colDepartamento;
    public TextField txtNombre;
    public TextField txtApellido;
    public TextField txtEmail;
    public TextField txtTelefono;
    public TextField txtDepartamento;
    public Button btnEliminar;
    public Button btnInsertar;
    public Button btnModificar;

    private ObservableList<Tutor> tutores = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupTableTutor();
        loadTutores();
    }

    private void setupTableTutor() {
        colIdTutor.setCellValueFactory(new PropertyValueFactory<>("id_tutor"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colDepartamento.setCellValueFactory(new PropertyValueFactory<>("departamento"));
    }

    private void loadTutores() {
        tutores.clear();
        try{
            Connection connection = ConnectionDB.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM tutores");

            while (resultSet.next()) {
                Tutor tutor = new Tutor(
                        resultSet.getInt("id_tutor"),
                        resultSet.getString("nombre"),
                        resultSet.getString("apellidos"),
                        resultSet.getString("email"),
                        resultSet.getString("telefono"),
                        resultSet.getString("departamento")
                );
                tutores.add(tutor);
            }
            tableTutor.setItems(tutores);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void seleccionarTutor() {
        Tutor tutor = (Tutor) tableTutor.getSelectionModel().getSelectedItem();
        if (tutor != null) {
            txtNombre.setText(tutor.getNombre());
            txtApellido.setText(tutor.getApellidos());
            txtEmail.setText(tutor.getEmail());
            txtTelefono.setText(tutor.getTelefono());
            txtDepartamento.setText(tutor.getDepartamento());
        }
    }

    @FXML
    public void insertarTutor(ActionEvent event) {
        String nombre = txtNombre.getText();
        String apellidos = txtApellido.getText();
        String email = txtEmail.getText();
        String telefono = txtTelefono.getText();
        String departamento = txtDepartamento.getText();
        try{
            Connection connection = ConnectionDB.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO tutores (nombre, apellidos, email, telefono, departamento) VALUES ('" + nombre + "', '" + apellidos + "', '" + email + "', '" + telefono + "', '" + departamento + "')");
            loadTutores();


        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void modificarTutor(ActionEvent event) {
        Tutor tutor = (Tutor) tableTutor.getSelectionModel().getSelectedItem();
        if (tutor != null) {
            String nombre = txtNombre.getText();
            String apellidos = txtApellido.getText();
            String email = txtEmail.getText();
            String telefono = txtTelefono.getText();
            String departamento = txtDepartamento.getText();
            try{
                Connection connection = ConnectionDB.getConnection();
                Statement statement = connection.createStatement();
                statement.executeUpdate("UPDATE tutores SET nombre = '" + nombre + "', apellidos = '" + apellidos + "', email = '" + email + "', telefono = '" + telefono + "', departamento = '" + departamento + "' WHERE id_tutor = '" + tutor.getId_tutor() + "'");
                loadTutores();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void eliminarTutor(ActionEvent event) {
        Tutor tutor = (Tutor) tableTutor.getSelectionModel().getSelectedItem();
        if (tutor != null) {
            try{
                Connection connection = ConnectionDB.getConnection();
                Statement statement = connection.createStatement();
                statement.executeUpdate("DELETE FROM tutores WHERE id_tutor = '" + tutor.getId_tutor() + "'");
                loadTutores();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void importarXML(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Seleccionar archivo XML");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Archivos XML", "*.xml")
            );
            File selectedFile = fileChooser.showOpenDialog(null);

            if (selectedFile != null) {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(selectedFile);

                doc.getDocumentElement().normalize();

                Connection conn = ConnectionDB.getConnection();
                NodeList nodeList = doc.getElementsByTagName("tutor");

                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;
                        String nombre = element.getElementsByTagName("nombre").item(0).getTextContent();
                        String apellidos = element.getElementsByTagName("apellidos").item(0).getTextContent();
                        String email = element.getElementsByTagName("email").item(0).getTextContent();
                        String telefono = element.getElementsByTagName("telefono").item(0).getTextContent();
                        String departamento = element.getElementsByTagName("departamento").item(0).getTextContent();

                        String query = "INSERT INTO tutores (nombre, apellidos, email, telefono, departamento) VALUES (?, ?, ?, ?, ?)";
                        PreparedStatement preparedStatement = conn.prepareStatement(query);
                        preparedStatement.setString(1, nombre);
                        preparedStatement.setString(2, apellidos);
                        preparedStatement.setString(3, email);
                        preparedStatement.setString(4, telefono);
                        preparedStatement.setString(5, departamento);
                        preparedStatement.executeUpdate();
                    }
                }

                conn.close();
                System.out.println("Datos insertados correctamente en la base de datos.");

                // Actualizar la tabla después de la inserción
                loadTutores();

            } else {
                System.out.println("Ningún archivo seleccionado.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void importarCSV(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Seleccionar archivo CSV");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Archivos CSV", "*.csv")
            );
            File selectedFile = fileChooser.showOpenDialog(null);

            if (selectedFile != null) {
                // Establecer la conexión
                Connection conn = null;
                PreparedStatement preparedStatement = null;
                try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
                    conn = ConnectionDB.getConnection();
                    String insertQuery = "INSERT INTO tutores (nombre, apellidos, email, telefono, departamento) VALUES (?, ?, ?, ?, ?)";
                    preparedStatement = conn.prepareStatement(insertQuery);

                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] parts = line.split(",");
                        if (parts.length == 5) { // Verificar que haya 5 campos en cada línea
                            // Obtener y limpiar los datos
                            String nombre = parts[0].trim();
                            String apellidos = parts[1].trim();
                            String email = parts[2].trim();
                            String telefono = parts[3].trim();
                            String departamento = parts[4].trim();

                            // Establecer los parámetros
                            preparedStatement.setString(1, nombre);
                            preparedStatement.setString(2, apellidos);
                            preparedStatement.setString(3, email);
                            preparedStatement.setString(4, telefono);
                            preparedStatement.setString(5, departamento);

                            // Ejecutar la inserción
                            preparedStatement.executeUpdate();
                        } else {
                            System.out.println("Error: línea incorrecta en el archivo CSV.");
                        }
                    }
                    // Actualizar la tabla después de la inserción
                    loadTutores();
                    System.out.println("Datos insertados correctamente desde el archivo CSV.");
                } catch (IOException e) {
                    System.out.println("Error al leer el archivo CSV: " + e.getMessage());
                } finally {
                    // Cerrar la conexión y el statement
                    if (preparedStatement != null) {
                        preparedStatement.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                }
            } else {
                System.out.println("Ningún archivo seleccionado.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
