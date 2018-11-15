package gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ViewGUI extends HBox {

    private Image leftImage;
    private Image rightImage;
    private Image originalImage;

    private ImageView leftView;
    private ImageView rightView;

    public ViewGUI(String imagePath) {
        try {

            FileInputStream inLeft = new FileInputStream(new File(imagePath));
            FileInputStream inRight = new FileInputStream(new File(imagePath));


            this.leftImage = new Image(inLeft);
            this.originalImage = this.leftImage;
            this.leftView = new ImageView(this.leftImage);
            this.leftView.setPreserveRatio(true);
            this.leftView.setFitWidth(640);
            this.leftView.setFitHeight(360);

            this.rightImage = new Image(inRight);
            this.rightView = new ImageView(this.rightImage);
            this.rightView.setPreserveRatio(true);
            this.rightView.setFitWidth(640);
            this.rightView.setFitHeight(360);

            super.getChildren().addAll(this.leftView, this.rightView);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Image getLeftImage() { return this.leftImage; }
    public Image getRightImage() { return this.rightImage; }
    public Image getOriginalImage() { return this.originalImage; }

    public void setLeftImage(Image left) {
        this.leftImage = left;
        this.leftView.setImage(this.leftImage);
    }

    public void setRightImage(Image right) {
        this.rightImage = right;
        this.rightView.setImage(this.rightImage);
    }
}