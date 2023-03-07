import java.awt.Color;

/**
 * A subclass of Cell representing a cancer cell.
 * Cancer cells can become malignant, and can spread to neighbouring cells.
 * Cancer cells are magenta, and malignant cancer cells are red.
 * Fun Fact: Cancer cells are the only cells in the body that can divide
 * indefinitely.
 *
 * @author Rayan Popat (K21056367) & James Coward (K22004743)
 * @version 2023.02.23
 */

public class Cancer extends Cell {

    // class fields
    private boolean malignant;

    // chance for a cancer cell to become malignant
    private final double malignantChance = 0.005;

    /**
     * Cancer cell constructor.
     *
     * @param field    The field currently occupied.
     * @param location The location within the field.
     * @param type     The type of cell.
     */
    public Cancer(Field field, Location location, CellType type) {
        super(field, location, type);
        setColor(Color.MAGENTA);
        this.malignant = false;
    }

    /*
     * Act method
     * Increments the cell age, and checks if the cell is malignant.
     * If the cell is not malignant, it has a chance to become malignant.
     */
    public void act() {

        if (!malignant) {
            if (Math.random() <= malignantChance) {
                setColor(Color.RED);
                this.malignant = true;
            }
        }

        updateState(CellType.CANCER);
    }

    /*
     * Returns whether the cell is malignant or not.
     */
    public boolean isMalignant() {
        return malignant;
    }
}
