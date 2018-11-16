package traceio;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class Other {

    private Other() {   }

    public static Image saturate(Image in) {

        WritableImage out = new WritableImage(
                in.getPixelReader(),
                (int) in.getWidth(),
                (int) in.getHeight()
        );

        PixelReader reader = in.getPixelReader();
        PixelWriter writer = out.getPixelWriter();

        for (int row = 0; row < in.getHeight(); row++) {
            for (int col = 0; col < in.getWidth(); col++) {
                writer.setColor(col, row, reader.getColor(col, row).saturate());
            }
        }

        return out;
    }

    public static Image invert(Image in) {

        WritableImage out = new WritableImage(
                in.getPixelReader(),
                (int) in.getWidth(),
                (int) in.getHeight()
        );

        PixelReader reader = in.getPixelReader();
        PixelWriter writer = out.getPixelWriter();

        for (int row = 0; row < in.getHeight(); row++) {
            for (int col = 0; col < in.getWidth(); col++) {
                writer.setColor(col, row, reader.getColor(col, row).invert());
            }
        }

        return out;
    }

    public static Image grayscale(Image in) {

        WritableImage out = new WritableImage(
                in.getPixelReader(),
                (int) in.getWidth(),
                (int) in.getHeight()
        );

        PixelReader reader = in.getPixelReader();
        PixelWriter writer = out.getPixelWriter();

        for (int row = 0; row < in.getHeight(); row++) {
            for (int col = 0; col < in.getWidth(); col++) {
                writer.setColor(col, row, reader.getColor(col, row).grayscale());
            }
        }

        return out;
    }


    public static Image darker(Image in) {

        WritableImage out = new WritableImage(
                in.getPixelReader(),
                (int) in.getWidth(),
                (int) in.getHeight()
        );

        PixelReader reader = in.getPixelReader();
        PixelWriter writer = out.getPixelWriter();

        for (int row = 0; row < in.getHeight(); row++) {
            for (int col = 0; col < in.getWidth(); col++) {
                writer.setColor(col, row, reader.getColor(col, row).darker());
            }
        }

        return out;
    }

    public static Image brighter(Image in) {

        WritableImage out = new WritableImage(
                in.getPixelReader(),
                (int) in.getWidth(),
                (int) in.getHeight()
        );

        PixelReader reader = in.getPixelReader();
        PixelWriter writer = out.getPixelWriter();

        for (int row = 0; row < in.getHeight(); row++) {
            for (int col = 0; col < in.getWidth(); col++) {
                writer.setColor(col, row, reader.getColor(col, row).brighter());
            }
        }

        return out;
    }


    public static Image desaturate(Image in) {

        WritableImage out = new WritableImage(
                in.getPixelReader(),
                (int) in.getWidth(),
                (int) in.getHeight()
        );

        PixelReader reader = in.getPixelReader();
        PixelWriter writer = out.getPixelWriter();

        for (int row = 0; row < in.getHeight(); row++) {
            for (int col = 0; col < in.getWidth(); col++) {
                writer.setColor(col, row, reader.getColor(col, row).desaturate());
            }
        }

        return out;
    }
}
