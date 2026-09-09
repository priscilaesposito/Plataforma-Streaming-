package enumerativo;

/**
 * Excepcion personalizada para cuando no se encuentra una pelicula solicitada.
 * Esta es una Checked Exception que debe ser manejada explicitamente.
 * 
 * Se lanza cuando:
 * - No se encuentra una pelicula por su ID
 * - No se encuentra una pelicula por su titulo
 * - La busqueda en OMDb no retorna resultados
 * - No hay peliculas disponibles para recomendar
 */
public class PeliculaNoEncontradaException extends Exception {

    private String criterioBusqueda; // Criterio usado para buscar (ID, titulo, etc.)
    private String valorBuscado; // Valor que se busco

    /**
     * Constructor basico con mensaje de error
     * 
     * @param mensaje Descripcion del error
     */
    public PeliculaNoEncontradaException(String mensaje) {
        super(mensaje);
    }

    /**
     * Constructor con mensaje y causa
     * 
     * @param mensaje Descripcion del error
     * @param causa   Excepcion que causo este error
     */
    public PeliculaNoEncontradaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    /**
     * Constructor completo con informacion de busqueda
     * 
     * @param mensaje          Descripcion del error
     * @param criterioBusqueda Tipo de busqueda realizada
     * @param valorBuscado     Valor que se intento encontrar
     */
    public PeliculaNoEncontradaException(String mensaje, String criterioBusqueda, String valorBuscado) {
        super(mensaje);
        this.criterioBusqueda = criterioBusqueda;
        this.valorBuscado = valorBuscado;
    }

    /**
     * Obtiene el criterio de busqueda usado
     * 
     * @return Criterio de busqueda
     */
    public String getCriterioBusqueda() {
        return criterioBusqueda;
    }

    /**
     * Obtiene el valor que se busco
     * 
     * @return Valor buscado
     */
    public String getValorBuscado() {
        return valorBuscado;
    }

    /**
     * Retorna un mensaje detallado del error
     */
    @Override
    public String toString() {
        if (criterioBusqueda != null && valorBuscado != null) {
            return "PeliculaNoEncontradaException: " + getMessage() +
                    " [Busqueda por " + criterioBusqueda + ": " + valorBuscado + "]";
        }
        return "PeliculaNoEncontradaException: " + getMessage();
    }
}
