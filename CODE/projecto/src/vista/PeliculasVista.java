package vista;

import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.util.List;
import model.Usuario;
import model.Pelicula;

/**
 * Vista de Peliculas - Responsable unicamente de mostrar la lista de peliculas.
 * No contiene logica de negocio.
 */
public class PeliculasVista extends JFrame {

    private Usuario usuario;
    private List<Pelicula> peliculasActuales;

    private JPanel mainPanel;
    private JPanel contentPanel;
    private JTextField searchField;
    private JButton searchButton;
    private JButton logoutButton;

    public PeliculasVista(Usuario usuario, List<Pelicula> peliculas, boolean esPrimerLogin) {
        this.usuario = usuario;
        this.peliculasActuales = peliculas;

        inicializarComponentes();
        mostrarPeliculas();
        establecerPropiedadesVentana();
    }

    private void inicializarComponentes() {
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.setBackground(Color.WHITE);

        // Barra de control superior
        JPanel controlBar = crearBarraControl();
        containerPanel.add(controlBar, BorderLayout.NORTH);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        JPanel topBar = crearBarraSuperior();
        mainPanel.add(topBar, BorderLayout.NORTH);

        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        containerPanel.add(mainPanel, BorderLayout.CENTER);
        add(containerPanel);
    }

    private JPanel crearBarraControl() {
        JPanel controlBar = new JPanel(new BorderLayout());
        controlBar.setBackground(Color.WHITE);
        controlBar.setPreferredSize(new Dimension(0, 40));
        controlBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));

        String nombreCompleto = usuario.getNombre() + " " + usuario.getApellido();
        JLabel titleLabel = new JLabel("Plataforma de Streaming - " + nombreCompleto);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        controlBar.add(titleLabel, BorderLayout.WEST);

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

        controlBar.add(controlButtonsPanel, BorderLayout.EAST);

        return controlBar;
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

    private JPanel crearBarraSuperior() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.WHITE);
        topBar.setPreferredSize(new Dimension(0, 160));
        topBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(25, 40, 25, 40)));

        // Panel superior con usuario y boton cerrar sesion
        JPanel topRowPanel = new JPanel(new BorderLayout());
        topRowPanel.setBackground(Color.WHITE);

        // Usuario a la izquierda - Mostrar nombre completo
        String nombreCompleto = usuario.getNombre() + " " + usuario.getApellido();
        JLabel userLabel = new JLabel(nombreCompleto);
        userLabel.setFont(new Font("Arial", Font.BOLD, 15));

        // Boton cerrar sesion a la derecha
        logoutButton = new JButton("Cerrar Sesion");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 13));
        logoutButton.setBackground(new Color(0, 122, 255));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setOpaque(true);
        logoutButton.setBorderPainted(false);
        logoutButton.setFocusPainted(false);
        logoutButton.setPreferredSize(new Dimension(140, 35));

        topRowPanel.add(userLabel, BorderLayout.WEST);
        topRowPanel.add(logoutButton, BorderLayout.EAST);

        // Panel inferior con titulo y buscador
        JPanel bottomRowPanel = new JPanel(new BorderLayout(30, 0));
        bottomRowPanel.setBackground(Color.WHITE);
        bottomRowPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        // Titulo a la izquierda
        JLabel titleLabel = new JLabel("Bienvenido a la Plataforma de Streaming");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));

        // Buscador a la derecha
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 5));
        searchPanel.setBackground(Color.WHITE);

        searchField = new JTextField(25);
        searchField.setPreferredSize(new Dimension(300, 38));
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 1, 2, 1, Color.BLACK),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));

        searchButton = new JButton("Buscar");
        searchButton.setFont(new Font("Arial", Font.BOLD, 14));
        searchButton.setBackground(new Color(0, 122, 255));
        searchButton.setForeground(Color.WHITE);
        searchButton.setOpaque(true);
        searchButton.setBorderPainted(false);
        searchButton.setFocusPainted(false);
        searchButton.setPreferredSize(new Dimension(100, 38));
        searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        bottomRowPanel.add(titleLabel, BorderLayout.WEST);
        bottomRowPanel.add(searchPanel, BorderLayout.EAST);

        // Panel principal con ambas filas
        JPanel mainTopPanel = new JPanel();
        mainTopPanel.setLayout(new BoxLayout(mainTopPanel, BoxLayout.Y_AXIS));
        mainTopPanel.setBackground(Color.WHITE);

        topRowPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        bottomRowPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        mainTopPanel.add(topRowPanel);
        mainTopPanel.add(Box.createVerticalStrut(12));
        mainTopPanel.add(bottomRowPanel);

        topBar.add(mainTopPanel, BorderLayout.CENTER);

        return topBar;
    }

    public void mostrarPeliculas() {
        contentPanel.removeAll();

        JPanel peliculasPanel = new JPanel(new BorderLayout());
        peliculasPanel.setBackground(Color.WHITE);
        peliculasPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Mensaje de bienvenida
        String mensajeSeccion = "Seguro viste alguna de estas peliculas, haznos saber que te parecio dejando una resena";
        JLabel mensajeLabel = new JLabel(mensajeSeccion);
        mensajeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        mensajeLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        peliculasPanel.add(mensajeLabel, BorderLayout.NORTH);

        // Panel para la tabla
        JPanel tablaPanel = new JPanel();
        tablaPanel.setLayout(new BoxLayout(tablaPanel, BoxLayout.Y_AXIS));
        tablaPanel.setBackground(Color.WHITE);

        // Crear encabezado
        JPanel headerPanel = crearEncabezadoTabla();
        headerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        tablaPanel.add(headerPanel);

        // Crear filas de peliculas
        for (Pelicula pelicula : peliculasActuales) {
            JPanel filaPanel = crearFilaPelicula(pelicula);
            filaPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            tablaPanel.add(filaPanel);
        }

        JScrollPane scrollPane = new JScrollPane(tablaPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        peliculasPanel.add(scrollPane, BorderLayout.CENTER);

        contentPanel.add(peliculasPanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private JPanel crearEncabezadoTabla() {
        JPanel headerPanel = new JPanel(new GridBagLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(200, 200, 200)));
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.gridy = 0;

        // Poster
        gbc.gridx = 0;
        gbc.weightx = 0.08;
        JLabel posterLabel = new JLabel("Poster");
        posterLabel.setFont(new Font("Arial", Font.BOLD, 13));
        headerPanel.add(posterLabel, gbc);

        // Titulo con botones de ordenamiento
        gbc.gridx = 1;
        gbc.weightx = 0.15;
        JPanel tituloPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        tituloPanel.setBackground(Color.WHITE);
        
        JLabel tituloLabel = new JLabel("Titulo");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 13));
        tituloPanel.add(tituloLabel);
        
        JPanel botonesPanel = new JPanel();
        botonesPanel.setLayout(new BoxLayout(botonesPanel, BoxLayout.Y_AXIS));
        botonesPanel.setBackground(Color.WHITE);
        
        JButton btnAscTitulo = new JButton("▲");
        btnAscTitulo.setFont(new Font("Arial", Font.PLAIN, 8));
        btnAscTitulo.setMargin(new Insets(0, 2, 0, 2));
        btnAscTitulo.setPreferredSize(new Dimension(16, 12));
        btnAscTitulo.addActionListener(e -> ordenarPorTitulo(true));
        
        JButton btnDescTitulo = new JButton("▼");
        btnDescTitulo.setFont(new Font("Arial", Font.PLAIN, 8));
        btnDescTitulo.setMargin(new Insets(0, 2, 0, 2));
        btnDescTitulo.setPreferredSize(new Dimension(16, 12));
        btnDescTitulo.addActionListener(e -> ordenarPorTitulo(false));
        
        botonesPanel.add(btnAscTitulo);
        botonesPanel.add(btnDescTitulo);
        tituloPanel.add(botonesPanel);
        
        headerPanel.add(tituloPanel, gbc);

        // Genero con botones de ordenamiento
        gbc.gridx = 2;
        gbc.weightx = 0.15;
        JPanel generoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        generoPanel.setBackground(Color.WHITE);
        
        JLabel generoLabel = new JLabel("Genero");
        generoLabel.setFont(new Font("Arial", Font.BOLD, 13));
        generoPanel.add(generoLabel);
        
        JPanel botonesGeneroPanel = new JPanel();
        botonesGeneroPanel.setLayout(new BoxLayout(botonesGeneroPanel, BoxLayout.Y_AXIS));
        botonesGeneroPanel.setBackground(Color.WHITE);
        
        JButton btnAscGenero = new JButton("▲");
        btnAscGenero.setFont(new Font("Arial", Font.PLAIN, 8));
        btnAscGenero.setMargin(new Insets(0, 2, 0, 2));
        btnAscGenero.setPreferredSize(new Dimension(16, 12));
        btnAscGenero.addActionListener(e -> ordenarPorGenero(true));
        
        JButton btnDescGenero = new JButton("▼");
        btnDescGenero.setFont(new Font("Arial", Font.PLAIN, 8));
        btnDescGenero.setMargin(new Insets(0, 2, 0, 2));
        btnDescGenero.setPreferredSize(new Dimension(16, 12));
        btnDescGenero.addActionListener(e -> ordenarPorGenero(false));
        
        botonesGeneroPanel.add(btnAscGenero);
        botonesGeneroPanel.add(btnDescGenero);
        generoPanel.add(botonesGeneroPanel);
        
        headerPanel.add(generoPanel, gbc);

        // Resumen
        gbc.gridx = 3;
        gbc.weightx = 0.42;
        JLabel resumenLabel = new JLabel("Resumen");
        resumenLabel.setFont(new Font("Arial", Font.BOLD, 13));
        headerPanel.add(resumenLabel, gbc);

        // Boton
        gbc.gridx = 4;
        gbc.weightx = 0.2;
        JLabel accionLabel = new JLabel("");
        headerPanel.add(accionLabel, gbc);

        return headerPanel;
    }
    
    private void ordenarPorTitulo(boolean ascendente) {
        peliculasActuales.sort((p1, p2) -> {
            String t1 = p1.getMetadatos().getTitulo();
            String t2 = p2.getMetadatos().getTitulo();
            return ascendente ? t1.compareToIgnoreCase(t2) : t2.compareToIgnoreCase(t1);
        });
        mostrarPeliculas();
    }
    
    private void ordenarPorGenero(boolean ascendente) {
        peliculasActuales.sort((p1, p2) -> {
            String g1 = p1.getGeneros().isEmpty() ? "" : p1.getGeneros().get(0);
            String g2 = p2.getGeneros().isEmpty() ? "" : p2.getGeneros().get(0);
            return ascendente ? g1.compareToIgnoreCase(g2) : g2.compareToIgnoreCase(g1);
        });
        mostrarPeliculas();
    }

    private JPanel crearFilaPelicula(Pelicula pelicula) {
        JPanel filaPanel = new JPanel(new GridBagLayout());
        filaPanel.setBackground(Color.WHITE);
        filaPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));
        filaPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 0;

        // Poster
        gbc.gridx = 0;
        gbc.weightx = 0.08;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel posterLabel = new JLabel();
        posterLabel.setPreferredSize(new Dimension(60, 80));
        posterLabel.setHorizontalAlignment(SwingConstants.CENTER);
        posterLabel.setVerticalAlignment(SwingConstants.CENTER);

        String posterUrl = pelicula.getPoster();
        if (posterUrl != null && !posterUrl.isEmpty() && !posterUrl.equals("N/A")) {
            try {
                ImageIcon icon = new ImageIcon(new URI(posterUrl).toURL());
                Image img = icon.getImage().getScaledInstance(60, 80, Image.SCALE_SMOOTH);
                posterLabel.setIcon(new ImageIcon(img));
            } catch (Exception e) {
                posterLabel.setOpaque(true);
                posterLabel.setBackground(new Color(255, 255, 153));
                posterLabel.setText("<html><center>Imagen No<br>Disponible</center></html>");
                posterLabel.setFont(new Font("Arial", Font.PLAIN, 9));
                posterLabel.setForeground(Color.BLACK);
            }
        } else {
            posterLabel.setOpaque(true);
            posterLabel.setBackground(new Color(255, 255, 153));
            posterLabel.setText("<html><center>Imagen No<br>Disponible</center></html>");
            posterLabel.setFont(new Font("Arial", Font.PLAIN, 9));
            posterLabel.setForeground(Color.BLACK);
        }
        filaPanel.add(posterLabel, gbc);

        // Titulo
        gbc.gridx = 1;
        gbc.weightx = 0.15;
        gbc.insets = new Insets(5, 20, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        JLabel tituloLabel = new JLabel(pelicula.getMetadatos().getTitulo());
        tituloLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        filaPanel.add(tituloLabel, gbc);

        // Genero
        gbc.gridx = 1;
        gbc.weightx = 0.15;
        gbc.insets = new Insets(5, 300, 5, 10);
        String genero = pelicula.getGeneros().isEmpty() ? "" : pelicula.getGeneros().get(0);
        JLabel generoLabel = new JLabel(genero);
        generoLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        filaPanel.add(generoLabel, gbc);

        // Resumen con limite de 60 caracteres por linea
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.insets = new Insets(5, 400, 5, 10);
        gbc.fill = GridBagConstraints.BOTH;
        String resumen = pelicula.getMetadatos().getSipnosis();
        if (resumen == null || resumen.isEmpty()) {
            resumen = "No se encuentra disponible";
        }
        
        // Limitar a 180 caracteres (3 lineas de 60)
        if (resumen.length() > 180) {
            resumen = resumen.substring(0, 177) + "...";
        }
        
        // Usar HTML con ancho fijo para forzar saltos de linea cada 60 caracteres aproximadamente
        String resumenHtml = "<html><div style='width:400px;'>" + resumen + "</div></html>";
        JLabel resumenLabel = new JLabel(resumenHtml);
        resumenLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        resumenLabel.setVerticalAlignment(SwingConstants.TOP);
        filaPanel.add(resumenLabel, gbc);

        // Boton Calificar (a la derecha del resumen)
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.15;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 10, 5, 10);
        JButton calificarButton = new JButton("Calificar");
        calificarButton.setFont(new Font("Arial", Font.BOLD, 12));
        calificarButton.setBackground(new Color(0, 122, 255));
        calificarButton.setForeground(Color.WHITE);
        calificarButton.setOpaque(true);
        calificarButton.setBorderPainted(false);
        calificarButton.setFocusPainted(false);
        calificarButton.setPreferredSize(new Dimension(100, 30));
        calificarButton.putClientProperty("pelicula", pelicula);
        filaPanel.add(calificarButton, gbc);

        // Resetear gridy para otros componentes
        gbc.gridy = 0;
        filaPanel.add(calificarButton, gbc);

        return filaPanel;
    }

    private void establecerPropiedadesVentana() {
        String nombreCompleto = usuario.getNombre() + " " + usuario.getApellido();
        setTitle("Plataforma de Streaming - " + nombreCompleto);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(1100, 700));
    }

    // Getters para el Controlador
    public Usuario getUsuario() {
        return usuario;
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JButton getLogoutButton() {
        return logoutButton;
    }

    public String getSearchTerm() {
        return searchField.getText().trim();
    }
}
