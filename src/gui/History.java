package gui;


import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * Saves images to be retrieved later.
 */
public class History<E> {

    private ArrayList<E> history;
    private int currentIndex;
    private int maxSize;

    public History(int max) {
        this.history = new ArrayList<>();
        this.currentIndex = 0;
        this.maxSize = max;
    }

    /**
     * Return the previous Image.
     */
    public E previous() {

        if (currentIndex > 0 && this.history.size() > 0) {
            return this.history.get(--currentIndex);

        } else { return null; }
    }

    /**
     * Return the next Image.
     */
    public E next() {

        if (currentIndex < this.history.size()-1) {
            return this.history.get(++currentIndex);

        } else { return null; }
    }

    /**
     * Remove all item from the end of the array to currentImage index.
     * Add the new image to the end of the history. Set the index to the
     * new end of the list. If the list would exceed the maximum number of
     */
    public void push(E item) {

        // Remove all irrelevant history
        for (int i = this.history.size()-1; i > this.currentIndex; i--) {
            this.history.remove(i);
        }

        if (this.history.size() == this.maxSize) {
            this.history.remove(0);
        }

        this.history.add(item);
        this.currentIndex = this.history.size() - 1;
    }
}
