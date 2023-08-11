package cs3500.pa04.client.model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Representation of a player controlled artificially by the computer.
 */
public class AiData extends PlayerData {
  private final Random rand;
  private final ArrayList<Coord> shotList = new ArrayList<>();

  /**
   * Instantiates a new AiData.
   *
   * @param board the player's board
   * @param shots the shots taken by the player
   */
  public AiData(GameBoard board, ArrayList<Coord> shots) {
    super(board, shots);
    this.rand = new Random();
  }

  /**
   * Instantiates a new AiData.
   *
   * @param board the player's board
   * @param shots the shots taken by the player
   * @param seed  the seed to determine randomness of the shots taken by the player.
   */
  public AiData(GameBoard board, ArrayList<Coord> shots, int seed) {
    super(board, shots);
    this.rand = new Random(seed);
  }

  /**
   * Instantiates a new AiData given a Random.
   *
   * @param board the player's board
   * @param shots the shots taken by the player
   * @param rand  determines the randomness of the shots taken by the player.
   */
  public AiData(GameBoard board, ArrayList<Coord> shots, Random rand) {
    super(board, shots);
    this.rand = rand;
  }

  /**
   * Gets shots taken by the player.
   *
   * @return the shots
   */

  public ArrayList<Coord> getShots() {
    ArrayList<Coord> coords = new ArrayList<>();

    int width = this.getBoard().getGameBoard()[0].length;
    int length = this.getBoard().getGameBoard().length;
    int activeShips = this.getBoard().activeShips();
    int numShotsLeft = Math.min((width * length) - shotList.size(), activeShips);

    for (int i = 0; i < numShotsLeft; i++) {
      int x = rand.nextInt(width);
      int y = rand.nextInt(length);
      Coord initCoord = new Coord(x, y);

      while (shotList.contains(initCoord)) {
        x = rand.nextInt(this.getBoard().getGameBoard()[0].length);
        y = rand.nextInt(this.getBoard().getGameBoard().length);
        initCoord = new Coord(x, y);
      }

      shotList.add(initCoord);
      coords.add(initCoord);
    }
    return coords;
  }
}