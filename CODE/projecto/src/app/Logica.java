package app;

import java.util.List;
import java.util.ArrayList;
import java.sql.SQLException;

import dao.DatosPersonalesDAO;
import dao.UsuarioDAO;
import dao.PeliculaDAO;

import daoJDBC.DatosPersonalesDAOJdbc;
import daoJDBC.UsuarioDAOjdbc;
import db.ConsultaPeliculasOMDb;
import daoJDBC.PeliculaDAOjdbc;
import model.Usuario;
import gestion.Administrador;
import gestion.GestionUsuario;
import gestion.ListasyResenias;
import model.Resenia;
import model.Pelicula;
import enumerativo.PeliculaNoEncontradaException;
import enumerativo.ReseniaInvalidaException;

import java.time.LocalDateTime;
import org.json.JSONObject;
import org.json.JSONArray;

public class Logica {

    private static UsuarioDAO usuarioDAO = new UsuarioDAOjdbc();
    private static DatosPersonalesDAO datosPersonalesDAO = new DatosPersonalesDAOJdbc();
    private static PeliculaDAO peliculaDAO = new PeliculaDAOjdbc();
    private static GestionUsuario gestionUsuario = new GestionUsuario();
    private static Administrador Administrador = new Administrador();
    private static ListasyResenias listasyResenias = new ListasyResenias();

    //LOGIN
    public static Usuario login(String email, String password) throws Exception {
        try {
            // Validaciones basicas
            if (email == null || email.trim().isEmpty() ||
                    password == null || password.trim().isEmpty()) {
                return null;
            }

            // Intentar validar usuario usando el DAO con email
            Usuario usuarioValidado = usuarioDAO.validarPorEmail(email.trim().toLowerCase(), password);

            return usuarioValidado; // Retorna el usuario si es valido, null si no

        } catch (SQLException e) {
            throw new Exception("Error de conexion a la base de datos: " + e.getMessage());
        } catch (Exception e) {
            throw new Exception("Error inesperado durante el login: " + e.getMessage());
        }
    }

    public static boolean existeUsuario(String username) throws Exception {
        try {
            Usuario u = usuarioDAO.buscar(username);
            return u != null;
        } catch (SQLException e) {
            throw new Exception("Error al verificar usuario: " + e.getMessage());
        }
    }

   
    public static boolean existeEmail(String email) throws Exception {
        try {
            return usuarioDAO.existeEmail(email);
        } catch (SQLException e) {
            throw new Exception("Error al verificar email: " + e.getMessage());
        }
    }

    public static boolean existeDNI(String dni) throws Exception {
        try {
            int dniInt = Integer.parseInt(dni);
            return datosPersonalesDAO.existeDNI(dniInt);
        } catch (NumberFormatException e) {
            throw new Exception("DNI invalido");
        } catch (SQLException e) {
            throw new Exception("Error al verificar DNI: " + e.getMessage());
        }
    }


    public static boolean registrarUsuario(String nombre, String apellido, String dni,
            int edad, String direccion, String telefono,
            String username, String email, String password) throws Exception {
        try {
            // Primero registrar datos personales
            Usuario datosPersonales = new Usuario();
            datosPersonales.setNombre(nombre);
            datosPersonales.setApellido(apellido);
            datosPersonales.setDNI(Integer.parseInt(dni));

            // Validar datos personales
            gestionUsuario.validacionDatosPersonales(datosPersonales);

            // Registrar datos personales en la BD
            datosPersonalesDAO.registrar(datosPersonales);

            // Obtener el ID generado (buscar por DNI)
            Usuario dpRegistrado = datosPersonalesDAO.buscarPorDNI(Integer.parseInt(dni));

            if (dpRegistrado == null) {
                throw new Exception("Error al recuperar los datos personales registrados");
            }

            // Crear usuario
            Usuario usuario = new Usuario();
            usuario.setUsername(username);
            usuario.setCorreo(email.trim().toLowerCase());
            usuario.setContrasenia(password);
            usuario.setID_DATOS_PERSONALES(dpRegistrado.getID_DATOS_PERSONALES());

            // Validar datos de usuario
            gestionUsuario.ValidacionUsuario(usuario);

            // Registrar usuario
            usuarioDAO.registrar(usuario);

            return true;

        } catch (IllegalArgumentException e) {
            throw new Exception("Error de validacion: " + e.getMessage());
        } catch (SQLException e) {
            throw new Exception("Error al registrar en la base de datos: " + e.getMessage());
        } catch (Exception e) {
            throw new Exception("Error inesperado: " + e.getMessage());
        }
    }

   
    public static JSONObject buscarPeliculaOMDb(String titulo) throws Exception {
        try {
            JSONObject resultado = ConsultaPeliculasOMDb.buscarPelicula(titulo);

            if (resultado == null || resultado.optString("Response", "False").equals("False")) {
                throw new PeliculaNoEncontradaException(
                        "No se encontro la pelicula en OMDb",
                        "Titulo",
                        titulo);
            }

            return resultado;
        } catch (PeliculaNoEncontradaException e) {
            throw e; // Re-lanzar la excepcion personalizada
        } catch (java.net.ConnectException | java.net.UnknownHostException e) {
            throw new java.io.IOException("Sin conexion a Internet. " + e.getMessage());
        } catch (java.io.IOException e) {
            throw e; // Re-lanzar errores de red
        } catch (Exception e) {
            throw new Exception("Error al buscar pelicula: " + e.getMessage());
        }
    }

    public static JSONObject buscarVariasPeliculasOMDb(String searchTerm) throws Exception {
        return ConsultaPeliculasOMDb.buscarVariasPeliculas(searchTerm);
    }

    public static boolean buscarYGuardarPeliculaOMDb(String titulo) throws Exception {
        try {
            JSONObject datosOMDb = ConsultaPeliculasOMDb.buscarPelicula(titulo);

            if (datosOMDb == null) {
                throw new Exception("No se encontro la pelicula en OMDb");
            }

            // Crear objeto Pelicula
            Pelicula pelicula = new Pelicula();

            // Mapear datos basicos
            pelicula.getMetadatos().setTitulo(datosOMDb.getString("Title"));
            pelicula.getMetadatos().setSipnosis(datosOMDb.optString("Plot", "Sin sinopsis"));
            pelicula.getMetadatos().setDirector(datosOMDb.optString("Director", "Desconocido"));

            // Extraer anio
            String year = datosOMDb.optString("Year", "0");
            try {
                // Por si es serie (viene como "2010-2020")
                pelicula.setAnio(Integer.parseInt(year.split("-")[0]));
            } catch (NumberFormatException e) {
                pelicula.setAnio(0);
            }

            // Rating promedio de IMDb
            String imdbRating = datosOMDb.optString("imdbRating", "0");
            if (!imdbRating.equals("N/A") && !imdbRating.isEmpty()) {
                try {
                    pelicula.setRatingPromedio(Float.parseFloat(imdbRating));
                } catch (NumberFormatException e) {
                    pelicula.setRatingPromedio(0.0f);
                }
            }

            // Poster URL
            pelicula.setPoster(datosOMDb.optString("Poster", ""));

            // Generos (viene como "Action, Adventure, Drama")
            String generos = datosOMDb.optString("Genre", "");
            if (!generos.isEmpty() && !generos.equals("N/A")) {
                for (String genero : generos.split(", ")) {
                    pelicula.anadirGeneros(genero.trim());
                }
            } else {
                pelicula.anadirGeneros("Sin genero");
            }

            // Duracion (viene como "142 min")
            String runtime = datosOMDb.optString("Runtime", "0 min");
            if (!runtime.equals("N/A")) {
                try {
                    int duracion = Integer.parseInt(runtime.split(" ")[0]);
                    pelicula.getVideo().setDuracion(duracion);
                } catch (NumberFormatException e) {
                    pelicula.getVideo().setDuracion(0);
                }
            }

            // Validar y guardar
            Administrador.validarRegistroPelicula(pelicula);
            peliculaDAO.registrar(pelicula);

            return true;

        } catch (IllegalArgumentException e) {
            throw new Exception("Error de validacion: " + e.getMessage());
        } catch (SQLException e) {
            throw new Exception("Error al guardar en la base de datos: " + e.getMessage());
        } catch (Exception e) {
            throw new Exception("Error al buscar/guardar pelicula: " + e.getMessage());
        }
    }

    public static JSONArray listarPeliculasOMDb(String searchTerm) throws Exception {
        JSONObject resultado = ConsultaPeliculasOMDb.buscarVariasPeliculas(searchTerm);

        if (resultado != null && resultado.has("Search")) {
            return resultado.getJSONArray("Search");
        }

        return new JSONArray(); // Retorna array vacio si no hay resultados
    }

    public static boolean esPrimerLogin(int idUsuario) throws Exception {
        try (java.sql.Connection conn = db.BaseDeDatos.conectar();
                java.sql.PreparedStatement pstmt = conn.prepareStatement(
                        "SELECT COUNT(*) FROM PRIMER_LOGIN WHERE ID_USUARIO = ?")) {

            pstmt.setInt(1, idUsuario);
            java.sql.ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) == 0; // True si no hay registro
            }
            return true;
        } catch (SQLException e) {
            throw new Exception("Error al verificar primer login: " + e.getMessage());
        }
    }

    public static void registrarPrimerLogin(int idUsuario) throws Exception {
        try (java.sql.Connection conn = db.BaseDeDatos.conectar();
                java.sql.PreparedStatement pstmt = conn.prepareStatement(
                        "INSERT INTO PRIMER_LOGIN (ID_USUARIO, FECHA_PRIMER_LOGIN) VALUES (?, ?)")) {

            pstmt.setInt(1, idUsuario);
            pstmt.setString(2, LocalDateTime.now().toString());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new Exception("Error al registrar primer login: " + e.getMessage());
        }
    }

    public static List<Pelicula> obtenerTop10Peliculas() throws Exception {
        try {
            // 1. Obtener todas las peliculas de la BD (con ratings actualizados)
            List<Pelicula> peliculasBD = peliculaDAO.listarTodos();
            
            // 2. Cargar peliculas del CSV
            List<Pelicula> peliculasCSV = utilidades.CargadorCSV.cargarPeliculasDesdeCSV();
            
            // 3. Crear conjunto de titulos+anos de BD para evitar duplicados
            java.util.Set<String> peliculasEnBD = new java.util.HashSet<>();
            for (Pelicula p : peliculasBD) {
                String clave = p.getMetadatos().getTitulo() + "_" + p.getAnio();
                peliculasEnBD.add(clave);
            }
            
            // 4. Combinar: agregar peliculas del CSV que NO estan en BD
            List<Pelicula> todasPeliculas = new ArrayList<>(peliculasBD);
            for (Pelicula p : peliculasCSV) {
                String clave = p.getMetadatos().getTitulo() + "_" + p.getAnio();
                if (!peliculasEnBD.contains(clave)) {
                    todasPeliculas.add(p);
                }
            }
            
            // 5. Ordenar por rating descendente
            todasPeliculas.sort((p1, p2) -> Float.compare(p2.getRatingPromedio(), p1.getRatingPromedio()));
            
            // 6. Tomar las 10 mejores
            List<Pelicula> top10 = todasPeliculas.subList(0, Math.min(10, todasPeliculas.size()));

            if (top10.isEmpty()) {
                throw new PeliculaNoEncontradaException(
                        "No hay peliculas disponibles en el catalogo",
                        "Top 10",
                        "Ninguna");
            }

            return top10;
        } catch (PeliculaNoEncontradaException e) {
            throw e;
        } catch (SQLException e) {
            throw new Exception("Error al obtener top peliculas: " + e.getMessage());
        }
    }

    public static List<Pelicula> obtener10PeliculasRandom(int idUsuario) throws Exception {
        try {
            List<Pelicula> peliculas = peliculaDAO.obtener10RandomNoCalificadas(idUsuario);

            if (peliculas == null || peliculas.isEmpty()) {
                throw new PeliculaNoEncontradaException(
                        "No hay peliculas disponibles para recomendar",
                        "Recomendaciones",
                        "Usuario ID: " + idUsuario);
            }

            return peliculas;
        } catch (PeliculaNoEncontradaException e) {
            throw e;
        } catch (SQLException e) {
            throw new Exception("Error al obtener peliculas random: " + e.getMessage());
        }
    }

    public static boolean calificarPelicula(int idUsuario, int idPelicula, int calificacion, String comentario)
            throws Exception {
        try {
            // Validar calificacion
            if (calificacion < 1 || calificacion > 10) {
                throw new ReseniaInvalidaException(
                        "La calificacion debe estar entre 1 y 10",
                        calificacion);
            }

            // Validar comentario
            if (comentario == null || comentario.trim().isEmpty()) {
                throw new ReseniaInvalidaException(
                        "El comentario no puede estar vacio",
                        "Comentario vacio");
            }

            if (comentario.trim().length() < 10) {
                throw new ReseniaInvalidaException(
                        "El comentario debe tener al menos 10 caracteres",
                        "Comentario demasiado corto");
            }

            Resenia resenia = new Resenia();
            resenia.setID_Usuario(idUsuario);
            resenia.setID_Pelicula(idPelicula);
            resenia.setCalificacion(calificacion);
            resenia.setComentario(comentario.trim());
            resenia.setAprobado(1); // Auto-aprobar calificaciones simples
            resenia.setFechaHora(LocalDateTime.now().toString());

            listasyResenias.aniadirResenias(resenia);
            return true;

        } catch (ReseniaInvalidaException e) {
            throw e;
        } catch (SQLException e) {
            throw new Exception("Error al guardar calificacion: " + e.getMessage());
        }
    }
}