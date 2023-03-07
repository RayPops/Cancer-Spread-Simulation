import java.awt.Color;
import java.util.HashMap;

/**
 * A subclass of Cell representing a dead cell.
 * Dead cells can become any cell, if the right conditions are met.
 * Dead cells are white.
 *
 * @author Rayan Popat (K21056367) & James Coward (K22004743)
 * @version 2023.02.23
 */

public class Deadcell extends Cell {

    // chance for a deacell to become cancer / chance for a cancer cell to spawn
    private final double cancerChance = 0.00001;

    /**
     * Create a new dead cell at location in field.
     *
     * @param field    The field currently occupied.
     * @param location The location within the field.
     * @param type     The type of cell.
     */
    public Deadcell(Field field, Location location, CellType type) {
        super(field, location, type);
        setColor(Color.white);
    }

    /**
     * Act method
     * Increments the cell age.
     * Has rules for what the cell can become.
     * If the cell is not cancer, it has a chance to become cancer.
     * If the cell has 3 mycoplasma neighbours, it becomes mycoplasma.
     * If the cell has 4 saureus neighbours, it becomes saureus.
     * If the cell has 3 lactobacillus neighbours, it becomes lactobacillus.
     * 
     */
    public void act() {
        addAge();
        HashMap<CellType, Integer> neighbourTypeCount = getNeighbourTypes();
        if (getAge() % 5 == 0 && getAge() > 25) {
            if (Math.random() <= cancerChance) {
                updateState(CellType.CANCER);
                return;
            }
        }

        if (neighbourTypeCount.get(CellType.MYCOPLASMA) != null && neighbourTypeCount.get(CellType.MYCOPLASMA) == 3) {
            updateState(CellType.MYCOPLASMA);
            ;
        }

        else if (neighbourTypeCount.get(CellType.SAUREUS) != null && (neighbourTypeCount.get(CellType.SAUREUS) == 4)) {
            updateState(CellType.SAUREUS);
        }

        else if (neighbourTypeCount.get(CellType.LACTOBACILLUS) != null
                && (neighbourTypeCount.get(CellType.LACTOBACILLUS) == 3)) {
            updateState(CellType.LACTOBACILLUS);
        }

        else {
            updateState(CellType.DEADCELL);
            ;
        }
    }
}
