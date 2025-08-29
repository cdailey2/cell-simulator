// Erik Nordman
package simulation.waterWorld;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import simulation.cell.Cell;

import java.util.ArrayList;

public class WaterWorldDisplay extends Application {

    private WaterWorldGrid waterWorldGrid;
    private int rows;
    private int cols;
    private double fishDensity = 0.7;
    private double sharkDensity = 0.1;
    private int fishBreedTime = 1;
    private int sharkBreedTime = 20;
    private int sharkStarveTime = 5;

    private boolean isPaused = true;
    private GridPane gridPane;
    private Timeline simLoop;

    private static final int FRAMES_PER_SECOND = 60;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

    @Override
    public void start(Stage primaryStage) {
        inputScreen(primaryStage);
    }

    public void inputScreen(Stage primaryStage) {
        Label rowLabel = new Label("Enter number of rows:");
        TextField rowInput = new TextField();

        Label colLabel = new Label("Enter number of columns:");
        TextField colInput = new TextField();

        Label fishDensityLabel = new Label("Fish Density (0.0 - 1.0):");
        TextField fishDensityInput = new TextField(String.valueOf(fishDensity));

        Label sharkDensityLabel = new Label("Shark Density (0.0 - 1.0):");
        TextField sharkDensityInput = new TextField(String.valueOf(sharkDensity));

        Label fishBreedTimeLabel = new Label("Fish Breed Time:");
        TextField fishBreedTimeInput = new TextField(String.valueOf(fishBreedTime));

        Label sharkBreedTimeLabel = new Label("Shark Breed Time:");
        TextField sharkBreedTimeInput = new TextField(String.valueOf(sharkBreedTime));

        Label sharkStarveTimeLabel = new Label("Shark Starve Time:");
        TextField sharkStarveTimeInput = new TextField(String.valueOf(sharkStarveTime));

        Button submitButton = new Button("Start Simulation");

        submitButton.setOnAction(event -> {
            try {
                rows = Integer.parseInt(rowInput.getText());
                cols = Integer.parseInt(colInput.getText());
                fishDensity = Double.parseDouble(fishDensityInput.getText());
                sharkDensity = Double.parseDouble(sharkDensityInput.getText());
                fishBreedTime = Integer.parseInt(fishBreedTimeInput.getText());
                sharkBreedTime = Integer.parseInt(sharkBreedTimeInput.getText());
                sharkStarveTime = Integer.parseInt(sharkStarveTimeInput.getText());

                waterWorldGrid = new WaterWorldGrid(rows, cols, (float) sharkDensity, (float) fishDensity, fishBreedTime, sharkStarveTime);
                gridPane = new GridPane();

                controlMenu(primaryStage);
                redraw();
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter valid numbers.");
            }
        });

        VBox inputLayout = new VBox(10, rowLabel, rowInput, colLabel, colInput, fishDensityLabel, fishDensityInput,
                sharkDensityLabel, sharkDensityInput, fishBreedTimeLabel, fishBreedTimeInput,
                sharkBreedTimeLabel, sharkBreedTimeInput, sharkStarveTimeLabel, sharkStarveTimeInput, submitButton);
        inputLayout.setAlignment(Pos.CENTER);

        Scene inputScene = new Scene(inputLayout, 500, 600);
        primaryStage.setTitle("Water World - Input");
        primaryStage.setScene(inputScene);
        primaryStage.show();
    }

    private void controlMenu(Stage primaryStage) {
        Button togglePlayButton = new Button("Play");
        Button newSimulationButton = new Button("New Simulation");
        Button stepButton = new Button("Step");

        togglePlayButton.setOnAction(event -> {
            isPaused = !isPaused;
            togglePlayButton.setText(isPaused ? "Play" : "Pause");
            if (isPaused) {
                simLoop.stop();
            } else {
                simLoop.play();
            }
        });

        newSimulationButton.setOnAction(event -> inputScreen(primaryStage));

        stepButton.setOnAction(event -> {
            if (isPaused) {
                waterWorldGrid.step();
                redraw();
            }
        });

        HBox controlButtons = new HBox(10, togglePlayButton, stepButton, newSimulationButton);
        controlButtons.setAlignment(Pos.CENTER);

        VBox layout = new VBox(20, new Label("Water World Controls"), gridPane, controlButtons);
        layout.setAlignment(Pos.CENTER);

        Scene controlScene = new Scene(layout, 800, 700);
        primaryStage.setScene(controlScene);
        primaryStage.setTitle("Water World");
        primaryStage.show();

        simLoop = new Timeline(new KeyFrame(Duration.seconds(SECOND_DELAY), e -> {
            if (!isPaused) {
                waterWorldGrid.step();
                redraw();
            }
        }));
        simLoop.setCycleCount(Timeline.INDEFINITE);
    }

    public void redraw() {
        gridPane.getChildren().clear();
        ArrayList<ArrayList<Cell>> cellGrid = waterWorldGrid.cellGrid;

        for (int i = 0; i < cellGrid.size(); i++) {
            ArrayList<Cell> row = cellGrid.get(i);
            for (int j = 0; j < row.size(); j++) {
                Cell cell = row.get(j);
                String color;
                if (cell.isEdge) {
                    color = "black";
                } else if (cell instanceof FishCell) {
                    color = "green";
                } else if (cell instanceof Shark) {
                    color = "yellow";
                } else {
                    color = "blue";
                }

                Label cellLabel = new Label();
                cellLabel.setStyle("-fx-background-color: " + color + "; -fx-border-color: gray;");
                cellLabel.setMinSize(20, 20);
                gridPane.add(cellLabel, j, i);
            }
        }

        gridPane.setAlignment(Pos.CENTER);
    }

//    public static void main(String[] args) {
//        launch(args);
//    }
}

