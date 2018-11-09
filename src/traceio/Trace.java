package traceio;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Trace {

    private static final int THRESHOLD = 0xFFFFAAAA * 60;

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
    public static Image trace(Image in, int scanRange, Color fg, Color bg) {

        int numRows = (int) in.getHeight() - scanRange;
        int numCols = (int) in.getWidth() - scanRange;

        WritableImage out = new WritableImage(
                in.getPixelReader(),
                (int) in.getWidth(),
                (int) in.getHeight());

        PixelWriter writer = out.getPixelWriter();

        for (int row = 0; row < in.getHeight(); row++) {

            for (int col = 0; col < in.getWidth(); col++) {

                int delta = getDelta(in, col, row, scanRange);

                if (delta > THRESHOLD) {

                    writer.setColor(col, row, bg);

                } else {

                    writer.setColor(col, row, fg);
                }
            }
        }

        return out;
    }
}
