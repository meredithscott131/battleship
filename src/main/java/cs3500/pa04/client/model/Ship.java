package cs3500.pa04.client.model;

import java.util.ArrayList;

/**
 * Representation of a ship in a game of BattleSalvo.
 */
public class Ship {
  private final ShipType type;
  private final ArrayList<Coord> coordinates;

  /**
   * Instantiates a new Ship.
   *
   * @param type        the type of ship
   * @param coordinates the coordinates belonging to this ship
   */
  public Ship(ShipType type, ArrayList<Coord> coordinates) {
    this.type = type;
    this.coordinates = coordinates;
    for (Coord c : coordinates) {
      c.setContainsShip();
    }
  }

  /**
   * Given a list of attacks from the opposing player, determines
   * if any hit the ship and updates its individual coordinates accordingly.
   *
   * @param coord the coordinates of the opposing player
   */
  public void hit(Coord coord) {
    for (Coord coordinate : this.coordinates) {
      if (coordinate.getX() == coord.getX() && coordinate.getY() == coord.getY()) {
        coordinate.hit();
      }
    }
  }

  /**
   * Determines whether the ship is down.
   *
   * @return if the ship is down or not
   */
  public boolean isDown() {
    boolean isDown = true;
    for (Coord c : this.coordinates) {
      if (!c.getVisited()) {
        isDown = false;
        break;
      }
    }
    return isDown;
  }

  /**
   * Gets a list of coordinates that belong to the ship.
   *
   * @return the coordinates of the ship.
   */
  public ArrayList<Coord> getCoords() {
    return this.coordinates;
  }

  /**
   * Gets the ship's type.
   *
   * @return the ship type.
   */
  public ShipType getType() {
    return this.type;
  }
}