package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.client.model.Coord;
import cs3500.pa04.client.model.GameBoard;
import cs3500.pa04.client.model.Ship;
import cs3500.pa04.client.model.ShipType;
import cs3500.pa04.client.model.UserData;
import cs3500.pa04.client.model.UserPlayer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test the UserPlayer class.
 */
class UserPlayerTest {
  private UserPlayer userplayer;
  private Map<ShipType, Integer> map;
  private ArrayList<Coord> shots;

  /**
   * Innit.
   */
  @BeforeEach
  public void innit() {
    List<Ship> ships = new ArrayList<>(List.of(new Ship(ShipType.SUBMARINE, new ArrayList<>(
        Arrays.asList(new Coord(0, 0),
            new Coord(1, 0), new Coord(2, 0))))));
    shots = new ArrayList<>(List.of(new Coord(0, 0),
                                    new Coord(0, 1),
                                    new Coord(0, 2)));
    userplayer = new UserPlayer(new UserData(new GameBoard(8, 8, ships), shots), 8);
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
    List<Ship> ships = userplayer.setup(8, 8, map);
    assertEquals(4, ships.get(0).getCoords().get(2).getX());
    assertEquals(1, ships.get(1).getCoords().get(1).getX());
    assertEquals(3, ships.get(2).getCoords().get(0).getX());
  }

  /**
   * Test takeShots method.
   */
  @Test
  public void testTakeShots() {
    assertEquals(shots.get(0).getY(), userplayer.takeShots().get(0).getY());
  }

  /**
   * Test reportDamage method.
   */
  @Test
  public void testReportDamage() {
    assertEquals(0,
        userplayer.reportDamage(new ArrayList<>(List.of(new Coord(2, 0),
            new Coord(4, 4),
            new Coord(2, 6)))).size());
  }
}