package model;

import java.util.LinkedList;

public class Video {
	private int ultimoMinutoVisto;
	private String video;
	private double duracion;

	public int getUltimoMinutoVisto() {
		return ultimoMinutoVisto;
	}
	
	public void setUltimoMinutoVisto(int ultimoMinutoVisto) {
		this.ultimoMinutoVisto = ultimoMinutoVisto;
	}
	
	public String getArchivoVideo() {
		return video;
	}
	
	public double getDuracion() {
		return duracion;
	}
	public void setDuracion(double duracion) {
		this.duracion = duracion;
	}
	public LinkedList<String> getAudios() {
		return new LinkedList<>();
	}
	
	public LinkedList<String> getSubtitulos() {
		return new LinkedList<>();
	}
	
	public void agregarSubtitulos(String archivoSubtitulos, String idioma) {
	}
	
	public void agregarAudios(String archivoAudios, String idioma) {
	}
}
