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

import javax.swing.text.View;

public class Main extends Application {

    private String inFileName;

    private ViewGUI viewGUI;
    private HistoryGUI history;
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
    private static Node makeControlPanel(ViewGUI view, HistoryGUI history) {

        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab blurTab = new Tab("Blur");
        Tab traceTab = new Tab("Trace", new TraceGUI(view, history));
        Tab solidifyTab = new Tab("Solidify", new SolidifyGUI(view, history));
        Tab overlayTab = new Tab("Overlay", new OverlayGUI(view, history));
        tabPane.getTabs().addAll(traceTab,blurTab,solidifyTab,overlayTab);

        VBox optionsPane = new VBox();
        Button swapBtn = new Button("Swap");
        Button resetBtn = new Button("Reset");
        optionsPane.getChildren().addAll(swapBtn,resetBtn);

        GridPane controlPanel = new GridPane();
        controlPanel.setGridLinesVisible(true);
        controlPanel.add(tabPane, 0, 0);
        controlPanel.add(optionsPane, 0, 1);


        Button blurBtn = new Button("Blur");
        blurTab.setContent(blurBtn);
        blurBtn.setOnAction(actionEvent ->  {
            view.setRightImage(Blur.blur(view.getLeftImage()));
        });

        swapBtn.prefWidthProperty().bind(optionsPane.widthProperty());
        swapBtn.setOnAction(actionEvent -> {
            view.setLeftImage(view.getRightImage());
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
        this.history = makeHistoryPanel(this.viewGUI);
        this.controlPanel = makeControlPanel(this.viewGUI, this.history);

        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(this.viewGUI);
        mainPane.setLeft(this.history);
        mainPane.setRight(this.controlPanel);

        return mainPane;
    }

    public static void main(String args[]) {

        launch(args);
    }
}