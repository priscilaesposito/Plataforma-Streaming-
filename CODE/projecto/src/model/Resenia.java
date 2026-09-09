package model;

public class Resenia {
    private int calificacion;
    private String comentario;
    private int aprobado;
    private String fechaHora;
    private int ID_Usuario;
    private int ID_Pelicula;
    private int ID_Resenia;

    public Resenia() {
    }

    public Resenia(int calificacion, String comentario, int aprobado, String fechaHora, int ID_Usuario, int ID_Pelicula,
            int ID_Resenia) {
        this.calificacion = calificacion;
        this.comentario = comentario;
        this.aprobado = aprobado;
        this.fechaHora = fechaHora;
        this.ID_Usuario = ID_Usuario;
        this.ID_Pelicula = ID_Pelicula;
        this.ID_Resenia = ID_Resenia;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public String getComentario() {
        return comentario;
    }

    public int isAprobado() {
        return aprobado;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public int getID_Usuario() {
        return ID_Usuario;
    }

    public int getID_Pelicula() {
        return ID_Pelicula;
    }

    public int getID_Resenia() {
        return ID_Resenia;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public void setAprobado(int aprobado) {
        this.aprobado = aprobado;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public void setID_Usuario(int ID_Usuario) {
        this.ID_Usuario = ID_Usuario;
    }

    public void setID_Pelicula(int ID_Pelicula) {
        this.ID_Pelicula = ID_Pelicula;
    }

    public void setID_Resenia(int ID_Resenia) {
        this.ID_Resenia = ID_Resenia;
    }

}
