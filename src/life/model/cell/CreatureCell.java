package life.model.cell;

public class CreatureCell extends Cell {

    public CreatureCell(int x, int y) {
        super(x, y);
    }

    public CreatureType getType() {
        return CreatureType.EMPTY;
    }

    public int getEnergy() {
        throw new UnsupportedOperationException();
    }

    public void setEnergy(int energy) {
        throw new UnsupportedOperationException();
    }


}
