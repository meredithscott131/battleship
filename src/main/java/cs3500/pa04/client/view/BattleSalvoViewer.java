package cs3500.pa04.client.view;

import cs3500.pa04.client.model.Coord;
import cs3500.pa04.client.model.GameBoard;
import cs3500.pa04.client.model.GameResult;
import java.util.ArrayList;

/**
 * The interface for a BattleSalvo view.
 */
public interface BattleSalvoViewer {
  /**
   * The welcome message and call to input game board parameters.
   *
   * @return the string of the message.
   */
  String boardParameters();

  /**
   * Generates a list of 2 values for the game board parameter
   * inputted by the user.
   *
   * @return the list of parameter values.
   */
  ArrayList<Integer> askParameters();

  /**
   * A message alerting the user that they entered invalid
   * dimensions, and they must re-enter them.
   *
   * @return the message.
   */
  String invalidDimensions();

  /**
   * A message prompting the user to enter their desired fleet size.
   *
   * @param size the maximum size of the player's fleet.
   * @return the message.
   */
  String fleetSelection(int size);

  /**
   * Generates a list of fleet amounts to be placed on the player's
   * game board.
   *
   * @return the list of fleet amounts.
   */
  ArrayList<Integer> askFleet();

  /**
   * Alerts the user that their fleet amounts are invalid and
   * are prompted to re-enter.
   *
   * @param size the maximum size of the player's fleet.
   * @return the message.
   */
  String invalidFleet(int size);

  /**
   * Displays the game boards and in their current states.
   *
   * @param opponent    the opponent's board
   * @param player      the player's board
   * @return the game boards.
   */
  String showBoards(GameBoard opponent, GameBoard player);

  /**
   * A message alerting the user to enter their shots.
   *
   * @param numShots the number of shots the user can make.
   * @return the message.
   */
  String enterShots(int numShots);

  /**
   * A message alerting the user the shots they previously
   * entered were invalid and are prompted to re-enter.
   *
   * @param numShots the number of shots the user can make.
   * @return the message.
   */
  String invalidShots(int numShots);

  /**
   * Generates a list of the shots the user inputted into the console.
   *
   * @param numShots the number of shots the user can make.
   * @return the list of shots by the user.
   */
  ArrayList<Coord> askShots(int numShots);

  /**
   * A message alerting that the results of the game.
   *
   * @param result the result of the game.
   * @return the message.
   */
  String gameOver(GameResult result);
}