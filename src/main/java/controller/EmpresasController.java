package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Empresa;
import utils.ConnectionDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EmpresasController {

    public TextField textFieldCodigoEmpresa;
    public TextField textFieldCIF;
    public TextField textFieldNombre;
    public TextField textFieldDireccion;
    public TextField textFieldCP;
    public TextField textFieldLocalidad;
    public ComboBox comboBoxJornada;
    public ComboBox comboBoxModalidad;
    public TextField textFieldMail;
    public Button btnInsertarEmpresa;
    public Button btnModificarEmpresa;
    public Button btnEliminarEmpresa;
    public Button btnInsertarPersona;
    public Button btnModificarPersona;
    public Button btnEliminarPersona;
    public TextField textFieldDNIRepLegal;
    public TextField textFieldNombreRL;
    public TextField textFieldNombreTL;
    public TextField textFieldApellidosTL;
    public TextField textFieldApellidosRL;
    public TextField textFieldDNITutLab;
    public TextField textFieldTelefonoTL;
    public TableColumn colNombreEmpresa;
    @FXML
    private TableView<Empresa> tableEmpresas;

    @FXML
    private TableColumn<Empresa, Integer> colId;
    @FXML
    private TableColumn<Empresa, String> colCodigoEmpresa;
    @FXML
    private TableColumn<Empresa, String> colCIF;
    @FXML
    private TableColumn<Empresa, String> colDireccion;
    @FXML
    private TableColumn<Empresa, String> colCP;
    @FXML
    private TableColumn<Empresa, String> colLocalidad;
    @FXML
    private TableColumn<Empresa, String> colTipoJornada;
    @FXML
    private TableColumn<Empresa, String> colModalidad;
    @FXML
    private TableColumn<Empresa, String> colMail;
    @FXML
    private TableColumn<Empresa, String> colDNIResp;
    @FXML
    private TableColumn<Empresa, String> colNombreResp;
    @FXML
    private TableColumn<Empresa, String> colApellidosResp;
    @FXML
    private TableColumn<Empresa, String> colDNITut;
    @FXML
    private TableColumn<Empresa, String> colNombreTut;
    @FXML
    private TableColumn<Empresa, String> colApellidosTut;
    @FXML
    private TableColumn<Empresa, String> colTelefonoTut;

    private ObservableList<Empresa> data = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        setupTableColumns();
        loadEmpresasData();
        initializeComboBoxes();
    }

    private void initializeComboBoxes() {
        ObservableList<String> jornadaOptions = FXCollections.observableArrayList(
                "Completa", "Parcial"
        );
        comboBoxJornada.setItems(jornadaOptions);

        ObservableList<String> modalidadOptions = FXCollections.observableArrayList(
                "Presencial", "Semipresencial", "Distancia"
        );
        comboBoxModalidad.setItems(modalidadOptions);
    }

    private void setupTableColumns() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id_empresa"));
        colCodigoEmpresa.setCellValueFactory(new PropertyValueFactory<>("codigo_empresa"));
        colCIF.setCellValueFactory(new PropertyValueFactory<>("cif"));
        colNombreEmpresa.setCellValueFactory(new PropertyValueFactory<>("nombre_empresa"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colCP.setCellValueFactory(new PropertyValueFactory<>("cp"));
        colLocalidad.setCellValueFactory(new PropertyValueFactory<>("localidad"));
        colTipoJornada.setCellValueFactory(new PropertyValueFactory<>("tipo_jornada"));
        colModalidad.setCellValueFactory(new PropertyValueFactory<>("modalidad"));
        colMail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        colDNIResp.setCellValueFactory(new PropertyValueFactory<>("dni_responsable"));
        colNombreResp.setCellValueFactory(new PropertyValueFactory<>("nombre_responsable"));
        colApellidosResp.setCellValueFactory(new PropertyValueFactory<>("apellidos_responsable"));
        colDNITut.setCellValueFactory(new PropertyValueFactory<>("dni_tutor_laboral"));
        colNombreTut.setCellValueFactory(new PropertyValueFactory<>("nombre_tutor_laboral"));
        colApellidosTut.setCellValueFactory(new PropertyValueFactory<>("apellidos_tutor_laboral"));
        colTelefonoTut.setCellValueFactory(new PropertyValueFactory<>("telefono_tutor_laboral"));
    }

    private void loadEmpresasData() {
        data.clear();
        try (Connection conexion = ConnectionDB.getConnection()) {
            Statement statement = conexion.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM empresas");

            while (resultSet.next()) {
                Empresa empresa = new Empresa();
                empresa.setId_empresa(resultSet.getInt("id_empresa"));
                empresa.setCodigo_empresa(resultSet.getString("codigo_empresa"));
                empresa.setCif(resultSet.getString("cif"));
                empresa.setNombre_empresa(resultSet.getString("nombre_empresa"));
                empresa.setDireccion(resultSet.getString("direccion"));
                empresa.setCp(resultSet.getString("cp"));
                empresa.setLocalidad(resultSet.getString("localidad"));
                empresa.setTipo_jornada(resultSet.getString("tipo_jornada"));
                empresa.setModalidad(resultSet.getString("modalidad"));
                empresa.setMail(resultSet.getString("mail"));
                empresa.setDni_responsable(resultSet.getString("dni_responsable"));
                empresa.setNombre_responsable(resultSet.getString("nombre_responsable"));
                empresa.setApellidos_responsable(resultSet.getString("apellidos_responsable"));
                empresa.setDni_tutor_laboral(resultSet.getString("dni_tutor_laboral"));
                empresa.setNombre_tutor_laboral(resultSet.getString("nombre_tutor_laboral"));
                empresa.setApellidos_tutor_laboral(resultSet.getString("apellidos_tutor_laboral"));
                empresa.setTelefono_tutor_laboral(resultSet.getString("telefono_tutor_laboral"));

                data.add(empresa);
            }

            tableEmpresas.setItems(data);

        } catch (SQLException e) {
            e.printStackTrace();
            // Opcionalmente mostrar un mensaje de error al usuario
        }
    }

    @FXML
    public void insertarEmpresa(ActionEvent event) {
        String codigoEmpresa = textFieldCodigoEmpresa.getText();
        String cif = textFieldCIF.getText();
        String nombre = textFieldNombre.getText();
        String direccion = textFieldDireccion.getText();
        String cp = textFieldCP.getText();
        String localidad = textFieldLocalidad.getText();
        String tipoJornada = comboBoxJornada.getValue().toString();
        String modalidad = comboBoxModalidad.getValue().toString();
        String mail = textFieldMail.getText();


        try (Connection conexion = ConnectionDB.getConnection()) {
            Statement statement = conexion.createStatement();
            statement.executeUpdate("INSERT INTO empresas (codigo_empresa, cif, nombre_empresa, direccion, cp, localidad, tipo_jornada, modalidad, mail) " +
                    "VALUES ('" + codigoEmpresa + "', '" + cif + "', '" + nombre + "', '" + direccion + "', '" + cp + "', '" + localidad + "', '" + tipoJornada + "', '" + modalidad + "', '" + mail + "')");

            loadEmpresasData();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void modificarEmpresa(ActionEvent event) {
        Empresa empresa = tableEmpresas.getSelectionModel().getSelectedItem();
        if (empresa == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("No se ha seleccionado ninguna empresa");
            alert.showAndWait();
        }

        String codigoEmpresa = textFieldCodigoEmpresa.getText();
        String cif = textFieldCIF.getText();
        String nombre = textFieldNombre.getText();
        String direccion = textFieldDireccion.getText();
        String cp = textFieldCP.getText();
        String localidad = textFieldLocalidad.getText();
        String tipoJornada = comboBoxJornada.getValue().toString();
        String modalidad = comboBoxModalidad.getValue().toString();
        String mail = textFieldMail.getText();

        try (Connection conexion = ConnectionDB.getConnection()) {
            Statement statement = conexion.createStatement();
            statement.executeUpdate("UPDATE empresas SET codigo_empresa = '" + codigoEmpresa + "', cif = '" + cif + "', nombre_empresa = '" + nombre + "', direccion = '" + direccion + "', cp = '" + cp + "', localidad = '" + localidad + "', tipo_jornada = '" + tipoJornada + "', modalidad = '" + modalidad  + "', mail = '" + mail  +  "' WHERE id_empresa = " + empresa.getId_empresa());

            loadEmpresasData();

        } catch (SQLException e) {
            e.printStackTrace();
            // Opcionalmente mostrar un mensaje de error al usuario
        }
    }

    @FXML
    public void eliminarEmpresa(ActionEvent event) {
        Empresa empresa = tableEmpresas.getSelectionModel().getSelectedItem();
        if (empresa == null) {
            // Opcionalmente mostrar un mensaje de error al usuario
            return;
        }

        try (Connection conexion = ConnectionDB.getConnection()) {
            Statement statement = conexion.createStatement();
            statement.executeUpdate("DELETE FROM empresas WHERE id_empresa = " + empresa.getId_empresa());

            loadEmpresasData();

        } catch (SQLException e) {
            e.printStackTrace();
            // Opcionalmente mostrar un mensaje de error al usuario
        }
    }

    @FXML
    private void seleccionar(MouseEvent event) {
        Empresa empresa = tableEmpresas.getSelectionModel().getSelectedItem();

        if (empresa != null) {
            textFieldCodigoEmpresa.setText(empresa.getCodigo_empresa());
            textFieldCIF.setText(empresa.getCif());
            textFieldNombre.setText(empresa.getNombre_empresa());
            textFieldDireccion.setText(empresa.getDireccion());
            textFieldCP.setText(empresa.getCp());
            textFieldLocalidad.setText(empresa.getLocalidad());
            comboBoxJornada.setValue(empresa.getTipo_jornada());
            comboBoxModalidad.setValue(empresa.getModalidad());
            textFieldMail.setText(empresa.getMail());
            textFieldDNIRepLegal.setText(empresa.getDni_responsable());
            textFieldNombreRL.setText(empresa.getNombre_responsable());
            textFieldApellidosRL.setText(empresa.getApellidos_responsable());
            textFieldDNITutLab.setText(empresa.getDni_tutor_laboral());
            textFieldNombreTL.setText(empresa.getNombre_tutor_laboral());
            textFieldApellidosTL.setText(empresa.getApellidos_tutor_laboral());
            textFieldTelefonoTL.setText(empresa.getTelefono_tutor_laboral());
        }
    }

    @FXML
    private void modificarPersona(ActionEvent event){
        Empresa empresa = tableEmpresas.getSelectionModel().getSelectedItem();
        if (empresa == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("No se ha seleccionado ninguna empresa");
            alert.showAndWait();
        }
        String dniRepLegal = textFieldDNIRepLegal.getText();
        String nombreRL = textFieldNombreRL.getText();
        String apellidosRL = textFieldApellidosRL.getText();
        String dniTutLab = textFieldDNITutLab.getText();
        String nombreTL = textFieldNombreTL.getText();
        String apellidosTL = textFieldApellidosTL.getText();
        String telefonoTL = textFieldTelefonoTL.getText();

        try (Connection conexion = ConnectionDB.getConnection()) {
            Statement statement = conexion.createStatement();
            statement.executeUpdate("UPDATE empresas SET dni_responsable = '" + dniRepLegal + "', nombre_responsable = '" + nombreRL + "', apellidos_responsable = '" + apellidosRL + "', dni_tutor_laboral = '" + dniTutLab + "', nombre_tutor_laboral = '" + nombreTL + "', apellidos_tutor_laboral = '" + apellidosTL + "', telefono_tutor_laboral = '" + telefonoTL + "' WHERE id_empresa = " + empresa.getId_empresa());

            loadEmpresasData();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void eliminarPersona(ActionEvent event){
        Empresa empresa = tableEmpresas.getSelectionModel().getSelectedItem();
        if (empresa == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("No se ha seleccionado ninguna empresa");
            alert.showAndWait();
        }

        try (Connection conexion = ConnectionDB.getConnection()) {
            Statement statement = conexion.createStatement();
            statement.executeUpdate("UPDATE empresas SET dni_responsable = '" + "" + "', nombre_responsable = '" + "" + "', apellidos_responsable = '" + "" + "', dni_tutor_laboral = '" + "" + "', nombre_tutor_laboral = '" + "" + "', apellidos_tutor_laboral = '" + "" + "', telefono_tutor_laboral = '" + "" + "' WHERE id_empresa = " + empresa.getId_empresa());


            loadEmpresasData();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

