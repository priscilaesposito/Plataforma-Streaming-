package utilidades;

import java.util.Comparator;
import model.Usuario;

public class OrdenarPorNombreUsuario implements Comparator<Usuario> {

    @Override
    public int compare(Usuario u1, Usuario u2) {
        return u1.getUsername().compareTo(u2.getUsername());
    }
}