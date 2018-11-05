package src.traceio;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;

/**
 * TraceIO takes an image, traces it, and saves that into a new file.
 *
 * $ java TraceIO [input file] [output file]
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


    private File inFile;
    private File outFile;

    private BufferedImage inImage;
    private Graphics2D inGraphics;

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
     * Create the associated Graphics2D object as well.
     */
    private void readInputFile() {

        try {

            this.inImage = ImageIO.read(this.inFile);
            this.inGraphics = inImage.createGraphics();

        } catch (IOException e) {

            System.out.println(BAD_INPUT_FILE);
            System.out.println(e);
            System.exit(1);
        }
    }


    public TraceIO(String[] args) {

        parseCommandLineArguments(args);
        readInputFile();



    }
}