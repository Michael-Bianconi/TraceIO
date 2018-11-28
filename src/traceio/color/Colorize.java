package traceio.color;


import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/** Methods that alter color channels in the images. */
public class Colorize {


    private static final double DEFAULT_TINT = 1.5;


    /** Static class. */
    private Colorize() {   }


    public static Image removeRed(Image in) {
        return tintColors(in, 0, 1, 1);
    }

    public static Image removeGreen(Image in) {
        return tintColors(in, 1, 0, 1);
    }

    public static Image removeBlue(Image in) {
        return tintColors(in, 1, 1, 0);
    }

    public static Image tintRed(Image in, double tint) {
        return tintColors(in, tint, 1, 1);
    }

    public static Image tintGreen(Image in, double tint) {
        return tintColors(in, 1, tint, 1);
    }

    public static Image tintBlue(Image in, double tint) {
        return tintColors(in, 1, 1, tint);
    }

    /**
     * Multiply each channel in the image by the respective values.
     * @param in Image to process.
     * @param red Red channel modifier (>= 0).
     * @param green Green channel modifier (>= 0).
     * @param blue Blue channel modifier (>= 0).
     * @return The new image.
     */
    public static Image tintColors(
            Image in, double red, double green, double blue) {

        // Modifiers must be between 0.0 and 1.0
        assert red >= 0: "Red channel must be greater than 0!";
        assert green >= 0: "Green channel must be greater than 0!";
        assert blue >= 0: "blue channel must be greater than 0!";

        // Create the new image
        WritableImage out = new WritableImage(
                in.getPixelReader(),
                (int) in.getWidth(),
                (int) in.getHeight()
        );

        // Image accessors
        PixelReader reader = in.getPixelReader();
        PixelWriter writer = out.getPixelWriter();

        // For each pixel in the image
        for (int row = 0; row < in.getHeight(); row++) {
            for (int col = 0; col < in.getWidth(); col++) {

                // Get the old pixel
                Color c = reader.getColor(col, row);

                // Calculate the new values (can't go above 1.0)
                double r = Math.min(c.getRed() * red, 1.0);
                double g = Math.min(c.getGreen() * green, 1.0);
                double b = Math.min(c.getBlue() * blue, 1.0);

                // Create the new pixel
                c = new Color(r, g, b, c.getOpacity());

                // Write the new pixel
                writer.setColor(col, row, c);
            }
        }

        // Return the modified image
        return out;
    }


    /**
     * Invert the colors in the image.
     * @param in Image to invert.
     * @return Inverted image.
     */
    public static Image invert(Image in) {

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

                // Invert it
                writer.setColor(col, row, reader.getColor(col, row).invert());
            }
        }

        // Return the new image
        return out;
    }


}
