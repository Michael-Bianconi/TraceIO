package traceio;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Trace {

    /**
     * Get the difference of the given pixel from the surrounding pixels.
     */
    private static int getDelta(Image in, int x, int y, int scanRange) {

        PixelReader reader = in.getPixelReader();
        int middle = reader.getArgb(x,y);
        int starty = y - scanRange;
        int endy = y + scanRange;
        int startx = x - scanRange;
        int endx = x + scanRange;
        int delta = 0;

        if (starty < 0) { starty = 0; }
        if (endy > in.getHeight()) { endy = (int) in.getHeight(); }
        if (startx < 0) { startx = 0; }
        if (endx > in.getWidth()) { endx = (int) in.getWidth(); }

        for (int r = starty; r < endy; r++) {
            for (int c = startx; c < endx; c++) {

                delta += middle - reader.getArgb(c, r);
            }
        }

        return delta;
    }


    /**
     * Trace!!!
     */
    public static Image trace(Image in, int scanRange, double threshold) {

        int numRows = (int) in.getHeight() - scanRange;
        int numCols = (int) in.getWidth() - scanRange;

        WritableImage out = new WritableImage(
                in.getPixelReader(),
                (int) in.getWidth(),
                (int) in.getHeight());

        PixelWriter writer = out.getPixelWriter();

        for (int row = scanRange; row < numRows; row++) {

            for (int col = scanRange; col < numCols; col++) {

                int delta = getDelta(in, col, row, scanRange);

                if (delta > threshold) {

                    writer.setArgb(col, row, 0xFFFFFFFF);

                } else {

                    writer.setArgb(col, row, 0xFF000000);
                }
            }
        }

        return out;
    }


    private static int average(Image in, int y, int x, int scanRange) {

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


    /**
     * Blurs the image based on the scanRange
     *
     * @param in Image to blur
     * @param scanRange Number of pixels to look at when blurring (6 is good)
     *
     * @return The new, blurred image
     */
    public static Image smooth(Image in, int scanRange) {

        int numRows = (int) in.getHeight() - scanRange;
        int numCols = (int) in.getWidth() - scanRange;
        WritableImage out = new WritableImage(
                in.getPixelReader(),
                (int) in.getWidth(),
                (int) in.getHeight());
        PixelWriter writer = out.getPixelWriter();

        for (int row = scanRange; row < numRows; row++) {

            for (int col = scanRange; col < numCols; col++) {

                writer.setArgb(col, row, average(in, row, col, scanRange));
            }
        }

        return out;
    }
}
