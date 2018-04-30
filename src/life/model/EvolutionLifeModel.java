package life.model;

import life.model.cell.*;

import java.util.ArrayList;
import java.util.Random;

public class EvolutionLifeModel implements LifeModel {

    protected CreatureCell[][] field;
    private Random random = new Random();

    public EvolutionLifeModel(int w, int h) {
        field = new CreatureCell[h][w];
        resetModel();
    }

    @Override
    public void resetModel() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                if (random.nextInt(4) == 0)
                    field[i][j] = new Protoplasm(j, i);
                else
                    field[i][j] = new CreatureCell(j, i);
            }
        }
    }

    @Override
    public void updateModel() {
        int i = random.nextInt(field.length);
        int j = random.nextInt(field[0].length);
        switch (field[i][j].getType()) {
            case EMPTY:
                updateEmptyCell(i, j);
                break;
            case PROTOPLASM:
                updateProtoplasmField(i, j);
                break;
            case INFUSORIAN:
                updateInfusorianFiefd(i, j);
                break;
            case ANIMAL:
                updateAnimalField(i, j);
                break;
        }
    }

    private void updateEmptyCell(int y, int x) {
        int protoCount = getNeighborsForType(y, x, CreatureType.PROTOPLASM);
        int infusorianCount = getNeighborsForType(y, x, CreatureType.INFUSORIAN);

        // Если вокруг не меньше 5 протоплазм - стираем их
        // и случайную из квадрата 3х3 превращаем в инфузорию
        if (protoCount >= 5) {
            int ri = random.nextInt(3) - 1;
            int rj = random.nextInt(3) - 1;
            for (int i = -1; i <= 1; i++)
                for (int j = -1; j <= 1; j++)
                    if (checkFieldType(x + j, y + i, CreatureType.PROTOPLASM)) {
                        if (j == rj && i == ri) {
                            setField(x + j, y + i, CreatureType.INFUSORIAN);
                        } else {
                            setField(x + j, y + i, CreatureType.EMPTY);
                        }
                    }
        } else if (infusorianCount == 2 || infusorianCount == 3) {
            setField(x, y, CreatureType.INFUSORIAN);
        } else {
            setField(x, y, CreatureType.PROTOPLASM);
        }
    }

    private void updateProtoplasmField(int y, int x) {
        int protoCount = getNeighborsForType(y, x, CreatureType.PROTOPLASM);

        // Если вокруг не меньше 4 протоплазм - стираем их
        // и случайную из квадрата 3х3 превращаем в инфузорию
        if (protoCount >= 4) {
            int ri = random.nextInt(3) - 1;
            int rj = random.nextInt(3) - 1;
            for (int i = -1; i <= 1; i++)
                for (int j = -1; j <= 1; j++)
                    if (checkFieldType(x + j, y + i, CreatureType.PROTOPLASM)) {
                        if (j == rj && i == ri) {
                            setField(x + j, y + i, CreatureType.INFUSORIAN);
                        } else {
                            setField(x + j, y + i, CreatureType.EMPTY);
                        }
                    }
        }
    }

    private void updateInfusorianFiefd(int y, int x) {
        int infusorianCount = getNeighborsForType(y, x, CreatureType.INFUSORIAN) - 1;

        // Если вокруг инфузорий меньше 2 или больше 3 - стираем ее
        if (infusorianCount < 2 || infusorianCount > 3)
            setField(x, y, CreatureType.EMPTY);

        // Если 3 инфузории образовали ряд
        // их стираем и создаем животное с начальной энергией 5
        if (checkFieldType(x, y - 1, CreatureType.INFUSORIAN)
                && checkFieldType(x, y + 1, CreatureType.INFUSORIAN)) {
            setField(x, y - 1, CreatureType.EMPTY);
            setField(x, y + 1, CreatureType.EMPTY);
            setField(x, y, CreatureType.ANIMAL);
        } else if (checkFieldType(x - 1, y, CreatureType.INFUSORIAN)
                && checkFieldType(x + 1, y, CreatureType.INFUSORIAN)) {
            setField(x - 1, y, CreatureType.EMPTY);
            setField(x + 1, y, CreatureType.EMPTY);
            setField(x, y, CreatureType.ANIMAL);
        } else if (infusorianCount == 2 || infusorianCount == 3)
            setField(x, y, CreatureType.INFUSORIAN);
    }

    private void updateAnimalField(int y, int x) {
        int dx = 0;
        int dy = 0;

        switch (random.nextInt(4)) {
            case 0:
                dx = -1;
                break;
            case 1:
                dx = 1;
                break;
            case 2:
                dy = -1;
                break;
            case 3:
                dy = 1;
                break;
        }
        moveAnimal(y, x, dy, dx);
    }

    private void moveAnimal(int y, int x, int dy, int dx) {
        if (checkFieldType(x + dx, y + dy, CreatureType.EMPTY)) {
            // Передвинуть животное, уменьшить энергию на 1
            setField(x + dx, y + dy, CreatureType.ANIMAL);
            getNewField(x + dx, y + dy).setEnergy(field[y][x].getEnergy() - 1);
            setField(x, y, CreatureType.EMPTY);
            if (getNewField(x + dx, y + dy).getEnergy() == 0)
                setField(x + dx, y + dy, CreatureType.EMPTY);
        } else if (checkFieldType(x + dx, y + dy, CreatureType.PROTOPLASM)) {
            // На месте протоплазмы будет животное, энергия не меняется
            setField(x + dx, y + dy, CreatureType.ANIMAL);
            setField(x, y, CreatureType.EMPTY);
        } else if (checkFieldType(x + dx, y + dy, CreatureType.INFUSORIAN)) {
            // На месте инфузории - животное, энергия увеличивается
            setField(x + dx, y + dy, CreatureType.ANIMAL);
            getNewField(x + dx, y + dy).setEnergy(field[y][x].getEnergy() + 1);
            setField(x, y, CreatureType.EMPTY);
        } else if (checkFieldType(x + dx, y + dy, CreatureType.ANIMAL)) {
            // В новой позиции - животное с суммарной энергией
            setField(x + dx, y + dy, CreatureType.ANIMAL);
            getNewField(x + dx, y + dy).setEnergy(getField(x + dx, y + dy).getEnergy()
                    + getField(x, y).getEnergy());
            setField(x, y, CreatureType.EMPTY);
        }

    }

    private int getNeighborsForType(int y, int x, CreatureType type) {
        int count = 0;
        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++)
                if (checkFieldType(x + j, y + i, type)) {
                    count++;
                }
        return count;
    }

    private boolean checkFieldType(int x, int y, CreatureType type) {
        int newY = y < 0 ? field.length - 1 : y >= field.length ? 0 : y;
        int newX = x < 0 ? field[0].length - 1 : x >= field[0].length ? 0 : x;
        return field[newY][newX].getType() == type;
    }

    private void setField(int x, int y, CreatureType type) {
        int newY = y < 0 ? field.length - 1 : y >= field.length ? 0 : y;
        int newX = x < 0 ? field[0].length - 1 : x >= field[0].length ? 0 : x;
        field[newY][newX] = CreatureFactory.getCreature(newX, newY, type);
    }

    private CreatureCell getNewField(int x, int y) {
        int newY = y < 0 ? field.length - 1 : y >= field.length ? 0 : y;
        int newX = x < 0 ? field[0].length - 1 : x >= field[0].length ? 0 : x;
        return field[newY][newX];
    }

    private CreatureCell getField(int x, int y) {
        int newY = y < 0 ? field.length - 1 : y >= field.length ? 0 : y;
        int newX = x < 0 ? field[0].length - 1 : x >= field[0].length ? 0 : x;
        return field[newY][newX];
    }

    @Override
    public ArrayList<Cell> getLivingCells() {
        ArrayList<Cell> livingCells = new ArrayList<>();
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                try {
                    if (field[i][j].getType() != CreatureType.EMPTY)
                        livingCells.add(field[i][j]);
                } catch (NullPointerException e) {
                    System.out.println(j + " " + i);
                    System.out.println(field[i][j]);
                }
            }
        }
        return livingCells;
    }
}
