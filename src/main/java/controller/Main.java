package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/VistaPrincipal.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("CRUD JavaFX Application");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void start(String[] args) {
        launch(args);
    }
}
