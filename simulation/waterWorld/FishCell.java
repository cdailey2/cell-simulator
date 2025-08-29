// Matilda - Modified by Jack Autieri
package simulation.waterWorld;

// FishCell.java
import javafx.scene.paint.Color;
import simulation.cell.Cell;
import simulation.cell.UpdateState;

public class FishCell extends Cell implements UpdateState {
    private static final int INITIAL_BREED_TIME = 1;
    private static final Color FISH_COLOR = Color.GREEN;

    public boolean isShark = false;
    protected int breedTime;
    protected int currentBreedTime;

    public FishCell(int x, int y) {
        super(x, y);
        this.breedTime = INITIAL_BREED_TIME;
        this.currentBreedTime = breedTime;
        isActive = true; // Indicates that the cell is occupied
        isEdge = false;
    }

    // Method to check if fish is ready to breed
    public boolean readyToBreed() {
        if (currentBreedTime <= 0) {
            currentBreedTime = breedTime;
            return true;
        }
        return false;
    }
    

    @Override
    public void updateState() {
        currentBreedTime--;
    }

    public Color getCellColor() {
        return FISH_COLOR;
    }

	public void setX(int newX) {
		this.x = newX;
	}
	
	public void setY(int newY) {
		this.y = newY;
	}
}


