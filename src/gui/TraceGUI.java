package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;


public class TraceGUI extends VBox {

    private Slider scanRangeSlider;
    private Button button;

    public TraceGUI() {

        super();

        // min, max, current
        this.scanRangeSlider = new Slider(1, 10, 5);
        this.scanRangeSlider.setShowTickMarks(true);
        this.scanRangeSlider.setShowTickLabels(true);

        this.button = new Button("Trace");

        GridPane sliders = new GridPane();
        sliders.add(new Label("Scan Range"), 0, 0);
        sliders.add(scanRangeSlider, 1,0);

        super.getChildren().addAll(button,sliders);
    }


    public void setOnAction(EventHandler<ActionEvent> e) {

        this.button.setOnAction(e);
    }


    public int getScanRange() { return (int) this.scanRangeSlider.getValue(); }

}
