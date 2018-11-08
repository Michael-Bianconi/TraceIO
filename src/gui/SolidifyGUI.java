package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;


public class SolidifyGUI extends HBox {

    private Slider kernelSizeSlider;
    private Button button;

    public SolidifyGUI() {

        super();

        // min, max, current
        this.kernelSizeSlider = new Slider(1,10, 5);
        this.kernelSizeSlider.setShowTickMarks(true);
        this.kernelSizeSlider.setShowTickLabels(true);

        this.button = new Button("Solidify");

        super.getChildren().add(kernelSizeSlider);
        super.getChildren().add(button);
    }


    public void setOnAction(EventHandler<ActionEvent> e) {

        this.button.setOnAction(e);
    }


    public int getKernelSize() { return (int) this.kernelSizeSlider.getValue(); }
}
