package model;

/**
 * Esta clase guarda la fecha de nacimiento de {@link Usuario}
 * 
 * @author Juana Sabbione
 * @author Priscila Esposito
 * @version 1.0
 * @since 2025
 * @see Usuario
 */


public class Fecha {
	public int dia;
    public int mes;
    public int anio;

    public Fecha() {
    }

    /**
     * Constructor con parametros para inicializar una fecha.
     *
     * @param dia dia del mes
     * @param mes mes del anio
     * @param anio anio correspondiente
     */
    public Fecha(int dia, int mes, int anio) {
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
    }

    /**
     * Asigna el dia de la fecha.
     *
     * @param dia dia del mes
     */
    public void setDia(int dia) {
        this.dia = dia;
    }

    /**
     * Obtiene el dia de la fecha.
     *
     * @return dia del mes
     */
    public int getDia() {
        return this.dia;
    }

    /**
     * Asigna el mes de la fecha.
     *
     * @param mes mes del anio
     */
    public void setMes(int mes) {
        this.mes = mes;
    }

    /**
     * Obtiene el mes de la fecha.
     *
     * @return mes del anio
     */
    public int getMes() {
        return this.mes;
    }

    /**
     * Asigna el anio de la fecha.
     *
     * @param anio anio correspondiente
     */
    public void setAnio(int anio) {
        this.anio = anio;
    }

    /**
     * Obtiene el anio de la fecha.
     *
     * @return anio correspondiente
     */
    public int getAnio() {
        return this.anio;
    }

}
