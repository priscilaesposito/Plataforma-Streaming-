package enumerativo;

/**
 * Excepcion personalizada para errores de validacion de DNI.
 * Esta es una Checked Exception que debe ser manejada explicitamente.
 * 
 * Se lanza cuando:
 * - El DNI tiene mas de 8 caracteres
 * - El DNI contiene caracteres no numericos
 * - El DNI esta vacio
 * - El DNI ya existe en la base de datos
 */
public class DNIInvalidoException extends Exception {

    private String dniIngresado; // DNI que causo el error
    private int longitudActual; // Longitud del DNI ingresado
    private String tipoError; // Tipo de error: "LONGITUD", "FORMATO", "DUPLICADO", "VACIO"

    /**
     * Constructor basico con mensaje de error
     * 
     * @param mensaje Descripcion del error
     */
    public DNIInvalidoException(String mensaje) {
        super(mensaje);
    }

    /**
     * Constructor con mensaje y causa
     * 
     * @param mensaje Descripcion del error
     * @param causa   Excepcion que causo este error
     */
    public DNIInvalidoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    /**
     * Constructor completo con informacion del DNI
     * 
     * @param mensaje      Descripcion del error
     * @param dniIngresado DNI que causo el error
     * @param tipoError    Tipo de error ocurrido
     */
    public DNIInvalidoException(String mensaje, String dniIngresado, String tipoError) {
        super(mensaje);
        this.dniIngresado = dniIngresado;
        this.longitudActual = dniIngresado != null ? dniIngresado.length() : 0;
        this.tipoError = tipoError;
    }

    /**
     * Obtiene el DNI que causo el error
     * 
     * @return DNI ingresado
     */
    public String getDniIngresado() {
        return dniIngresado;
    }

    /**
     * Obtiene la longitud actual del DNI
     * 
     * @return Longitud del DNI
     */
    public int getLongitudActual() {
        return longitudActual;
    }

    /**
     * Obtiene el tipo de error
     * 
     * @return Tipo de error
     */
    public String getTipoError() {
        return tipoError;
    }

    /**
     * Verifica si el error es por exceder la longitud maxima
     * 
     * @return true si el DNI tiene mas de 8 caracteres
     */
    public boolean esErrorDeLongitud() {
        return "LONGITUD".equals(tipoError);
    }

    /**
     * Verifica si el error es por formato invalido
     * 
     * @return true si el DNI contiene caracteres no numericos
     */
    public boolean esErrorDeFormato() {
        return "FORMATO".equals(tipoError);
    }

    /**
     * Verifica si el error es por DNI duplicado
     * 
     * @return true si el DNI ya existe en la base de datos
     */
    public boolean esErrorDeDuplicado() {
        return "DUPLICADO".equals(tipoError);
    }

    /**
     * Retorna un mensaje detallado del error
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("DNIInvalidoException: ");
        sb.append(getMessage());
        
        if (dniIngresado != null) {
            sb.append(" [DNI ingresado: ").append(dniIngresado).append("]");
        }
        
        if (tipoError != null) {
            sb.append(" [Tipo: ").append(tipoError).append("]");
        }
        
        if (longitudActual > 0) {
            sb.append(" [Longitud: ").append(longitudActual).append(" caracteres]");
        }
        
        return sb.toString();
    }

    /**
     * Genera un mensaje amigable para mostrar al usuario
     * 
     * @return Mensaje claro y conciso para el usuario
     */
    public String getMensajeUsuario() {
        if ("LONGITUD".equals(tipoError)) {
            return "El DNI debe tener como maximo 8 caracteres. Ingresaste " + longitudActual + " caracteres.";
        }
        
        if ("FORMATO".equals(tipoError)) {
            return "El DNI solo puede contener numeros.";
        }
        
        if ("DUPLICADO".equals(tipoError)) {
            return "El DNI " + dniIngresado + " ya esta registrado en el sistema.";
        }
        
        if ("VACIO".equals(tipoError)) {
            return "El DNI no puede estar vacio.";
        }
        
        return getMessage();
    }

    /**
     * Valida un DNI y lanza la excepcion si es invalido
     * 
     * @param dni DNI a validar
     * @throws DNIInvalidoException si el DNI es invalido
     */
    public static void validarDNI(String dni) throws DNIInvalidoException {
        // Verificar si esta vacio
        if (dni == null || dni.trim().isEmpty()) {
            throw new DNIInvalidoException(
                "El DNI no puede estar vacio",
                dni,
                "VACIO"
            );
        }
        
        // Verificar longitud maxima de 8 caracteres
        if (dni.length() > 8) {
            throw new DNIInvalidoException(
                "El DNI no puede tener mas de 8 caracteres",
                dni,
                "LONGITUD"
            );
        }
        
        // Verificar que solo contenga numeros
        if (!dni.matches("\\d+")) {
            throw new DNIInvalidoException(
                "El DNI solo puede contener numeros",
                dni,
                "FORMATO"
            );
        }
    }
}
