package simulation;

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
import simulation.gameoflife.GameOfLifeSimulationModel;

import java.util.ArrayList;

public class SimulationDisplay extends Application {

    private SimulationController mySimulationController;
    private int rows;
    private int cols;
    private boolean isPaused = true;
    private GridPane gridPane;
    private Timeline simLoop;

    private static final int FRAMES_PER_SECOND = 60;
    private static final double SECOND_DELAY = 15.0 / FRAMES_PER_SECOND;

    @Override
    public void start(Stage primaryStage) {
        inputScreen(primaryStage);
    }

    public void inputScreen(Stage primaryStage) {

        Label rowLabel = new Label("Enter number of rows: ");
        TextField rowInput = new TextField();

        Label colLabel = new Label("Enter number of columns:");
        TextField colInput = new TextField();

        Button enterButton = new Button("Submit");

        enterButton.setOnAction(event -> {
            try {
                rows = Integer.parseInt(rowInput.getText());
                cols = Integer.parseInt(colInput.getText());

                // Initialize the SimulationModel with the specified rows and columns
                SimulationModel simulationModel = new GameOfLifeSimulationModel(rows, cols);

                // Initialize the SimulationController
                mySimulationController = new SimulationController(this, simulationModel);

                gridPane = new GridPane();

                // Proceed to the control menu
                controlMenu(primaryStage);
                redraw();

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an Integer Value.");
            }
        });

        VBox inputLayout = new VBox(10, rowLabel, rowInput, colLabel, colInput, enterButton);
        inputLayout.setAlignment(Pos.CENTER);

        Scene inputScene = new Scene(inputLayout, 400, 300);
        primaryStage.setTitle("Game of Life - Input");
        primaryStage.setScene(inputScene);
        primaryStage.show();
    }

    private void controlMenu(Stage primaryStage) {
        // Creates the control buttons
        Button togglePlayButton = new Button("Press Play");
        Button newSimulationButton = new Button("New Simulation");
        Button stepButton = new Button("Single Step");

        // Output for when pause button is pressed
        togglePlayButton.setOnAction(event -> {
            isPaused = !isPaused;
            togglePlayButton.setText(isPaused ? "Resume" : "Pause");
            mySimulationController.togglePlay(isPaused);
        });

        // Output for when new simulation button is pressed
        newSimulationButton.setOnAction(event -> {
            inputScreen(primaryStage);
        });

        // Output for when single step button is pressed
        stepButton.setOnAction(event -> {
            mySimulationController.doOneStep(0);
        });

        // Layout for the control menu
        HBox controlButtons = new HBox(10, togglePlayButton, stepButton, newSimulationButton);
        controlButtons.setAlignment(Pos.CENTER);

        VBox layout = new VBox(20, new Label("Simulation Controls"), gridPane, controlButtons);
        layout.setAlignment(Pos.CENTER);

        // Sets the Scene with a label displaying the title
        Scene controlScene = new Scene(layout, 600, 500);
        primaryStage.setScene(controlScene);
        primaryStage.setTitle("Game of Life");
        primaryStage.show();

        // Initialize the simulation loop
        simLoop = new Timeline(new KeyFrame(Duration.seconds(SECOND_DELAY), e -> mySimulationController.doOneStep(SECOND_DELAY)));
        simLoop.setCycleCount(Timeline.INDEFINITE);
    }

    public void redraw() {
        gridPane.getChildren().clear();
        Grid grid = mySimulationController.getGrid();
        ArrayList<ArrayList<Cell>> cellGrid = grid.cellGrid;
        // Iterate over the grid and create labels based on cell states
        for (int i = 0; i < cellGrid.size(); i++) {
            ArrayList<Cell> row = cellGrid.get(i);
            for (int j = 0; j < row.size(); j++) {
                Label cellLabel = new Label();
                Cell cell = row.get(j);
                if (cell.isActive) {
                	// COLIN DAILEY
                    cellLabel.setStyle("-fx-border-color: gray; -fx-font-weight: bold; -fx-background-color: #67d645;");
                } else if (cell.isEdge) {
                    // Edge Cells have different colors to show they can't be changed
                    cellLabel.setStyle("-fx-border-color: gray; -fx-background-color: white;");
                } else {
                    cellLabel.setStyle("-fx-border-color: gray; -fx-background-color: black;");
                }
                cellLabel.setMinSize(20, 20);
                cellLabel.setAlignment(Pos.CENTER);
                gridPane.add(cellLabel, j, i);
            }
        }
        gridPane.setAlignment(Pos.CENTER);
    }

    public void playSimulation() {
        simLoop.play();
    }

    public void pauseSimulation() {
        simLoop.stop();
    }

//    public static void main(String[] args) {
//        launch(args);
//    }

}


