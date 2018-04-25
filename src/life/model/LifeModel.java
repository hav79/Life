package life.model;

import life.model.cell.Cell;

import java.util.ArrayList;

public interface LifeModel {
    void resetModel();

    void updateModel();

    ArrayList<Cell> getLivingCells();
}
