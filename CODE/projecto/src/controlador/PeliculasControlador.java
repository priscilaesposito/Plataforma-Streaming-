package controlador;

import vista.PeliculasVista;
import vista.CalificarPeliculaVista;
import vista.ResultadoBusquedaVista;
import model.Usuario;
import model.Pelicula;
import app.Logica;
import enumerativo.PeliculaNoEncontradaException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Controlador de Peliculas - Maneja la logica de eventos de la interfaz de
 * peliculas.
 */
public class PeliculasControlador {

    private PeliculasVista vista;

    public PeliculasControlador(PeliculasVista vista) {
        this.vista = vista;
        configurarEventos();
    }

    private void configurarEventos() {
        // Configurar evento de busqueda
        vista.getSearchButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarPelicula();
            }
        });

        // Configurar evento de cerrar sesion
        vista.getLogoutButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarSesion();
            }
        });

        // Configurar eventos de calificacion para todos los botones
        configurarEventosCalificacion();
    }

    private void configurarEventosCalificacion() {
        // Buscar todos los botones de calificar en la vista
        SwingUtilities.invokeLater(() -> {
            buscarYConfigurarBotonesEnContenedor(vista.getContentPane());
        });
    }
    
    private void buscarYConfigurarBotonesEnContenedor(Container container) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                if ("Calificar".equals(button.getText())) {
                    // Remover listeners anteriores si existen
                    for (ActionListener al : button.getActionListeners()) {
                        button.removeActionListener(al);
                    }
                    
                    // Crear referencia final al boton para el ActionListener
                    final JButton finalButton = button;
                    
                    button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Pelicula pelicula = (Pelicula) finalButton.getClientProperty("pelicula");
                            if (pelicula != null) {
                                System.out.println("Abriendo ventana de calificacion para: " + pelicula.getMetadatos().getTitulo());
                                // Abrir ventana emergente de calificacion
                                boolean calificado = CalificarPeliculaVista.mostrar(
                                    vista, 
                                    pelicula, 
                                    vista.getUsuario()
                                );
                                
                                if (calificado) {
                                    // Actualizar el boton en el EDT
                                    SwingUtilities.invokeLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            finalButton.setEnabled(false);
                                            finalButton.setText("Calificada");
                                            finalButton.setBackground(Color.GRAY);
                                            finalButton.repaint();
                                        }
                                    });
                                }
                            } else {
                                System.err.println("ERROR: No se encontro la pelicula en el boton");
                            }
                        }
                    });
                }
            } else if (comp instanceof Container) {
                buscarYConfigurarBotonesEnContenedor((Container) comp);
            }
        }
    }

    private void buscarPelicula() {
        String termino = vista.getSearchTerm();

        if (termino.isEmpty()) {
            JOptionPane.showMessageDialog(vista,
                    "Por favor ingrese un termino de busqueda",
                    "Busqueda",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Mostrar dialogo de busqueda
        JDialog loadingDialog = new JDialog(vista, "Buscando...", false);
        loadingDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        loadingDialog.setSize(300, 100);
        loadingDialog.setLocationRelativeTo(vista);

        JLabel loadingMsg = new JLabel("Buscando en OMDb: " + termino + "...", SwingConstants.CENTER);
        loadingDialog.add(loadingMsg);
        loadingDialog.setVisible(true);

        // Usar Thread para busqueda asincrona
        Thread searchThread = new Thread(new Runnable() {
            @Override
            public void run() {
                org.json.JSONObject resultado = null;
                Exception error = null;

                try {
                    resultado = Logica.buscarPeliculaOMDb(termino);
                } catch (Exception e) {
                    error = e;
                }

                // Actualizar UI en el Event Dispatch Thread
                final org.json.JSONObject finalResultado = resultado;
                final Exception finalError = error;

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialog.dispose();

                        if (finalError != null) {
                            // Manejar errores
                            if (finalError instanceof PeliculaNoEncontradaException) {
                                JOptionPane.showMessageDialog(vista,
                                        "La pelicula '" + termino + "' no fue encontrada en OMDb.\n" +
                                        "Verifica que el titulo este escrito correctamente.",
                                        "Pelicula no encontrada",
                                        JOptionPane.INFORMATION_MESSAGE);
                            } else if (finalError instanceof java.io.IOException) {
                                JOptionPane.showMessageDialog(vista,
                                        "Error de conexion:\n" + finalError.getMessage() + "\n\n" +
                                        "Verifica tu conexion a Internet e intenta nuevamente.",
                                        "Sin conexion",
                                        JOptionPane.WARNING_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(vista,
                                        "Error inesperado al buscar la pelicula:\n" + finalError.getMessage(),
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        } else if (finalResultado != null) {
                            mostrarResultadoBusqueda(finalResultado);
                        } else {
                            JOptionPane.showMessageDialog(vista,
                                    "Pelicula no encontrada",
                                    "Sin resultados",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                });
            }
        });

        searchThread.start();
    }

    private void mostrarResultadoBusqueda(org.json.JSONObject pelicula) {
        // Usar ventana emergente de ResultadoBusquedaVista con datos de OMDb
        ResultadoBusquedaVista.mostrar(vista, pelicula);
    }

    private void cerrarSesion() {
        int confirm = JOptionPane.showConfirmDialog(vista,
                "¿Esta seguro que desea cerrar sesion?",
                "Cerrar Sesion",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            vista.dispose();
            LoginControlador.iniciarLogin();
        }
    }

    /**
     * Metodo estatico para iniciar la interfaz de peliculas
     */
    public static void iniciarPeliculas(Usuario usuario, List<Pelicula> peliculas, boolean esPrimerLogin) {
        iniciarPeliculasConCallback(usuario, peliculas, esPrimerLogin, null);
    }
    
    /**
     * Metodo estatico para iniciar la interfaz de peliculas con callback
     */
    public static void iniciarPeliculasConCallback(Usuario usuario, List<Pelicula> peliculas, boolean esPrimerLogin, Runnable onReady) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Crear la vista en el hilo de eventos de Swing
                PeliculasVista vista = new PeliculasVista(usuario, peliculas, esPrimerLogin);
                PeliculasControlador controlador = new PeliculasControlador(vista);
                
                // Hacer visible la ventana
                vista.setVisible(true);
                
                // Configurar eventos despues de que todo este renderizado
                SwingUtilities.invokeLater(() -> {
                    controlador.configurarEventosCalificacion();
                    
                    // Ejecutar callback cuando todo este listo
                    if (onReady != null) {
                        // Pequeno delay adicional para asegurar que la ventana este completamente renderizada
                        Timer readyTimer = new Timer(200, e -> onReady.run());
                        readyTimer.setRepeats(false);
                        readyTimer.start();
                    }
                });
            }
        });
    }
    
    // Hacer publico el metodo para poder llamarlo desde fuera
    public void reconfigurarEventos() {
        configurarEventosCalificacion();
    }
}
