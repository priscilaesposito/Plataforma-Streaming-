package utilidades;

import java.util.Comparator;
import model.Pelicula;

public class OrdenarPorDuracion implements Comparator<Pelicula> {

    @Override
    public int compare(Pelicula p1, Pelicula p2) {
        double duracion1 = p1.getVideo().getDuracion();
        double duracion2 = p2.getVideo().getDuracion();
        return Double.compare(duracion1, duracion2);
    }
}