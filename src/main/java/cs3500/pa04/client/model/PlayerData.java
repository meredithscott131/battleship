package cs3500.pa04.client.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representation of a player's data.
 */
public abstract class PlayerData {
  private GameBoard board;
  private ArrayList<Coord> shots;

  /**
   * Instantiates a new PlayerData.
   *
   * @param board the player's game board.
   * @param shots the shots taken by the player.
   */
  PlayerData(GameBoard board, ArrayList<Coord> shots) {
    this.board = board;
    this.shots = shots;
  }

  /**
   * Gets the player's board.
   *
   * @return the board
   */
  public GameBoard getBoard() {
    return this.board;
  }

  /**
   * Calculates the number of shots this player can make based
   * on the amount of ships they have left active on their board.
   *
   * @param opponent the opponent
   * @return the int
   */
  public int numShots(GameBoard opponent) {
    return Math.min(this.board.activeShips(), opponent.emptyCoords());
  }

  /**
   * Sets the list of shots from the player.
   *
   * @param shots the shots
   */
  public void setShots(ArrayList<Coord> shots) {
    this.shots.clear();
    this.shots = shots;
  }

  /**
   * Gets shots taken by the player.
   *
   * @return the shots .
   */
  public ArrayList<Coord> getShots() {
    return this.shots;
  }

  /**
   * Sets the player's ships onto their game board.
   *
   * @param ships the ships
   */
  public void setShips(List<Ship> ships) {
    this.board.setShips(ships);
  }

  /**
   * Sets this player's game board.
   *
   * @param board the game board
   */
  public void setGameBoard(GameBoard board) {
    this.board = board;
  }
}