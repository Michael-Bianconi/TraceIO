package gui;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

/**
 * The thumbnail GUI holds a collection of images in a horizontal grid.
 */
public class ThumbnailGUI extends HBox {

    public ThumbnailGUI() {
        setHeight(150);
    }

    public void addThumbnail(Thumbnail thumbnail) {

        thumbnail.deleteSetOnAction(actionEvent -> {
            super.getChildren().remove(thumbnail);
        });

        super.getChildren().add(thumbnail);
    }
}
