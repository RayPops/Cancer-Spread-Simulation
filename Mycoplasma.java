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

public class Mycoplasma extends Cell {

    /**
     * Create a new Mycoplasma.
     *
     * @param field    The field currently occupied.
     * @param location The location within the field.
     */
    public Mycoplasma(Field field, Location location, CellType type) {
        super(field, location, type);
        setColor(Color.ORANGE);
        // setType("Mycoplasma");
    }

    /**
     * Act method
     * Increments the cell age, and checks if the cell has cancer neighbours that
     * are malignant (and therefore able to spread).
     * If the cell has between 2 and 3 mycoplasma cells as neighbours, it will
     * become a mycoplasma cell.
     * If the cell has more than 3 saureus cells as neighbours, it will become a
     * dead cell (competition).
     */
    public void act() {
        HashMap<CellType, Integer> neighbourTypeCount = getNeighbourTypes();

        if (neighbourTypeCount.get(CellType.CANCER) != null && checkMalignant()) {
            if (Math.random() <= spreadChance) {
                updateState(CellType.CANCER);
                return;
            }
        }

        if (neighbourTypeCount.get(CellType.DEADCELL) != null && (neighbourTypeCount.get(CellType.DEADCELL) >= 6)) {
            updateState(CellType.DEADCELL);
        }

        if (neighbourTypeCount.get(CellType.MYCOPLASMA) != null && (neighbourTypeCount.get(CellType.MYCOPLASMA) > 3 || neighbourTypeCount.get(CellType.MYCOPLASMA) < 2)) {
            updateState(CellType.DEADCELL);
        }

        else if (neighbourTypeCount.get(CellType.MYCOPLASMA) != null && (neighbourTypeCount.get(CellType.MYCOPLASMA) == 2 || neighbourTypeCount.get(CellType.MYCOPLASMA) == 3)) {
            updateState(CellType.MYCOPLASMA);
        }

        else if (neighbourTypeCount.get(CellType.SAUREUS) != null && (neighbourTypeCount.get(CellType.SAUREUS) > 3)) {
            updateState(CellType.DEADCELL);
        }

        else {
            updateState(CellType.MYCOPLASMA);
        }
    }
}
