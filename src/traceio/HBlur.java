package traceio;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class HBlur {

    private HBlur() {   }

    public static Image hBlur(Image in, int scanRange, int magnitude) {

        WritableImage out = new WritableImage(
                in.getPixelReader(),
                (int) in.getWidth(),
                (int) in.getHeight()
        );

        PixelReader reader = in.getPixelReader();
        PixelWriter writer = out.getPixelWriter();

        for (int row = 0; row < in.getHeight(); row++) {
            for (int col = 0; col < in.getWidth(); col++) {

                writer.setColor(col, row, getBlurColor(in, col, row, scanRange, magnitude));
            }
        }

        return out;
    }


    private static Color getBlurColor(
            Image in, int column, int row, int scanRange, int magnitude) {

        PixelReader reader = in.getPixelReader();
        Color start = reader.getColor(column, row);
        double red = start.getRed();
        double green = start.getGreen();
        double blue = start.getBlue();

        int colIndex = column;
        while(colIndex < in.getWidth() && colIndex - column < scanRange) {

            Color next = reader.getColor(colIndex, row);
            red += (next.getRed() - red) / magnitude;
            green += (next.getGreen() - green) / magnitude;
            blue += (next.getBlue() - blue) / magnitude;
            colIndex++;
        }

        return new Color(red, green, blue, start.getOpacity());
    }
}