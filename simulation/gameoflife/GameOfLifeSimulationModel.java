package simulation.gameoflife;

import simulation.Grid;
import simulation.SimulationModel;
import simulation.cell.Cell;
import simulation.cell.UpdateState;

import java.util.ArrayList;

public class GameOfLifeSimulationModel extends SimulationModel {
	// COLIN DAILEY - Modified By Jack Autieri
    public GameOfLifeSimulationModel(int rows, int cols) {
        super(rows, cols, new Grid(rows, cols, new GameOfLifeCellFactory(rows, cols)));
    }

    @Override
    public void doOneStep(double elapsedTime) {
        updateAllCellNeighbors();
        updateCellStates();
    }

    public void updateAllCellNeighbors() {
        ArrayList<ArrayList<Cell>> cellGrid = grid.cellGrid;

        // Loop through the grid excluding the edge cells
        for (int i = 1; i < cellGrid.size() - 1; i++) {
            ArrayList<Cell> row = cellGrid.get(i);
            for (int j = 1; j < row.size() - 1; j++) {
                Cell cell = row.get(j);
                if (cell instanceof UpdateState) {
                    int activeNeighbors = countActiveNeighbors(i, j);
                    if (cell instanceof Bacteria) {
                        ((Bacteria) cell).neighborsActive = activeNeighbors;
                    }
                }
            }
        }
    }

    private int countActiveNeighbors(int rowIndex, int colIndex) {
        int activeNeighbors = 0;
        ArrayList<ArrayList<Cell>> cellGrid = grid.cellGrid;

        // Check all eight neighbors
        for (int i = rowIndex - 1; i <= rowIndex + 1; i++) {
            for (int j = colIndex - 1; j <= colIndex + 1; j++) {
                if (i == rowIndex && j == colIndex) {
                    continue; // Skip the cell itself
                }
                Cell neighbor = cellGrid.get(i).get(j);
                if (neighbor.isActive) {
                    activeNeighbors++;
                }
            }
        }
        return activeNeighbors;
    }

    public void updateCellStates() {
        ArrayList<ArrayList<Cell>> cellGrid = grid.cellGrid;

        // Loop through the grid excluding the edge cells
        for (int i = 1; i < cellGrid.size() - 1; i++) {
            ArrayList<Cell> row = cellGrid.get(i);
            for (int j = 1; j < row.size() - 1; j++) {
                Cell cell = row.get(j);
                if (cell instanceof UpdateState) {
                    ((UpdateState) cell).updateState();
                }
            }
        }
    }
}
