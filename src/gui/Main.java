package gui;

import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import traceio.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private String inFileName;
    private String outFileName;

    private ThumbnailBarGUI thumbnailPane;
    private ViewGUI viewGUI;

    @Override
    public void init() {

        this.inFileName = getParameters().getUnnamed().get(0);
        this.outFileName = getParameters().getUnnamed().get(1);
    }


    @Override
    public void start(Stage stage) {
 
        //Creating a scene object
        VBox scenePane = new VBox();
        Node mainPane = makeMainPane();

        scenePane.getChildren().add(new MenuBarGUI(stage,this.viewGUI));
        scenePane.getChildren().add(mainPane);

        Scene scene = new Scene(scenePane);

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
        Tab traceTab = new Tab("Trace", new TraceGUI(this.viewGUI, this.thumbnailPane));
        Tab solidifyTab = new Tab("Solidify", new SolidifyGUI(this.viewGUI, this.thumbnailPane));
        Tab overlayTab = new Tab("Overlay", new OverlayGUI(this.viewGUI, this.thumbnailPane));
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

        swapBtn.prefWidthProperty().bind(optionsPane.widthProperty());
        swapBtn.setOnAction(actionEvent -> {
            this.viewGUI.setLeftImage(this.viewGUI.getRightImage());
        });

        resetBtn.prefWidthProperty().bind(optionsPane.widthProperty());
        resetBtn.setOnAction(actionEvent -> {
            this.viewGUI.setLeftImage(this.viewGUI.getOriginalImage());
        });


        return controlPanel;
    }


    private Node makeThumbnailPane() {
        ThumbnailBarGUI gui = new ThumbnailBarGUI();
        Thumbnail thumbnail = new Thumbnail(this.viewGUI.getLeftImage());
        thumbnail.imageSetOnAction(actionEvent -> {
            this.viewGUI.setLeftImage(thumbnail.getImage());
        });
        this.thumbnailPane = gui;
        return gui;
    }

    private BorderPane makeMainPane() {


        this.viewGUI = new ViewGUI(inFileName);

        BorderPane mainPane = new BorderPane();
        mainPane.setBottom(makeThumbnailPane());
        mainPane.setCenter(this.viewGUI);
        mainPane.setRight(makeControlPanel());

        return mainPane;
    }

    public static void main(String args[]) {

        launch(args);
    }
}