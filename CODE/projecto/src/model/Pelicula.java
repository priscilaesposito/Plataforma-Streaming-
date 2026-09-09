package model;

public class Pelicula extends Titulo {
    private Video video;
    private int ID;
    private float ratingPromedio;
    private int cantidadVotos;
    private int anio;
    private String poster;

    // Constructor que inicializa el objeto Video
    public Pelicula() {
        this.video = new Video();
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public float getRatingPromedio() {
        return ratingPromedio;
    }

    public void setRatingPromedio(float ratingPromedio) {
        this.ratingPromedio = ratingPromedio;
    }

    public int getCantidadVotos() {
        return cantidadVotos;
    }

    public void setCantidadVotos(int cantidadVotos) {
        this.cantidadVotos = cantidadVotos;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

}
