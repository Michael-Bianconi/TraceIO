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

    private History<Image> history;

    public ViewGUI(String imagePath) {
        this(new File(imagePath));
    }

    public ViewGUI(File inFile) {
        try {

            FileInputStream inLeft = new FileInputStream(inFile);
            FileInputStream inRight = new FileInputStream(inFile);


            this.leftImage = new Image(inLeft);
            this.originalImage = this.leftImage;
            this.leftView = new ImageView(this.leftImage);
            this.leftView.setPreserveRatio(true);
            this.leftView.setFitWidth(456);
            this.leftView.setFitHeight(456);

            this.rightImage = new Image(inRight);
            this.rightView = new ImageView(this.rightImage);
            this.rightView.setPreserveRatio(true);
            this.rightView.setFitWidth(456);
            this.rightView.setFitHeight(456);

            this.history = new History<>(10);

            super.getChildren().addAll(this.leftView, this.rightView);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Image getLeftImage() { return this.leftImage; }
    public Image getRightImage() { return this.rightImage; }
    public Image getOriginalImage() { return this.originalImage; }
    public History<Image> getHistory() { return this.history; }

    public void setLeftImage(Image left) {
        this.leftImage = left;
        this.leftView.setImage(this.leftImage);
    }

    public void setRightImage(Image right) { this.setRightImage(right, true); }
    public void setRightImage(Image right, boolean addToHistory) {
        this.rightImage = right;
        this.rightView.setImage(this.rightImage);
        if (addToHistory) { this.history.push(right); }
    }

    public void load(File in) {

        try {
            FileInputStream inLeft = new FileInputStream(in);
            FileInputStream inRight = new FileInputStream(in);


            this.leftImage = new Image(inLeft);
            this.originalImage = this.leftImage;
            this.leftView.setImage(this.leftImage);

            this.rightImage = new Image(inRight);
            this.rightView.setImage(this.rightImage);

        } catch (FileNotFoundException e) { throw new RuntimeException(e); }
    }
}
