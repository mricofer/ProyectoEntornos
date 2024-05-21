module com.example.proyectoentornos {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.proyectoentornos to javafx.fxml;
    exports com.example.proyectoentornos;
}