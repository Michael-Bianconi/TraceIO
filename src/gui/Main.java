package gui;

import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import traceio.*;

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
    private ThumbnailGUI thumbnailPane;

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
    private Node makeControlPanel() {

        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        Tab blurTab = new Tab("Blur");
        Tab traceTab = new Tab("Trace");
        Tab solidifyTab = new Tab("Solidify");
        Tab overlayTab = new Tab("Overlay");
        tabPane.getTabs().addAll(traceTab,blurTab,solidifyTab,overlayTab);

        VBox optionsPane = new VBox();
        Button saveBtn = new Button("Save");
        Button swapBtn = new Button("Swap");
        Button resetBtn = new Button("Reset");
        optionsPane.getChildren().addAll(saveBtn,swapBtn,resetBtn);

        GridPane controlPanel = new GridPane();
        controlPanel.setGridLinesVisible(true);
        controlPanel.add(tabPane, 0, 0);
        controlPanel.add(optionsPane, 0, 1);


        Button blurBtn = new Button("Blur");
        blurTab.setContent(blurBtn);
        blurBtn.setOnAction(actionEvent ->  {
            this.outImage = Blur.blur(this.inImage);
            this.outView.setImage(this.outImage);
        });

        TraceGUI traceGUI = new TraceGUI();
        traceTab.setContent(traceGUI);
        traceGUI.setOnAction(actionEvent -> {
            this.outImage = Trace.trace(this.inImage,
                                        traceGUI.getScanRange(),
                                        traceGUI.getFGColor(),
                                        traceGUI.getBGColor());
            this.addThumbnailFromOutImage();
            this.outView.setImage(this.outImage);
        });

        SolidifyGUI solidifyGUI = new SolidifyGUI();
        solidifyTab.setContent(solidifyGUI);
        solidifyGUI.setOnAction(actionEvent ->  {
            this.outImage = Solidify.solidify(this.inImage,
                                              solidifyGUI.getKernelSize(),
                                              solidifyGUI.getRThreshold(),
                                              solidifyGUI.getGThreshold(),
                                              solidifyGUI.getBThreshold());
            this.addThumbnailFromOutImage();
            this.outView.setImage(this.outImage);
        });

        OverlayGUI overlayGUI = new OverlayGUI();
        overlayTab.setContent(overlayGUI);
        overlayGUI.setOnAction(actionEvent -> {
            this.outImage = Overlay.overlay(this.inImage,
                                            this.outImage,
                                            overlayGUI.getIgnoredColor());
            this.addThumbnailFromOutImage();
            this.outView.setImage(this.outImage);
        });

        swapBtn.prefWidthProperty().bind(optionsPane.widthProperty());
        swapBtn.setOnAction(actionEvent -> {
            System.out.println("Swapping it");
            this.inImage = this.outImage;
            this.inView.setImage(this.inImage);
        });

        saveBtn.prefWidthProperty().bind(optionsPane.widthProperty());
        saveBtn.setOnAction(actionEvent -> {
            System.out.println("Saving it");
            Save.save(this.outImage, this.outFileName);
        });

        resetBtn.prefWidthProperty().bind(optionsPane.widthProperty());
        resetBtn.setOnAction(actionEvent -> {
            System.out.println("Reseti spaghetti");
            this.inImage = this.originalImage;
            this.inView.setImage(this.inImage);
        });


        return controlPanel;
    }


    private Node makeThumbnailPane() {
        ThumbnailGUI gui = new ThumbnailGUI();
        Thumbnail thumbnail = new Thumbnail(this.inImage);
        thumbnail.imageSetOnAction(actionEvent -> {
            this.inImage = thumbnail.getImage();
            this.inView.setImage(this.inImage);
        });
        this.thumbnailPane = gui;
        return gui;
    }

    private void addThumbnailFromOutImage() {
        Thumbnail nail = new Thumbnail(this.outImage);
        nail.imageSetOnAction(actionEvent -> {
            this.inImage = nail.getImage();
            this.inView.setImage(this.inImage);
        });
        this.thumbnailPane.addThumbnail(nail);
    }


    private BorderPane makeMainPane() {

        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(makeImageViews());
        mainPane.setRight(makeControlPanel());
        mainPane.setBottom(makeThumbnailPane());

        return mainPane;
    }

    public static void main(String args[]) {

        launch(args);
    }
}