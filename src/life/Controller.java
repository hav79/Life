package life;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.util.Duration;
import javafx.util.StringConverter;
import life.model.CreatureLifeModel;
import life.model.EvolutionLifeModel;
import life.model.cell.Cell;
import life.model.LifeModel;
import life.model.SimpleLifeModel;

import java.util.stream.IntStream;

public class Controller {
    private final int WIDTH = 50;
    private final int HEIGHT = 30;
    private final int GRID_CELL_SIZE = 20;

    @FXML
    private GridPane grid;

    @FXML
    private Button startStopButton;

    @FXML
    private Button resetButton;

    @FXML
    private ChoiceBox<LifeModel> choiceModel;

    private Timeline timeline;
    private ObjectProperty<LifeModel> lifeModelProperty = new SimpleObjectProperty<>();
    private boolean isRunning;

    @FXML
    public void initialize() {
        resetButton.setOnAction(event -> resetActiveModel());

        choiceModel.setOnAction(event -> resetActiveModel());

        startStopButton.setOnAction(event -> {
            if (isRunning) {
                timeline.stop();
                startStopButton.setText("Start");
                resetButton.setDisable(false);
                choiceModel.setDisable(false);
                isRunning = false;
            } else {
                isRunning = true;
                startStopButton.setText("Stop");
                resetButton.setDisable(true);
                choiceModel.setDisable(true);
                timeline.play();
            }
        });

        timeline = new Timeline(new KeyFrame(Duration.millis(100),
                event -> {
                    lifeModelProperty.get().updateModel();
                    clearGrid();
                    drawModel();
                }));
        timeline.setCycleCount(Timeline.INDEFINITE);

        initGrid();
        initModel();
        drawModel();
    }

    private void resetActiveModel() {
        clearGrid();
        lifeModelProperty.get().resetModel();
        drawModel();
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
        ObservableList<LifeModel> models = FXCollections.observableArrayList();
        models.add(new SimpleLifeModel(WIDTH, HEIGHT));
        models.add(new CreatureLifeModel(WIDTH, HEIGHT));
        models.add(new EvolutionLifeModel(WIDTH, HEIGHT));
        choiceModel.setItems(models);
        choiceModel.setConverter(new StringConverter<LifeModel>() {
            @Override
            public String toString(LifeModel object) {
                return object.getClass().getSimpleName();
            }

            @Override
            public LifeModel fromString(String string) {
                return null;
            }
        });
        lifeModelProperty.bind(choiceModel.getSelectionModel().selectedItemProperty());
        choiceModel.getSelectionModel().selectFirst();
    }

    private void clearGrid() {
        grid.getChildren().clear();
    }

    private void drawModel() {
        for (Cell cell : lifeModelProperty.get().getLivingCells())
            grid.add(cell, cell.getX(), cell.getY());

    }
}
