package gui;

import traceio.Trace;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private String inFileName;
    private String outFileName;
    private Image originalImage;
    private Image inImage;
    private Image outImage;
    private ImageView inView;
    private ImageView outView;

    @Override
    public void init() {

        this.inFileName = getParameters().getUnnamed().get(0);
        this.outFileName = getParameters().getUnnamed().get(1);
    }


    @Override
    public void start(Stage stage) {
 
        //Creating a scene object
        Scene scene = new Scene(makeMainPane());

        //Setting title to the Stage
        stage.setTitle("TraceIO");

        //Adding scene to the stage
        stage.setScene(scene);

        //Displaying the contents of the stage
        stage.show();
    }


    /**
     * Creates an HBox populated with 2 identical Images - taken from the
     * inFileName instance variable.
     *
     * @return An HBox with two Images.
     */
    private HBox makeImageViews() {

        HBox imageViews = new HBox();

        try {

            this.inImage = new Image(new FileInputStream(this.inFileName));
            this.originalImage = this.inImage;
            this.inView = new ImageView(this.inImage);
            inView.setPreserveRatio(true);
            inView.setFitWidth(640);
            inView.setFitHeight(360);

            this.outImage = new Image(new FileInputStream(this.inFileName));
            this.outView = new ImageView(this.outImage);
            outView.setPreserveRatio(true);
            outView.setFitWidth(640);
            outView.setFitHeight(360);

            imageViews.getChildren().addAll(inView, outView);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return imageViews;
    }


    /**
     * Make the buttons that go on the right side of the window.
     *
     * @return VBox with the buttons.
     */
    private VBox makeControlPanel() {

        VBox pane = new VBox();

        Button smoothBtn = new Button("Smooth");
        smoothBtn.setOnAction(actionEvent ->  {
            System.out.println("Makin it smooth");
            this.outImage = Trace.smooth(this.inImage, 6);
            this.outView.setImage(this.outImage);
        });

        Button traceBtn = new Button("Trace");
        traceBtn.setOnAction(actionEvent -> {
            System.out.println("Tracing the shit out of this");
            this.outImage = Trace.trace(this.inImage, 6,0xFFFF8888 * 60);
            this.outView.setImage(this.outImage);
        });

        Button swapBtn = new Button("Swap");
        swapBtn.setOnAction(actionEvent -> {
            System.out.println("Swapping it");
            this.inImage = this.outImage;
            this.inView.setImage(this.inImage);
        });

        Button saveBtn = new Button("Save");
        saveBtn.setOnAction(actionEvent -> {
            System.out.println("Saving it");
            Trace.save(this.outImage, this.outFileName);
        });

        Button resetBtn = new Button("Reset");
        resetBtn.setOnAction(actionEvent -> {
            System.out.println("Reseti spaghetti");
            this.inImage = this.originalImage;
            this.inView.setImage(this.inImage);
        });

        pane.getChildren().addAll(smoothBtn, traceBtn, swapBtn, saveBtn, resetBtn);

        return pane;
    }


    private BorderPane makeMainPane() {

        BorderPane mainPane = new BorderPane();

        mainPane.setCenter(makeImageViews());
        mainPane.setRight(makeControlPanel());

        return mainPane;
    }

    public static void main(String args[]) {

        launch(args);
    }
}