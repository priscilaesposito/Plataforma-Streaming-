package enumerativo;

public enum Genero {
    ACCION,
    COMEDIA,
    DRAMA,
    TERROR,
    SUSPENSO,
    CIENCIA_FICCION,
    AVENTURA,
    ANIMACION;

    public static boolean genValido(String generoString) {
        if (generoString == null || generoString.trim().isEmpty()) {
            return false;
        }
        try {
            Genero.valueOf(generoString.trim().toUpperCase().replace(' ', '_'));
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}