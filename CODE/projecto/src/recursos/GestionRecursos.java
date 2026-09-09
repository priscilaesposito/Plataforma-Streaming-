package recursos;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.net.URL;

public class GestionRecursos {
    
    // Ruta base para los recursos
    private static final String RESOURCE_PATH = "/recursos/";
    
    public static ImageIcon loadImage(String fileName) {
        try {
            URL imageURL = GestionRecursos.class.getResource(RESOURCE_PATH + fileName);
            if (imageURL != null) {
                return new ImageIcon(imageURL);
            } else {
                System.err.println("No se pudo encontrar la imagen: " + RESOURCE_PATH + fileName);
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error al cargar la imagen " + fileName + ": " + e.getMessage());
            return null;
        }
    }
    
    public static ImageIcon loadScaledImage(String fileName, int width, int height) {
        ImageIcon originalIcon = loadImage(fileName);
        if (originalIcon != null) {
            Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        }
        return null;
    }
    
    public static ImageIcon loadImageWithFallback(String fileName, int width, int height) {
        // Intentar cargar la imagen desde archivo
        ImageIcon loadedImage = loadScaledImage(fileName, width, height);
     return loadedImage;
        
    }
    
}