
package dao;

import java.sql.SQLException;
import java.util.List;
import model.Resenia;

public interface ReseniaDAO {

    void registrar(Resenia resenia) throws SQLException;

    Resenia buscarPorId(int id) throws SQLException;

    List<Resenia> listarNoAprobadas() throws SQLException;

    void aprobarResenia(int idResenia) throws SQLException;
}