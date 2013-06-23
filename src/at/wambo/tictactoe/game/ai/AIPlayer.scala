package at.wambo.tictactoe.game.ai

import at.wambo.tictactoe.game.TTTGame

/**
 * Created with IntelliJ IDEA.
 * User: Martin
 * Date: 23.06.13
 * Time: 22:11
 */
trait AIPlayer {
  val game: TTTGame
  val symbol: Char

  def move: (Int, Int)
}
