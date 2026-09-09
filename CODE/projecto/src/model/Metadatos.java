package model;

import java.util.LinkedList;

public class Metadatos {
    private String sipnosis;
    private String titulo;
    private LinkedList<String> elenco;
    private String director;
    private String idioma;
    private LinkedList<String> subtitulosDisponibles;
    private String poster;
    
    public Metadatos() {
        this.elenco = new LinkedList<>();
        this.subtitulosDisponibles = new LinkedList<>();
    }
    
    public void setSipnosis(String sipnosis) {
        this.sipnosis = sipnosis;
    }
    
    public String getSipnosis() {
        return sipnosis;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public void aniadirElenco(String actor) {
        this.elenco.add(actor);
    }
    
    public LinkedList<String> getElenco() {
        return elenco;
    }
    
    public void setDirector(String director) {
        this.director = director;
    }
    
    public String getDirector() {
        return director;
    }
    
    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }
    
    public String getIdioma() {
        return idioma;
    }
    
    public void aniadirSubtitulosDisponibles(String subtitulo) {
        this.subtitulosDisponibles.add(subtitulo);
    }
    
    public void eliminarSubtitulosDisponibles(String subtitulo) {
        this.subtitulosDisponibles.remove(subtitulo);
    }
    
    public LinkedList<String> getSubtitulosDisponibles() {
        return subtitulosDisponibles;
    }
    
    public void setPoster(String poster) {
        this.poster = poster;
    }
    
    public String getPoster() {
        return poster;
    }
}

