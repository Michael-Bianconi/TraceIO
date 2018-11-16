package gui;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

public class HistoryGUI extends VBox {

    private int maxBoxes;
    private int nextBox;
    private int boxCount;
    private ViewGUI view;

    public HistoryGUI(ViewGUI view) {
        this(view,10);
    }


    public HistoryGUI(ViewGUI view, int maxBoxes) {
        this.maxBoxes = maxBoxes;
        this.nextBox = 0;
        this.boxCount++;
        this.view = view;
        super.setPrefWidth(100);
    }


    public void addBox(Image image, String name) {
        Button b = new Button(boxCount++ + ": " + name);
        b.setOnAction(actionEvent -> {
            this.view.setLeftImage(image);
        });
        b.prefWidthProperty().bind(super.widthProperty());

        if (super.getChildren().size() < this.maxBoxes) {
            super.getChildren().add(b);

        } else { super.getChildren().set(this.nextBox, b); }

        this.nextBox = this.nextBox < this.maxBoxes - 1 ? this.nextBox + 1 : 0;
    }

    public void setView(ViewGUI view) {
        this.view = view;
    }
}
