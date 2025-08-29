// Adam Alkins - Modified By Jack Autieri
package simulation;

import java.util.ArrayList;

import simulation.cell.Cell;
import simulation.cell.CellFactory;

public class Grid {
    public ArrayList<ArrayList<Cell>> cellGrid;

    // Empty constructor to allow subclassing without parameters
    public Grid() {
        cellGrid = new ArrayList<>();
    }

    public Grid(int rows, int columns, CellFactory cellFactory) {
        cellGrid = new ArrayList<>();
        for (int numRow = 1; numRow <= (rows + 2); numRow++) {
            ArrayList<Cell> cellsInRow = new ArrayList<>();
            for (int numCol = 1; numCol <= (columns + 2); numCol++) {
                cellsInRow.add(cellFactory.createCell(numRow, numCol));
            }
            cellGrid.add(cellsInRow);
        }
    }
}
