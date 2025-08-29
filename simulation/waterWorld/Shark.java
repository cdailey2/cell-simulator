// Matilda - Modified By Jack Autieri
package simulation.waterWorld;

import javafx.scene.paint.Color;

// Shark.java
public class Shark extends FishCell {

    private static final int INITIAL_BREED_TIME = 20;
    private static final int INITIAL_STARVE_TIME = 5;
    private static final Color SHARK_COLOR = Color.YELLOW;

    private int starveTime;
    private int currentStarveTime;

    public Shark(int x, int y) {
        super(x, y);
        this.breedTime = INITIAL_BREED_TIME;
        this.currentBreedTime = breedTime;
        this.starveTime = INITIAL_STARVE_TIME;
        this.currentStarveTime = starveTime;
        this.isShark = true;
    }

    // Methods to handle breeding and starving
    public boolean hasStarved() {
        return currentStarveTime <= 0;
    }

    public void resetStarveTime() {
        currentStarveTime = starveTime;
    }

    @Override
    public void updateState() {
        currentBreedTime--;
        currentStarveTime--;
    }

    @Override
    public Color getCellColor() {
        return SHARK_COLOR;
    }
    

}
