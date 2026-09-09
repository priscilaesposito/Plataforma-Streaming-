package utilidades;

import java.util.Comparator;
import model.Pelicula;

public class OrdenarPorPrimerGenero implements Comparator<Pelicula> {

    @Override
    public int compare(Pelicula p1, Pelicula p2) {
        // Asumimos que la lista de generos no esta vacia (esto se valida en el
        // registro).
        String g1 = p1.getGeneros().getFirst();
        String g2 = p2.getGeneros().getFirst();
        return g1.compareTo(g2);
    }
}