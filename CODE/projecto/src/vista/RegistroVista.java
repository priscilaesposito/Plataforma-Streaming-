package vista;

import javax.swing.*;
import java.awt.*;

/**
 * Vista del Registro - Responsable unicamente de la presentacion del
 * formulario.
 * No contiene logica de negocio ni validaciones.
 */
public class RegistroVista extends JFrame {

    // Campos del formulario
    private JTextField nombresField;
    private JTextField apellidosField;
    private JTextField dniField;
    private JTextField emailField;
    private JPasswordField passwordField;

    // Botones
    private JButton registrarButton;

    private JPanel mainPanel;

    public RegistroVista() {
        inicializarComponentes();
        configurarDisenio();
        establecerPropiedadesVentana();
    }

    private void inicializarComponentes() {
        nombresField = new JTextField(20);
        apellidosField = new JTextField(20);
        dniField = new JTextField(20);
        emailField = new JTextField(20);
        passwordField = new JPasswordField(20);

        registrarButton = new JButton("Registrar");

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
    }

    private void configurarDisenio() {
        JPanel topBar = crearBarraSuperior();

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JPanel formPanel = crearPanelFormulario();

        contentPanel.add(formPanel, BorderLayout.CENTER);

        mainPanel.add(topBar, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JPanel crearBarraSuperior() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.WHITE);
        topBar.setPreferredSize(new Dimension(0, 50));
        topBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)));

        JLabel title = new JLabel("Plataforma de Streaming - Registracion");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        topBar.add(title, BorderLayout.WEST);

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
        closeButton.addActionListener(e -> dispose());

        controlButtonsPanel.add(minimizeButton);
        controlButtonsPanel.add(maximizeButton);
        controlButtonsPanel.add(closeButton);

        topBar.add(controlButtonsPanel, BorderLayout.EAST);

        return topBar;
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

    private JPanel crearPanelFormulario() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 10, 12, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // Nombres
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel nombresLabel = new JLabel("Nombres:");
        nombresLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(nombresLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.WEST;
        nombresField.setPreferredSize(new Dimension(250, 30));
        formPanel.add(nombresField, gbc);
        row++;

        // Apellidos
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel apellidosLabel = new JLabel("Apellidos:");
        apellidosLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(apellidosLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.WEST;
        apellidosField.setPreferredSize(new Dimension(250, 30));
        formPanel.add(apellidosField, gbc);
        row++;

        // DNI
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel dniLabel = new JLabel("DNI:");
        dniLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(dniLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.WEST;
        dniField.setPreferredSize(new Dimension(250, 30));
        formPanel.add(dniField, gbc);
        row++;

        // E-mail
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel emailLabel = new JLabel("E-mail:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.WEST;
        emailField.setPreferredSize(new Dimension(250, 30));
        formPanel.add(emailField, gbc);
        row++;

        // Password
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.WEST;
        passwordField.setPreferredSize(new Dimension(250, 30));
        formPanel.add(passwordField, gbc);
        row++;

        // Espacio
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        formPanel.add(Box.createVerticalStrut(20), gbc);
        row++;

        // Boton Registrar
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        registrarButton.setBackground(new Color(0, 153, 255));
        registrarButton.setForeground(Color.WHITE);
        registrarButton.setFont(new Font("Arial", Font.BOLD, 14));
        registrarButton.setPreferredSize(new Dimension(150, 40));
        registrarButton.setOpaque(true);
        registrarButton.setBorderPainted(false);
        registrarButton.setFocusPainted(false);
        formPanel.add(registrarButton, gbc);

        return formPanel;
    }

    private void establecerPropiedadesVentana() {
        setTitle("Plataforma de Streaming - Registracion");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    // Getters para el Controlador
    public JTextField getNombresField() {
        return nombresField;
    }

    public JTextField getApellidosField() {
        return apellidosField;
    }

    public JTextField getDniField() {
        return dniField;
    }

    public JTextField getEmailField() {
        return emailField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JButton getRegistrarButton() {
        return registrarButton;
    }

    // Metodos para obtener valores
    public String getNombres() {
        return nombresField.getText().trim();
    }

    public String getApellidos() {
        return apellidosField.getText().trim();
    }

    public String getDni() {
        return dniField.getText().trim();
    }

    public String getEmail() {
        return emailField.getText().trim();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }
}
