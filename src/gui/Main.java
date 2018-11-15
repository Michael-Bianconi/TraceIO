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

    private ThumbnailGUI thumbnailPane;
    private ViewGUI viewGUI;

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
            this.viewGUI.setRightImage(Blur.blur(this.viewGUI.getLeftImage()));
        });

        TraceGUI traceGUI = new TraceGUI();
        traceTab.setContent(traceGUI);
        traceGUI.setOnAction(actionEvent -> {
            this.viewGUI.setRightImage(Trace.trace(this.viewGUI.getRightImage(),
                                      traceGUI.getScanRange(),
                                      traceGUI.getFGColor(),
                                      traceGUI.getBGColor()));
            this.addThumbnailFromOutImage();
        });

        SolidifyGUI solidifyGUI = new SolidifyGUI();
        solidifyTab.setContent(solidifyGUI);
        solidifyGUI.setOnAction(actionEvent ->  {
            this.viewGUI.setRightImage(Solidify.solidify(this.viewGUI.getLeftImage(),
                                       solidifyGUI.getKernelSize(),
                                       solidifyGUI.getRThreshold(),
                                       solidifyGUI.getGThreshold(),
                                       solidifyGUI.getBThreshold()));
            this.addThumbnailFromOutImage();
        });

        OverlayGUI overlayGUI = new OverlayGUI();
        overlayTab.setContent(overlayGUI);
        overlayGUI.setOnAction(actionEvent -> {
            this.viewGUI.setRightImage(Overlay.overlay(this.viewGUI.getLeftImage(),
                                            this.viewGUI.getRightImage(),
                                            overlayGUI.getIgnoredColor()));
            this.addThumbnailFromOutImage();
        });

        swapBtn.prefWidthProperty().bind(optionsPane.widthProperty());
        swapBtn.setOnAction(actionEvent -> {
            this.viewGUI.setLeftImage(this.viewGUI.getRightImage());
        });

        saveBtn.prefWidthProperty().bind(optionsPane.widthProperty());
        saveBtn.setOnAction(actionEvent -> {
            Save.save(this.viewGUI.getRightImage(), this.outFileName);
        });

        resetBtn.prefWidthProperty().bind(optionsPane.widthProperty());
        resetBtn.setOnAction(actionEvent -> {
            this.viewGUI.setLeftImage(this.viewGUI.getOriginalImage());
        });


        return controlPanel;
    }


    private Node makeThumbnailPane() {
        ThumbnailGUI gui = new ThumbnailGUI();
        Thumbnail thumbnail = new Thumbnail(this.viewGUI.getLeftImage());
        thumbnail.imageSetOnAction(actionEvent -> {
            this.viewGUI.setLeftImage(thumbnail.getImage());
        });
        this.thumbnailPane = gui;
        return gui;
    }

    private void addThumbnailFromOutImage() {
        Thumbnail nail = new Thumbnail(this.viewGUI.getRightImage());
        nail.imageSetOnAction(actionEvent -> {
            this.viewGUI.setLeftImage(nail.getImage());
        });
        this.thumbnailPane.addThumbnail(nail);
    }


    private BorderPane makeMainPane() {

        this.viewGUI = new ViewGUI(inFileName);

        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(this.viewGUI);
        mainPane.setRight(makeControlPanel());
        mainPane.setBottom(makeThumbnailPane());

        return mainPane;
    }

    public static void main(String args[]) {

        launch(args);
    }
}