package life.model.cell;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.io.InputStream;

public class Animal extends CreatureCell {

    private static final Image image;

    static {
        InputStream inputStream = CreatureCell.class.getResourceAsStream("images/animal.jpg");
        image = new Image(inputStream);
    }

    private int energy = 5;

    public Animal(int x, int y) {
        super(x, y);
        setFill(new ImagePattern(image));
    }

    @Override
    public CreatureType getType() {
        return CreatureType.ANIMAL;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }
}
