package vista;

import javax.swing.*;
import java.awt.*;
import model.Usuario;

/**
 * Vista de la Ventana Principal - Muestra pantalla de carga mientras se cargan
 * las peliculas con barra de progreso animada.
 */
public class VentanaPrincipalVista extends JFrame {

    private Usuario usuario;
    private JPanel mainPanel;
    private JLabel loadingLabel;
    private JProgressBar progressBar;
    private Timer animacionTimer;

    public VentanaPrincipalVista(Usuario usuario) {
        this.usuario = usuario;
        inicializarComponentes();
        establecerPropiedadesVentana();
        iniciarAnimacion();
    }

    private void inicializarComponentes() {
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.setBackground(Color.WHITE);

        // Barra superior con botones de control
        JPanel topBar = crearBarraSuperior();
        containerPanel.add(topBar, BorderLayout.NORTH);

        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 20, 0, 20);

        // Icono de carga - reloj de arena
        JLabel spinnerLabel = new JLabel("⏳");
        spinnerLabel.setFont(new Font("Arial", Font.PLAIN, 40));
        mainPanel.add(spinnerLabel, gbc);

        gbc.gridy = 1;
        loadingLabel = new JLabel("Cargando peliculas", SwingConstants.CENTER);
        loadingLabel.setFont(new Font("Arial", Font.BOLD, 18));
        loadingLabel.setForeground(new Color(0, 122, 255));
        mainPanel.add(loadingLabel, gbc);

        gbc.gridy = 2;
        JLabel messageLabel = new JLabel("Por favor espere mientras cargamos las peliculas...");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        messageLabel.setForeground(Color.GRAY);
        mainPanel.add(messageLabel, gbc);

        // Agregar barra de progreso
        gbc.gridy = 3;
        gbc.insets = new Insets(30, 40, 20, 40);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setPreferredSize(new Dimension(300, 25));
        progressBar.setStringPainted(true);
        progressBar.setString("Cargando...");
        progressBar.setForeground(new Color(0, 122, 255));
        mainPanel.add(progressBar, gbc);

        containerPanel.add(mainPanel, BorderLayout.CENTER);
        add(containerPanel);
    }

    private JPanel crearBarraSuperior() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.WHITE);
        topBar.setPreferredSize(new Dimension(0, 40));
        topBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));

        JLabel titleLabel = new JLabel("Cargando");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        topBar.add(titleLabel, BorderLayout.WEST);

        JPanel controlButtonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        controlButtonsPanel.setBackground(Color.WHITE);

        JButton minimizeButton = crearBotonControlVentana(new Color(255, 189, 68), "−");
        minimizeButton.addActionListener(e -> setState(JFrame.ICONIFIED));

        JButton maximizeButton = crearBotonControlVentana(new Color(39, 201, 63), "+");
        maximizeButton.addActionListener(e -> {
            if (getExtendedState() == JFrame.MAXIMIZED_BOTH) {
                setExtendedState(JFrame.NORMAL);
            } else {
                setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
        });

        JButton closeButton = crearBotonControlVentana(new Color(255, 95, 86), "×");
        closeButton.addActionListener(e -> System.exit(0));

        controlButtonsPanel.add(minimizeButton);
        controlButtonsPanel.add(maximizeButton);
        controlButtonsPanel.add(closeButton);

        topBar.add(controlButtonsPanel, BorderLayout.EAST);

        return topBar;
    }

    private JButton crearBotonControlVentana(Color color, String symbol) {
        JButton button = new JButton(symbol);
        button.setPreferredSize(new Dimension(12, 12));
        button.setBackground(color);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setFocusPainted(false);
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        button.setFont(new Font("Arial", Font.BOLD, 8));
        button.setForeground(Color.WHITE);
        button.setMargin(new Insets(0, 0, 0, 0));
        return button;
    }

    /**
     * Inicia la animacion del texto de carga
     * Deshabilitada - texto fijo
     */
    private void iniciarAnimacion() {
        // Animacion deshabilitada - el texto permanece fijo como "Cargando peliculas"
    }

    /**
     * Detiene la animacion
     */
    public void detenerAnimacion() {
        if (animacionTimer != null) {
            animacionTimer.stop();
        }
    }

    @Override
    public void dispose() {
        detenerAnimacion();
        super.dispose();
    }

    private void establecerPropiedadesVentana() {
        setTitle("Cargando...");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    // Getters
    public Usuario getUsuario() {
        return usuario;
    }

    public void actualizarMensaje(String mensaje) {
        SwingUtilities.invokeLater(() -> {
            loadingLabel.setText(mensaje);
        });
    }
}
