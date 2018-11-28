package gui.menubar;

import gui.History;
import gui.ViewGUI;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;

public class EditMenu extends Menu {

    public EditMenu(ViewGUI view) {
        super("edit"); // initialize the menu button with the label "edit"
        super.getItems().add(undoItem(view));
        super.getItems().add(redoItem(view));
    }


    /**
     * When clicked, change the left image to the previous image in the
     * view's history.
     * @param view View to alter.
     * @return MenuItem
     */
    private static MenuItem undoItem(ViewGUI view) {

        // ctrl-z
        final KeyCodeCombination UNDO_SHORTCUT =
                new KeyCodeCombination(
                        KeyCode.Z,
                        KeyCodeCombination.CONTROL_DOWN);

        MenuItem item = new MenuItem("undo");
        item.setAccelerator(UNDO_SHORTCUT);
        item.setOnAction(actionEvent -> {
            Image previous = view.getHistory().previous();
            if (previous != null) { view.setLeftImage(previous); }
        });

        return item;
    }

    private static MenuItem redoItem(ViewGUI view) {

        // ctrl-shift-z
        final KeyCodeCombination REDO_SHORTCUT =
                new KeyCodeCombination(
                        KeyCode.Z,
                        KeyCodeCombination.CONTROL_DOWN,
                        KeyCodeCombination.SHIFT_DOWN);

        MenuItem item = new MenuItem("redo");
        item.setAccelerator(REDO_SHORTCUT);
        item.setOnAction(actionEvent -> {
            Image next = view.getHistory().next();
            if (next != null) { view.setLeftImage(next); }
        });

        return item;
    }
}
