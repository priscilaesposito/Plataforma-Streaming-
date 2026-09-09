package controlador;

import vista.LoginVista;
import model.Usuario;
import app.Logica;
import enumerativo.UsuarioInvalidoException;

import javax.swing.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controlador del Login - Maneja la logica de eventos y coordina entre la Vista
 * y el Modelo.
 */
public class LoginControlador {

    private LoginVista vista;

    public LoginControlador(LoginVista vista) {
        this.vista = vista;
        configurarEventos();
    }

    private void configurarEventos() {
        // Evento del boton de login
        vista.getLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarLogin();
            }
        });

        // Evento del boton de registro
        vista.getRegisterButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirVentanaRegistro();
            }
        });

        // Enter en el campo de password ejecuta login
        vista.getPasswordField().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarLogin();
            }
        });
    }

    private void realizarLogin() {
        String email = vista.getEmail();
        String password = vista.getPassword();

        // Validaciones basicas
        if (email.isEmpty() || password.isEmpty()) {
            mostrarMensajeError("E-mail y Password son campos obligatorios");
            return;
        }

        if (!esEmailValido(email)) {
            mostrarMensajeError("Formato de E-mail invalido");
            return;
        }

        // Intentar login
        try {
            vista.mostrarMensaje("Verificando credenciales...", Color.BLUE);

            Usuario usuario = Logica.login(email, password);

            if (usuario != null) {
                String nombreCompleto = usuario.getNombre() + " " + usuario.getApellido();
                mostrarMensajeExito("¡Bienvenido/a, " + nombreCompleto + "!");
                abrirVentanaPrincipal(usuario);
                vista.dispose();
            } else {
                mostrarMensajeError("E-mail o Password incorrectos");
            }

        } catch (UsuarioInvalidoException e) {
            // Manejar especificamente errores de usuario invalido
            String mensaje = "Usuario invalido: " + e.getMessage();
            if (e.getCampo() != null) {
                mensaje += "\nCampo problematico: " + e.getCampo();
            }
            mostrarMensajeError(mensaje);
        } catch (Exception ex) {
            mostrarMensajeError("Error de conexion: " + ex.getMessage());
        }
    }

    private void abrirVentanaRegistro() {
        RegistroControlador.iniciarRegistro();
    }

    private void abrirVentanaPrincipal(Usuario usuario) {
        VentanaPrincipalControlador.iniciarVentanaPrincipal(usuario);
    }

    private boolean esEmailValido(String email) {
        return email.contains("@") && email.contains(".") && email.length() > 5;
    }

    private void mostrarMensajeError(String mensaje) {
        vista.mostrarMensaje(mensaje, Color.RED);
        // Hacer que el mensaje desaparezca despues de 5 segundos
        Timer timer = new Timer(5000, e -> vista.limpiarMensaje());
        timer.setRepeats(false);
        timer.start();
    }

    private void mostrarMensajeExito(String mensaje) {
        vista.mostrarMensaje(mensaje, new Color(0, 150, 0));
    }

    /**
     * Metodo estatico para iniciar la interfaz de login
     */
    public static void iniciarLogin() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    // Usar el Look and Feel por defecto si falla
                }

                LoginVista vista = new LoginVista();
                new LoginControlador(vista);
                vista.setVisible(true);
            }
        });
    }
}
