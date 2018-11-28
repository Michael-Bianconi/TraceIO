package gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import traceio.Trace;


public class TraceGUI extends VBox {

    private ColorPicker fgColorPicker;
    private ColorPicker bgColorPicker;
    private Slider scanRangeSlider;
    private Button button;

    /**
     * Create the GUI
     * @param imageViews This GUI will change the images in this pane.
     */
    public TraceGUI(ViewGUI imageViews) {

        super();

        // foreground and background colors
        this.fgColorPicker = new ColorPicker(Color.BLACK);
        this.bgColorPicker = new ColorPicker(Color.WHITE);

        // min, max, current
        this.scanRangeSlider = new Slider(1, 10, 5);
        this.scanRangeSlider.setShowTickMarks(true);
        this.scanRangeSlider.setShowTickLabels(true);

        this.button = new Button("Trace");
        this.button.prefWidthProperty().bind(super.widthProperty());
        this.button.setAlignment(Pos.BOTTOM_CENTER);

        GridPane sliders = new GridPane();
        sliders.add(new Label("Scan Range"), 0, 0);
        sliders.add(scanRangeSlider, 1,0);
        sliders.add(new Label("Foreground"), 0, 1);
        sliders.add(fgColorPicker, 1, 1);
        sliders.add(new Label("Background"), 0, 2);
        sliders.add(bgColorPicker, 1, 2);

        super.getChildren().addAll(sliders,button);

        // when the solidify button is pressed
        this.button.setOnAction(buttonActionEvent -> {

            // set the right image to the traced left image
            imageViews.setRightImage(Trace.trace(imageViews.getLeftImage(),
                                                 getScanRange(),
                                                 getFGColor(),
                                                 getBGColor()));
        });
    }

    public int getScanRange() { return (int) this.scanRangeSlider.getValue(); }
    public Color getFGColor() { return this.fgColorPicker.getValue(); }
    public Color getBGColor() { return this.bgColorPicker.getValue(); }

}
