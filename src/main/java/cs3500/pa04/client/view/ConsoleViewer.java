package cs3500.pa04.client.view;

import cs3500.pa04.client.model.Coord;
import cs3500.pa04.client.model.GameBoard;
import cs3500.pa04.client.model.GameResult;
import cs3500.pa04.client.model.ShipType;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Representation of the console view of BattleSalvo.
 */
public class ConsoleViewer implements BattleSalvoViewer {
  private final Scanner scan;

  /**
   * Instantiates a new ConsoleViewer.
   *
   * @param in  the in
   */
  public ConsoleViewer(Readable in) {
    this.scan = new Scanner(in);
  }

  /**
   * The welcome message and call to input game board parameters.
   *
   * @return the string of the message
   */
  @Override
  public String boardParameters() {
    return """
        Hello! Welcome to the OOD BattleSalvo Game!
        Please enter a valid height and width below:
        ------------------------------------------------------
        """;
  }

  /**
   * Generates a list of 2 values for the game board parameter
   * inputted by the user.
   *
   * @return the list of parameter values.
   */
  @Override
  public ArrayList<Integer> askParameters() {
    ArrayList<Integer> p = new ArrayList<>();
    for (int i = 0; i < 2; i++) {
      p.add(scan.nextInt());
    }
    return p;
  }

  /**
   * A message alerting the user that they entered invalid
   * dimensions, and they must re-enter them.
   *
   * @return the message.
   */
  @Override
  public String invalidDimensions() {
    return """
        ------------------------------------------------------
        Uh Oh! You've entered invalid dimensions. Please remember that the height and width
        of the game must be in the range (6, 10), inclusive. Try again!
        ------------------------------------------------------
        """;
  }

  /**
   * A message prompting the user to enter their desired fleet size.
   *
   * @param size the maximum size of their fleet.
   * @return the message.
   */
  @Override
  public String fleetSelection(int size) {
    return "------------------------------------------------------\n"
          + "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine]."
          + "\nRemember, your fleet may not exceed size " + size
          + ".\n------------------------------------------------------\n";
  }

  /**
   * Generates a list of fleet amounts to be placed on the player's
   * game board.
   *
   * @return the list of fleet amounts.
   */
  @Override
  public ArrayList<Integer> askFleet() {
    ArrayList<Integer> amounts = new ArrayList<>();
    for (int i = 0; i < ShipType.values().length; i++) {
      amounts.add(scan.nextInt());
    }
    return amounts;
  }

  /**
   * Alerts the user that their fleet amounts are invalid and
   * are prompted to re-enter.
   *
   * @param size the maximum size of the player's fleet.
   * @return the message.
   */
  @Override
  public String invalidFleet(int size) {
    return """
        --------------------------------------------------------------------------------
        Uh Oh! You've entered invalid fleet sizes.
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine]."""
        + "\nRemember, your fleet may not exceed size " + size
        + ".\n------------------------------------------------------\n";
  }

  /**
   * Displays the game boards in their current states.
   *
   * @param opponent    the opponent's board
   * @param player      the player's board
   * @return the game boards.
   */
  @Override
  public String showBoards(GameBoard opponent, GameBoard player) {
    return """
        ------------------------------------------------------
        Opponent Board Data:
        """
        + this.getOpponentBoard(opponent)
        + "\nYour Board:\n"
        + this.getPlayerBoard(player)
        + "\n";
  }

  /**
   * @param opponent board
   * @return string representation of the opponent's board.
   */
  private String getOpponentBoard(GameBoard opponent) {
    StringBuilder board = new StringBuilder();
    for (int j = 0; j < opponent.getGameBoard().length; j++) {
      for (int i = 0; i < opponent.getGameBoard()[0].length; i++) {
        if (opponent.getGameBoard()[j][i].getVisited()) {
          if (opponent.getGameBoard()[j][i].getContainsShip()) {
            if (i == opponent.getGameBoard()[0].length - 1) {
              board.append("X");
            } else {
              board.append("X ");
            }
          } else {
            if (i == opponent.getGameBoard()[0].length - 1) {
              board.append("+");
            } else {
              board.append("+ ");
            }
          }
        } else {
          if (i == opponent.getGameBoard()[0].length - 1) {
            board.append(".");
          } else {
            board.append(". ");
          }
        }
      }
      board.append("\n");
    }
    return board.toString();
  }

  /**
   * @param player board
   * @return string representation of the player's board.
   */
  private String getPlayerBoard(GameBoard player) {
    StringBuilder board = new StringBuilder();
    for (int j = 0; j < player.getGameBoard().length; j++) {
      for (int i = 0; i < player.getGameBoard()[0].length; i++) {
        if (player.getGameBoard()[j][i].getVisited()) {
          if (player.getGameBoard()[j][i].getContainsShip()) {
            if (i == player.getGameBoard()[0].length - 1) {
              board.append("X");
            } else {
              board.append("X ");
            }
          } else {
            if (i == player.getGameBoard()[0].length - 1) {
              board.append("+");
            } else {
              board.append("+ ");
            }
          }
        } else {
          if (player.getGameBoard()[j][i].getContainsShip()) {
            if (i == player.getGameBoard()[0].length - 1) {
              board.append("|");
            } else {
              board.append("| ");
            }
          } else {
            if (i == player.getGameBoard()[0].length - 1) {
              board.append(".");
            } else {
              board.append(". ");
            }
          }
        }
      }
      board.append("\n");
    }
    return board.toString();
  }

  /**
   * A message alerting the user to enter their shots.
   *
   * @param numShots the number of shots the user can make.
   * @return the message.
   */
  @Override
  public String enterShots(int numShots) {
    return "Please Enter " + numShots + " Shots:"
        + "\n--------------------------------------------------------------------------------\n";
  }

  /**
   * A message alerting the user the shots they previously
   * entered were invalid and are prompted to re-enter.
   *
   * @param numShots the number of shots the user can make.
   * @return the message.
   */
  @Override
  public String invalidShots(int numShots) {
    return "Uh oh! You entered invalid shots. Please Enter " + numShots + " Shots:"
        + "\nMake sure the coordinates are within the dimensions of the board."
        + "\n--------------------------------------------------------------------------------\n";
  }

  /**
   * Generates a list of the shots the user inputted into the console.
   *
   * @param numShots the number of shots the user can make.
   * @return the list of shots by the user.
   */
  @Override
  public ArrayList<Coord> askShots(int numShots) {
    ArrayList<Coord> coords = new ArrayList<>();
    for (int i = 0; i < numShots; i++) {
      int x = scan.nextInt();
      int y = scan.nextInt();
      Coord coord = new Coord(x, y);
      coords.add(coord);
    }
    return coords;
  }

  /**
   * A message alerting that the results of the game.
   *
   * @param result the result of the game.
   * @return the message.
   */
  @Override
  public String gameOver(GameResult result) {
    return result.toString();
  }
}