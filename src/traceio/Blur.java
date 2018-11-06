package traceio;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

/**
 * Holds the methods that apply a Gaussian blur to the image
 * @author Michael Bianconi
 * @author https://www.github.com/Michael-Bianconi
 * @version 1
 * @since 05 November 2018
 */
public class Blur {


    /**
     *  Applies a Gaussian blur to the image with a kernel size of 5.
     *
     * @param in Image to blur.
     * @return The blurred image.
     */
    public static Image blur(Image in) {

        return blur(in, 5);
    }

    /**
     * Applies a Gaussian blur to the image.
     *
     * @param in Image to blur.
     * @param kernelSize The larger the size, the blur it gets, but it also
     *                   requires exponentially more computation time. 5 is
     *                   a good value. Must be odd.
     *
     * @return The blurred image.
     */
    public static Image blur(Image in, int kernelSize) {

        WritableImage out = new WritableImage(
                in.getPixelReader(), (int)in.getWidth(), (int)in.getHeight());
        PixelWriter writer = out.getPixelWriter();
        PixelReader reader = in.getPixelReader();

        double[][] kernel = buildKernel(5);

        for (int y = 0; y < in.getHeight(); y++) {
            for (int x = 0; x < in.getWidth(); x++) {

                int color = getAverage(in, x, y, 6);
                //System.out.println(reader.getArgb(x, y) + " -> " + color);
                writer.setArgb(x, y, color);
            }
        }

        return out;
    }


    /**
     * Builds the kernel from the given kernel size.
     *
     * This proving to be a pain so I'm returning a size-5
     * pre-determined kernel for right now.
     *
     * @param size Size of the Kernel.
     * @return Array of values representing the Gaussian curve.
     */
    private static double[][] buildKernel(int size) {
        
        double d = 273;

        return new double[][] {
                {1/d,  4/d,  7/d,  4/d, 1/d},
                {4/d, 16/d, 26/d, 16/d, 4/d},
                {7/d, 26/d, 41/d, 26/d, 7/d},
                {4/d, 16/d, 26/d, 16/d, 4/d},
                {1/d,  4/d,  7/d,  4/d, 1/d}
        };
    }


    /**
     *  Determines the blurred pixel value by applying the Gaussian
     *  kernel and summing up the resulting values.
     *
     * @param in Image to process.
     * @param x X coordinate of the pixel.
     * @param y Y coordinate of the pixel.
     * @param kernel Kernel to apply.
     * @return The new value of the pixel.
     */
    private static int applyKernel(Image in, int x, int y, double[][] kernel) {

        int width = (int) in.getWidth();
        int height = (int) in.getHeight();
        int range = (int) Math.floor(kernel.length / (double) 2);

        // make sure we don't go out of bounds
        int startx = (x - range < 0) ? 0 : x - range;
        int starty = (y - range < 0) ? 0 : y - range;
        int endx = (x + range > width) ? width : x + range;
        int endy = (y + range > height) ? height : y + range;

        double result = 0;
        PixelReader reader = in.getPixelReader();

        for (int kX = startx; kX < endx; kX++) {
            for (int kY = starty; kY < endy; kY++) {

                result += (reader.getArgb(kX, kY) * kernel[kY - starty][kX - startx]);
            }
        }

        return (int) Math.round(result);
    }


    /**
     * Return the average value of the pixels surrounding x,y.
     *
     * @param in Image to process.
     * @param x X coordinate.
     * @param y Y coordinate.
     * @param scanRange Range to scan (from x-range to x+range)
     * @return The rounded average of all pixel values in range.
     */
    private static int getAverage(Image in, int x, int y, int scanRange) {

        PixelReader reader = in.getPixelReader();
        int middle = reader.getArgb(x, y);
        int starty = y - scanRange;
        int endy = y + scanRange;
        int startx = x - scanRange;
        int endx = x + scanRange;
        int average_alpha = 0;
        int average_red = 0;
        int average_green = 0;
        int average_blue = 0;


        if (starty < 0) { starty = 0; }
        if (endy > in.getHeight()) { endy = (int) in.getHeight(); }
        if (startx < 0) { startx = 0; }
        if (endx > in.getWidth()) { endx = (int) in.getWidth(); }

        // top
        for (int r = starty; r < endy; r++) {
            for (int c = startx; c < endx; c++) {

                average_alpha += (reader.getArgb(c, r) & 0xFF000000) >> 24;
                average_red += (reader.getArgb(c, r) & 0x00FF0000) >> 16;
                average_green += (reader.getArgb(c, r) & 0x0000FF00) >> 8;
                average_blue += (reader.getArgb(c, r) & 0x000000FF);
            }
        }

        average_alpha /= ((endy - starty) * (endx - startx));
        average_red /= ((endy - starty) * (endx - startx));
        average_green /= ((endy - starty) * (endx - startx));
        average_blue /= ((endy - starty) * (endx - startx));

        return average_alpha << 24
                | average_red << 16
                | average_green << 8
                | average_blue;
    }
}
