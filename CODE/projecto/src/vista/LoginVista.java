package vista;

import javax.swing.*;
import java.awt.*;

/**
 * Vista del Login - Responsable unicamente de la presentacion de la interfaz.
 */
public class LoginVista extends JFrame {

    // Componentes de la interfaz
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JLabel statusLabel;
    private JPanel mainPanel;

    public LoginVista() {
        inicializarComponentes();
        configurarDisenio();
        establecerPropiedadesVentana();
    }

    private void inicializarComponentes() {
        emailField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Ingresar");
        registerButton = new JButton("Registrate");
        statusLabel = new JLabel(" ");

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));
    }

    private void configurarDisenio() {
        JPanel topBar = crearBarraSuperior();
        JPanel leftPanel = crearPanelImagen();
        JPanel rightPanel = crearPanelLogin();

        JPanel bottomContainer = new JPanel(new BorderLayout());
        bottomContainer.add(leftPanel, BorderLayout.CENTER);
        bottomContainer.add(rightPanel, BorderLayout.EAST);

        mainPanel.add(topBar, BorderLayout.NORTH);
        mainPanel.add(bottomContainer, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JPanel crearBarraSuperior() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.WHITE);
        topBar.setPreferredSize(new Dimension(0, 50));
        topBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)));

        JLabel welcomeTitle = new JLabel("Bienvenido a la Plataforma de Streaming");
        welcomeTitle.setFont(new Font("Arial", Font.BOLD, 16));
        topBar.add(welcomeTitle, BorderLayout.WEST);

        JPanel controlButtonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        controlButtonsPanel.setBackground(Color.WHITE);

        JButton minimizeButton = crearBotonControlVentana(new Color(255, 189, 68));
        minimizeButton.setText("−");
        minimizeButton.setFont(new Font("Arial", Font.BOLD, 16));
        minimizeButton.setForeground(Color.WHITE);
        minimizeButton.addActionListener(e -> setState(JFrame.ICONIFIED));

        JButton maximizeButton = crearBotonControlVentana(new Color(39, 201, 63));
        maximizeButton.setText("□");
        maximizeButton.setFont(new Font("Arial", Font.BOLD, 12));
        maximizeButton.setForeground(Color.WHITE);
        maximizeButton.addActionListener(e -> {
            if (getExtendedState() == JFrame.MAXIMIZED_BOTH) {
                setExtendedState(JFrame.NORMAL);
            } else {
                setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
        });

        JButton closeButton = crearBotonControlVentana(new Color(0, 122, 255));
        closeButton.setText("×");
        closeButton.setFont(new Font("Arial", Font.BOLD, 14));
        closeButton.setForeground(Color.WHITE);
        closeButton.addActionListener(e -> System.exit(0));

        controlButtonsPanel.add(minimizeButton);
        controlButtonsPanel.add(maximizeButton);
        controlButtonsPanel.add(closeButton);

        topBar.add(controlButtonsPanel, BorderLayout.EAST);

        return topBar;
    }

    private JPanel crearPanelImagen() {
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBackground(new Color(255, 223, 186));
        imagePanel.setPreferredSize(new Dimension(600, 400));

        try {
            ImageIcon dogStreamingIcon = recursos.GestionRecursos.loadImageWithFallback("perrito.png", 500, 380);
            JLabel imageLabel = new JLabel(dogStreamingIcon);
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            imagePanel.add(imageLabel, BorderLayout.CENTER);
        } catch (Exception e) {
            JLabel placeholderLabel = new JLabel("🐕", SwingConstants.CENTER);
            placeholderLabel.setFont(new Font("Arial", Font.PLAIN, 100));
            imagePanel.add(placeholderLabel, BorderLayout.CENTER);
        }

        return imagePanel;
    }

    private JButton crearBotonControlVentana(Color color) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(12, 12));
        button.setBackground(color);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setFocusPainted(false);
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        button.setBorderPainted(false);

        return button;
    }

    private JPanel crearPanelLogin() {
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setPreferredSize(new Dimension(300, 400));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(new JLabel("E-mail:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        emailField.setPreferredSize(new Dimension(150, 30));
        loginPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        passwordField.setPreferredSize(new Dimension(150, 30));
        loginPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        loginButton.setBackground(new Color(51, 153, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setPreferredSize(new Dimension(120, 40));
        loginButton.setOpaque(true);
        loginButton.setBorderPainted(false);
        loginButton.setFocusPainted(false);
        loginPanel.add(loginButton, gbc);

        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel registerPanel = new JPanel(new FlowLayout());
        registerPanel.setBackground(Color.WHITE);
        registerPanel.add(new JLabel("¿Aun no sos usuario?"));

        registerButton.setBackground(new Color(51, 153, 255));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("Arial", Font.BOLD, 12));
        registerButton.setOpaque(true);
        registerButton.setBorderPainted(false);
        registerButton.setFocusPainted(false);
        registerPanel.add(registerButton);

        loginPanel.add(registerPanel, gbc);

        gbc.gridy = 4;
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        loginPanel.add(statusLabel, gbc);

        return loginPanel;
    }

    private void establecerPropiedadesVentana() {
        setTitle("Plataforma de Streaming - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    // Getters para que el Controlador acceda a los componentes
    public JTextField getEmailField() {
        return emailField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getRegisterButton() {
        return registerButton;
    }

    public JLabel getStatusLabel() {
        return statusLabel;
    }

    // Metodos para actualizar la vista desde el Controlador
    public void mostrarMensaje(String mensaje, Color color) {
        statusLabel.setText(mensaje);
        statusLabel.setForeground(color);
    }

    public void limpiarMensaje() {
        statusLabel.setText(" ");
    }

    public String getEmail() {
        return emailField.getText().trim();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }
}
