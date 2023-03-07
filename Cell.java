import java.awt.Color;
import java.util.List;
import java.util.HashMap;

/**
 * A superclass representing a cell in the simulation.
 * Contains methods for getting and setting the cell's location, field, and
 * state.
 *
 * @author Rayan Popat (K21056367) & James Coward (K22004743)
 * @version 2023.02.23
 */

public abstract class Cell {
  // fields
  
  // Whether the cell is alive or not.
  private boolean alive;

  // The cell's field.
  private Field field;

  // The cell's position in the field.
  private Location location;

  // The cell's color
  private Color color = Color.white;

  // The cell's next type
  private CellType nextCellType;

  // The cell's type
  private CellType type;

  // The cell's age
  private int age;

  // chance of neighbour to be infected by cancer
  protected final double spreadChance = 0.05;

  /**
   * Constructor for objects of class Cell
   *
   * @param field    The field currently occupied.
   * @param location The location within the field.
   * @param type     The type of cell.
   */
  public Cell(Field field, Location location, CellType type) {
    alive = true;
    this.field = field;
    setLocation(location);
    this.type = type;
    setAge(0);
  }

  // mutator methods

  /**
   * Make this cell act - that is: the cell decides it's status in the
   * next generation.
   */
  abstract public void act();

  /**
   * Check whether the cell is alive or not.
   * 
   * @return true if the cell is still alive.
   */
  protected boolean isAlive() {
    return alive;
  }

  /**
   * Changes the type of cell.
   * 
   * @param nextType The cell's next type.
   */
  public void updateState(CellType nextType) {
    nextCellType = nextType;
  }

  /**
   * Mutates the color of the cell
   */
  public void setColor(Color col) {
    color = col;
  }

  /**
   * @return The cell's color.
   */
  public Color getColor() {
    return color;
  }

  /**
   * Return the cell's location.
   * 
   * @return The cell's location.
   */
  protected Location getLocation() {
    return location;
  }

  /**
   * Place the cell at the new location in the given field.
   * 
   * @param location The cell's location.
   */
  protected void setLocation(Location location) {
    this.location = location;
    field.place(this, location);
  }

  /*
   * Increments the cell's age.
   */
  protected void addAge() {
    this.age = this.age + 1;
  }

  /*
   * Sets the cell's age.
   */
  protected void setAge(int age) {
    this.age = age;
  }

  // accessor methods

  /**
   * Return the cell's field.
   * 
   * @return The cell's field.
   */
  protected Field getField() {
    return field;
  }

  /*
   * Returns the cell's type.
   * 
   * @return The cell's type.
   */
  protected CellType getType() {
    return type;
  }

  /*
   * Returns the cell's next type.
   * 
   * @return The cell's next type.
   */
  protected CellType getNextType() {
    return nextCellType;
  }

  /*
   * Returns a list of the cell's neighbours.
   * 
   * @return A list of the cell's neighbours.
   */
  protected List<Cell> getNeighbours() {
    return getField().getLivingNeighbours(getLocation());
  }

  /*
   * Returns a hashmap of the cell's neighbours.
   * Key is the cell type, value is the number of neighbours of that type.
   * 
   * @return A hashmap of the cell's neighbours.
   */
  protected HashMap<CellType, Integer> getNeighbourTypes() {
    HashMap<CellType, Integer> neighbourTypes = new HashMap<>();
    List<Cell> neighbours = getNeighbours();
    for (Cell neighbour : neighbours) {
      neighbourTypes.merge(neighbour.getType(), 1, Integer::sum);
    }
    return neighbourTypes;
  }

  /*
   * Checks if the cell has a cancerous neighbour.
   * 
   * @return true if the cell has a cancerous neighbour.
   */
  protected boolean checkMalignant() {
    List<Cell> neighbours = getNeighbours();
    for (Cell neighbour : neighbours) {
      if (neighbour.getType() == CellType.CANCER) {
        Cancer currentCancer = (Cancer) neighbour;
        return currentCancer.isMalignant();
      }
    }
    return false;
  }

  /*
   * Returns the cell's age.
   * 
   * @return The cell's age.
   */
  protected int getAge() {
    return this.age;
  }

}
