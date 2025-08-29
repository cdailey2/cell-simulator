// Adam Alkins
package simulation.waterWorld;

import java.util.ArrayList;
import java.util.Random;

import simulation.Grid;
import simulation.cell.Cell;

public class WaterWorldGrid extends Grid {

    // FIELDS
    public float sharkDensity;
    public float fishDensity;
    public int breedTime;
    public int starveTime;
    private Random random = new Random();

    public WaterWorldGrid(int rows, int columns, float sharkDensity, float fishDensity, int breedTime, int starveTime) {
        super();
        this.breedTime = breedTime;
        this.starveTime = starveTime;
        cellGrid = new ArrayList<>();
        for (int numRow = 1; numRow <= (rows + 2); numRow++) {
            ArrayList<Cell> cellsInRow = new ArrayList<>();
            for (int numCol = 1; numCol <= (columns + 2); numCol++) {
                if ((numRow == 1 || numRow == rows + 2) || (numCol == 1 || numCol == columns + 2)) {
                    cellsInRow.add(new Cell(numRow, numCol));
                } else {
                    cellsInRow.add(pickCellType(fishDensity, sharkDensity, numRow, numCol));
                }
            }
            cellGrid.add(cellsInRow);
        }
    }

    public void step() {
        // Loop through the grid excluding the edge cells
        for (int i = 1; i < cellGrid.size() - 1; i++) {
            ArrayList<Cell> row = cellGrid.get(i);
            for (int j = 1; j < row.size() - 1; j++) {
                Cell cell = row.get(j);
                if (cell instanceof FishCell) {
                    moveWaterAnimal((FishCell) cell);
                    if (((FishCell) cell).readyToBreed()) {
                        spawnNewWaterAnimal((FishCell) cell);
                    }
                }
            }
        }
    }

    // Determine the type of cell to spawn.
    public Cell pickCellType(float fishDensity, float sharkDensity, int row, int column) {
        float cellTypeOdds = random.nextFloat();
        if (cellTypeOdds <= fishDensity) {
            return new FishCell(row, column);
        } else if (cellTypeOdds <= (sharkDensity + fishDensity)) {
            return new Shark(row, column);
        } else {
            return new Cell(row, column);
        }
    }

    public void spawnNewWaterAnimal(FishCell fishCell) {
        ArrayList<Cell> neighbors = getNeighborWaterCells(fishCell);
        if (!neighbors.isEmpty()) {
            Cell newFishCell = neighbors.get(random.nextInt(neighbors.size()));
            int cellX = newFishCell.getX();
            int cellY = newFishCell.getY();

            // Remove and replace cell.
            if (fishCell.isShark) {
                cellGrid.get(cellX).set(cellY, new Shark(cellX, cellY));
            } else {
                cellGrid.get(cellX).set(cellY, new FishCell(cellX, cellY));
            }
        }
    }

    public void moveWaterAnimal(FishCell fishCell) {
        ArrayList<Cell> neighbors;
        if (fishCell.isShark) {
            neighbors = getNeighborFishCells(fishCell);
        } else {
            neighbors = getNeighborWaterCells(fishCell);
        }
        moveFish(fishCell, neighbors);
    }

    public void moveFish(FishCell fishCell, ArrayList<Cell> neighbors) {
        // Using neighbors, determine randomly which cell to move to.
        if (!neighbors.isEmpty()) {
            Cell cellToMoveTo = neighbors.get(random.nextInt(neighbors.size()));

            int fishOldX = fishCell.getX();
            int fishOldY = fishCell.getY();

            int newX = cellToMoveTo.getX();
            int newY = cellToMoveTo.getY();

            // Ensure newX and newY are within bounds before accessing the grid
            if (newX >= 0 && newX < cellGrid.size() && newY >= 0 && newY < cellGrid.get(newX).size()) {
                // Move the fish to the new location
                cellGrid.get(newX).set(newY, fishCell);
                fishCell.setX(newX);
                fishCell.setY(newY);

                // Replace the old location with an empty Cell
                cellGrid.get(fishOldX).set(fishOldY, new Cell(fishOldX, fishOldY));
            } else {
                System.out.println("Invalid move attempt: (" + newX + ", " + newY + ")");
            }
         }
    }

    public ArrayList<Cell> getNeighborWaterCells(FishCell fishCell) {
    	 ArrayList<Cell> neighborCells = new ArrayList<>();
    	    int fishCellX = fishCell.getX();
    	    int fishCellY = fishCell.getY();

    	    // Check neighbors up, down, left, and right
    	    int[] dx = {-1, 1, 0, 0};
    	    int[] dy = {0, 0, -1, 1};

    	    for (int direction = 0; direction < 4; direction++) {
    	        int newRow = fishCellX + dx[direction];
    	        int newCol = fishCellY + dy[direction];

    	        // Ensure the indices are within bounds and not edge cells
    	        if (newRow > 0 && newRow < cellGrid.size() - 1 &&
    	            newCol > 0 && newCol < cellGrid.get(newRow).size() - 1) {

    	            Cell neighbor = cellGrid.get(newRow).get(newCol);
    	            if (!neighbor.isActive) { // Only add inactive (water) cells
    	                neighborCells.add(neighbor);
    	            }
    	        }
    	    }

    	    return neighborCells;
    	}

    public ArrayList<Cell> getNeighborFishCells(FishCell fishCell) {
    	ArrayList<Cell> neighborCells = new ArrayList<>();
        int fishCellX = fishCell.getX();
        int fishCellY = fishCell.getY();

        // Check neighbors up, down, left, and right
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        for (int direction = 0; direction < 4; direction++) {
            int newRow = fishCellX + dx[direction];
            int newCol = fishCellY + dy[direction];

            // Ensure the indices are within bounds and not edge cells
            if (newRow > 0 && newRow < cellGrid.size() - 1 &&
                newCol > 0 && newCol < cellGrid.get(newRow).size() - 1) {

                Cell neighbor = cellGrid.get(newRow).get(newCol);
                if (neighbor.isActive && neighbor instanceof FishCell && !((FishCell) neighbor).isShark) {
                    neighborCells.add(neighbor);
                }
            }
        }

        // If no valid fish neighbors, check for empty water cells
        if (neighborCells.isEmpty()) {
            return getNeighborWaterCells(fishCell);
        }

        return neighborCells;
    }

}
