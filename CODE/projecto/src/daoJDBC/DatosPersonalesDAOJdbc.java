// Archivo: src/daojdbc/DatosPersonalesDAOJdbc.java
package daoJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.DatosPersonalesDAO;
import db.BaseDeDatos;
import model.Usuario;

public class DatosPersonalesDAOJdbc implements DatosPersonalesDAO {

    @Override
    public void registrar(Usuario datos) throws SQLException{
        String sql = "INSERT INTO DATOS_PERSONALES (NOMBRES, APELLIDO, DNI) VALUES (?, ?, ?)";
        try (Connection conn = BaseDeDatos.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, datos.getNombre());
            pstmt.setString(2, datos.getApellido());
            pstmt.setInt(3, datos.getDNI());
            pstmt.executeUpdate();

        }
    }

    @Override
    public List<Usuario> listarTodos() throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM DATOS_PERSONALES";
        try (Connection conn = BaseDeDatos.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                lista.add(tomarDatos(rs));
            }
        }
        return lista;
    }

    private Usuario tomarDatos(ResultSet rs) throws SQLException {
        Usuario datos = new Usuario();
        datos.setID_DATOS_PERSONALES(rs.getInt("ID"));
        datos.setNombre(rs.getString("NOMBRES"));
        datos.setApellido(rs.getString("APELLIDO"));
        datos.setDNI(rs.getInt("DNI"));
        return datos;
    }

    @Override
    public boolean existeDNI(int dni) throws SQLException {
        String sql = "SELECT * from datos_personales where dni = ?;";

        try (Connection conn = BaseDeDatos.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, dni);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new SQLException("Error en la busqueda ", e);
        }
    }
    
    @Override
    public Usuario buscarPorDNI(int dni) throws SQLException {
        String sql = "SELECT * FROM DATOS_PERSONALES WHERE DNI = ?";

        try (Connection conn = BaseDeDatos.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, dni);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return tomarDatos(rs);
                }
            }
            return null;

        } catch (SQLException e) {
            throw new SQLException("Error al buscar por DNI", e);
        }
    }

}