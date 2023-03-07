import java.awt.Color;
import java.util.HashMap;

/**
 * Simplest form of life.
 * Fun Fact: S are one of the simplest forms of life. A type of
 * bacteria, they only have 500-1000 genes! For comparison, fruit flies have
 * about 14,000 genes.
 *
 * @author Rayan Popat (K21056367) & James Coward (K22004743)
 * @version 2023.02.23
 */

public class Saureus extends Cell {

    /**
     * Create a new Saureus.
     *
     * @param field    The field currently occupied.
     * @param location The location within the field.
     * @param type     The type of cell
     */
    public Saureus(Field field, Location location, CellType type) {
        super(field, location, type);
        setColor(Color.GREEN);
        // setType("Saureus");
    }

    /**
     * Act method
     * Increments the cell age, and checks if the cell has cancer neighbours that
     * are malignant (and therefore able to spread).
     * If the cell has less than 1 or more than 5 saureus cells as neighbours, it
     * will become a dead cell (loneliness and competition).
     * If the cell has between 2 and 3 saureus cells as neighbours, it will become a
     * saureus cell.
     * If the cell has more than 4 mycoplasma cells as neighbours, it will become a
     * dead cell (competition).
     * If the cell has more than 4 lactobacillus cells as neighbours, it will become
     * a dead cell (competition).
     * 
     */
    public void act() {

        HashMap<CellType, Integer> neighbourTypeCount = getNeighbourTypes();

        if (neighbourTypeCount.get(CellType.CANCER) != null && checkMalignant()) {
            if (Math.random() <= spreadChance) {
                updateState(CellType.CANCER);
                return;
            }
        }

        if (neighbourTypeCount.get(CellType.SAUREUS) != null
                && (neighbourTypeCount.get(CellType.SAUREUS) <= 1 || neighbourTypeCount.get(CellType.SAUREUS) >= 5)) {
            updateState(CellType.DEADCELL);
            ;
        }

        else if (neighbourTypeCount.get(CellType.SAUREUS) != null
                && (neighbourTypeCount.get(CellType.SAUREUS) >= 2 && neighbourTypeCount.get(CellType.SAUREUS) <= 3)) {
            updateState(CellType.SAUREUS);
        }

        else if (neighbourTypeCount.get(CellType.MYCOPLASMA) != null
                && (neighbourTypeCount.get(CellType.MYCOPLASMA) >= 4)) {
            updateState(CellType.DEADCELL);
        }

        else if (neighbourTypeCount.get(CellType.LACTOBACILLUS) != null
                && (neighbourTypeCount.get(CellType.LACTOBACILLUS) >= 6)) {
            updateState(CellType.DEADCELL);
        }

        else {
            updateState(CellType.SAUREUS);
        }
    }
}
