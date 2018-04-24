package life;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.util.Duration;

import java.util.stream.IntStream;

public class Controller {
    private final int WIDTH = 50;
    private final int HEIGHT = 30;
    private final int GRID_CELL_SIZE = 15;

    @FXML
    private GridPane grid;

    @FXML
    private Button startStopButton;

    @FXML
    private Button resetButton;

    private Timeline timeline;
    private LifeModel model = new LifeModel(WIDTH, HEIGHT);
    private boolean isRunning;

    @FXML
    public void initialize() {
        initGrid();
        initModel();
        resetButton.setOnAction(event -> initModel());

        startStopButton.setOnAction(event -> {
            if (isRunning) {
                timeline.stop();
                startStopButton.setText("Start");
                isRunning = false;
            } else {
                isRunning = true;
                startStopButton.setText("Stop");
//                initModel();
                timeline.play();
            }
        });

        timeline = new Timeline(new KeyFrame(Duration.millis(100),
                event -> {
                    model.updateModel();
                    clearGrid();
                    drawModel();
                }));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void initGrid() {
        ColumnConstraints columnConstraints = new ColumnConstraints(GRID_CELL_SIZE);
        columnConstraints.setHalignment(HPos.CENTER);
        columnConstraints.setHgrow(Priority.NEVER);
        columnConstraints.setMinWidth(GRID_CELL_SIZE);
        columnConstraints.setPrefWidth(GRID_CELL_SIZE);
        columnConstraints.setMaxWidth(GRID_CELL_SIZE);

        RowConstraints rowConstraints = new RowConstraints(GRID_CELL_SIZE);
        rowConstraints.setValignment(VPos.CENTER);
        rowConstraints.setVgrow(Priority.NEVER);
        rowConstraints.setMinHeight(GRID_CELL_SIZE);
        rowConstraints.setPrefHeight(GRID_CELL_SIZE);
        rowConstraints.setMaxHeight(GRID_CELL_SIZE);

        IntStream.range(0, WIDTH).forEach(i -> grid.getColumnConstraints().add(i, columnConstraints));
        IntStream.range(0, HEIGHT).forEach(i -> grid.getRowConstraints().add(i, rowConstraints));
    }

    private void initModel() {
        model = new LifeModel(WIDTH, HEIGHT);
        clearGrid();
        drawModel();
    }

    private void clearGrid() {
        grid.getChildren().clear();
//        for (int i = 0; i < HEIGHT; i++) {
//            for (int j = 0; j < WIDTH; j++) {
//                grid.add(null, i, j);
//            }
//        }
    }

    private void drawModel() {
        for (Cell cell : model.getLivingCells())
            grid.add(cell, cell.getX(), cell.getY());

    }
}
