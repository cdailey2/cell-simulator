// Matilda - Modified By Jack Autieri
package simulation.waterWorld;

import javafx.scene.paint.Color;
import simulation.cell.Cell;

// WaTorCell.java
public abstract class WaTorCell extends Cell {
    protected boolean isUnoccupied;
    protected boolean isEdible;
    protected boolean canEat;
    protected Color cellColor;

    public WaTorCell(int x, int y) {
        super(x, y);
    }

    public boolean isUnoccupied() {
        return isUnoccupied;
    }

    public boolean isEdible() {
        return isEdible;
    }

    public boolean canEat() {
        return canEat;
    }

    public Color getCellColor() {
        return cellColor;
    }

}
