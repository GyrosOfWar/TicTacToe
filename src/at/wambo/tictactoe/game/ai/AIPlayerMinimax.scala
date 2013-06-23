package at.wambo.tictactoe.game.ai

import at.wambo.tictactoe.game.TTTGame

/**
 * Created with IntelliJ IDEA.
 * User: Martin
 * Date: 23.06.13
 * Time: 22:32
 */
class AIPlayerMinimax(val symbol: Char, val game: TTTGame) extends AIPlayer {
  def move: (Int, Int) = minimax(2, 'O')

  private def minimax(depth: Int, player: Char): (Int, Int) = {
    val nextMoves = generateMoves
    var bestScore = Int.MinValue
    var bestX = -1
    var bestY = -1

    if (nextMoves.isEmpty || depth == 0)
      bestScore = evaluate
    (bestX, bestY)
  }

  private def evaluateLine(line: String): Int = ???

  private def evaluate: Int = ???

  private def generateMoves: Vector[(Int, Int)] = ???
}
