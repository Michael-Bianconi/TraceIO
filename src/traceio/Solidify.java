package traceio;


import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

/**
 * Find the most prevalent color in a group of pixels and convert them all to
 * the extreme of that color. For example, 0xFFAA0843 (redish) becomes
 * 0xFFFF0000 (pure red).
 * @author Michael Bianconi
 * @author https://www.github.com/Michael-Bianconi
 * @version 1
 * @since 07 November 2018
 */
public class Solidify {

    private static final int ALPHA = 0xFF000000;
    private static final int RED = 0x00FF0000;
    private static final int GREEN = 0x0000FF00;
    private static final int BLUE = 0x000000FF;
    private static final int BLACK = 0;

    private Solidify() {   }

    public static Image solidify(Image in) {

        return solidify(in, 5);
    }


    public static Image solidify(Image in, int kernelSize) {

        WritableImage out = new WritableImage(
                in.getPixelReader(), (int) in.getWidth(), (int) in.getHeight());

        for (int y = 0; y < in.getHeight(); y += kernelSize) {
            for (int x = 0; x < in.getWidth(); x += kernelSize) {

                splot(out, x, y, kernelSize, getSplotColor(in,x,y,kernelSize));
            }
        }

        return out;
    }


    /**
     * Recolor all pixels within the kernel.
     *
     * @param out   Image to splot.
     * @param x     Top-left corner of the kernel, > 0
     * @param y     Top-left corner of the kernel, > 0
     * @param size  Size of the kernel.
     * @param color Color to splot.
     */
    private static void splot(
            WritableImage out, int x, int y, int size, int color) {

        PixelWriter writer = out.getPixelWriter();

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {

                // make sure the pixel is in bounds
                if (c + x < out.getWidth() && r + y < out.getHeight()) {

                    writer.setArgb(c + x, r + y, color);
                }
            }
        }
    }


    /**
     * Get most used color in the kernel and maximize it.
     *
     * @param in   Image to process.
     * @param x    Top left corner of the kernel.
     * @param y    Top left corner of the kernel.
     * @param size Size of the kernel.
     * @return The determined splot color.
     */
    private static int getSplotColor(Image in, int x, int y, int size) {

        int red = 0;
        int blue = 0;
        int green = 0;
        PixelReader reader = in.getPixelReader();

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {

                if (c + x < in.getWidth() && r + y < in.getHeight()) {

                    int color = magnify(reader.getArgb(c + x, r + y));
                    System.out.println(color);

                    if ((color & RED) > 0) { red++; }
                    if ((color & BLUE) > 0) { blue++; }
                    if ((color & GREEN) > 0) { green++; }
                }
            }
        }

        int totalPixels = size * size;
        int splotColor = 0;

        if (red > totalPixels / 2) { splotColor |= RED; }
        if (blue > totalPixels / 2) { splotColor |= BLUE; }
        if (green > totalPixels / 2) { splotColor |= GREEN; }

        return splotColor | ALPHA;
    }

    /**
     * Magnify the given color.
     *
     * @param color to magnify.
     * @return Magnified color.
     */
    private static int magnify(int color){

        // break the color into ARGB
        int alpha = color & ALPHA;
        int red =   color & RED;
        int green = color & GREEN;
        int blue =  color & BLUE;

        // Set the colors to 256, 128, or 0
        if      (red > 0x00A00000) { red = RED; }
        else {                      red = BLACK; }

        if      (green > 0x0000A000) { green = GREEN; }
        else {                         green = BLACK; }

        if      (blue > 0x000000A0) { blue = BLUE; }
        else {                        blue = BLACK; }

        // combine them and return
        return alpha | red | green | blue;
    }
}