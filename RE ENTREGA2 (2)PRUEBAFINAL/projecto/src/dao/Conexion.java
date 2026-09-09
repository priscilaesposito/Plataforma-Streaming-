package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.URISyntaxException;

public class Conexion {
    private static final String DB_FILE = System.getProperty(
        "streaming.db.path", "plataforma_streaming.db");
    private static final Path DB_PATH = obtenerRutaBaseDatos();
    private static final String DB_URL = "jdbc:sqlite:" + DB_PATH;

    private static Connection connection = null;

    private static Path obtenerRutaBaseDatos() {
        Path rutaConfigurada = Paths.get(DB_FILE);
        if (rutaConfigurada.isAbsolute()) {
            return rutaConfigurada.normalize();
        }

        try {
            Path ubicacionClases = Paths.get(Conexion.class.getProtectionDomain()
                    .getCodeSource().getLocation().toURI());
            Path carpetaProyecto = ubicacionClases.getFileName().toString().equals("bin")
                    ? ubicacionClases.getParent()
                    : ubicacionClases;
            return carpetaProyecto.resolve(rutaConfigurada).toAbsolutePath().normalize();
        } catch (URISyntaxException | NullPointerException e) {
            return rutaConfigurada.toAbsolutePath().normalize();
        }
    }

    //DRIVER
    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Driver JDBC de SQLite no encontrado: " + e.getMessage());
            throw new RuntimeException("Driver JDBC no encontrado", e);
        }
    }
    
   
    public static synchronized Connection conectar() throws SQLException {
        if (connection == null || connection.isClosed()) {
            //CREA NUEVA
            connection = DriverManager.getConnection(DB_URL);
        }
        return connection;
    }

    
    public static synchronized void desconectar() {
        try {
             if (connection != null && !connection.isClosed()) {
                connection.close();
                connection = null; 
                System.out.println("Conexion a la base de datos cerrada.");
            }
        } catch (SQLException e) {
            System.out.println("Error al desconectar de la base de datos SQLite: " + e.getMessage());
        }
    }
}

