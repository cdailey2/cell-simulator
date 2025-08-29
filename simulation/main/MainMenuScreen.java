package simulation.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import simulation.SimulationDisplay;
import simulation.waterWorld.WaterWorldDisplay;

// COLIN DAILEY //
public class MainMenuScreen extends Application {

    private SimulationDisplay gameOfLifeDisplay = new SimulationDisplay();
    private WaterWorldDisplay waterWorldDisplay = new WaterWorldDisplay(); 

    @Override
    public void start(Stage mainMenuScreen) {
        // Create buttons
        Button gameOfLifeButton = new Button("Run Game of Life");
        Button waterWorldButton = new Button("Run Water World");

        gameOfLifeButton.setOnAction(e -> {
            try {
                gameOfLifeDisplay.start(new Stage()); // Open Game of Life in a new stage
                mainMenuScreen.close(); // Close main menu window for seamless transition
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        waterWorldButton.setOnAction(e -> {
            try {
                waterWorldDisplay.start(new Stage()); // Open Water World in a new stage
                mainMenuScreen.close(); // Close main menu window for seamless transition
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Build Layout
        VBox layout = new VBox(10); // Vertical box with spacing
        layout.getChildren().addAll(gameOfLifeButton, waterWorldButton);
        layout.setStyle("-fx-alignment: center; -fx-padding: 20;");

        // Build and display Scene
        Scene scene = new Scene(layout, 300, 200);
        mainMenuScreen.setScene(scene);
        mainMenuScreen.setTitle("Cell Simulator Main Menu");
        mainMenuScreen.show();
    }
    
    // Logic for what happens if button is pressed
    // 		The button variable should be passed so it can set logic for that button
    //		The corresponding display model variable should be passed as a parameter so it can be run on click 
//    private void setOnClickLogic(Button buttonName, ) {
//    	
//    }
}
