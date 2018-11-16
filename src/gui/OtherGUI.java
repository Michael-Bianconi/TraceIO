package gui;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import traceio.Other;

import javax.swing.text.View;

public class OtherGUI extends VBox {

    private Button saturateButton;
    private Button invertButton;
    private Button grayscaleButton;

    public OtherGUI(ViewGUI view, HistoryGUI history) {

        saturateButton = new Button("Saturate");
        saturateButton.prefWidthProperty().bind(super.widthProperty());
        saturateButton.setOnAction(actionEvent -> {
            view.setRightImage(Other.saturate(view.getLeftImage()));
            if (history != null) { history.addBox(view.getRightImage(), "Saturate"); }
        });
        super.getChildren().add(saturateButton);

        invertButton = new Button("invert");
        invertButton.prefWidthProperty().bind(super.widthProperty());
        invertButton.setOnAction(actionEvent -> {
            view.setRightImage(Other.invert(view.getLeftImage()));
            if (history != null) { history.addBox(view.getRightImage(), "Invert"); }
        });
        super.getChildren().add(invertButton);

        grayscaleButton = new Button("Grayscale");
        grayscaleButton.prefWidthProperty().bind(super.widthProperty());
        grayscaleButton.setOnAction(actionEvent -> {
            view.setRightImage(Other.grayscale(view.getLeftImage()));
            if (history != null) { history.addBox(view.getRightImage(), "Grayscale"); }
        });
        super.getChildren().add(grayscaleButton);
    }
}
