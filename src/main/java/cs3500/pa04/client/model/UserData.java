package cs3500.pa04.client.model;

import java.util.ArrayList;

/**
 * Representation of a player controlled by a console user.
 */
public class UserData extends PlayerData {

  /**
   * Instantiates a new UserData.
   *
   * @param board the player's board
   * @param shots the shots taken by the player.
   */
  public UserData(GameBoard board, ArrayList<Coord> shots) {
    super(board, shots);
  }
}