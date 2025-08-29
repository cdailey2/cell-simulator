// Adam Alkins
package simulation.gameoflife;

import simulation.cell.Cell;
import simulation.cell.UpdateState;

import java.util.HashMap;
import java.util.Random;

public class Bacteria extends Cell implements UpdateState {
    public int neighborsActive;

    public Bacteria(int x, int y) {
        super(x, y);
        Random random = new Random();
        this.isActive = (random.nextInt(1,10) <= 4); // 40% chance to be active
        this.isEdge = false;
    }

    public boolean getActivityNextStep() {
        HashMap<Integer, Boolean> neighborActivityMap = new HashMap<>();
        boolean twoNeighbors = isActive;

        neighborActivityMap.put(0, false);
        neighborActivityMap.put(1, false);
        neighborActivityMap.put(2, twoNeighbors);
        neighborActivityMap.put(3, true);
        for (int i = 4; i <= 8; ++i) {
            neighborActivityMap.put(i, false);
        }

        return neighborActivityMap.get(neighborsActive);
    }

    // Adam Alkins - Modified By Jack Autieri
    @Override
    public void updateState() {
        isActive = getActivityNextStep();
    }
}
