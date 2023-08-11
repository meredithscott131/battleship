package cs3500.pa04.client.model;

/**
 * Representation of a coordinate on a board in a game of BattleSalvo.
 */
public class Coord {
  private final int xcoord;
  private final int ycoord;
  private boolean isVisited;
  private boolean containsShip;

  /**
   * Instantiates a new Coordinate.
   *
   * @param x            the x
   * @param y            the y
   */
  public Coord(int x, int y) {
    this.xcoord = x;
    this.ycoord = y;
    this.isVisited = false;
    this.containsShip = false;
  }

  /**
   * Changes this coordinate to be visited by the opposing player.
   */
  public void hit() {
    this.isVisited = true;
  }

  /**
   * Compares this Coord with the given object
   */
  @Override
  public boolean equals(Object other) {
    if (other instanceof Coord o) {
      return o.getX() == this.getX() && o.getY() == this.getY();
    } else {
      throw new IllegalStateException("Cannot compare object because it is not a Coord");
    }
  }

  /**
   * Hashcode for this Coord
   */
  @Override
  public int hashCode() {
    return this.xcoord * 300 + this.ycoord * 100;
  }

  /**
   * Gets whether this coordinate has been visited.
   *
   * @return the visited
   */
  public boolean getVisited() {
    return isVisited;
  }

  /**
   * Gets the x of this coordinate.
   *
   * @return the x
   */
  public int getX() {
    return this.xcoord;
  }

  /**
   * Gets the y of this coordinate.
   *
   * @return the y
   */
  public int getY() {
    return this.ycoord;
  }

  /**
   * Gets whether this coordinate contains a ship.
   *
   * @return the contains ship
   */
  public boolean getContainsShip() {
    return containsShip;
  }

  /**
   * Changes this coordinate to contain a ship.
   */
  public void setContainsShip() {
    this.containsShip = true;
  }
}
