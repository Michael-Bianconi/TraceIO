package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import traceio.Solidify;


public class SolidifyGUI extends VBox {

    private Slider rThresholdSlider;
    private Slider gThresholdSlider;
    private Slider bThresholdSlider;
    private Slider kernelSizeSlider;
    private Button button;

    public SolidifyGUI(ViewGUI imageViews) {
        this(imageViews, null);
    }

    public SolidifyGUI(ViewGUI imageViews, ThumbnailBarGUI thumbnails) {

        super();

        // min, max, current
        this.kernelSizeSlider = new Slider(1,10, 5);
        this.kernelSizeSlider.setShowTickMarks(true);
        this.kernelSizeSlider.setShowTickLabels(true);

        this.button = new Button("Solidify");
        this.button.prefWidthProperty().bind(super.widthProperty());

        this.rThresholdSlider = new Slider(0, 255, 128);
        this.rThresholdSlider.setShowTickMarks(true);
        this.rThresholdSlider.setShowTickLabels(true);

        this.gThresholdSlider = new Slider(0, 255, 128);
        this.gThresholdSlider.setShowTickMarks(true);
        this.gThresholdSlider.setShowTickLabels(true);

        this.bThresholdSlider = new Slider(0, 255, 128);
        this.bThresholdSlider.setShowTickMarks(true);
        this.bThresholdSlider.setShowTickLabels(true);

        Label rThresholdLabel = new Label("Red Threshold");
        Label gThresholdLabel = new Label("Green Threshold");
        Label bThresholdLabel = new Label("Blue Threshold");
        Label kernelSizeLabel = new Label("Kernel Size");

        GridPane sliders = new GridPane();
        sliders.add(kernelSizeLabel, 0, 0);
        sliders.add(kernelSizeSlider, 1,0);
        sliders.add(rThresholdLabel, 0, 1);
        sliders.add(rThresholdSlider, 1, 1);
        sliders.add(gThresholdLabel, 0, 2);
        sliders.add(gThresholdSlider, 1, 2);
        sliders.add(bThresholdLabel, 0, 3);
        sliders.add(bThresholdSlider, 1, 3);

        super.getChildren().addAll(sliders, button);

        button.setOnAction(buttonActionEvent -> {
            imageViews.setRightImage(Solidify.solidify(
                    imageViews.getLeftImage(),
                    getKernelSize(),
                    getRThreshold(),
                    getGThreshold(),
                    getBThreshold()));

            // if applicable, create a thumbnail of the result as well
            if (thumbnails != null) {
                Thumbnail nail = new Thumbnail(imageViews.getRightImage());
                nail.imageSetOnAction(thumbnailActionEvent -> {
                    imageViews.setLeftImage(nail.getImage());
                });

                thumbnails.addThumbnail(nail);
            }
        });
    }

    public int getKernelSize() { return (int) this.kernelSizeSlider.getValue(); }
    public int getRThreshold() { return (int) rThresholdSlider.getValue(); }
    public int getGThreshold() { return (int) gThresholdSlider.getValue(); }
    public int getBThreshold() { return (int) bThresholdSlider.getValue(); }
}
