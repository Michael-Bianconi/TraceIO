package src.traceio;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;

/**
 * TraceIO takes an image, traces it, and saves that into a new file.
 *
 * $ java Main [input file] [output file]
 *
 * @author Michael Bianconi
 * @author https://www.github.com/Michael-Bianconi
 * @since 04 November 2018
 */
public class TraceIO {

    private static final String BAD_COMMAND_LINE_ARGUMENTS =
        "$ java TraceIO [input file] [output file]";

    private static final String BAD_INPUT_FILE =
        "Something went wrong with the input file!";

    private static final String BAD_OUTPUT_FILE =
        "Something went wrong with the output file!";

    // How many pixels to look at when determining if to trace
    private int TRACE_READ_SIZE = 6;

    // How different the pixel has to be when determining to trace
    private double TRACE_THRESHOLD = 0xFFFF8888 * TRACE_READ_SIZE * 10;

    // Color to trace with
    private int TRACE_COLOR = 0xFFFFFFFF;

    // Color when not tracing
    private int TRACE_NO_COLOR = 0xFF000000;


    private File inFile;
    private File outFile;

    private BufferedImage inImage;
    private BufferedImage outImage;
    private int[][] inRGB;

    /**
     * Reads the command line arguments into the input and output files.
     * If args is invalid, exit the program.
     *
     * @param args [input file, output file]
     */
    private void parseCommandLineArguments(String[] args) {

        if (args.length != 2) {

            System.out.println(BAD_COMMAND_LINE_ARGUMENTS);
            System.exit(1);
        }

        this.inFile = new File(args[0]);
        this.outFile = new File(args[1]);
    }


    /**
     * Creates the BufferedImage from the in instance variable file.
     */
    private void readInputFile() {

        // read in the image
        try {

            this.inImage = ImageIO.read(this.inFile);
    
        } catch (IOException e) {

            System.out.println(BAD_INPUT_FILE);
            System.out.println(e);
            System.exit(1);
        }

        // get its RGB values
        int width = this.inImage.getWidth();
        int height = this.inImage.getHeight();
        this.inRGB = new int[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {

                //System.out.print(col + " " + row + " ");
                this.inRGB[row][col] = inImage.getRGB(col, row);
                //System.out.println(String.format("0x%08X", this.inRGB[row][col]));

            }
        }
    }


    /**
     * Create the output file with the same dimensions as the input file.
     * @req.me readInputFile() must have been called prior to this.
     */
    private void createOutputImage() {

        this.outImage = new BufferedImage(inImage.getWidth(),
                                          inImage.getHeight(),
                                          BufferedImage.TYPE_INT_ARGB);
    }


    /**
     * Get the difference of the given pixel from the surrounding pixels.
     */
    private int getDelta(int x, int y) {

        int middle = this.inRGB[y][x];
        int starty = y - TRACE_READ_SIZE;
        int endy = y + TRACE_READ_SIZE;
        int startx = x - TRACE_READ_SIZE;
        int endx = x + TRACE_READ_SIZE;
        int delta = 0;

        if (starty < 0) { starty = 0; }
        if (endy > inImage.getHeight()) { endy = inImage.getHeight(); }
        if (startx < 0) { startx = 0; }
        if (endx > inImage.getWidth()) { endx = inImage.getWidth(); }

        for (int r = starty; r < endy; r++) {
            for (int c = startx; c < endx; c++) {

                delta += middle - inRGB[r][c];
            }
        }

        return delta;
    }


    /**
     * Trace!!!
     */
    private void trace() {

        int numRows = this.inImage.getHeight() - TRACE_READ_SIZE;
        int numCols = this.inImage.getWidth() - TRACE_READ_SIZE;

        for (int row = TRACE_READ_SIZE; row < numRows; row++) {

            for (int col = TRACE_READ_SIZE; col < numCols; col++) {

                int delta = getDelta(col, row);

                if (delta > TRACE_THRESHOLD) {

                    this.outImage.setRGB(col, row, TRACE_COLOR);

                } else {

                    this.outImage.setRGB(col, row, TRACE_NO_COLOR);
                }
            }
        }
    }


    /**
     * Save the outImage to the outFile.
     */
    private void save() {

        try {
            ImageIO.write(this.outImage, "png", this.outFile);

        } catch (IOException e) {

            System.out.println(BAD_OUTPUT_FILE);
            System.out.println(e);
            System.exit(1);
        }

    }


    public TraceIO(String[] args) {

        parseCommandLineArguments(args);
        readInputFile();
        createOutputImage();
        trace();
        save();



    }
}