package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa04.client.model.Coord;
import cs3500.pa04.client.model.GameBoard;
import cs3500.pa04.client.model.Ship;
import cs3500.pa04.client.model.ShipType;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The type Game board test.
 */
class GameBoardTest {

  /**
   * The Board.
   */
  GameBoard board;

  /**
   * Innit.
   */
  @BeforeEach
  public void innit() {
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

    board = new GameBoard(8, 8, new ArrayList<>(Arrays.asList(s, d, b)));
  }

  /**
   * Test updateBoard method.
   */
  @Test
  public void testUpdateBoard() {
    ArrayList<Coord> hits = new ArrayList<>(Arrays.asList(new Coord(7, 0),
        new Coord(7, 1),
        new Coord(7, 2),
        new Coord(7, 3)));
    board.updateBoard(hits);
    assertTrue(board.getGameBoard()[0][7].getVisited());
  }

  /**
   * Test getGameBoard method.
   */
  @Test
  public void testGetGameBoard() {
    assertEquals(8, board.getGameBoard().length);
  }

  /**
   * Test getShips method.
   */
  @Test
  public void testGetShips() {
    assertEquals(3, this.board.getShips().size());
  }

  /**
   * Test activeShips method.
   */
  @Test
  public void testActiveShips() {
    assertEquals(3, this.board.activeShips());
    this.board.getShips().get(0).hit(new Coord(0, 0));
    this.board.getShips().get(0).hit(new Coord(0, 1));
    this.board.getShips().get(0).hit(new Coord(0, 2));
    assertEquals(2, this.board.activeShips());
  }

  /**
   * Test setShips method.
   */
  @Test
  public void testSetShips() {
    Ship s1 = new Ship(ShipType.BATTLESHIP,
        new ArrayList<>(Arrays.asList(new Coord(0, 0),
            new Coord(0, 1))));
    Ship s2 = new Ship(ShipType.CARRIER,
        new ArrayList<>(Arrays.asList(new Coord(0, 0),
            new Coord(0, 1))));

    board.setShips(new ArrayList<>(Arrays.asList(s1, s2)));
    assertEquals(s2, board.getShips().get(1));
  }

  /**
   * Test emptyCoords method.
   */
  @Test
  public void testEmptyCoords() {
    assertEquals(64, board.emptyCoords());
    board.getGameBoard()[0][0].hit();
    assertEquals(63, board.emptyCoords());
  }
}