package at.wambo.tictactoe.ai

import util.Random
import at.wambo.tictactoe.game.TTTGame

/**
 * Created with IntelliJ IDEA.
 * User: Martin
 * Date: 23.06.13
 * Time: 20:21
 */
class AIPlayerRandom(val game: TTTGame) extends AIPlayer {
  private val numAttempts = 30
  val player = game.Player2

  def move(): Option[(Int, Int)] = {
    var x = Random.nextInt(game.size)
    var y = Random.nextInt(game.size)
    var count = 0
    while (count < numAttempts) {
      count += 1
      x = Random.nextInt(game.size)
      y = Random.nextInt(game.size)
      if (game.canMove(x, y, player)) {
        return Some(x, y)
      }
    }
    None
  }
}
