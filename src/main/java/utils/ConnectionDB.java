package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    private static final String URL = "jdbc:mysql://localhost:3306/ProyectoEntornosDB";
    private static final String USER = "root";
    private static final String PASSWORD = "1daw";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Error al establecer la conexión con la base de datos: " + e.getMessage());
            throw new RuntimeException("Error al conectar a la base de datos", e);
        }
        return connection;
    }

}
