package enumerativo;

/**
 * Excepcion personalizada para errores en resenias de peliculas.
 * Esta es una Checked Exception que debe ser manejada explicitamente.
 * 
 * Se lanza cuando:
 * - La resenia esta vacia o es demasiado corta
 * - La calificacion esta fuera del rango valido (1-10)
 * - El usuario ya califico la pelicula
 * - La resenia contiene contenido inapropiado
 * - No se encuentra la resenia solicitada
 */
public class ReseniaInvalidaException extends Exception {

    private int calificacion; // Calificacion que causo el error
    private String motivoRechazo; // Motivo especifico del rechazo
    private boolean esProblemaCalificacion; // true si el error es por calificacion, false si es por texto

    /**
     * Constructor basico con mensaje de error
     * 
     * @param mensaje Descripcion del error
     */
    public ReseniaInvalidaException(String mensaje) {
        super(mensaje);
        this.esProblemaCalificacion = false;
    }

    /**
     * Constructor con mensaje y causa
     * 
     * @param mensaje Descripcion del error
     * @param causa   Excepcion que causo este error
     */
    public ReseniaInvalidaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
        this.esProblemaCalificacion = false;
    }

    /**
     * Constructor para errores relacionados con la calificacion
     * 
     * @param mensaje      Descripcion del error
     * @param calificacion Calificacion invalida ingresada
     */
    public ReseniaInvalidaException(String mensaje, int calificacion) {
        super(mensaje);
        this.calificacion = calificacion;
        this.esProblemaCalificacion = true;
    }

    /**
     * Constructor completo con motivo de rechazo
     * 
     * @param mensaje       Descripcion del error
     * @param motivoRechazo Razon especifica del rechazo
     */
    public ReseniaInvalidaException(String mensaje, String motivoRechazo) {
        super(mensaje);
        this.motivoRechazo = motivoRechazo;
        this.esProblemaCalificacion = false;
    }

    /**
     * Obtiene la calificacion que causo el error
     * 
     * @return Calificacion invalida
     */
    public int getCalificacion() {
        return calificacion;
    }

    /**
     * Obtiene el motivo del rechazo
     * 
     * @return Motivo del rechazo
     */
    public String getMotivoRechazo() {
        return motivoRechazo;
    }

    /**
     * Indica si el error es por calificacion invalida
     * 
     * @return true si es problema de calificacion
     */
    public boolean esProblemaCalificacion() {
        return esProblemaCalificacion;
    }

    /**
     * Retorna un mensaje detallado del error
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ReseniaInvalidaException: ");
        sb.append(getMessage());

        if (esProblemaCalificacion) {
            sb.append(" [Calificacion invalida: ").append(calificacion).append("]");
        }

        if (motivoRechazo != null) {
            sb.append(" [Motivo: ").append(motivoRechazo).append("]");
        }

        return sb.toString();
    }
}
