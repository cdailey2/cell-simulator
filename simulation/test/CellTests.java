package simulation.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import simulation.Grid;
import simulation.SimulationModel;
import simulation.gameoflife.Bacteria;
import simulation.gameoflife.GameOfLifeSimulationModel;

public class CellTests {
    // Adam Alkins & Jack Autieri
    // Instantiate the GameOfLifeSimulationModel directly
    SimulationModel simMod = new GameOfLifeSimulationModel(3, 3);

    // Gets a single cell
    public Bacteria getCell(int row, int col) {
        return (Bacteria) simMod.getGrid().cellGrid.get(row).get(col);
    }

    // Used to set a single cell's activity
    public void setActivity(int row, int col, boolean activity) {
        getCell(row, col).isActive = activity;
    }

    public boolean getActivity(int row, int col) {
        return getCell(row, col).isActive;
    }

    public void setGridBoolean(boolean activity) {
        // Set the entire grid's activity excluding edges
        Grid grid = simMod.getGrid();
        int rows = grid.cellGrid.size();
        int cols = grid.cellGrid.get(0).size();
        for (int i = 1; i < rows - 1; i++) {
            for (int j = 1; j < cols - 1; j++) {
                setActivity(i, j, activity);
            }
        }
    }

    // Gets the number of active neighbors for a cell
    public int getCellNeighbors(Bacteria cell) {
        return cell.neighborsActive;
    }

    @Test
    public void testOneNeighbor() {
        setGridBoolean(false);
        setActivity(2, 2, true);
        setActivity(1, 2, true);
        ((GameOfLifeSimulationModel) simMod).updateAllCellNeighbors();
        ((GameOfLifeSimulationModel) simMod).updateCellStates();
        assertFalse("Cell should be inactive with one neighbor", getActivity(2, 2));
    }

    @Test
    public void testEightNeighbors() {
        setGridBoolean(true);
        ((GameOfLifeSimulationModel) simMod).updateAllCellNeighbors();
        ((GameOfLifeSimulationModel) simMod).updateCellStates();
        assertFalse("Cell should be inactive with eight neighbors", getActivity(2, 2));
    }

    @Test
    public void testSixNeighbors() {
        setGridBoolean(true);
        setActivity(1, 1, false);
        setActivity(1, 2, false);
        simMod.doOneStep(0);
        assertFalse("Cell should be inactive with six neighbors", getActivity(2, 2));
    }

    @Test
    public void testTwoTrue() {
        setGridBoolean(false);
        setActivity(2, 2, true);
        setActivity(1, 2, true);
        setActivity(3, 2, true);
        simMod.doOneStep(0);
        assertTrue("Cell should remain active with two neighbors", getActivity(2, 2));
    }

    @Test
    public void testTwoFalse() {
        setGridBoolean(false);
        setActivity(2, 2, false);
        setActivity(1, 2, true);
        setActivity(3, 2, true);
        simMod.doOneStep(0);
        assertFalse("Cell should remain inactive with two neighbors", getActivity(2, 2));
    }

    @Test
    public void notSensingSelf() {
        setGridBoolean(false);
        setActivity(2, 2, true);
        ((GameOfLifeSimulationModel) simMod).updateAllCellNeighbors();
        assertEquals("Cell should not count itself as a neighbor", 0, getCellNeighbors(getCell(2, 2)));
    }
}
