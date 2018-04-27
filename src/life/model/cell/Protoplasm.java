package life.model.cell;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.io.InputStream;

public class Protoplasm extends CreatureCell {

    private static final Image image;

    static {
        InputStream inputStream = CreatureCell.class.getResourceAsStream("images/proto.png");
        image = new Image(inputStream);
    }

    public Protoplasm(int x, int y) {
        super(x, y);
        setFill(new ImagePattern(image));
    }

    @Override
    public CreatureType getType() {
        return CreatureType.PROTOPLASM;
    }
}
