package traceio;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Holds methods for saving Images.
 * @author Michael Bianconi
 * @author https://www.github.com/Michael-Bianconi
 * @version 1
 * @since 05 November 2018
 */
public class Save {

    /**
     * Saves the image to the given destination as a .png file.
     *
     * @param in The Image to save.
     * @param pathOut The destination to save to.
     */
    public static void save(Image in, String pathOut) {

        save(in, new File(pathOut));
    }

    public static void save(Image in, File out) {

        BufferedImage img = SwingFXUtils.fromFXImage(in, null);

        try {

            ImageIO.write(img, "png", out);

        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }
}
