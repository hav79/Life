package life;

import javafx.scene.shape.Circle;

public class Cell extends Circle {
    private int x;
    private int y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.setRadius(5);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
