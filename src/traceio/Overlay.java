package traceio;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Overlay {

    private Overlay() {   }

    public static Image overlay(Image in1, Image in2, Color ignore) {

        ArrayList<Color> ignoredColors = new ArrayList<>();
        ignoredColors.add(ignore);
        return Overlay.overlay(in1, in2, ignoredColors);
    }

    /**
     * Superimpose in2 onto in1, turning ignored colors completely transparent.
     * As it is right now, unless the colors are completely ignored
     * @param in1 Base image
     * @param in2 Image to overlay on top of in1
     * @param ignore Colors to ignore
     * @return Overlayed images.
     */
    public static Image overlay(Image in1, Image in2, ArrayList<Color> ignore) {

        System.out.println("in1: " + in1.getWidth() + " x " + in1.getHeight());
        System.out.println("in2: " + in2.getWidth() + " x " + in2.getHeight());

        double numCols = in1.getWidth() < in2.getWidth() ? in1.getWidth() : in2.getWidth();
        double numRows = in1.getHeight() < in2.getHeight() ? in1.getHeight() : in2.getHeight();

        PixelReader reader1 = in1.getPixelReader();
        PixelReader reader2 = in2.getPixelReader();

        WritableImage out = new WritableImage(
                in1.getPixelReader(), (int)numCols, (int)numRows);
        PixelWriter writer = out.getPixelWriter();

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {

                Color under = reader1.getColor(col, row);
                Color over = reader2.getColor(col, row);

                if (ignore.contains(over)) { writer.setColor(col, row, under); }
                else { writer.setColor(col, row, blend(over, under)); }
            }
        }

        return out;
    }


    /**
     * Get the average of the RGB channels
     * @param c1 Color 1
     * @param c2 Color 2
     * @return Color 1 blended with Color 2
     */
    private static Color blend(Color c1, Color c2) {

        double alpha = 1 - (1 - c1.getOpacity()) * (1 - c2.getOpacity());

        double red = c1.getRed() * c1.getOpacity() / alpha
                   + c2.getRed() * c2.getOpacity() * (1 - c1.getOpacity()) / alpha;

        double blue = c1.getBlue() * c1.getOpacity() / alpha
                    + c2.getBlue() * c2.getOpacity() * (1 - c1.getOpacity()) / alpha;

        double green = c1.getGreen() * c1.getOpacity() / alpha
                     + c2.getGreen() * c2.getOpacity() * (1 - c1.getOpacity()) / alpha;

        return new Color(red, green, blue, alpha);
    }
}
