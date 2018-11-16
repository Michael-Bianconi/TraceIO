package gui;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import traceio.Other;
import traceio.Save;

public class MenuBarGUI extends MenuBar {

    private final FileChooser loadChooser = new FileChooser();
    private final FileChooser saveChooser = new FileChooser();

    /**
     * The menu bar has options for loading and saving images.
     */
    public MenuBarGUI(Stage stage, ViewGUI imageViews, HistoryGUI history) {

        Menu fileMenu = new Menu("file");
        MenuItem load = new MenuItem("load");
        MenuItem save = new MenuItem("save");
        this.loadChooser.setTitle("Load a file");
        this.saveChooser.setTitle("Save this image");

        Menu imageMenu = new ImageMenu(imageViews, history);


        save.setOnAction(actionEvent -> {
            Save.save(imageViews.getRightImage(), saveChooser.showSaveDialog(stage));
        });

        load.setOnAction(actionEvent -> {
           imageViews.load(loadChooser.showOpenDialog(stage));
        });

        fileMenu.getItems().addAll(load,save);
        super.getMenus().addAll(fileMenu, imageMenu);

    }

    private class ImageMenu extends Menu {

        public ImageMenu(ViewGUI views, HistoryGUI history) {

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
                views.setRightImage(Other.brighter(views.getLeftImage()));
                if (history != null) {
                    history.addBox(views.getRightImage(), "brighter");
                }
            });


            darker.setOnAction(actionEvent -> {
                views.setRightImage(Other.darker(views.getLeftImage()));
                if (history != null) {
                    history.addBox(views.getRightImage(), "darker");
                }
            });


            saturate.setOnAction(actionEvent -> {
                views.setRightImage(Other.saturate(views.getLeftImage()));
                if (history != null) {
                    history.addBox(views.getRightImage(), "saturate");
                }
            });


            desaturate.setOnAction(actionEvent -> {
                views.setRightImage(Other.desaturate(views.getLeftImage()));
                if (history != null) {
                    history.addBox(views.getRightImage(), "desaturate");
                }
            });


            invert.setOnAction(actionEvent -> {
                views.setRightImage(Other.invert(views.getLeftImage()));
                if (history != null) {
                    history.addBox(views.getRightImage(), "invert");
                }
            });


            grayscale.setOnAction(actionEvent -> {
                views.setRightImage(Other.grayscale(views.getLeftImage()));
                if (history != null) {
                    history.addBox(views.getRightImage(), "grayscale");
                }
            });
        }
    }
}
