package gui;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import traceio.Save;

public class MenuBarGUI extends MenuBar {

    private Menu fileMenu;
    private MenuItem loadItem;
    private MenuItem saveItem;
    private final FileChooser loadChooser = new FileChooser();
    private final FileChooser saveChooser = new FileChooser();

    /**
     * The menu bar has options for loading and saving images.
     */
    public MenuBarGUI(Stage stage, ViewGUI imageViews) {

        this.fileMenu = new Menu("file");
        this.loadItem = new MenuItem("load");
        this.saveItem = new MenuItem("save");
        this.loadChooser.setTitle("Load a file");
        this.saveChooser.setTitle("Save this image");

        this.saveItem.setOnAction(actionEvent -> {
            Save.save(imageViews.getRightImage(), saveChooser.showSaveDialog(stage));
        });

        this.loadItem.setOnAction(actionEvent -> {
           imageViews.load(loadChooser.showOpenDialog(stage));
        });

        fileMenu.getItems().addAll(loadItem,saveItem);
        super.getMenus().addAll(fileMenu);




    }
}
