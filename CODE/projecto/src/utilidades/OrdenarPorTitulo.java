package utilidades;

import java.util.Comparator;
import model.Pelicula;

public class OrdenarPorTitulo implements Comparator<Pelicula> {

    @Override
    public int compare(Pelicula p1, Pelicula p2) {
        return p1.getMetadatos().getTitulo().compareTo(p2.getMetadatos().getTitulo());
    }
}