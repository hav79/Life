package life.model.cell;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.io.InputStream;

public class Infusorian extends CreatureCell {

    private static final Image image;

    static {
        InputStream inputStream = CreatureCell.class.getResourceAsStream("images/infusorian.jpg");
        image = new Image(inputStream);
    }

    public Infusorian(int x, int y) {
        super(x, y);
        setFill(new ImagePattern(image));
    }

    @Override
    public CreatureType getType() {
        return CreatureType.INFUSORIAN;
    }
}
