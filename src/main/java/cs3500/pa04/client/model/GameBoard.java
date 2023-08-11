package cs3500.pa04.client.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representation of a player's game board in a game of BattleSalvo
 */
public class GameBoard {
  private final Coord[][] gameBoard;
  private List<Ship> ships;

  /**
   * Instantiates a new Game board.
   *
   * @param height the height of the board
   * @param width  the width of the board
   * @param ships  the ships on the board
   */
  public GameBoard(int height, int width, List<Ship> ships) {
    gameBoard = new Coord[height][width];
    for (int y = 0; y < gameBoard.length; y++) {
      for (int x = 0; x < gameBoard[y].length; x++) {
        gameBoard[y][x] = new Coord(x, y);
      }
    }
    this.ships = ships;
  }

  /**
   * Finds all coordinates of all ships on this board.
   *
   * @return a list of all the coordinates in all of this board's ships
   */
  public ArrayList<Coord> shipCoords() {
    ArrayList<Coord> shipCoords = new ArrayList<>();
    for (Ship s : this.ships) {
      shipCoords.addAll(s.getCoords());
    }
    return shipCoords;
  }

  /**
   * Update board based on the given list of
   * coordinate attacks from the enemy player.
   *
   * @param hits the coordinates
   */
  public void updateBoard(List<Coord> hits) {
    for (Coord hit : hits) {
      int x = hit.getX();
      int y = hit.getY();
      this.gameBoard[y][x].hit();

      for (Ship s : ships) {
        for (Coord c : s.getCoords()) {
          if (c.getX() == x && c.getY() == y) {
            s.hit(c);
          }
        }
      }
    }
  }

  /**
   * Get the game board as a 2d array.
   *
   * @return the game board
   */
  public Coord[][] getGameBoard() {
    return gameBoard;
  }

  /**
   * Gets the ships on the board.
   *
   * @return the ships
   */
  public List<Ship> getShips() {
    return this.ships;
  }

  /**
   * Calculates the amount of active ships on the board,
   *
   * @return the int
   */
  public int activeShips() {
    int shipCount = 0;
    for (Ship ship : this.ships) {
      if (!ship.isDown()) {
        shipCount++;
      }
    }
    return shipCount;
  }

  /**
   * Sets the list of ships on the game board to the given list.
   *
   * @param ships the ships
   */
  public void setShips(List<Ship> ships) {
    // marks the coordinates that contain ships
    for (Ship ship : ships) {
      for (int j = 0; j < ship.getCoords().size(); j++) {
        int x = ship.getCoords().get(j).getX();
        int y = ship.getCoords().get(j).getY();
        gameBoard[y][x].setContainsShip();
      }
    }
    this.ships = ships;
  }

  /**
   * Counts how many coordinates on this game board that haven't been visited.
   *
   * @return the int
   */
  public int emptyCoords() {
    int empty = 0;
    for (Coord[] coords : gameBoard) {
      for (Coord coord : coords) {
        if (!coord.getVisited()) {
          empty++;
        }
      }
    }
    return empty;
  }

  /**
   * Gets height*
   *
   * @return height height
   */
  public int getHeight() {
    return gameBoard.length;
  }

  /**
   * Gets width*
   *
   * @return width width
   */
  public int getWidth() {
    return gameBoard[0].length;
  }
}