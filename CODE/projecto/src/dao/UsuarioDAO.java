
package dao;

import model.Usuario;
import java.util.List;
import java.sql.SQLException;

public interface UsuarioDAO {
   
    void registrar(Usuario usuario) throws SQLException;

    Usuario buscarPorId(int id) throws SQLException;

    List<Usuario> listarTodos() throws SQLException;

    Usuario validar(String nombreUsuario, String contrasenia) throws SQLException;
    
    Usuario validarPorEmail(String email, String contrasenia) throws SQLException;
     
    Usuario buscar(String nombreUsuario) throws SQLException;
    
    boolean existeEmail(String email) throws SQLException;

}