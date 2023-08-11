package cs3500.pa04.client.model;

/**
 * Representation of a ship type in a game of BattleSalvo.
 */
public enum ShipType {
  CARRIER(6),
  BATTLESHIP(5),
  DESTROYER(4),
  SUBMARINE(3);

  private final int length;

  ShipType(int length) {
    this.length = length;
  }

  /**
   * Gets the length of the ship type.
   *
   * @return the length of the ship type.
   */
  public int getLength() {
    return length;
  }
}
