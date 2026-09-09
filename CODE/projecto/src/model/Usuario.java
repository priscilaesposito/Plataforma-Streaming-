package model;

import java.util.LinkedList;

import gestion.Administrador;

/**
 * De esta clase heredan {@link Administrador} y {@link Cliente}.
 *
 * @author Juana Sabbione
 * @author Priscila Esposito
 * @version 1.0
 * @since 2025
 * @see Administrador
 * @see Cliente
 */


public class Usuario {
	private String nombre;
    private String apellido;
    private String username;
    private String correo;
    private String contrasenia;
    private String idioma;
    private LinkedList<String> preferenciasGenero;
    private Fecha fechaNacimiento;
    private int DNI;
    private int ID_USUARIO;
    private int ID_DATOS_PERSONALES;
        
    

    public int getID_DATOS_PERSONALES() {
        return ID_DATOS_PERSONALES;
    }   
    public void setID_DATOS_PERSONALES(int ID_DATOS_PERSONALES) {
        this.ID_DATOS_PERSONALES = ID_DATOS_PERSONALES;
    }

    public int getID_USUARIO() {
        return ID_USUARIO;
    }

    public void setID_USUARIO(int ID_USUARIO) {
        this.ID_USUARIO = ID_USUARIO;
    }

    public int getDNI() {
        return DNI;
    }       

    public void setDNI(int DNI) {
        this.DNI = DNI;
    }   

     
    /**
     * Constructor donde inicializo toda la informacion del usuario.
     * 
     * @param nombre             nombre del usuario
     * @param apellido           apellido del usuario
     * @param correo             correo del usuario
     * @param contrasenia         contrasenia del usuario
     * @param idioma             idioma en el que se maneja el usuario en la
     *                           plataforma
     * @param preferenciasGenero preferencias de genero de los contenidos(titulos)
     *                           del usuario
     * @param fechaNacimiento    fecha de nacimiento del usuario
     */
    public Usuario(String nombre, String apellido, String correo, String contrasenia, String idioma,
            LinkedList<String> preferenciasGenero, Fecha fechaNacimiento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contrasenia = contrasenia;
        this.idioma = idioma;
        this.preferenciasGenero = preferenciasGenero;
        this.fechaNacimiento = fechaNacimiento;
    }

    public Usuario() {
    }

    /**
     * Asigna el nombre del usuario.
     * 
     * @param nombre
     */

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el nombre del usuario.
     * 
     * @return Nombre del usuario.
     */

    public String getNombre() {
        return this.nombre;
    }

    /**
     * Asigna el apellido del usuario.
     * 
     * @param apellido
     */

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getUsername() {
        return username;
    }   

    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * Obtiene el apellido del usuario.
     * 
     * @return apellido
     */

    public String getApellido() {
        return this.apellido;
    }

    /**
     * Asigna el correo del usuario.
     * 
     * @param correo
     */

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Obtiene el correo del usuario.
     * 
     * @return Correo electronico del usuario.
     */

    public String getCorreo() {
        return this.correo;
    }

    /**
     * Asigna la contrasenia del usuario.
     * 
     * @param contrasenia
     */

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    /**
     * Obtiene la contrasenia del usuario.
     * 
     * @return Contrasenia del usuario.
     */

    public String getContrasenia() {
        return this.contrasenia;
    }

    /**
     * Asigna la fecha de nacimiento del usuario.
     * 
     * @param fechaNacimiento
     */

    public void setFechaNacimiento(Fecha fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Obtiene la fecha de nacimiento del usuario.
     * 
     * @return Fecha de nacimiento del usuario.
     */

    public Fecha getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    /**
     * Asigna el idioma del usuario.
     * 
     * @param idioma
     */

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    /**
     * Obtiene el idioma del usuario.
     * 
     * @return Idioma
     */

    public String getIdioma() {
        return this.idioma;
    }

    /**
     * Aniade a la lista de generos preferidos un genero seleccionado por el usuario.
     * 
     * @param genero
     */

    public void aniadirPreferenciasGenero(String genero) {
    }

    /**
     * Elimina de la lista de generos preferidos un genero seleccionado por el
     * usuario.
     * 
     * @param genero
     */

    public void eliminarPreferenciasGenero(String genero) {
    }

    /**
     * Obtiene la lista de generos preferidos del usuario.
     * 
     * @return genero
     */

    public LinkedList<String> getPreferenciasGenero() {
        return this.preferenciasGenero;
    }

}
