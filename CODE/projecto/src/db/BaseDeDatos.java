// Archivo: src/db/BaseDeDatos.java
package db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import dao.Conexion;

public class BaseDeDatos {

    public static Connection conectar() throws SQLException {
        return Conexion.conectar();
    }

    public static void inicializarBaseDeDatos() {
        try (Connection connection = conectar();
             Statement stmt = connection.createStatement()) {

            String sqlDatosPersonales = "CREATE TABLE IF NOT EXISTS DATOS_PERSONALES (" +
                    "ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    "NOMBRES TEXT (100) NOT NULL, " +
                    "APELLIDO TEXT (100) NOT NULL, " +
                    "DNI INTEGER NOT NULL" +
                    ");";
            stmt.executeUpdate(sqlDatosPersonales);

            String sqlPelicula = "CREATE TABLE IF NOT EXISTS PELICULA (" +
                    "ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    "GENERO TEXT (1) NOT NULL, " +
                    "TITULO TEXT (100) NOT NULL, " +
                    "RESUMEN TEXT (500), " +
                    "DIRECTOR TEXT (100) NOT NULL, " +
                    "DURACION REAL NOT NULL, " +
                    "RATING_PROMEDIO REAL, " +
                    "CANTIDAD_VOTOS INTEGER DEFAULT 0, " +
                    "ANIO INTEGER, " +
                    "POSTER TEXT (500)" +
                    ");";
            stmt.executeUpdate(sqlPelicula);
            
            // Agregar columna CANTIDAD_VOTOS si no existe (para bases de datos existentes)
            try {
                stmt.executeUpdate("ALTER TABLE PELICULA ADD COLUMN CANTIDAD_VOTOS INTEGER DEFAULT 0");
            } catch (SQLException e) {
                // Columna ya existe, ignorar
            }

            String sqlUsuario = "CREATE TABLE IF NOT EXISTS USUARIO (" +
                    "ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    "NOMBRE_USUARIO TEXT NOT NULL, " +
                    "EMAIL TEXT NOT NULL, " +
                    "CONTRASENIA TEXT NOT NULL, " +
                    "ID_DATOS_PERSONALES INTEGER NOT NULL, " +
                    "CONSTRAINT USUARIO_DATOS_PERSONALES_FK FOREIGN KEY (ID_DATOS_PERSONALES) " + 
                    "REFERENCES DATOS_PERSONALES (ID)" +
                    ");";
            stmt.executeUpdate(sqlUsuario);

            String sqlResenia = "CREATE TABLE IF NOT EXISTS RESENIA (" +
                    "ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    "CALIFICACION INTEGER NOT NULL, " +
                    "COMENTARIO TEXT(500), " +
                    "APROBADO INTEGER DEFAULT (1) NOT NULL, " +
                    "FECHA_HORA DATETIME NOT NULL, " +
                    "ID_USUARIO INTEGER NOT NULL, " +
                    "ID_PELICULA INTEGER NOT NULL, " +
                    "CONSTRAINT RESENIA_USUARIO_FK FOREIGN KEY (ID_USUARIO) " +
                    "REFERENCES USUARIO(ID), " +
                    "CONSTRAINT RESENIA_PELICULA_FK FOREIGN KEY (ID_PELICULA) " +
                    "REFERENCES PELICULA (ID)" +
                    ");";
            stmt.executeUpdate(sqlResenia);
            
            // Tabla para tracking de primer login
            String sqlPrimerLogin = "CREATE TABLE IF NOT EXISTS PRIMER_LOGIN (" +
                    "ID_USUARIO INTEGER NOT NULL PRIMARY KEY, " +
                    "FECHA_PRIMER_LOGIN DATETIME NOT NULL, " +
                    "CONSTRAINT PRIMER_LOGIN_USUARIO_FK FOREIGN KEY (ID_USUARIO) " +
                    "REFERENCES USUARIO(ID)" +
                    ");";
            stmt.executeUpdate(sqlPrimerLogin);
            
            // Agregar columnas faltantes a la tabla PELICULA si no existen
            agregarColumnaSiNoExiste(connection, "PELICULA", "RATING_PROMEDIO", "REAL");
            agregarColumnaSiNoExiste(connection, "PELICULA", "ANIO", "INTEGER");
            agregarColumnaSiNoExiste(connection, "PELICULA", "POSTER", "TEXT(500)");

        } catch (SQLException e) {
            System.out.println("Error al crear las tablas: " + e.getMessage());
        }
    }
    
    private static void agregarColumnaSiNoExiste(Connection connection, String tabla, String columna, String tipo) {
        try (Statement stmt = connection.createStatement()) {
            // Intentar agregar la columna
            String sql = "ALTER TABLE " + tabla + " ADD COLUMN " + columna + " " + tipo;
            stmt.executeUpdate(sql);
            System.out.println("Columna " + columna + " agregada a la tabla " + tabla);
        } catch (SQLException e) {
            // Si la columna ya existe, SQLite lanzara una excepcion - esto es normal
            if (e.getMessage().contains("duplicate column name")) {
                // Columna ya existe, no hacer nada
            } else {
                System.out.println("Error al agregar columna " + columna + ": " + e.getMessage());
            }
        }
    }
}
