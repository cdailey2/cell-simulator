// Adam Alkins
package simulation.cell;

// Base Cell class
public class Cell {
    protected int x;
    protected int y;
    public boolean isEdge = true;
    public boolean isActive = false;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Getters and setters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
