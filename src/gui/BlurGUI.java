package gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import traceio.Blur;
import traceio.HBlur;


public class BlurGUI extends VBox {

    private Slider scanRangeSlider;
    private Slider magnitudeSlider;
    private Button blurButton;
    private Button hBlurButton;

    public BlurGUI(ViewGUI imageViews) {
        this(imageViews, null);
    }

    /**
     * Create the GUI
     * @param imageViews This GUI will change the images in this pane.
     */
    public BlurGUI(ViewGUI imageViews, HistoryGUI history) {

        // min, max, current
        this.scanRangeSlider = new Slider(1, 10, 5);
        this.scanRangeSlider.setShowTickMarks(true);
        this.scanRangeSlider.setShowTickLabels(true);

        // min, max, current
        this.magnitudeSlider = new Slider(1, 10, 4);
        this.magnitudeSlider.setShowTickMarks(true);
        this.magnitudeSlider.setShowTickLabels(true);

        this.blurButton = new Button("Blur");
        this.blurButton.prefWidthProperty().bind(super.widthProperty());
        this.blurButton.setAlignment(Pos.BOTTOM_CENTER);

        this.hBlurButton = new Button("HBlur");
        this.hBlurButton.prefWidthProperty().bind(super.widthProperty());
        this.hBlurButton.setAlignment(Pos.BOTTOM_CENTER);

        GridPane sliders = new GridPane();
        sliders.add(new Label("Scan Range"), 0, 0);
        sliders.add(scanRangeSlider, 1,0);
        sliders.add(new Label("Magnitude"), 0, 1);
        sliders.add(magnitudeSlider, 1, 1);

        super.getChildren().addAll(sliders,blurButton, hBlurButton);

        // when the solidify button is pressed
        this.blurButton.setOnAction(buttonActionEvent -> {

            // set the right image to the traced left image
            imageViews.setRightImage(Blur.blur(
                    imageViews.getLeftImage(),
                    getScanRange()
            ));

            // if applicable, create a thumbnail of the result as well
            if (history != null) {
                history.addBox(imageViews.getRightImage(), "Blur");
            }
        });

        this.hBlurButton.setOnAction(hBlurActionEvent -> {

            // set the right image to the blurred left image
            imageViews.setRightImage(HBlur.hBlur(
                    imageViews.getLeftImage(),
                    getScanRange(),
                    getMagnitude()
            ));

            if (history != null) {
                history.addBox(imageViews.getRightImage(), "HBlur");
            }
        });

    }

    public int getScanRange() { return (int) this.scanRangeSlider.getValue(); }
    public int getMagnitude() { return (int) this.magnitudeSlider.getValue(); }

}
