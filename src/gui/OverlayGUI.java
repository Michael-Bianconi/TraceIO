package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class OverlayGUI extends VBox {

    private ColorPicker ignoredColorPicker;
    private Button button;

    public OverlayGUI() {

        super();

        this.ignoredColorPicker = new ColorPicker(Color.TRANSPARENT);

        this.button = new Button("Overlay");
        this.button.prefWidthProperty().bind(super.widthProperty());
        this.button.setAlignment(Pos.BOTTOM_CENTER);

        GridPane sliders = new GridPane();
        sliders.add(new Label("Ignored"), 0, 1);
        sliders.add(ignoredColorPicker, 1, 1);

        super.getChildren().addAll(sliders,button);
    }


    public void setOnAction(EventHandler<ActionEvent> e) {
        this.button.setOnAction(e);
    }

    public Color getIgnoredColor() { return this.ignoredColorPicker.getValue(); }

}
