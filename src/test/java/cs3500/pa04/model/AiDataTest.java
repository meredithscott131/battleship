package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import cs3500.pa04.client.model.AiData;
import cs3500.pa04.client.model.Coord;
import cs3500.pa04.client.model.GameBoard;
import cs3500.pa04.client.model.Ship;
import cs3500.pa04.client.model.ShipType;
import cs3500.pa04.client.model.UserData;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test the AiData class.
 */
class AiDataTest {
  private AiData compData;
  private UserData userData;

  /**
   * Innit.
   */
  @BeforeEach
  public void innit() {
    userData = new UserData(new GameBoard(6, 6, new ArrayList<>()), new ArrayList<>());

    ArrayList<Coord> coords1 = new ArrayList<>(Arrays.asList(new Coord(0, 0),
        new Coord(0, 1),
        new Coord(0, 2)));
    Ship s = new Ship(ShipType.SUBMARINE, coords1);

    ArrayList<Coord> coords2 = new ArrayList<>(Arrays.asList(new Coord(2, 1),
        new Coord(3, 1),
        new Coord(4, 1)));
    Ship d = new Ship(ShipType.DESTROYER, coords2);

    ArrayList<Coord> coords3 = new ArrayList<>(Arrays.asList(new Coord(7, 0),
        new Coord(7, 1),
        new Coord(7, 2),
        new Coord(7, 3)));
    Ship b = new Ship(ShipType.BATTLESHIP, coords3);

    GameBoard board = new GameBoard(8, 8, new ArrayList<>(Arrays.asList(s, d, b)));
    ArrayList<Coord> shots = new ArrayList<>();
    compData = new AiData(board, shots, 8);
  }

  /**
   * Test getBoard method.
   */
  @Test
  public void testGetBoard() {
    assertNotNull(compData.getBoard());
  }

  /**
   * Test numShots method.
   */
  @Test
  public void testNumShots() {
    assertEquals(3, compData.numShots(userData.getBoard()));
    compData.getBoard().getShips().get(0).getCoords().get(0).hit();
    compData.getBoard().getShips().get(0).getCoords().get(1).hit();
    compData.getBoard().getShips().get(0).getCoords().get(2).hit();
    assertEquals(2, compData.numShots(userData.getBoard()));
  }

  /**
   * Test setShots method.
   */
  @Test
  public void testSetShots() {
    compData.setShots(compData.getShots());
    assertEquals(3, compData.getShots().size());
  }

  /**
   * Test setShips method.
   */
  @Test
  public void testSetShips() {
    ArrayList<Ship> ships = new ArrayList<>(Arrays.asList(new Ship(ShipType.BATTLESHIP,
        new ArrayList<>()), new Ship(ShipType.CARRIER, new ArrayList<>())));
    compData.setShips(ships);
    assertEquals(ships, compData.getBoard().getShips());
  }
}
