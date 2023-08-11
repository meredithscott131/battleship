package cs3500.pa04;

import static cs3500.pa04.Driver.main;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Test the Driver
 */
class DriverTest {

  /**
   * Test the main method.
   */
  @Test
  public void testMain() {
    String[] args = {"hi", "hi"};
    assertThrows(IllegalArgumentException.class,
        () -> main(args));
  }

  /**
   * Test when connecting to the server fails.
   */
  @Test
  public void testServerFail() {
    String[] args = {"hi", "2"};
    assertThrows(RuntimeException.class,
        () -> main(args));
  }
}