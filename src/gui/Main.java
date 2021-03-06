package gui;

import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import traceio.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.text.View;

public class Main extends Application {

    private String inFileName;

    private ViewGUI viewGUI;
    private History<Image> history;
    private Node controlPanel;

    @Override
    public void init() {

        this.inFileName = getParameters().getUnnamed().get(0);
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
    private static Node makeControlPanel(ViewGUI view) {

        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab blurTab = new Tab("Blur", new BlurGUI(view));
        Tab traceTab = new Tab("Trace", new TraceGUI(view));
        Tab solidifyTab = new Tab("Solidify", new SolidifyGUI(view));
        Tab overlayTab = new Tab("Overlay", new OverlayGUI(view));
        tabPane.getTabs().addAll(traceTab,blurTab,solidifyTab,overlayTab);

        VBox optionsPane = new VBox();
        Button swapBtn = new Button("Swap");
        Button resetBtn = new Button("Reset");
        optionsPane.getChildren().addAll(swapBtn,resetBtn);

        GridPane controlPanel = new GridPane();
        controlPanel.setGridLinesVisible(true);
        controlPanel.add(tabPane, 0, 0);
        controlPanel.add(optionsPane, 0, 1);

        swapBtn.prefWidthProperty().bind(optionsPane.widthProperty());
        swapBtn.setOnAction(actionEvent -> {
            Image temp = view.getLeftImage();
            view.setLeftImage(view.getRightImage());
            view.setRightImage(temp, false);
        });

        resetBtn.prefWidthProperty().bind(optionsPane.widthProperty());
        resetBtn.setOnAction(actionEvent -> {
            view.setLeftImage(view.getOriginalImage());
        });


        return controlPanel;
    }


    /**
     * Create the history pane and add the current left image (original).
     * @param view View to alter when pressed.
     * @return The history pane.
     */
    private static HistoryGUI makeHistoryPanel(ViewGUI view) {
        HistoryGUI h = new HistoryGUI(view, 10);
        h.addBox(view.getLeftImage(), "Original");
        return h;
    }

    private BorderPane makeMainPane() {


        this.viewGUI = new ViewGUI(this.inFileName);
        this.history = new History<>(10);
        this.controlPanel = makeControlPanel(this.viewGUI);

        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(this.viewGUI);
        mainPane.setRight(this.controlPanel);

        return mainPane;
    }

    public static void main(String args[]) {

        launch(args);
    }
}