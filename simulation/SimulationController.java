// Colin Dailey
package simulation;

public class SimulationController {

    private SimulationModel simulationModel;
    private SimulationDisplay simulationDisplay;

    public SimulationController(SimulationDisplay simulationDisplay, SimulationModel simulationModel) {
        this.simulationDisplay = simulationDisplay;
        this.simulationModel = simulationModel;
    }

    // Toggles whether the simulation loop is played or paused
    public void togglePlay(boolean isPaused) {
        if (!isPaused) {
            System.out.println("Play");
            simulationDisplay.playSimulation();
        } else {
            System.out.println("Pause");
            simulationDisplay.pauseSimulation();
        }
    }

    // Allows doOneStep from the model to be called by UI and redraw it
    public void doOneStep(double elapsedTime) {
        simulationModel.doOneStep(elapsedTime);
        simulationDisplay.redraw();
    }

    // Allows the display to access the model's grid
    public Grid getGrid() {
        return simulationModel.getGrid();
    }
}
