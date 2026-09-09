package enumerativo;

/**
 * Excepcion personalizada para errores relacionados con datos de usuario
 * invalidos.
 * Esta es una Checked Exception que debe ser manejada explicitamente.
 * 
 * Se lanza cuando:
 * - El DNI ya existe en la base de datos
 * - El nombre o apellido contienen caracteres invalidos
 * - El email tiene un formato incorrecto
 * - La contrasenia no cumple con los requisitos minimos
 * - El nombre de usuario ya esta en uso
 */
public class UsuarioInvalidoException extends Exception {

    private String campo; // Campo que causo el error (ej: "DNI", "email", "password")
    private String valorIngresado; // Valor que causo el error

    /**
     * Constructor basico con mensaje de error
     * 
     * @param mensaje Descripcion del error
     */
    public UsuarioInvalidoException(String mensaje) {
        super(mensaje);
    }

    /**
     * Constructor con mensaje y causa
     * 
     * @param mensaje Descripcion del error
     * @param causa   Excepcion que causo este error
     */
    public UsuarioInvalidoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    /**
     * Constructor completo con informacion detallada del error
     * 
     * @param mensaje        Descripcion del error
     * @param campo          Campo que causo el error
     * @param valorIngresado Valor que causo el error
     */
    public UsuarioInvalidoException(String mensaje, String campo, String valorIngresado) {
        super(mensaje);
        this.campo = campo;
        this.valorIngresado = valorIngresado;
    }

    /**
     * Obtiene el campo que causo el error
     * 
     * @return Nombre del campo
     */
    public String getCampo() {
        return campo;
    }

    /**
     * Obtiene el valor que causo el error
     * 
     * @return Valor ingresado
     */
    public String getValorIngresado() {
        return valorIngresado;
    }

    /**
     * Retorna un mensaje detallado del error
     */
    @Override
    public String toString() {
        if (campo != null && valorIngresado != null) {
            return "UsuarioInvalidoException: " + getMessage() +
                    " [Campo: " + campo + ", Valor: " + valorIngresado + "]";
        }
        return "UsuarioInvalidoException: " + getMessage();
    }
}
