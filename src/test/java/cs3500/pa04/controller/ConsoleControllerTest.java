package cs3500.pa04.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.client.controller.ConsoleController;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

/**
 * Test the ConsoleController class.
 */
class ConsoleControllerTest {

  /**
   * Test a console user win
   */
  @Test
  public void testGameWin() {
    int seed = 1;
    StringBuilder out = new StringBuilder();
    String inString = "60 -1\n"
        + "7 6\n"
        + "24 0 0 2\n"
        + "1 1 1 1\n"
        + "0 0\n0 1\n0 2\n0 3\n"
        + "0 4\n0 5\n1 0\n1 1\n"
        + "1 2\n1 3\n1 4\n1 5\n"
        + "2 0\n2 1\n2 2\n2 3\n"
        + "2 4\n2 5\n3 0\n3 1\n"
        + "3 2\n3 3\n3 4\n3 5\n"
        + "4 0\n4 1\n4 2\n4 3\n"
        + "4 4\n4 5\n5 0\n5 1\n"
        + "5 2\n5 3\n5 4\n5 5\n"
        + "0 6\n1 6\n2 6\n3 6\n"
        + "4 6\n 5 6\n";
    Reader in = new StringReader(inString);

    ConsoleController controller = new ConsoleController(in, out, seed);
    controller.play();
    Path filename = Path.of("src/test/resources/gameWin.txt");
    try {
      String gameString = Files.readString(filename);
      assertEquals(gameString.trim().replaceAll("[\n\r]+", ""),
          out.toString().trim().replaceAll("[\n\r]+", ""));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Test a console user lose.
   */
  @Test
  public void testGameLose() {
    int seed = 2;
    StringBuilder out = new StringBuilder();
    String inString = "6 7\n"
        + "1 1 1 1\n"
        + "2 25\n2 -1\n2 -5\n5 5\n"
        + "5 5\n5 5\n5 5\n5 5\n"
        + "-1 25\n0 -1\n25 -5\n5 5\n"
        + "5 5\n5 5\n5 5\n5 5\n"
        + "0 0\n0 0\n-2 0\n5 5\n"
        + "5 5\n5 5\n5 5\n5 5\n"
        + "0 0\n0 0\n0 0\n5 -5\n"
        + "5 5\n5 5\n5 5\n5 5\n"
        + "5 5\n5 5\n5 5\n5 5\n"
        + "5 5\n5 5\n5 5\n5 5\n"
        + "5 5\n5 5\n5 5\n5 5\n"
        + "5 5\n5 5\n5 5\n5 5\n"
        + "5 5\n5 5\n5 5\n5 5\n"
        + "5 5\n5 5\n5 5\n5 5\n"
        + "5 5\n5 5\n5 5\n5 5\n"
        + "5 5\n5 5\n5 5\n5 5\n"
        + "5 5\n5 5\n5 5\n5 5\n"
        + "5 5\n5 5\n5 5\n5 5\n"
        + "5 5\n5 5\n5 5\n5 5\n"
        + "5 5\n5 5\n5 5\n5 5\n"
        + "5 5\n5 5\n5 5\n5 5\n"
        + "5 5\n5 5\n5 5\n5 5\n"
        + "5 5\n5 5\n5 5\n5 5\n"
        + "5 5\n5 5\n5 5\n5 5\n"
        + "5 5\n5 5\n5 5\n5 5\n"
        + "5 5\n5 5\n5 5\n5 5\n"
        + "5 5\n5 5\n5 5\n5 5\n"
        + "5 5\n5 5\n5 5\n5 5\n"
        + "5 5\n5 5\n5 5\n5 5\n"
        + "5 5\n5 5\n5 5\n5 5\n"
        + "5 5\n5 5\n5 5\n5 5\n"
        + "5 5\n5 5\n5 5\n5 5\n"
        + "5 5\n5 5\n5 5\n5 5\n";
    Reader in = new StringReader(inString);

    ConsoleController controller = new ConsoleController(in, out, seed);
    controller.play();
    Path filename = Path.of("src/test/resources/gameLose.txt");
    try {
      String gameString = Files.readString(filename);
      assertEquals(gameString.trim().replaceAll("[\n\r]+", ""),
          out.toString().trim().replaceAll("[\n\r]+", ""));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}