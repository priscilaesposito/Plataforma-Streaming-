package utilidades;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import dao.Conexion;
import model.Pelicula;
import daoJDBC.PeliculaDAOjdbc;

public class CargadorCSV {
    
    private static final String CSV_PATH = System.getProperty("user.home") + 
        "/PriEsposito/proyectoFinal/RE ENTREGA2 (2)PRUEBAFINAL/projecto/movies_database.csv";
    
    public static List<Pelicula> cargarPeliculasDesdeCSV() {
        System.out.println("Cargando peliculas desde movies_database.csv...");
        
        List<Pelicula> peliculasEnMemoria = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_PATH));
             Connection conn = Conexion.conectar()) {
            
            String line;
            boolean primeraLinea = true;
            int contador = 0;
            int yaExistentes = 0;
            final int LIMITE_PELICULAS = 10;
            
            // SQL para verificar si existe la pelicula
            String sqlCheck = "SELECT COUNT(*) FROM PELICULA WHERE TITULO = ? AND ANIO = ?";
            
            // SQL para insertar pelicula (ahora incluye CANTIDAD_VOTOS)
            String sql = "INSERT INTO PELICULA (GENERO, TITULO, RESUMEN, DIRECTOR, DURACION, RATING_PROMEDIO, CANTIDAD_VOTOS, ANIO, POSTER) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            while ((line = br.readLine()) != null && contador < LIMITE_PELICULAS) {
                // Saltar la primera linea si es cabecera
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }
                
                // Parsear linea CSV
                String[] datos = parsearLineaCSV(line);
                if (datos == null || datos.length < 9) {
                    continue;
                }
                
                try {
                    // Formato CSV: Release_Date,Title,Overview,Popularity,Vote_Count,Vote_Average,Original_Language,Genre,Poster_Url
                    String genero = datos[7]; // Genre
                    String titulo = datos[1]; // Title
                    String resumen = datos[2]; // Overview
                    String director = "Unknown"; // No hay director en este CSV
                    double duracion = 120.0; // Duracion por defecto
                    float ratingPromedio = Float.parseFloat(datos[5]); // Vote_Average
                    int cantidadVotos = Integer.parseInt(datos[4]); // Vote_Count
                    int anio = extraerAnio(datos[0]); // Release_Date (formato YYYY-MM-DD)
                    String poster = datos[8]; // Poster_Url
                    
                    // Verificar si la pelicula ya existe
                    boolean existe = false;
                    try (PreparedStatement pstmtCheck = conn.prepareStatement(sqlCheck)) {
                        pstmtCheck.setString(1, titulo);
                        pstmtCheck.setInt(2, anio);
                        try (ResultSet rs = pstmtCheck.executeQuery()) {
                            if (rs.next() && rs.getInt(1) > 0) {
                                existe = true;
                                yaExistentes++;
                            }
                        }
                    }
                    
                    // Solo insertar si no existe
                    if (!existe) {
                        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                            pstmt.setString(1, genero);
                            pstmt.setString(2, titulo);
                            pstmt.setString(3, resumen);
                            pstmt.setString(4, director);
                            pstmt.setDouble(5, duracion);
                            pstmt.setFloat(6, ratingPromedio);
                            pstmt.setInt(7, cantidadVotos);
                            pstmt.setInt(8, anio);
                            pstmt.setString(9, poster);
                            
                            pstmt.executeUpdate();
                            contador++;
                            
                            System.out.println("  [" + contador + "] Cargada: " + titulo + 
                                             " (Rating: " + ratingPromedio + ", Votos: " + cantidadVotos + ")");
                        }
                    }
                    
                } catch (Exception e) {
                    System.err.println("Error al insertar pelicula: " + (datos.length > 1 ? datos[1] : "desconocida") + " - " + e.getMessage());
                }
            }
            
            System.out.println("\n✅ Peliculas cargadas: " + contador);
            if (yaExistentes > 0) {
                System.out.println("⏭️  Peliculas ya existentes (no sobrescritas): " + yaExistentes);
            }
            
            
        } catch (Exception e) {
            System.err.println("Error al cargar CSV: " + e.getMessage());
            e.printStackTrace();
        }
        
        return peliculasEnMemoria;
    }
    
    private static String[] parsearLineaCSV(String linea) {
        // Parseo simple para CSV - puede necesitar ajustes segun el formato
        String[] partes = linea.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        for (int i = 0; i < partes.length; i++) {
            partes[i] = partes[i].trim().replaceAll("^\"|\"$", "");
        }
        return partes;
    }
    
    private static int extraerAnio(String fecha) {
        try {
            if (fecha != null && fecha.length() >= 4) {
                return Integer.parseInt(fecha.substring(0, 4));
            }
        } catch (Exception e) {
            // Ignorar error
        }
        return 2020; // Ano por defecto
    }
    
    public static boolean existenPeliculasEnBD() {
        try (Connection conn = Conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM PELICULA")) {
            
            var rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            // Si hay error, asumir que no hay peliculas
        }
        return false;
    }
}
