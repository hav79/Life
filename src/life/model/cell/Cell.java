package life.model.cell;

import javafx.scene.shape.Circle;

public class Cell extends Circle {
    private int x;
    private int y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.setRadius(7);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}