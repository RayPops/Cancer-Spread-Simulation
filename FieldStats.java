import java.util.HashMap;

/**
 * This class collects and provides some statistical data on the state
 * of a field. It is flexible: it will create and maintain a counter
 * for any class of object that is found within the field.
 *
 * @author Rayan Popat (K21056367) & James Coward (K22004743)
 * @version 2023.02.23
 */

public class FieldStats {
    // Counters for each type of life in the simulation.
    private HashMap<Class<?>, Counter> counters;

    // Whether the counters are currently up to date.
    private boolean countsValid;

    /**
     * Construct a FieldStats object. Set up a collection for counters for
     * each type of cell that we might find
     */
    public FieldStats() {
        counters = new HashMap<>();
        countsValid = true;
    }

    /**
     * Get details of what is in the field.
     * 
     * @return A string describing what is in the field.
     */
    public String getPopulationDetails(Field field) {
        StringBuffer buffer = new StringBuffer();
        if (!countsValid) {
            generateCounts(field);
        }
        for (Class<?> key : counters.keySet()) {
            Counter info = counters.get(key);
            buffer.append(info.getName());
            buffer.append(": ");
            buffer.append(info.getCount());
            buffer.append(' ');
        }
        return buffer.toString();
    }

    /**
     * Invalidate the current set of statistics; reset all
     * counts to zero.
     */
    public void reset() {
        countsValid = false;
        for (Class<?> key : counters.keySet()) {
            Counter count = counters.get(key);
            count.reset();
        }
    }

    /**
     * Increment the count for one class of life
     * 
     * @param cellClass The class of cell to increment.
     */
    public void incrementCount(Class<?> cellClass) {
        Counter count = counters.get(cellClass);

        if (count == null) {
            // We do not have a counter for this species yet. Create one.
            count = new Counter(cellClass.getName());
            counters.put(cellClass, count);
        }
        count.increment();
    }

    /**
     * Indicate that a cell count has been completed.
     */
    public void countFinished() {
        countsValid = true;
    }

    /**
     * Determine whether the simulation is still viable.
     * I.e., should it continue to run.
     * 
     * @return true If there is more than one life form alive
     */
    public boolean isViable(Field field) {
        int nonZero = 0;
        if (!countsValid) {
            generateCounts(field);
        }
        for (Class<?> key : counters.keySet()) {
            Counter info = counters.get(key);
            if (info.getCount() > 0) {
                nonZero++;
            }
        }

        return nonZero >= 1;
    }

    /**
     * Generate counts of the number of cells.
     * These are not kept up to date.
     * 
     * @param field The field to generate the stats for.
     */
    private void generateCounts(Field field) {
        reset();
        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                Cell cell = field.getObjectAt(row, col);

                if (cell != null) {
                    incrementCount(cell.getClass());
                }
            }
        }
        countsValid = true;
    }
}
