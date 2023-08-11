package cs3500.pa04.client.model;

import cs3500.pa04.json.objects.CoordJson;
import cs3500.pa04.json.objects.ShipJson;
import java.util.ArrayList;

/**
 * Responsible for converting Ships to ShipJsons and vis versa
 */
public class ShipAdapter {
  private final Ship ship;
  private final ShipJson shipjs;

  /**
   * Instantiates a new Ship adapter given a ShipJson.
   *
   * @param shipjs the ship json
   */
  public ShipAdapter(ShipJson shipjs) {
    this.shipjs = shipjs;
    this.ship = null;
  }

  /**
   * Instantiates a new Ship adapter given a Ship.
   *
   * @param ship the ship
   */
  public ShipAdapter(Ship ship) {
    this.ship = ship;
    this.shipjs = null;
  }

  /**
   * Convert the ship json to a ship
   *
   * @return the new ship
   */
  public Ship convertToShip() {
    ShipType type = ShipType.CARRIER;
    ArrayList<Coord> coords = new ArrayList<>();

    // Sets this ship's type
    assert this.shipjs != null;
    if (this.shipjs.length() == 6) {
      type = ShipType.CARRIER;
    } else if (this.shipjs.length() == 5) {
      type = ShipType.BATTLESHIP;
    } else if (this.shipjs.length() == 4) {
      type = ShipType.DESTROYER;
    } else if (this.shipjs.length() == 3) {
      type = ShipType.SUBMARINE;
    }

    // Sets this ship's coordinates
    if (this.shipjs.direction().equals(ShipDirection.VERTICAL)) {
      for (int i = 0; i < this.shipjs.length(); i++) {
        coords.add(new Coord(this.shipjs.coord().xcoord(), this.shipjs.coord().ycoord() + i));
      }
    } else if (this.shipjs.direction().equals(ShipDirection.HORIZONTAL)) {
      for (int i = 0; i < this.shipjs.length(); i++) {
        coords.add(new Coord(this.shipjs.coord().xcoord() + i, this.shipjs.coord().ycoord()));
      }
    }
    return new Ship(type, coords);
  }

  /**
   * Convert the ship to a ship json
   *
   * @return the new ship json
   */
  public ShipJson convertToShipJson() {

    // Determine its length
    assert this.ship != null;
    int length = this.ship.getType().getLength();

    // Determine staring Coordinate
    CoordJson startingCoord = new CoordJson(this.ship.getCoords().get(0).getX(),
        this.ship.getCoords().get(0).getY());

    // Determine direction
    ShipDirection direction = ShipDirection.VERTICAL;
    if (this.ship.getCoords().get(0).getY() < this.ship.getCoords().get(1).getY()) {
      direction = ShipDirection.VERTICAL;
    } else if (this.ship.getCoords().get(0).getX() < this.ship.getCoords().get(1).getX()) {
      direction = ShipDirection.HORIZONTAL;
    }

    return new ShipJson(startingCoord, length, direction);
  }
}