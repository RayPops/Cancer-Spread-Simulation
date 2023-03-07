import java.awt.Color;
import java.util.HashMap;

/**
 * Simplest form of life.
 * Fun Fact: Mycoplasma are one of the simplest forms of life. A type of
 * bacteria, they only have 500-1000 genes! For comparison, fruit flies have
 * about 14,000 genes.
 *
 * @author Rayan Popat (K21056367) & James Coward (K22004743)
 * @version 2023.02.23
 */

public class Lactobacillus extends Cell {

    /**
     * Create a new Lacotbacillus cell.
     *
     * @param field    The field currently occupied.
     * @param location The location within the field.
     * @param type     The type of cell.
     */
    public Lactobacillus(Field field, Location location, CellType type) {
        super(field, location, type);
        setColor(Color.BLUE);
        // setType("Saureus");
    }

    /**
     * Act method
     * Increments the cell age, and checks if the cell has cancer neighbours that
     * are malignant (and therefore able to spread).
     * If the cell has 3 or less dead cells as neighbours (i.e. 5 or more living
     * neighbours), it will become a dead cell (overcrowding).
     * If the cell has more than 2 saureus cells as neighbours, it will become a
     * dead cell (competition).
     * If the cell has more than 4 lactobacillus cells as neighbours, it will become
     * a dead cell (competition).
     * Otherwise, it will become a lactobacillus cell.
     */
    public void act() {

        HashMap<CellType, Integer> neighbourTypeCount = getNeighbourTypes();

        if (neighbourTypeCount.get(CellType.CANCER) != null && checkMalignant()) {
            if (Math.random() <= spreadChance) {
                updateState(CellType.CANCER);
                return;
            }
        }

        if (neighbourTypeCount.get(CellType.DEADCELL) != null && (neighbourTypeCount.get(CellType.DEADCELL) <= 3)) {
            updateState(CellType.DEADCELL);
        }

        else if (neighbourTypeCount.get(CellType.SAUREUS) != null && (neighbourTypeCount.get(CellType.SAUREUS) >= 2)) {
            updateState(CellType.DEADCELL);
        }

        else if (neighbourTypeCount.get(CellType.LACTOBACILLUS) != null
                && (neighbourTypeCount.get(CellType.LACTOBACILLUS) >= 4)) {
            updateState(CellType.DEADCELL);
        }

        else {
            updateState(CellType.LACTOBACILLUS);
        }
    }
}
