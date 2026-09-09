package app;

import java.sql.SQLException;

import dao.Conexion;
import db.BaseDeDatos;
import controlador.LoginControlador;

public class Main {

    public static void main(String[] args) throws Exception {
        try {
            // Inicializar base de datos
            Conexion.conectar();
            BaseDeDatos.inicializarBaseDeDatos();

            //cerrar la conexion al salir
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Cerrando aplicacion...");
                Conexion.desconectar();
            }));

            System.out.println("Iniciando interfaz grafica...");
            // Iniciar interfaz grafica
            LoginControlador.iniciarLogin();

        } catch (SQLException e) {
            System.err.println("Error al iniciar la aplicacion: La conexion a la BD fallo.");
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

}
