package utilidades;

import java.util.Comparator;
import model.Usuario;

public class OrdenarPorMail implements Comparator<Usuario> {

    @Override
    public int compare(Usuario u1, Usuario u2) {
        return u1.getCorreo().compareTo(u2.getCorreo());
    }
}