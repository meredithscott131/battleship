package cs3500.pa04.client.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Representation of a player in BattleSalvo controlled artificially by the computer.
 */
public class AiPlayer implements Player {
  private final Random rand;
  private final AiData data;

  /**
   * Instantiates a new AiPlayer.
   *
   * @param data the data of the player.
   * @param seed the seed of randomness for the placement of ships on the player's game board.
   */
  public AiPlayer(AiData data, int seed) {
    this.data = data;
    this.rand = new Random(seed);
  }

  /**
   * Instantiates a new AiPlayer.
   *
   * @param data the data
   */
  public AiPlayer(AiData data) {
    this.data = data;
    this.rand = new Random();
  }

  public AiPlayer(int seed) {
    this.data = new AiData(new GameBoard(6, 6, new ArrayList<>()), new ArrayList<>(), seed);
    this.rand = new Random((seed));
  }

  /**
   * Instantiates a new AiData given a Random.
   *
   * @param data the data
   * @param rand the randomness of the placement of the ships on the player's game board
   */
  public AiPlayer(AiData data, Random rand) {
    this.data = data;
    this.rand = rand;
  }

  /**
   * Get the player's name.
   *
   * @return the player's name
   */
  @Override
  public String name() {
    return null;
  }

  /**
   * Given the specifications for a BattleSalvo board, return a list of ships with their locations
   * on the board.
   *
   * @param height the height of the board, range: [6, 15] inclusive
   * @param width the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return the placements of each ship on the board
   */
  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    List<Ship> emptShips = new ArrayList<>();

    GameBoard board = new GameBoard(height, width, emptShips);
    this.getData().setGameBoard(board);

    List<Ship> newShip = new ArrayList<>();

    RandomShipGen randBoard = new RandomShipGen(rand);

    int numOfCarrier = specifications.get(ShipType.CARRIER);
    for (int i = 0; i < numOfCarrier; i++) {
      Ship thisShip = randBoard.getRandomShip(board, ShipType.CARRIER);
      newShip.add(thisShip);
    }

    int numOfBattleShip = specifications.get(ShipType.BATTLESHIP);
    for (int i = 0; i < numOfBattleShip; i++) {
      Ship thisShip = randBoard.getRandomShip(board, ShipType.BATTLESHIP);
      newShip.add(thisShip);
    }

    int numOfDestroyer = specifications.get(ShipType.DESTROYER);
    for (int i = 0; i < numOfDestroyer; i++) {
      Ship thisShip = randBoard.getRandomShip(board, ShipType.DESTROYER);
      newShip.add(thisShip);
    }

    int numOfSubmarine = specifications.get(ShipType.SUBMARINE);
    for (int i = 0; i < numOfSubmarine; i++) {
      Ship thisShip = randBoard.getRandomShip(board, ShipType.SUBMARINE);
      newShip.add(thisShip);
    }
    return newShip;
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public List<Coord> takeShots() {
    return this.data.getShots();
  }

  /**
   * Given the list of shots the opponent has fired on this player's board, report which
   * shots hit a ship on this player's board.
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return a filtered list of the given shots that contain all locations of shots that hit a ship
   */
  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    List<Coord> hits = new ArrayList<>();

    for (Coord c : opponentShotsOnBoard) {
      if (this.data.getBoard().shipCoords().contains(c)) {
        hits.add(c);
      }
    }
    return hits;
  }

  /**
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    //NA
  }

  /**
   * Notifies the player that the game is over.
   * Win, lose, and draw should all be supported
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   *
   */
  @Override
  public void endGame(GameResult result, String reason) {
    //NA
  }

  /**
   * Retrieves the data belonging to this player.
   * Only used in the ProxyController class for the sake of testing.
   *
   * @return the data belonging to this player
   */
  public AiData getData() {
    return this.data;
  }
}