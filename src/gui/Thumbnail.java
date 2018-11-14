package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class Thumbnail extends VBox {

    private Image deleteImage;
    private Image thumbnailImage;
    private Button imageButton;
    private Button deleteButton;

    public Thumbnail(Image image) {

        ImageView thumbnailImage = new ImageView(image);
        thumbnailImage.setFitHeight(100);
        thumbnailImage.setFitWidth(100);

        this.deleteImage = new Image("gui/deleteImage.png");
        this.thumbnailImage = image;

        this.imageButton = new Button();
        this.imageButton.setGraphic(thumbnailImage);

        this.deleteButton = new Button();
        this.deleteButton.setGraphic(new ImageView(deleteImage));

        super.getChildren().addAll(imageButton, deleteButton);
    }

    public void imageSetOnAction(EventHandler<ActionEvent> e) {
        this.imageButton.setOnAction(e);
    }

    public Image getImage() { return this.thumbnailImage; }

    public void deleteSetOnAction(EventHandler<ActionEvent> e) {
        this.deleteButton.setOnAction(e);
    }
}