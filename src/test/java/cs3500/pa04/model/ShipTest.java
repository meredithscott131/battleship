package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa04.client.model.Coord;
import cs3500.pa04.client.model.Ship;
import cs3500.pa04.client.model.ShipType;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test the Ship class.
 */
class ShipTest {
  private Ship ship;

  /**
   * Innit.
   */
  @BeforeEach
  public void innit() {
    Coord c1 = new Coord(2, 2);
    Coord c2 = new Coord(2, 3);
    Coord c3 = new Coord(2, 4);
    ArrayList<Coord> coordList = new ArrayList<>(Arrays.asList(c1, c2, c3));
    this.ship = new Ship(ShipType.DESTROYER, coordList);
  }

  /**
   * Test hit method.
   */
  @Test
  public void testHit() {
    this.ship.hit(new Coord(2, 2));
    this.ship.hit(new Coord(2, 3));
    this.ship.hit(new Coord(2, 4));
    assertTrue(this.ship.isDown());
  }

  /**
   * Test isDown method.
   */
  @Test
  public void testIsDown() {
    assertFalse(this.ship.isDown());
  }

  /**
   * Test getCoords method.
   */
  @Test
  public void testGetCoords() {
    assertEquals(3, this.ship.getCoords().size());
  }

  /**
   * Test getType method.
   */
  @Test
  public void testGetType() {
    assertEquals(ShipType.DESTROYER, this.ship.getType());
  }
}