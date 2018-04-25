package life.model;

import life.model.cell.Cell;
import life.model.cell.CreatureCell;
import life.model.cell.CreatureType;

import java.util.ArrayList;

public class CreatureLifeModel extends SimpleLifeModel {

    public CreatureLifeModel(int w, int h) {
        super(w, h);
    }

    @Override
    public ArrayList<Cell> getLivingCells() {
        ArrayList<Cell> livingCells = new ArrayList<>();
        for (int i = 0; i < field.length; i++)
            for (int j = 0; j < field[0].length; j++)
                if (field[i][j])
                    livingCells.add(new CreatureCell(j, i, CreatureType.INFUSORIAN));
        return livingCells;
    }
}
