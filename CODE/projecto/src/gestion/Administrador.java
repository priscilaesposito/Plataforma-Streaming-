package gestion;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import enumerativo.Genero;
import model.Pelicula;
import model.Usuario;
import dao.PeliculaDAO;

/**
 * Administrador se hereda de {@link Usuario}
 * 
 * @author Juana Sabbione
 * @author Priscila Esposito
 * @version 1.0
 * @since 2025
 * @see Usuario
 */

public class Administrador extends Usuario {
    private PeliculaDAO peliD = new daoJDBC.PeliculaDAOjdbc();

    public Administrador() {
    }

    public void validarRegistroPelicula(Pelicula pelicula) {

        List<String> errores = new LinkedList<>();

        if (pelicula.getMetadatos() == null) {
            errores.add("Faltan los metadatos de la pelicula.");
        } else {
            if (pelicula.getMetadatos().getTitulo() == null || pelicula.getMetadatos().getTitulo().trim().isEmpty()) {
                errores.add("El titulo es un campo requerido.");
            }

            if (pelicula.getMetadatos().getDirector() == null
                    || pelicula.getMetadatos().getDirector().trim().isEmpty()) {
                errores.add("El director es un campo requerido.");
            }

        }

        LinkedList<String> generos = pelicula.getGeneros();

        if (generos == null || generos.isEmpty()) {
            errores.add("Se requiere al menos un genero.");
        } else {
            for (String genero : generos) {
                if (!Genero.genValido(genero)) {
                    errores.add("El genero '" + genero + "' no es un genero valido.");
                }
            }
        }

		if (pelicula.getVideo().getDuracion() < 0) {
			errores.add("La duracion/visualizaciones debe ser un numero positivo.");
		}

		if (!errores.isEmpty()) {
			String mensajeError = "El registro de la pelicula fallo por errores de validacion: " + String.join(", ", errores);
			throw new IllegalArgumentException(mensajeError);
		}
	}    public void almacenarPelicula(Pelicula pelicula) throws SQLException {
        peliD.registrar(pelicula);

    }
}
