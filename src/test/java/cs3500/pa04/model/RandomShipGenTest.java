package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import cs3500.pa04.client.model.Coord;
import cs3500.pa04.client.model.GameBoard;
import cs3500.pa04.client.model.RandomShipGen;
import cs3500.pa04.client.model.Ship;
import cs3500.pa04.client.model.ShipType;
import java.util.ArrayList;
import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 * Tests for the RandomShipGen class
 */
class RandomShipGenTest {

  /**
   * Tests the getRandomCoordFirst method.
   */
  @Test
  void getRandomCoordFirst() {

    Random rand = new Random(1);
    RandomShipGen randShipGen = new RandomShipGen(rand);

    ArrayList<Coord> shipCoords = new ArrayList<>();
    shipCoords.add(new Coord(4, 3));
    shipCoords.add(new Coord(4, 4));
    shipCoords.add(new Coord(4, 5));

    Ship ship = randShipGen.getRandomShip(new GameBoard(6, 6,
        new ArrayList<>()), ShipType.SUBMARINE);

    assertEquals(ship.getCoords().size(), 3);
    assertEquals(ship.getCoords(), shipCoords);

    RandomShipGen randShipGen2 = new RandomShipGen(rand);

    ArrayList<Coord> shipCoords2 = new ArrayList<>();
    shipCoords2.add(new Coord(3, 1));
    shipCoords2.add(new Coord(4, 1));
    shipCoords2.add(new Coord(5, 1));

    Ship ship2 = randShipGen2.getRandomShip(new GameBoard(6, 6,
        new ArrayList<>()), ShipType.SUBMARINE);

    assertEquals(ship2.getCoords().size(), 3);
    assertEquals(ship2.getCoords(), shipCoords2);
    assertNotEquals(ship2, ship);
  }
}
