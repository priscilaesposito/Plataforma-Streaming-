package gestion;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;

import dao.DatosPersonalesDAO;
import dao.UsuarioDAO;
import model.Usuario;
import enumerativo.UsuarioInvalidoException;

public class GestionUsuario {
    private DatosPersonalesDAO UDJ = new daoJDBC.DatosPersonalesDAOJdbc();
    private UsuarioDAO UD = new daoJDBC.UsuarioDAOjdbc();

    private boolean dniUnico(int dni) throws SQLException {
        return UDJ.existeDNI(dni);
    }

    private boolean stringValido(String linea) {
        LinkedList<Character> caracteresInvalidos = new LinkedList<>(Arrays.asList('0', '1', '2', '3', '4', '5', '6',
                '7', '8', '9', '!', '"', '#', '$', '%', '&', '/', '(', ')', '=', '?', '¡', '¿', '+', '-', '*', '{', '}',
                '[', ']', '^', '`', '´', '¨', ';', ':', '.', ','));
        for (char c : linea.toCharArray()) {
            if (caracteresInvalidos.contains(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Valida todos los campos de un objeto Usuario segun los requisitos.
     * 
     * @param usuario El objeto Usuario con los datos a validar de datos personales.
     * @return El mismo objeto Usuario si es valido, o null si hay errores.
     * @throws SQLException
     * @throws UsuarioInvalidoException si los datos del usuario son invalidos
     */
    public void validacionDatosPersonales(Usuario usuario) throws SQLException, UsuarioInvalidoException {
        // Validar longitud del DNI (maximo 8 digitos)
        String dniString = String.valueOf(usuario.getDNI());
        if (dniString.length() > 8) {
            throw new UsuarioInvalidoException(
                    "El DNI no puede tener mas de 8 digitos.",
                    "DNI",
                    dniString);
        }
        
        // Validar unicidad de DNI
        if (dniUnico(usuario.getDNI())) {
            throw new UsuarioInvalidoException(
                    "El DNI ya existe en la base de datos.",
                    "DNI",
                    String.valueOf(usuario.getDNI()));
        }

        if (usuario.getNombre() == null || usuario.getNombre().isEmpty()) {
            throw new UsuarioInvalidoException(
                    "El nombre no puede estar vacio.",
                    "nombre",
                    usuario.getNombre());
        }

        if (!stringValido(usuario.getNombre())) {
            throw new UsuarioInvalidoException(
                    "El nombre no debe contener numeros ni caracteres especiales.",
                    "nombre",
                    usuario.getNombre());
        }

        if (usuario.getApellido() == null || usuario.getApellido().isEmpty()) {
            throw new UsuarioInvalidoException(
                    "El apellido no puede estar vacio.",
                    "apellido",
                    usuario.getApellido());
        }

        if (!stringValido(usuario.getApellido())) {
            throw new UsuarioInvalidoException(
                    "El apellido no debe contener numeros ni caracteres especiales.",
                    "apellido",
                    usuario.getApellido());
        }

        return;
    }

    public void registrarDatosPersonales(Usuario nuevoUsuario) throws Exception {
        UDJ.registrar(nuevoUsuario);
    }

    private boolean mailValido(String mail) {

        if (mail == null || mail.trim().isEmpty()) {
            return false;
        }

        int atIndex = mail.indexOf('@');

        if (atIndex <= 0) {
            return false;
        }

        if (mail.lastIndexOf('@') != atIndex) {
            return false;
        }

        if (atIndex == mail.length() - 1) {
            return false;
        }

        return true;

    }

    /**
     * Valida los datos de inicio de sesion del usuario.
     * 
     * @param usuario El objeto Usuario con los datos a validar
     * @throws UsuarioInvalidoException si los datos son invalidos
     */
    public void ValidacionUsuario(Usuario usuario) throws UsuarioInvalidoException {

        if (usuario.getUsername() == null || usuario.getUsername().trim().isEmpty()) {
            throw new UsuarioInvalidoException(
                    "El nombre de usuario no puede estar vacio.",
                    "username",
                    usuario.getUsername());
        }

        if (usuario.getContrasenia() == null || usuario.getContrasenia().isEmpty()) {
            throw new UsuarioInvalidoException(
                    "La contrasenia no puede estar vacia.",
                    "password",
                    "***");
        }

        if (!mailValido(usuario.getCorreo())) {
            throw new UsuarioInvalidoException(
                    "El formato del mail ingresado debe ser xxx@yyy.",
                    "email",
                    usuario.getCorreo());
        }

        return;
    }

    public void registrarUsuario(Usuario nuevoUsuario) throws Exception {
        UD.registrar(nuevoUsuario);
    }

    public boolean validacionUsuarioContrasenia(String usuario, String contrasenia) throws SQLException {
        if (UD.validar(usuario, contrasenia) != null) {
            return true;
        } else {
            return false;
        }
    }

    public Usuario buscar(String nombreUsuario) throws SQLException {
        return UD.buscar(nombreUsuario);
    }

}
