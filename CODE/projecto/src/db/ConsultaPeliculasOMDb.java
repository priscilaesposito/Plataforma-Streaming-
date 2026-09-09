package db;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;

public class ConsultaPeliculasOMDb {
    
    // Reemplaza con tu API Key obtenida en https://www.omdbapi.com/apikey.aspx
    private static final String API_KEY = "1dfc3229";
    
    public static void main(String[] args) {
        String titulo = "titanic";  // Reemplazar por el titulo a buscar
        consultarPelicula(titulo);
    }
    
    public static void consultarPelicula(String titulo) {
        try {
            // Armar la URL de consulta (encodear espacios con '+')
            String url = "https://www.omdbapi.com/?t=" + titulo.replace(" ", "+") + "&apikey=" + API_KEY;
            
            // Crear cliente y solicitud
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();
            
            // Enviar solicitud
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            // Procesar respuesta JSON
            JSONObject json = new JSONObject(response.body());
            
            if (json.has("Response") && json.getString("Response").equals("True")) {
                System.out.println("🎬 Titulo: " + json.getString("Title"));
                System.out.println("📅 Anio: " + json.getString("Year"));
                System.out.println("📖 Sinopsis: " + json.getString("Plot"));
                System.out.println("⭐ Rating: " + json.optString("imdbRating", "N/A"));
                System.out.println("🖼️ Poster: " + json.optString("Poster", "N/A"));
            } else {
                System.out.println("❌ Pelicula no encontrada o error en la consulta.");
            }
            
        } catch (Exception e) {
            System.out.println("Error al consultar la API: " + e.getMessage());
        }
    }
    
    /**
     * Buscar pelicula y retornar objeto JSON con los datos
     * 
     * @throws java.net.ConnectException si no hay conexion a Internet
     * @throws java.net.UnknownHostException si no se puede resolver el DNS
     * @throws java.io.IOException si hay problemas de red
     */
    public static JSONObject buscarPelicula(String titulo) throws Exception {
        try {
            // Armar la URL de consulta (encodear espacios con '+')
            String url = "https://www.omdbapi.com/?t=" + titulo.replace(" ", "+") + "&apikey=" + API_KEY;
            
            // Crear cliente y solicitud con timeout
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(java.time.Duration.ofSeconds(10))
                    .build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(java.time.Duration.ofSeconds(10))
                    .build();
            
            // Enviar solicitud
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            // Procesar respuesta JSON
            JSONObject json = new JSONObject(response.body());
            
            if (json.has("Response") && json.getString("Response").equals("True")) {
                return json;
            } else {
                return null;
            }
        } catch (java.net.ConnectException e) {
            throw new java.net.ConnectException("No se pudo conectar a OMDb. Verifica tu conexion a Internet.");
        } catch (java.net.UnknownHostException e) {
            throw new java.net.UnknownHostException("No se pudo resolver el servidor de OMDb. Verifica tu conexion a Internet.");
        } catch (java.net.http.HttpTimeoutException e) {
            throw new java.io.IOException("Tiempo de espera agotado. El servidor no respondio a tiempo.");
        } catch (java.io.IOException e) {
            throw new java.io.IOException("Error de red al consultar OMDb: " + e.getMessage());
        }
    }
    
    /**
     * Buscar pelicula por ID de IMDb
     */
    public static JSONObject buscarPorIMDbID(String imdbID) throws Exception {
        String url = "https://www.omdbapi.com/?i=" + imdbID + "&apikey=" + API_KEY;
        
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject json = new JSONObject(response.body());
        
        if (json.has("Response") && json.getString("Response").equals("True")) {
            return json;
        } else {
            return null;
        }
    }
    
    /**
     * Buscar multiples peliculas por termino de busqueda
     */
    public static JSONObject buscarVariasPeliculas(String searchTerm) throws Exception {
        String url = "https://www.omdbapi.com/?s=" + searchTerm.replace(" ", "+") + "&apikey=" + API_KEY;
        
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject json = new JSONObject(response.body());
        
        if (json.has("Response") && json.getString("Response").equals("True")) {
            return json;
        } else {
            return null;
        }
    }
}
