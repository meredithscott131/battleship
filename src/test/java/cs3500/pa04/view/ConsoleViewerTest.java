package cs3500.pa04.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.client.model.Coord;
import cs3500.pa04.client.model.GameBoard;
import cs3500.pa04.client.model.GameResult;
import cs3500.pa04.client.model.Ship;
import cs3500.pa04.client.model.ShipType;
import cs3500.pa04.client.view.ConsoleViewer;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * Tests the ConsoleViewer class.
 */
class ConsoleViewerTest {
  private ConsoleViewer view;

  /**
   * Test boardParameters method.
   */
  @Test
  public void testBoardParameters() {
    Readable in = new StringReader("na");
    view = new ConsoleViewer(in);

    assertEquals("""
        Hello! Welcome to the OOD BattleSalvo Game!
        Please enter a valid height and width below:
        ------------------------------------------------------
        """, view.boardParameters());
  }

  /**
   * Test askParameters method.
   */
  @Test
  public void testAskParameters() {
    Readable in = new StringReader("6 6");
    view = new ConsoleViewer(in);

    assertEquals(6, view.askParameters().get(0));
  }

  /**
   * Test invalidDimensions method.
   */
  @Test
  public void testInvalidDimensions() {
    Readable in = new StringReader("4 6");
    view = new ConsoleViewer(in);

    assertEquals("""
        ------------------------------------------------------
        Uh Oh! You've entered invalid dimensions. Please remember that the height and width
        of the game must be in the range (6, 10), inclusive. Try again!
        ------------------------------------------------------
        """, view.invalidDimensions());
  }

  /**
   * Test fleetSelection method.
   */
  @Test
  public void testFleetSelection() {
    Readable in = new StringReader("6 6");
    view = new ConsoleViewer(in);

    assertEquals("""
        ------------------------------------------------------
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        Remember, your fleet may not exceed size 6.
        ------------------------------------------------------
        """, view.fleetSelection(6));
  }

  /**
   * Test askFleet method.
   */
  @Test
  public void testAskFleet() {
    Readable in = new StringReader("1 1 1 1");
    view = new ConsoleViewer(in);

    assertEquals(1, view.askFleet().get(0));
  }

  /**
   * Test invalidFleet method.
   */
  @Test
  public void testInvalidFleet() {
    Readable in = new StringReader("1 1 1 1");
    view = new ConsoleViewer(in);

    assertEquals("""
        --------------------------------------------------------------------------------
        Uh Oh! You've entered invalid fleet sizes.
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        Remember, your fleet may not exceed size 6.
        ------------------------------------------------------
        """, view.invalidFleet(6));
  }

  /**
   * Test show gameBoards method.
   */
  @Test
  public void testShowBoards() {
    Readable in = new StringReader("");
    view = new ConsoleViewer(in);

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

    GameBoard b1 = new GameBoard(8, 8, new ArrayList<>(Arrays.asList(s, d, b)));

    ArrayList<Coord> coords4 = new ArrayList<>(Arrays.asList(new Coord(0, 0),
        new Coord(0, 1),
        new Coord(0, 2)));
    Ship s4 = new Ship(ShipType.SUBMARINE, coords4);

    ArrayList<Coord> coords5 = new ArrayList<>(Arrays.asList(new Coord(2, 1),
        new Coord(3, 1),
        new Coord(4, 1)));
    Ship d5 = new Ship(ShipType.DESTROYER, coords5);

    ArrayList<Coord> coords6 = new ArrayList<>(Arrays.asList(new Coord(7, 0),
        new Coord(7, 1),
        new Coord(7, 2),
        new Coord(7, 3)));
    Ship b6 = new Ship(ShipType.BATTLESHIP, coords6);

    GameBoard b2 = new GameBoard(8, 8, new ArrayList<>(Arrays.asList(s4, d5, b6)));

    assertEquals("""
        ------------------------------------------------------
        Opponent Board Data:
        . . . . . . . .
        . . . . . . . .
        . . . . . . . .
        . . . . . . . .
        . . . . . . . .
        . . . . . . . .
        . . . . . . . .
        . . . . . . . .
                
        Your Board:
        . . . . . . . .
        . . . . . . . .
        . . . . . . . .
        . . . . . . . .
        . . . . . . . .
        . . . . . . . .
        . . . . . . . .
        . . . . . . . .
        
        """, view.showBoards(b1, b2));
  }

  /**
   * Test enterShots method.
   */
  @Test
  public void testEnterShots() {
    Readable in = new StringReader("6 6 1 1 1 1");
    view = new ConsoleViewer(in);

    assertEquals("""
        Please Enter 4 Shots:
        --------------------------------------------------------------------------------
        """, view.enterShots(4));
  }

  /**
   * Test invalidShots method.
   */
  @Test
  public void testInvalidShots() {
    Readable in = new StringReader("6 6 1 1 1 1");
    view = new ConsoleViewer(in);

    assertEquals("""
        Uh oh! You entered invalid shots. Please Enter 4 Shots:
        Make sure the coordinates are within the dimensions of the board.
        --------------------------------------------------------------------------------
        """, view.invalidShots(4));
  }

  /**
   * Test askShots method.
   */
  @Test
  public void testAskShots() {
    Readable in = new StringReader("0 0 0 1 0 2 0 3");
    view = new ConsoleViewer(in);

    assertEquals(4, view.askShots(4).size());
  }

  /**
   * Test gameOver method.
   */
  @Test
  public void testGameOver() {
    Readable in = new StringReader("");
    view = new ConsoleViewer(in);

    assertEquals("Game Over: Congratulations! You win.", view.gameOver(GameResult.WIN));
    assertEquals("Game Over: You lose :(", view.gameOver(GameResult.LOSE));
    assertEquals("Game Over: This game is a draw.", view.gameOver(GameResult.DRAW));
  }
}