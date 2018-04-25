package life.model.cell;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.io.InputStream;

public class CreatureCell extends Cell {

    private static final Image image;

    static {
        InputStream inputStream = CreatureCell.class.getResourceAsStream("images/infusorian.jpg");
        image = new Image(inputStream);
    }

    public CreatureCell(int x, int y, CreatureType type) {
        super(x, y);
        setFill(new ImagePattern(image));
    }
}
