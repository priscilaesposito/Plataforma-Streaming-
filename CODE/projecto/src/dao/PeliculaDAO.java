
package dao;

import java.sql.SQLException;
import java.util.List;
import model.Pelicula;

public interface PeliculaDAO {

    void registrar(Pelicula pelicula) throws SQLException;

    Pelicula buscarPorId(int id) throws SQLException;

    List<Pelicula> listarTodos() throws SQLException;
    
    List<Pelicula> obtenerTop10PorRating() throws SQLException;
    
    List<Pelicula> obtener10RandomNoCalificadas(int idUsuario) throws SQLException;
}
