package traceio.color;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;


/**
 * Static methods that brighten and darken images.
 * @author Michael Bianconi
 * @version 1
 * @since 18 November 2018
 */
public class Brightness {


    /** Static class. */
    private Brightness() {   }


    /**
     * Brighten the image.
     * @param in Image to brighten.
     * @return Brighter image.
     */
    public static Image brighter(Image in) { return brighter(in, 1); }

    /**
     * Brightens the image. The higher the magnitude, the higher
     * the brightness.
     *
     * @param in Image to brighten.
     * @param magnitude Number of times to brighten (> 0)
     * @return The brightened image.
     */
    public static Image brighter(Image in, int magnitude) {

        // magnitude cannot be less than 1
        assert magnitude > 0: "Brighten magnitude must be greater than 0!";

        // Create the new image to write to
        WritableImage out = new WritableImage(
                in.getPixelReader(),
                (int) in.getWidth(),
                (int) in.getHeight()
        );

        // Create image accessors
        PixelReader reader = in.getPixelReader();
        PixelWriter writer = out.getPixelWriter();

        // For each pixel in the image
        for (int row = 0; row < in.getHeight(); row++) {
            for (int col = 0; col < in.getWidth(); col++) {

                // Get the original color
                Color c = reader.getColor(col, row);

                // Brighten it magnitude-times
                for (int m = 0; m < magnitude; m++) { c = c.brighter(); }

                // Write the new color to the new image
                writer.setColor(col, row, c);
            }
        }

        // Return the brightened image
        return out;
    }


    /**
     * Darken the image.
     * @param in Image to darken.
     * @return Darker image.
     */
    public static Image darker(Image in) { return darker(in, 1); }

    /**
     * Darkens the image. The higher the magnitude, the higher
     * the darkness.
     *
     * @param in Image to darken.
     * @param magnitude Number of times to darken (> 0)
     * @return The darkened image.
     */
    public static Image darker(Image in, int magnitude) {

        // magnitude cannot be less than 1
        assert magnitude > 0: "Darken magnitude must be greater than 0!";

        // Create the new image to write to
        WritableImage out = new WritableImage(
                in.getPixelReader(),
                (int) in.getWidth(),
                (int) in.getHeight()
        );

        // Create image accessors
        PixelReader reader = in.getPixelReader();
        PixelWriter writer = out.getPixelWriter();

        // For each pixel in the image
        for (int row = 0; row < in.getHeight(); row++) {
            for (int col = 0; col < in.getWidth(); col++) {

                // Get the original color
                Color c = reader.getColor(col, row);

                // Darken it magnitude-times
                for (int m = 0; m < magnitude; m++) { c = c.darker(); }

                // Write the new color to the new image
                writer.setColor(col, row, c);
            }
        }

        // Return the darkened image
        return out;
    }



}
