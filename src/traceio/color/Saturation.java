package traceio.color;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;


/**
 * Static methods that saturate and desaturate images.
 * @author Michael Bianconi
 * @version 1
 * @since 18 November 2018
 */
public class Saturation {


    /** Static class. */
    private Saturation() {   }


    /**
     * Saturate the image.
     * @param in Image to Saturate.
     * @return Saturated image.
     */
    public static Image saturate(Image in) { return saturate(in, 1); }


    /**
     * Saturates the image. The higher the magnitude, the higher
     * the saturation.
     *
     * @param in Image to saturate.
     * @param magnitude Number of time to saturate (> 0)
     * @return The saturated image.
     */
    public static Image saturate(Image in, int magnitude) {

        // magnitude cannot be less than 1
        assert magnitude > 0: "Saturation magnitude must be greater than 0!:";

        // Create the image to return
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

                // Get the color
                Color c = reader.getColor(col, row);

                // Saturate it magnitude-times
                for (int m = 0; m < magnitude; m++) {
                    c = c.saturate();
                }

                // Write the new color
                writer.setColor(col, row, c);
            }
        }

        // Return the saturated image
        return out;
    }


    /**
     * Desaturate the image.
     * @param in Image to desaturate.
     * @return Desaturated image.
     */
    public static Image desaturate(Image in) {
        return desaturate(in, 1);
    }


    /**
     * Desaturates the image. The higher the magnitude, the higher
     * the desaturation.
     *
     * @param in Image to desaturate.
     * @param magnitude Number of time to desaturate.
     * @return The desaturated image.
     */
    public static Image desaturate(Image in, int magnitude) {

        // magnitude cannot be less than 1
        assert magnitude > 0: "Desaturation magnitude must be greater than 0!";

        // Create a new image to return
        WritableImage out = new WritableImage(
                in.getPixelReader(),
                (int) in.getWidth(),
                (int) in.getHeight()
        );

        // Create the image accessors
        PixelReader reader = in.getPixelReader();
        PixelWriter writer = out.getPixelWriter();

        // For each pixel in the image
        for (int row = 0; row < in.getHeight(); row++) {
            for (int col = 0; col < in.getWidth(); col++) {

                // Get the color
                Color c = reader.getColor(col, row);

                // Desaturate it magnitude-times
                for (int m = 0; m < magnitude; m++) {
                    c = c.desaturate();
                }

                // Write that color to the new image
                writer.setColor(col, row, c);
            }
        }

        // Return the new image
        return out;
    }


    /**
     * Converts the image into grayscale.
     * @param in Image to convert.
     * @return Grayscaled image.
     */
    public static Image grayscale(Image in) {

        // Create the new image
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

                // Turn it into grayscale
                writer.setColor(col, row, reader.getColor(col, row).grayscale());
            }
        }

        // Return the grayscale image
        return out;
    }

}
