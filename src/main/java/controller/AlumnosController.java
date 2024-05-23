package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import model.Alumno;
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
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class AlumnosController {
    public TableView tableAlumnos;
    public TableColumn colIdAlumno;
    public TableColumn colCodigoAlumno;
    public TableColumn colDni;
    public TableColumn colNombre;
    public TableColumn colApellidos;
    public TableColumn colFechaNacimiento;
    public TableColumn colTelefono;
    public TableColumn colMail;
    public TextField txtCodigoAlumno;
    public TextField txtDni;
    public TextField txtNombre;
    public TextField txtApellidos;
    public DatePicker dateFechaNacimiento;
    public TextField txtTelefono;
    public TextField txtMail;
    public Button btnEliminar;
    public Button btnInsertar;
    public Button btnModificar;
    public Button btnImportarCSV;
    public Button btnImportarTXT;
    public Button btnImportarXML;

    private ObservableList<Alumno> data = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupTableAlumnos();
        loadAlumnos();
    }

    private void setupTableAlumnos() {
        colIdAlumno.setCellValueFactory(new PropertyValueFactory<>("id_alumno"));
        colCodigoAlumno.setCellValueFactory(new PropertyValueFactory<>("codigo_alumno"));
        colDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colFechaNacimiento.setCellValueFactory(new PropertyValueFactory<>("fecha_nacimiento"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colMail.setCellValueFactory(new PropertyValueFactory<>("mail"));
    }

    private void loadAlumnos() {
        data.clear();
        try {
            Connection connection = ConnectionDB.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM alumnos");

            while (resultSet.next()) {
                Alumno alumno = new Alumno(
                        resultSet.getInt("id_alumno"),
                        resultSet.getString("codigo_alumno"),
                        resultSet.getString("dni"),
                        resultSet.getString("nombre"),
                        resultSet.getString("apellidos"),
                        resultSet.getString("fecha_nacimiento"),
                        resultSet.getString("email"),
                        resultSet.getString("telefono")
                );
                data.add(alumno);
            }
            tableAlumnos.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void seleccionarAlumno() {
        Alumno alumno = (Alumno) tableAlumnos.getSelectionModel().getSelectedItem();
        if (alumno != null) {
            txtCodigoAlumno.setText(alumno.getCodigo_alumno());
            txtDni.setText(alumno.getDni());
            txtNombre.setText(alumno.getNombre());
            txtApellidos.setText(alumno.getApellidos());

            String fechaNacimientoStr = alumno.getFecha_nacimiento();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            try {
                LocalDate fechaNacimiento = LocalDate.parse(fechaNacimientoStr, formatter);
                dateFechaNacimiento.setValue(fechaNacimiento);
            } catch (DateTimeParseException e) {
                // Handle parsing error, e.g., show an error message
                System.err.println("Error parsing date: " + e.getMessage());
                dateFechaNacimiento.setValue(null);  // Or some default value
            }

            txtTelefono.setText(alumno.getTelefono());
            txtMail.setText(alumno.getMail());
        }
    }


    @FXML
    public void insertarAlumno(ActionEvent event) {
        String codigoAlumno = txtCodigoAlumno.getText();
        String dni = txtDni.getText();
        String nombre = txtNombre.getText();
        String apellidos = txtApellidos.getText();
        String fechaNacimiento = dateFechaNacimiento.getValue().toString();
        String telefono = txtTelefono.getText();
        String mail = txtMail.getText();
        try {
            Connection connection = ConnectionDB.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO alumnos (codigo_alumno, dni, nombre, apellidos, fecha_nacimiento, email, telefono) VALUES ('" + codigoAlumno + "', '" + dni + "', '" + nombre + "', '" + apellidos + "', '" + fechaNacimiento + "', '" + mail + "', '" + telefono + "')");

            loadAlumnos();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void modificarAlumno() {
        Alumno alumno = (Alumno) tableAlumnos.getSelectionModel().getSelectedItem();

        String codigoAlumno = txtCodigoAlumno.getText();
        String dni = txtDni.getText();
        String nombre = txtNombre.getText();
        String apellidos = txtApellidos.getText();
        String fechaNacimiento = dateFechaNacimiento.getValue().toString();
        String telefono = txtTelefono.getText();
        String mail = txtMail.getText();
        try {
            Connection connection = ConnectionDB.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE alumnos SET codigo_alumno = '" + codigoAlumno + "', dni = '" + dni + "', nombre = '" + nombre + "', apellidos = '" + apellidos + "', fecha_nacimiento = '" + fechaNacimiento + "', email = '" + mail + "', telefono = '" + telefono + "' WHERE id_alumno = " + alumno.getId_alumno());

            loadAlumnos();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void eliminarAlumno() {
        Alumno alumno = (Alumno) tableAlumnos.getSelectionModel().getSelectedItem();
        try {
            Connection connection = ConnectionDB.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM alumnos WHERE id_alumno = " + alumno.getId_alumno());

            loadAlumnos();

        } catch (Exception e) {
            e.printStackTrace();
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
                NodeList nodeList = doc.getElementsByTagName("alumno");

                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;
                        String codigoAlumno = element.getElementsByTagName("codigo_alumno").item(0).getTextContent();
                        String dni = element.getElementsByTagName("dni").item(0).getTextContent();
                        String nombre = element.getElementsByTagName("nombre").item(0).getTextContent();
                        String apellidos = element.getElementsByTagName("apellidos").item(0).getTextContent();
                        String fechaNacimiento = element.getElementsByTagName("fecha_nacimiento").item(0).getTextContent();
                        String telefono = element.getElementsByTagName("telefono").item(0).getTextContent();
                        String mail = element.getElementsByTagName("mail").item(0).getTextContent();

                        String sql = "INSERT INTO alumnos (codigo_alumno, dni, nombre, apellidos, fecha_nacimiento, telefono, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
                        PreparedStatement statement = conn.prepareStatement(sql);
                        statement.setString(1, codigoAlumno);
                        statement.setString(2, dni);
                        statement.setString(3, nombre);
                        statement.setString(4, apellidos);
                        statement.setString(5, fechaNacimiento);
                        statement.setString(6, telefono);
                        statement.setString(7, mail);
                        statement.executeUpdate();
                    }
                }

                conn.close();
                System.out.println("Datos insertados correctamente en la base de datos.");

                // Actualizar la tabla después de la inserción
                loadAlumnos();

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
                Connection conn = ConnectionDB.getConnection();
                String insertQuery = "INSERT INTO alumnos (codigo_alumno, dni, nombre, apellidos, fecha_nacimiento, email, telefono) VALUES (?, ?, ?, ?, ?, ?, ?)";

                try (BufferedReader br = new BufferedReader(new FileReader(selectedFile));
                     PreparedStatement preparedStatement = conn.prepareStatement(insertQuery)) {

                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] parts = line.split(",");
                        if (parts.length == 7) { // Verificar que haya 7 campos en cada línea
                            preparedStatement.setString(1, parts[0]); // Código de alumno
                            preparedStatement.setString(2, parts[1]); // DNI
                            preparedStatement.setString(3, parts[2]); // Nombre
                            preparedStatement.setString(4, parts[3]); // Apellidos
                            String fechaNacimiento = parts[4]; // Suponiendo que la fecha está en el índice 4
                            try {
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                java.util.Date parsedDate = dateFormat.parse(fechaNacimiento);
                                java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());
                                preparedStatement.setDate(5, sqlDate); // Fecha de nacimiento
                            } catch (ParseException e) {
                                // Manejar el error de formato de fecha
                                System.err.println("Error al parsear la fecha de nacimiento: " + fechaNacimiento);
                                continue; // Omitir esta línea y continuar con la siguiente
                            }
                            preparedStatement.setString(6, parts[5]); // Email
                            preparedStatement.setString(7, parts[6]); // Teléfono

                            preparedStatement.executeUpdate();
                        }
                    }
                }

                // Actualizar la tabla después de la inserción
                loadAlumnos();

                conn.close();
                System.out.println("Datos insertados correctamente desde el archivo CSV.");
            } else {
                System.out.println("Ningún archivo seleccionado.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
