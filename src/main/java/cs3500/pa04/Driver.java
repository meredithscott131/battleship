package cs3500.pa04;

import cs3500.pa04.client.controller.ConsoleController;
import cs3500.pa04.client.controller.ProxyController;
import cs3500.pa04.client.model.AiData;
import cs3500.pa04.client.model.AiPlayer;
import cs3500.pa04.client.model.GameBoard;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

/**
 * This is the main driver of BattleSalvo.
 * Enter no command line arguments to play the game in the console.
 * Enter two command line arguments (host, port) to play the game on a server.
 */
public class Driver {

  /**
   * Project entry point
   *
   * @param args - no command line args required
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      ConsoleController c = new ConsoleController(new InputStreamReader(System.in), System.out);
      c.play();
    } else if (args.length == 2) {
      Driver.runClient(args[0], Integer.parseInt(args[1]));
    }
  }

  /**
   * Plays the BattleSalvo game on the given server
   */
  private static void runClient(String host, int port) {
    Socket server;
    try {
      server = new Socket(host, port);
    } catch (IOException e) {
      throw new RuntimeException("Cannot connect to server");
    }
    AiPlayer player = new AiPlayer(new AiData(new GameBoard(6, 6, new ArrayList<>()),
        new ArrayList<>()));
    ProxyController proxyController = new ProxyController(server, player);
    proxyController.play();
  }
}