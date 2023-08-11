package cs3500.pa04.client.model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Generates a random ship placement on a game board.
 */
public class RandomShipGen {
  private final Random rand;

  /**
   * Instantiates a new random ship placement
   *
   * @param rand the random
   */
  public RandomShipGen(Random rand) {
    this.rand = rand;
  }

  /**
   * Gets random ship.
   *
   * @param board    the board
   * @param shipType the ship type
   * @return the random ship
   */
  public Ship getRandomShip(GameBoard board, ShipType shipType) {
    ArrayList<Coord> arrayCoordThisShip = new ArrayList<>();
    Ship newShip = new Ship(shipType, arrayCoordThisShip);

    int randHeight2 = rand.nextInt(board.getHeight());
    int randWidth2 = rand.nextInt(board.getWidth());

    Coord initalCoord = board.getGameBoard()[randHeight2][randWidth2];

    while (!isFreeOneCoord(initalCoord, board, shipType)) {
      randWidth2 = rand.nextInt(board.getWidth());
      randHeight2 = rand.nextInt(board.getHeight());
      initalCoord = board.getGameBoard()[randHeight2][randWidth2];
    }

    if (canGoRight(initalCoord, board, shipType)) {
      ArrayList<Coord> cord = goRightCoord(initalCoord, board, shipType);
      newShip = new Ship(shipType, cord);

    } else if (canGoDown(initalCoord, board, shipType)) {
      ArrayList<Coord> cord = goDownCoord(initalCoord, board, shipType);
      newShip = new Ship(shipType, cord);
    }
    return newShip;
  }

  /**
   * Checks if this coord
   * is valid. It checks
   * if it can be placed
   * either top, left, right
   * or bottom. Also checks
   * if it is in bounds and isn't
   * already a ship.
   *
   * @param board user board
   * @param shipType ship type
   * @param coord this coord
   *
   */
  private boolean isFreeOneCoord(Coord coord, GameBoard board, ShipType shipType) {
    return !coord.getContainsShip()
        && (canGoDown(coord, board, shipType)
        || canGoRight(coord, board, shipType));
  }

  /**
   * Checks if this ship can be
   * built down from the
   * first coord
   *
   * @param board this board
   * @param firstCoord the first cord of
   *                   ship
   * @param shipType the type of ship
   *
   */
  private boolean canGoDown(Coord firstCoord, GameBoard board, ShipType shipType) {
    boolean canGo = true;
    for (int i = 0; i < shipType.getLength() && canGo; i++) {
      if (firstCoord.getY() + i < board.getHeight()) {
        Coord thisCoord = board.getGameBoard()[firstCoord.getY() + i][firstCoord.getX()];

        canGo = !thisCoord.getContainsShip();
      } else {
        canGo = false;
      }
    }
    return canGo;
  }

  /**
   * Checks if this ship can be
   * built to right from the
   * first coord
   *
   * @param board this board
   * @param firstCoord the first cord of
   *                   ship
   * @param shipType the type of ship
   *
   */
  private boolean canGoRight(Coord firstCoord, GameBoard board, ShipType shipType) {
    boolean canGo = true;
    for (int i = 0; i < shipType.getLength() && canGo; i++) {
      if (firstCoord.getX() + i < board.getWidth()) {
        Coord thisCoord = board.getGameBoard()[firstCoord.getY()][firstCoord.getX() + i];
        if (thisCoord.getContainsShip()) {
          canGo = false;
        }
      } else {
        canGo = false;
      }
    }
    return canGo;
  }

  /**
   * Takes list of coords to the
   * right of the first coord
   * for the ship type size
   *
   * @param board this board
   * @param firstCoord the first cord of
   *                   ship
   * @param shipType the type of ship
   *
   * @return list of coordinates
   *
   */
  private ArrayList<Coord> goRightCoord(Coord firstCoord, GameBoard board, ShipType shipType) {

    ArrayList<Coord> listCoord = new ArrayList<>();
    for (int i = 0; i < shipType.getLength(); i++) {

      Coord thisCoord = board.getGameBoard()[firstCoord.getY()][firstCoord.getX() + i];
      thisCoord.setContainsShip();
      listCoord.add(thisCoord);
    }
    return listCoord;
  }

  /**
   * Takes list of coords
   * down of the first coord
   * for the ship type size
   *
   * @param board this board
   * @param firstCoord the first cord of
   *                   ship
   * @param shipType the type of ship
   *
   * @return list of coordinates
   */
  private ArrayList<Coord> goDownCoord(Coord firstCoord, GameBoard board, ShipType shipType) {
    ArrayList<Coord> listCoord = new ArrayList<>();
    for (int i = 0; i < shipType.getLength(); i++) {

      Coord thisCoord = board.getGameBoard()[firstCoord.getY() + i][firstCoord.getX()];
      thisCoord.setContainsShip();
      listCoord.add(thisCoord);
    }
    return listCoord;
  }
}
