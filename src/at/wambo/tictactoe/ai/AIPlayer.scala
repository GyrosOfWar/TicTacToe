package at.wambo.tictactoe.ai

import at.wambo.tictactoe.game.TTTGame

/**
 * Created with IntelliJ IDEA.
 * User: Martin
 * Date: 23.06.13
 * Time: 22:11
 */
trait AIPlayer {
  /**
   * Reference to the Tic Tac Toe-Game
   */
  val game: TTTGame
  /**
   * The player's symbol, will be 'O' most of the time
   */
  val symbol: Char

  /**
   * Does one Tic-Tac-Toe move from the AI player.
   * @return The next position the AI player wants to move to.
   */
  def move(): (Int, Int)
}
