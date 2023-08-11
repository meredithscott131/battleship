package cs3500.pa04.client.controller;

import cs3500.pa04.client.model.AiData;
import cs3500.pa04.client.model.AiPlayer;
import cs3500.pa04.client.model.Coord;
import cs3500.pa04.client.model.GameBoard;
import cs3500.pa04.client.model.GameResult;
import cs3500.pa04.client.model.Ship;
import cs3500.pa04.client.model.ShipType;
import cs3500.pa04.client.model.UserData;
import cs3500.pa04.client.model.UserPlayer;
import cs3500.pa04.client.view.ConsoleViewer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * The controller for the console BattleSalvo game.
 */
public class ConsoleController implements BattleSalvoController {
  private final Appendable out;
  private final ConsoleViewer view;
  private final Random rand;
  private UserPlayer userPlayer;
  private UserData userData;
  private ArrayList<Integer> fleet;
  private AiPlayer compPlayer;
  private AiData compData;
  private int height;
  private int width;

  /**
   * Instantiates a new ConsoleController.
   *
   * @param in  the in
   * @param out the out
   */
  public ConsoleController(Readable in, Appendable out) {
    this.out = out;
    view = new ConsoleViewer(in);
    this.rand = new Random();
  }

  /**
   * Instantiates a new ConsoleController.
   *
   * @param in   the in
   * @param out  the out
   * @param seed the seed for randomness involving ordering of ships and shots by the computer.
   */
  public ConsoleController(Readable in, Appendable out, int seed) {
    this.out = out;
    view = new ConsoleViewer(in);
    this.rand = new Random(seed);
  }

  /**
   * Play the BattleSalvo game.
   */
  @Override
  public void play() {
    this.selectParameters();
    this.selectFleet();
    this.setBoards();
    this.exchangeShots();
    this.displayResult();
  }

  /**
   * Prompts the user to select parameters for the game boards.
   */
  private void selectParameters() {
    try {
      this.out.append(view.boardParameters());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    ArrayList<Integer> parameters = view.askParameters();
    this.height = parameters.get(0);
    this.width = parameters.get(1);
    while (this.height < 6 || this.height > 15 || this.width < 6 || this.width > 15) {
      try {
        this.out.append(this.view.invalidDimensions());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      parameters = this.view.askParameters();
      this.height = parameters.get(0);
      this.width = parameters.get(1);
    }
  }

  /**
   * Prompts the user to select fleet amounts for the game boards.
   */
  private void selectFleet() {
    try {
      this.out.append(this.view.fleetSelection(Math.min(this.height, this.width)));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    this.fleet = this.view.askFleet();
    int sum = 0;
    for (int amount : fleet) {
      sum += amount;
    }
    while (sum > Math.min(this.height, this.width) || this.fleet.contains(0)) {
      try {
        this.out.append(this.view.invalidFleet(Math.min(this.height, this.width)));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      this.fleet = this.view.askFleet();
      sum = 0;
      for (int amount : this.fleet) {
        sum += amount;
      }
    }
  }

  /**
   * Creates the player boards.
   */
  private void setBoards() {
    List<Ship> userShips = new ArrayList<>();
    HashMap<ShipType, Integer>  userSpecs = new HashMap<>();
    userSpecs.put(ShipType.CARRIER, this.fleet.get(0));
    userSpecs.put(ShipType.BATTLESHIP, this.fleet.get(1));
    userSpecs.put(ShipType.DESTROYER, this.fleet.get(2));
    userSpecs.put(ShipType.SUBMARINE, this.fleet.get(3));

    GameBoard userBoard = new GameBoard(this.height, this.width, userShips);
    this.userData = new UserData(userBoard, new ArrayList<>());
    this.userPlayer = new UserPlayer(this.userData, rand);
    this.userData.setShips(this.userPlayer.setup(this.height, this.width, userSpecs));

    List<Ship> compShips = new ArrayList<>();
    HashMap<ShipType, Integer>  compSpecs = new HashMap<>();
    compSpecs.put(ShipType.CARRIER, this.fleet.get(0));
    compSpecs.put(ShipType.BATTLESHIP, this.fleet.get(1));
    compSpecs.put(ShipType.DESTROYER, this.fleet.get(2));
    compSpecs.put(ShipType.SUBMARINE, this.fleet.get(3));

    GameBoard compBoard = new GameBoard(this.height, this.width, compShips);
    this.compData = new AiData(compBoard, new ArrayList<>(), rand);
    this.compPlayer = new AiPlayer(this.compData, rand);
    this.compData.setShips(this.compPlayer.setup(this.height, this.width, compSpecs));
  }

  /**
   * Player and computer exchange shots until one loses
   * or the game results in a draw.
   */
  private void exchangeShots() {
    while (userData.numShots(compData.getBoard()) > 0
        && compData.numShots(userData.getBoard()) > 0) {
      try {
        this.out.append(view.showBoards(compData.getBoard(), userData.getBoard()));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      try {
        out.append(view.enterShots(userData.numShots(compData.getBoard())));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      ArrayList<Coord> coords = view.askShots(userData.numShots(compData.getBoard()));
      while (this.overParameters(coords, width, height)) {
        try {
          out.append(view.invalidShots(userData.numShots(compData.getBoard())));
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
        coords = view.askShots(userData.numShots(compData.getBoard()));
      }
      List<Coord> compShots = compPlayer.takeShots();
      List<Coord> compHits = userPlayer.reportDamage(compShots);
      userData.getBoard().updateBoard(new ArrayList<>(compHits));
      userData.getBoard().updateBoard(new ArrayList<>(compShots));
      List<Coord> userHits = compPlayer.reportDamage(coords);
      compData.getBoard().updateBoard(userHits);
      compData.getBoard().updateBoard(coords);
    }
    try {
      this.out.append(view.showBoards(compData.getBoard(), userData.getBoard()));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Displays the final result of the game.
   */
  private void displayResult() {
    if (userData.numShots(compData.getBoard()) == 0) {
      try {
        out.append(GameResult.LOSE.toString());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    } else if (compData.numShots(userData.getBoard()) == 0) {
      try {
        out.append(GameResult.WIN.toString());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    } else if (userData.numShots(compData.getBoard()) == 0
        && compData.numShots(userData.getBoard()) == 0) {
      try {
        out.append(GameResult.DRAW.toString());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  /**
   * Checks whether any of the given coordinates are out
   * of bounds on the game board.
   */
  private boolean overParameters(ArrayList<Coord> l, int maxX, int maxY) {
    return l.stream().anyMatch(x -> x.getX() > maxX)
          || l.stream().anyMatch(y -> y.getY() > maxY)
          || l.stream().anyMatch(x -> x.getX() < 0)
          || l.stream().anyMatch(y -> y.getY() < 0);
  }
}