package model;

import java.util.LinkedList;

public class Titulo {
    private int cantVisualizaciones;
    private int cantVisualizacionesDia;
    private String tipoContenido;
    private LinkedList<String> generos;
    private LinkedList<String> paisesDisponible;
    private Metadatos Metadatos;
    private Resenia LR;

    public Titulo() {
        this.generos = new LinkedList<>();
        this.paisesDisponible = new LinkedList<>();
        this.Metadatos = new Metadatos();
        this.LR = new Resenia();
    }

    public Resenia getListasyResenias() {
        return LR;
    }

    public void setListasyResenias(Resenia lR) {
        LR = lR;
    }

    public void setCantVisualizaciones(int cantVisualizaciones) {
        this.cantVisualizaciones = cantVisualizaciones;
    }

    public Metadatos getMetadatos() {
        return Metadatos;
    }

    public void setMetadatos(Metadatos metadatos) {
        Metadatos = metadatos;
    }

    public int getCantVisualizaciones() {
        return cantVisualizaciones;
    }

    public void setCantVisualizacionesDia(int cantVisualizacionesDia) {
        this.cantVisualizacionesDia = cantVisualizacionesDia;
    }

    public int getCantVisualizacionesDia() {
        return cantVisualizacionesDia;
    }

    public void setTipoContenido(String tipoContenido) {
        this.tipoContenido = tipoContenido;
    }

    public String getTipoContenido() {
        return tipoContenido;
    }

    public void anadirGeneros(String genero) {
        this.generos.add(genero);
    }

    public LinkedList<String> getGeneros() {
        return generos;
    }

    public void anadirPaisesDisponible(String pais) {
        this.paisesDisponible.add(pais);
    }

    public void eliminarPaisesDisponibles(String pais) {
        this.paisesDisponible.remove(pais);
    }

    public LinkedList<String> getPaisesDisponible() {
        return paisesDisponible;
    }

    public void setPaisesDisponible(LinkedList<String> paisesDisponible) {
        this.paisesDisponible = paisesDisponible;
    }

    public void setGeneros(LinkedList<String> generos) {
        this.generos = generos;
    }
}
