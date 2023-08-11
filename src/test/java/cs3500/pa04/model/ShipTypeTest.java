package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.client.model.ShipType;
import org.junit.jupiter.api.Test;

/**
 * Test ShipType enum.
 */
class ShipTypeTest {
  private final ShipType carrier = ShipType.CARRIER;
  private final ShipType battleship = ShipType.BATTLESHIP;
  private final ShipType destroyer = ShipType.DESTROYER;
  private final ShipType submarine = ShipType.SUBMARINE;

  /**
   * Test length of a ship.
   */
  @Test
  public void testLength() {
    assertEquals(6, carrier.getLength());
    assertEquals(5, battleship.getLength());
    assertEquals(4, destroyer.getLength());
    assertEquals(3, submarine.getLength());
  }
}