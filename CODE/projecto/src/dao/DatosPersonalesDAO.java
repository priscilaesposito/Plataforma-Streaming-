
package dao;

import model.Usuario;

import java.sql.SQLException;
import java.util.List;

public interface DatosPersonalesDAO {

    void registrar(Usuario datos) throws SQLException;

    List<Usuario> listarTodos() throws SQLException;

    boolean existeDNI(int dni) throws SQLException;
    
    Usuario buscarPorDNI(int dni) throws SQLException;
}