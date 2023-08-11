package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.client.model.Coord;
import cs3500.pa04.client.model.Ship;
import cs3500.pa04.client.model.ShipAdapter;
import cs3500.pa04.client.model.ShipDirection;
import cs3500.pa04.client.model.ShipType;
import cs3500.pa04.json.objects.CoordJson;
import cs3500.pa04.json.objects.ShipJson;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the ShipAdapter class.
 */
public class ShipAdapterTest {
  private Ship shipSub;
  private Ship shipCarrier;
  private ShipJson shipJsonSub;
  private ShipJson shipJsonCarrier;
  private ShipJson shipJsonBattle;
  private ShipJson shipJsonDestroyer;

  /**
   * Innit.
   */
  @BeforeEach
  public void innit() {
    shipSub = new Ship(ShipType.SUBMARINE, new ArrayList<>(
        Arrays.asList(new Coord(0, 0), new Coord(0, 1), new Coord(0, 2))));
    shipCarrier = new Ship(ShipType.CARRIER, new ArrayList<>(
        Arrays.asList(new Coord(0, 0), new Coord(1, 0), new Coord(2, 0),
            new Coord(3, 0), new Coord(4, 0), new Coord(5, 0))));
    shipJsonSub = new ShipJson(new CoordJson(0, 0), 3, ShipDirection.HORIZONTAL);
    shipJsonCarrier = new ShipJson(new CoordJson(0, 0), 6, ShipDirection.VERTICAL);
    shipJsonBattle = new ShipJson(new CoordJson(0, 0), 5, ShipDirection.HORIZONTAL);
    shipJsonDestroyer = new ShipJson(new CoordJson(0, 0), 4, ShipDirection.VERTICAL);
  }

  /**
   * Test convertToShip method with a submarine.
   */
  @Test
  public void testConvertToShipSub() {
    ShipAdapter adapter = new ShipAdapter(shipJsonSub);
    Ship newShip = adapter.convertToShip();
    assertEquals(ShipType.SUBMARINE, newShip.getType());
  }

  /**
   * Test convertToShip method with a destroyer.
   */
  @Test
  public void testConvertToShipDestroyer() {
    ShipAdapter adapter = new ShipAdapter(shipJsonDestroyer);
    Ship newShip = adapter.convertToShip();
    assertEquals(ShipType.DESTROYER, newShip.getType());
  }

  /**
   * Test convertToShip method with a battleship.
   */
  @Test
  public void testConvertToShipBattle() {
    ShipAdapter adapter = new ShipAdapter(shipJsonBattle);
    Ship newShip = adapter.convertToShip();
    assertEquals(ShipType.BATTLESHIP, newShip.getType());
  }

  /**
   * Test convertToShip method with a carrier.
   */
  @Test
  public void testConvertToShipCarrier() {
    ShipAdapter adapter = new ShipAdapter(shipJsonCarrier);
    Ship newShip = adapter.convertToShip();
    assertEquals(ShipType.CARRIER, newShip.getType());
  }

  /**
   * Test convertToShipJson method with vertical alignment.
   */
  @Test
  public void testConvertToShipJsonVertical() {
    ShipAdapter adapter = new ShipAdapter(shipSub);
    ShipJson newShip = adapter.convertToShipJson();
    assertEquals(ShipDirection.VERTICAL, newShip.direction());
  }

  /**
   * Test convertToShipJson method with horizontal alignment.
   */
  @Test
  public void testConvertToShipJsonHorizontal() {
    ShipAdapter adapter = new ShipAdapter(shipCarrier);
    ShipJson newShip = adapter.convertToShipJson();
    assertEquals(ShipDirection.HORIZONTAL, newShip.direction());
  }
}