package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.client.model.GameResult;
import org.junit.jupiter.api.Test;

/**
 * Test GameResult enum
 */
class GameResultTest {
  private final GameResult win = GameResult.WIN;
  private final GameResult lose = GameResult.LOSE;
  private final GameResult draw = GameResult.DRAW;

  /**
   * Test toString method.
   */
  @Test
  public void testToString() {
    assertEquals("Game Over: Congratulations! You win.", win.toString());
    assertEquals("Game Over: You lose :(", lose.toString());
    assertEquals("Game Over: This game is a draw.", draw.toString());
  }
}