package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.client.model.AiData;
import cs3500.pa04.client.model.AiPlayer;
import cs3500.pa04.client.model.Coord;
import cs3500.pa04.client.model.GameBoard;
import cs3500.pa04.client.model.Ship;
import cs3500.pa04.client.model.ShipType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test AiPlayer class.
 */
class AiPlayerTest {
  private AiPlayer compPlayer;
  private Map<ShipType, Integer> map;

  /**
   * Innit.
   */
  @BeforeEach
  public void innit() {
    Coord c1 = new Coord(1, 0);
    c1.setContainsShip();
    List<Ship> ships = new ArrayList<>(List.of(new Ship(ShipType.SUBMARINE, new ArrayList<>(
        Arrays.asList(new Coord(0, 0),
            new Coord(1, 0), new Coord(2, 0))))));
    Coord c2 = new Coord(0, 0);
    c2.setContainsShip();
    ArrayList<Coord> shots = new ArrayList<>(List.of(c2,
        new Coord(0, 1),
        new Coord(0, 2)));
    compPlayer = new AiPlayer(new AiData(new GameBoard(8, 8, ships), shots), 8);
    map = new HashMap<>();
    map.put(ShipType.BATTLESHIP, 2);
    map.put(ShipType.CARRIER, 2);
    map.put(ShipType.SUBMARINE, 2);
    map.put(ShipType.DESTROYER, 2);
  }

  /**
   * Test setup method.
   */
  @Test
  public void testSetup() {
    List<Ship> ships = compPlayer.setup(8, 8, map);
    assertEquals(4, ships.get(0).getCoords().get(2).getX());
    assertEquals(4, ships.get(0).getCoords().get(2).getX());
    assertEquals(1, ships.get(1).getCoords().get(1).getX());
    assertEquals(3, ships.get(2).getCoords().get(0).getX());
  }

  /**
   * Test reportDamage method.
   */
  @Test
  public void testReportDamage() {
    assertEquals(1,
        compPlayer.reportDamage(new ArrayList<>(List.of(new Coord(2, 0),
            new Coord(4, 4),
            new Coord(2, 6)))).size());
  }
}