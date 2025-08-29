// Jack Autieri
package simulation.gameoflife;

import simulation.cell.Cell;
import simulation.cell.CellFactory;

public class GameOfLifeCellFactory implements CellFactory {
    private int rows;
    private int columns;

    public GameOfLifeCellFactory(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }

    // Adam Alkins
    @Override
    public Cell createCell(int numRow, int numCol) {
        if ((numRow == 1 || numRow == rows + 2) || (numCol == 1 || numCol == columns + 2)) {
            Cell edgeCell = new Cell(numRow, numCol);
            edgeCell.isEdge = true;
            return edgeCell; // Edge cells
        } else {
            return new Bacteria(numRow, numCol);
        }
    }
}
