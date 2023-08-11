package cs3500.pa04.client.model;

/**
 * Representation of final game results in BattleSalvo.
 */
public enum GameResult {
  WIN("Game Over: Congratulations! You win."),
  LOSE("Game Over: You lose :("),
  DRAW("Game Over: This game is a draw.");

  private final String message;

  GameResult(String message) {
    this.message = message;
  }

  /**
   *
   * @return string representation of a game result
   */
  @Override
  public String toString() {
    return this.message;
  }
}
