package gui;

import gui.menubar.EditMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import traceio.Save;
import traceio.color.*;

public class MenuBarGUI extends MenuBar {

    private final FileChooser loadChooser = new FileChooser();
    private final FileChooser saveChooser = new FileChooser();

    /**
     * The menu bar has options for loading and saving images.
     */
    public MenuBarGUI(Stage stage, ViewGUI imageViews) {

        Menu fileMenu = new Menu("file");
        MenuItem load = new MenuItem("load");
        MenuItem save = new MenuItem("save");
        this.loadChooser.setTitle("Load a file");
        this.saveChooser.setTitle("Save this image");

        Menu editMenu = new EditMenu(imageViews);

        Menu imageMenu = new ImageMenu(imageViews);


        save.setOnAction(actionEvent -> {
            Save.save(imageViews.getRightImage(), saveChooser.showSaveDialog(stage));
        });

        load.setOnAction(actionEvent -> {
           imageViews.load(loadChooser.showOpenDialog(stage));
        });

        fileMenu.getItems().addAll(load,save);
        super.getMenus().addAll(fileMenu, editMenu, imageMenu);

    }

    private class ImageMenu extends Menu {

        public ImageMenu(ViewGUI views) {

            super("image");
            MenuItem brighter = new MenuItem("brighter");
            MenuItem darker = new MenuItem("darker");
            MenuItem saturate = new MenuItem("saturate");
            MenuItem desaturate = new MenuItem("desaturate");
            MenuItem invert = new MenuItem("invert");
            MenuItem grayscale = new MenuItem("grayscale");
            super.getItems().addAll(
                    brighter, darker, saturate, desaturate, invert, grayscale
            );

            brighter.setOnAction(actionEvent -> {
                views.setRightImage(Brightness.brighter(views.getLeftImage()));
            });


            darker.setOnAction(actionEvent -> {
                views.setRightImage(Brightness.darker(views.getLeftImage()));
            });


            saturate.setOnAction(actionEvent -> {
                views.setRightImage(Saturation.saturate(views.getLeftImage()));
            });


            desaturate.setOnAction(actionEvent -> {
                views.setRightImage(Saturation.desaturate(views.getLeftImage()));
            });


            invert.setOnAction(actionEvent -> {
                views.setRightImage(Colorize.invert(views.getLeftImage()));
            });


            grayscale.setOnAction(actionEvent -> {
                views.setRightImage(Saturation.grayscale(views.getLeftImage()));
            });
        }
    }
}
