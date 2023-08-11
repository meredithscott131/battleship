package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa04.client.model.Coord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test Coord class.
 */
class CoordTest {
  private Coord coord;

  /**
   * Innit.
   */
  @BeforeEach
  public void innit() {
    coord = new Coord(1, 2);
  }

  /**
   * Test hit method.
   */
  @Test
  public void testHit() {
    coord.hit();
    assertTrue(coord.getVisited());
  }

  /**
   * Test getVisited method.
   */
  @Test
  public void testGetVisited() {
    assertFalse(coord.getVisited());
  }

  /**
   * Test getX method.
   */
  @Test
  public void testGetX() {
    assertEquals(1, coord.getX());
  }

  /**
   * Test getY method.
   */
  @Test
  public void testGetY() {
    assertEquals(2, coord.getY());
  }

  /**
   * Test getContainsShip method.
   */
  @Test
  public void testGetContainsShip() {
    assertFalse(coord.getContainsShip());
  }

  /**
   * Test changeContainsShip method.
   */
  @Test
  public void testChangeContainsShip() {
    coord.setContainsShip();
    assertTrue(coord.getContainsShip());
  }
}