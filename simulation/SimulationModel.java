// Jack Autieri
package simulation;

public abstract class SimulationModel {
    protected Grid grid;

    public SimulationModel(int rows, int cols, Grid grid) {
        this.grid = grid;
    }

    public abstract void doOneStep(double elapsedTime);

    public Grid getGrid() {
        return grid;
    }
}
