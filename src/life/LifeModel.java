package life;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Random;

public class LifeModel {

    private boolean[][] field;
    private boolean[][] changed;

    public LifeModel(int w, int h) {
        field = new boolean[h][w];
        changed = new boolean[h][w];
        Random random = new Random();
        for (int i = 0; i < field.length; i++)
            for (int j = 0; j < field[0].length; j++)
                if (random.nextInt(4) == 0)
                    field[i][j] = true;
                else
                    field[i][j] = false;
    }

    public void updateModel() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                int count = getNeighborsCount(i, j);
                if (count < 2 || count > 3)
                    changed[i][j] = false;
                else if (field[i][j])
                    changed[i][j] = true;
                else if (count == 3 && !field[i][j])
                    changed[i][j] = true;
            }
        }
        field = changed;
        changed = new boolean[field.length][field[0].length];
    }

    private int getNeighborsCount(int y, int x) {
        int count = 0;
        for (int i = -1; i <= 1; i++)
            if (y + i >= 0 && y + i < field.length)
                for (int j = -1; j <= 1; j++)
                    if (x + j >= 0 && x + j < field[0].length)
                        if (field[y + i][x + j])
                            count++;
        if (field[y][x])
            count--;
        return count;
    }

    public ArrayList<Cell> getLivingCells() {
        ArrayList<Cell> livingCells = new ArrayList<>();
        for (int i = 0; i < field.length; i++)
            for (int j = 0; j < field[0].length; j++)
                if (field[i][j])
                    livingCells.add(new Cell(j, i));
        return livingCells;
    }
}
