package vista;

import javax.swing.*;
import java.awt.*;
import org.json.JSONObject;

public class ResultadoBusquedaVista extends JDialog {

    private JSONObject pelicula;

    public ResultadoBusquedaVista(JFrame parent, JSONObject pelicula) {
        super(parent, "Plataforma de Streaming - Informacion", true);
        this.pelicula = pelicula;

        inicializarComponentes();
        establecerPropiedades();
    }

    private void inicializarComponentes() {
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.setBackground(Color.WHITE);

        // Barra superior con botones de control
        JPanel topBar = crearBarraSuperior();
        containerPanel.add(topBar, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(Color.WHITE);

        // Titulo de la pelicula
        JLabel tituloLabel = new JLabel(pelicula.getString("Title"));
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(tituloLabel, BorderLayout.NORTH);

        // Panel de informacion
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Anio
        JLabel anioLabel = new JLabel("Anio: " + pelicula.optString("Year", "N/A"));
        anioLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        anioLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(anioLabel);
        infoPanel.add(Box.createVerticalStrut(10));

        // Resumen
        JLabel resumenTituloLabel = new JLabel("Resumen:");
        resumenTituloLabel.setFont(new Font("Arial", Font.BOLD, 13));
        resumenTituloLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(resumenTituloLabel);
        infoPanel.add(Box.createVerticalStrut(5));

        JTextArea resumenArea = new JTextArea(pelicula.optString("Plot", "No disponible"));
        resumenArea.setFont(new Font("Arial", Font.PLAIN, 13));
        resumenArea.setLineWrap(true);
        resumenArea.setWrapStyleWord(true);
        resumenArea.setEditable(false);
        resumenArea.setFocusable(false);
        resumenArea.setBackground(Color.WHITE);
        resumenArea.setBorder(null);
        resumenArea.setAlignmentX(Component.LEFT_ALIGNMENT);

        JScrollPane resumenScrollPane = new JScrollPane(resumenArea);
        resumenScrollPane.setBorder(BorderFactory.createEmptyBorder());
        resumenScrollPane.setPreferredSize(new Dimension(450, 110));
        resumenScrollPane.setMinimumSize(new Dimension(250, 70));
        resumenScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(resumenScrollPane);

        mainPanel.add(infoPanel, BorderLayout.CENTER);

        // Boton Continuar
        JButton continuarBtn = new JButton("Continuar");
        continuarBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        continuarBtn.setBackground(new Color(0, 122, 255));
        continuarBtn.setForeground(Color.WHITE);
        continuarBtn.setFocusPainted(false);
        continuarBtn.setOpaque(true);
        continuarBtn.setBorderPainted(false);
        continuarBtn.setPreferredSize(new Dimension(100, 30));
        continuarBtn.addActionListener(e -> dispose());

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.setBackground(Color.WHITE);
        btnPanel.add(continuarBtn);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        containerPanel.add(mainPanel, BorderLayout.CENTER);
        setContentPane(containerPanel);
    }

    private JPanel crearBarraSuperior() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.WHITE);
        topBar.setPreferredSize(new Dimension(0, 40));
        topBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)));

        JLabel titleLabel = new JLabel("Informacion de Pelicula");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        topBar.add(titleLabel, BorderLayout.WEST);

        // Panel de botones de control
        JPanel controlButtonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        controlButtonsPanel.setBackground(Color.WHITE);

        // Boton minimizar (amarillo)
        JButton minimizeButton = crearBotonControlVentana(new Color(255, 189, 68));
        minimizeButton.setText("−");
        minimizeButton.setFont(new Font("Arial", Font.BOLD, 16));
        minimizeButton.setForeground(Color.WHITE);
        minimizeButton.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window instanceof JFrame) {
                ((JFrame) window).setState(JFrame.ICONIFIED);
            }
        });

        // Boton maximizar (verde)
        JButton maximizeButton = crearBotonControlVentana(new Color(39, 201, 63));
        maximizeButton.setText("□");
        maximizeButton.setFont(new Font("Arial", Font.BOLD, 12));
        maximizeButton.setForeground(Color.WHITE);
        maximizeButton.setEnabled(false); // Los JDialog no se maximizan

        // Boton cerrar (azul)
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
        return button;
    }

    private void establecerPropiedades() {
        setSize(500, 320);
        setLocationRelativeTo(getParent());
        setMinimumSize(new Dimension(400, 260));
        setResizable(true);
    }

    public static void mostrar(JFrame parent, JSONObject pelicula) {
        SwingUtilities.invokeLater(() -> {
            ResultadoBusquedaVista dialog = new ResultadoBusquedaVista(parent, pelicula);
            dialog.setVisible(true);
        });
    }
}
