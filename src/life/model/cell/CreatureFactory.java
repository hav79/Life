package life.model.cell;

public class CreatureFactory {

    public static CreatureCell getCreature(int x, int y, CreatureType type) {
        switch (type) {

            case EMPTY:
                return new CreatureCell(x, y);
            case PROTOPLASM:
                return new Protoplasm(x, y);
            case INFUSORIAN:
                return new Infusorian(x, y);
            case ANIMAL:
                return new Animal(x, y);
            default:
                return null;
        }
    }
}
