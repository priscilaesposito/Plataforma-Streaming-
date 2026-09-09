package controlador;

import vista.VentanaPrincipalVista;
import model.Usuario;
import model.Pelicula;
import app.Logica;

import javax.swing.*;
import java.util.List;

/**
 * Controlador de la Ventana Principal - Maneja la logica de carga de peliculas.
 */
public class VentanaPrincipalControlador {

    private VentanaPrincipalVista vista;
    private boolean esPrimerLogin;
    private List<Pelicula> peliculasActuales;

    public VentanaPrincipalControlador(VentanaPrincipalVista vista) {
        this.vista = vista;
    }

    public void cargarPeliculasEnBackground() {
        // Usar Thread para carga asincrona
        Thread loadThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Verificar si es primer login
                    esPrimerLogin = Logica.esPrimerLogin(vista.getUsuario().getID_USUARIO());

                    // Cargar peliculas desde BD segun sea primer login o no
                    List<Pelicula> peliculas;
                    if (esPrimerLogin) {
                        peliculas = Logica.obtenerTop10Peliculas();
                    } else {
                        peliculas = Logica.obtener10PeliculasRandom(vista.getUsuario().getID_USUARIO());
                    }

                    peliculasActuales = peliculas;

                    // Actualizar UI en el Event Dispatch Thread
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                // Marcar como no primer login si era el primero
                                if (esPrimerLogin) {
                                    Logica.registrarPrimerLogin(vista.getUsuario().getID_USUARIO());
                                }
                                
                                // Crear la ventana de peliculas pero mantener la ventana de carga abierta
                                PeliculasControlador.iniciarPeliculasConCallback(
                                    vista.getUsuario(), 
                                    peliculasActuales, 
                                    esPrimerLogin,
                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            // Este callback se ejecuta cuando la ventana de peliculas esta lista
                                            vista.dispose();
                                        }
                                    }
                                );
                            } catch (Exception e) {
                                JOptionPane.showMessageDialog(vista,
                                        "Error al cargar peliculas: " + e.getMessage(),
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE);
                                e.printStackTrace();
                                vista.dispose();
                                LoginControlador.iniciarLogin();
                            }
                        }
                    });

                } catch (Exception e) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            JOptionPane.showMessageDialog(vista,
                                    "Error al cargar peliculas: " + e.getMessage(),
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            e.printStackTrace();
                            vista.dispose();
                            LoginControlador.iniciarLogin();
                        }
                    });
                }
            }
        });

        loadThread.start();
    }

    /**
     * Metodo estatico para iniciar la ventana principal
     */
    public static void iniciarVentanaPrincipal(Usuario usuario) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                VentanaPrincipalVista vista = new VentanaPrincipalVista(usuario);
                VentanaPrincipalControlador controlador = new VentanaPrincipalControlador(vista);
                vista.setVisible(true);

                // Cargar peliculas en segundo plano despues de un pequenio delay
                Timer timer = new Timer(100, e -> controlador.cargarPeliculasEnBackground());
                timer.setRepeats(false);
                timer.start();
            }
        });
    }
}
