module com.example.proyectoentornos {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens model to javafx.base;
    opens controller to javafx.fxml;
    exports controller;


}