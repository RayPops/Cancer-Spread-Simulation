import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * A Life (Game of Life) simulator, first described by British mathematician
 * John Horton Conway in 1970.
 *
 * @author Rayan Popat (K21056367) & James Coward (K22004743)
 * @version 2023.02.23
 */

public class Simulator {
  // The default width for the grid.
  private static final int DEFAULT_WIDTH = 100;

  // The default depth of the grid.
  private static final int DEFAULT_DEPTH = 80;

  // List of cells in the field.
  private List<Cell> cells;

  // The current state of the field.
  private Field field;

  // The current generation of the simulation.
  private int generation;

  // A graphical view of the simulation.
  private SimulatorView view;

  private static final Map<CellType, Double> CELL_TYPE_PROBABILITIES = new HashMap<>();

  static {
    CELL_TYPE_PROBABILITIES.put(CellType.MYCOPLASMA, 0.5);
    CELL_TYPE_PROBABILITIES.put(CellType.SAUREUS, 0.4);
    CELL_TYPE_PROBABILITIES.put(CellType.LACTOBACILLUS, 0.2);
  }

  /**
   * Execute simulation
   */
  public static void main(String[] args) {
    Simulator sim = new Simulator();
    sim.simulate(4000);
  }

  /**
   * Construct a simulation field with default size.
   */
  public Simulator() {
    this(DEFAULT_DEPTH, DEFAULT_WIDTH);
  }

  /**
   * Create a simulation field with the given size.
   * 
   * @param depth Depth of the field. Must be greater than zero.
   * @param width Width of the field. Must be greater than zero.
   */
  public Simulator(int depth, int width) {
    if (width <= 0 || depth <= 0) {
      System.out.println("The dimensions must be greater than zero.");
      System.out.println("Using default values.");
      depth = DEFAULT_DEPTH;
      width = DEFAULT_WIDTH;
    }

    cells = new ArrayList<>();
    field = new Field(depth, width);

    // Create a view of the state of each location in the field.
    view = new SimulatorView(depth, width);

    // Setup a valid starting point.
    reset();
  }

  /**
   * Run the simulation from its current state for a reasonably long period,
   * (4000 generations).
   */
  public void runLongSimulation() {
    simulate(4000);
  }

  /**
   * Run the simulation from its current state for the given number of
   * generations. Stop before the given number of generations if the
   * simulation ceases to be viable.
   * 
   * @param numGenerations The number of generations to run for.
   */
  public void simulate(int numGenerations) {
    for (int gen = 1; gen <= numGenerations && view.isViable(field); gen++) {
      simOneGeneration();
      delay(10); // comment out to run simulation faster
    }
  }

  /**
   * Run the simulation from its current state for a single generation.
   * Iterate over the whole field updating the state of each life form.
   */
  public void simOneGeneration() {
    generation++;
    for (int row = 0; row < field.getDepth(); row++) {
      for (int col = 0; col < field.getWidth(); col++) {
        field.getObjectAt(row, col).act();
      }
    }

    for (int row = 0; row < field.getDepth(); row++) {
      for (int col = 0; col < field.getWidth(); col++) {
        Location location = new Location(row, col);
        Cell currentCell = field.getObjectAt(location);
        if (currentCell.getType() == currentCell.getNextType()) {
          continue;
        } else {

          // switch statement to change the cell type
          switch (currentCell.getNextType()) {
            case DEADCELL:
              Cell deadcell = new Deadcell(field, location, CellType.DEADCELL);
              field.place(deadcell, location);
              break;

            case MYCOPLASMA:
              Cell mycoplasmacell = new Mycoplasma(field, location, CellType.MYCOPLASMA);
              field.place(mycoplasmacell, location);
              break;

            case SAUREUS:
              Cell saureuscell = new Saureus(field, location, CellType.SAUREUS);
              field.place(saureuscell, location);
              break;

            case LACTOBACILLUS:
              Cell lactobacilluscell = new Lactobacillus(field, location, CellType.LACTOBACILLUS);
              field.place(lactobacilluscell, location);
              break;

            case CANCER:
              Cell cancercell = new Cancer(field, location, CellType.CANCER);
              field.place(cancercell, location);
              break;
          }
        }
      }
    }
    view.showStatus(generation, field);

  }

  /**
   * Reset the simulation to a starting position.
   */
  public void reset() {
    generation = 0;
    cells.clear();
    populate();

    // Show the starting state in the view.
    view.showStatus(generation, field);
  }

  /**
   * Randomly populate the field with cells, according to the
   * probabilities specified in the CELL_TYPE_PROBABILITIES map.
   */
  private void populate() {
    Random rand = new Random();
    field.clear();
    for (int row = 0; row < field.getDepth(); row++) {
      for (int col = 0; col < field.getWidth(); col++) {
        Location location = new Location(row, col);
        double randNum = rand.nextDouble();
        double cumulativeProb = 0.0;
        for (CellType type : CELL_TYPE_PROBABILITIES.keySet()) {
          cumulativeProb += CELL_TYPE_PROBABILITIES.get(type);
          if (randNum <= cumulativeProb) {
            switch (type) {
              case MYCOPLASMA:
                new Mycoplasma(field, location, type);
                break;
              case SAUREUS:
                new Saureus(field, location, type);
                break;
              case LACTOBACILLUS:
                new Lactobacillus(field, location, type);
                break;
              default:
                new Deadcell(field, location, type);
                break;
            }
            break;
          }
        }
      }
    }
  }

  // Get current generation
  public int getGeneration() {
    return generation;
  }

  /**
   * Pause for a given time.
   * 
   * @param millisec The time to pause for, in milliseconds
   */
  private void delay(int millisec) {
    try {
      Thread.sleep(millisec);
    } catch (InterruptedException ie) {
      // wake up
    }
  }
}
