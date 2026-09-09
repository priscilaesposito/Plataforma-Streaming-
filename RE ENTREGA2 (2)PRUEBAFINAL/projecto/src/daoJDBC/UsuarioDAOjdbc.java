package daoJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.UsuarioDAO;
import db.BaseDeDatos;
import model.Usuario;

public class UsuarioDAOjdbc implements UsuarioDAO {

    private static final String SELECT_USUARIO_CON_DATOS = 
        "SELECT U.ID, U.NOMBRE_USUARIO, U.EMAIL, U.CONTRASENIA, " +
        "DP.ID AS DP_ID, DP.NOMBRES, DP.APELLIDO, DP.DNI " +
        "FROM USUARIO U " +
        "JOIN DATOS_PERSONALES DP ON U.ID_DATOS_PERSONALES = DP.ID "; 

    @Override
    public void registrar(Usuario usuario)throws SQLException {
        String sql = "INSERT INTO USUARIO (NOMBRE_USUARIO, EMAIL, CONTRASENIA, ID_DATOS_PERSONALES) VALUES (?, ?, ?, ?)";
        try (Connection conn = BaseDeDatos.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, usuario.getUsername());
            pstmt.setString(2, usuario.getCorreo());
            pstmt.setString(3, usuario.getContrasenia());
            pstmt.setInt(4, usuario.getID_DATOS_PERSONALES());
            
            pstmt.executeUpdate();
            
        }
    }

    @Override
    public Usuario buscarPorId(int id) throws SQLException {
        String sql = SELECT_USUARIO_CON_DATOS + "WHERE U.ID = ?";
        try (Connection conn = BaseDeDatos.conectar();           
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUsuario(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Usuario> listarTodos() throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        String sql = SELECT_USUARIO_CON_DATOS; // 

        try (Connection conn = BaseDeDatos.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                lista.add(mapResultSetToUsuario(rs));
            }
        }
        return lista;
    }

    @Override
    public Usuario validar(String nombreUsuario, String contrasenia) throws SQLException{
        String sql = SELECT_USUARIO_CON_DATOS + "WHERE U.NOMBRE_USUARIO = ? AND U.CONTRASENIA = ?";
        try (Connection conn = BaseDeDatos.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nombreUsuario);
            pstmt.setString(2, contrasenia);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUsuario(rs);
                }
            }
        }
        return null;
    }
    
    @Override
    public Usuario validarPorEmail(String email, String contrasenia) throws SQLException{
        String sql = SELECT_USUARIO_CON_DATOS + "WHERE U.EMAIL = ? AND U.CONTRASENIA = ?";
        try (Connection conn = BaseDeDatos.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email.trim().toLowerCase());
            pstmt.setString(2, contrasenia);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUsuario(rs);
                }
            }
        }
        return null;
    }

    private Usuario mapResultSetToUsuario(ResultSet rs) throws SQLException {
        
        Usuario usuario = new Usuario();
        usuario.setID_USUARIO(rs.getInt("ID"));
        usuario.setUsername(rs.getString("NOMBRE_USUARIO"));
        usuario.setCorreo(rs.getString("EMAIL"));
        usuario.setContrasenia(rs.getString("CONTRASENIA"));
        usuario.setID_USUARIO(rs.getInt("DP_ID"));
        usuario.setNombre(rs.getString("NOMBRES"));
        usuario.setApellido(rs.getString("APELLIDO"));
        usuario.setDNI(rs.getInt("DNI"));
        return usuario;
    }

    @Override
    public Usuario buscar(String nombreUsuario) throws SQLException {
        String sql = SELECT_USUARIO_CON_DATOS + "WHERE U.NOMBRE_USUARIO = ?";
        try (Connection conn = BaseDeDatos.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombreUsuario);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUsuario(rs);
                }
            }
        }
        return null;
    }
    
    @Override
    public boolean existeEmail(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM USUARIO WHERE EMAIL = ?";
        try (Connection conn = BaseDeDatos.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email.trim().toLowerCase());
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}
