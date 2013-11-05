package at.wambo.tictactoe.ai

import at.wambo.tictactoe.game._
import scala.Some

/**
 * Created with IntelliJ IDEA.
 * User: Martin
 * Date: 28.06.13
 * Time: 02:36
 */
object CompareActors {
  var size = 7
  var game = new TTTGame(size)
  val oldActor = new AIPlayerMinimax(game)
  val newActor = new AIPlayerMinimax(game)
  newActor.UseNewEvaluationStrategy = true

  def doRandomMoves(count: Int) {
    def doRandomMove(p: Player) {
      val x = util.Random.nextInt(size)
      val y = util.Random.nextInt(size)
      game.move(x, y, p)
    }
    var p: Player = null
    for (i <- 0 until count) {
      p = if (p == PlayerOne) PlayerTwo else PlayerOne
      doRandomMove(p)
    }
  }

  def playAIvsAI(): (Int, Int) = {
    val PlayerOneWon = (1, 0)
    val PlayerTwoWon = (0, 1)
    def doOneGame(): (Int, Int) = {
      while (!game.hasWon(PlayerOne) && !game.hasWon(PlayerTwo)) {
        val actor1Move = oldActor.move()

        actor1Move match {
          case Some(move) => game.move(move._1, move._2, PlayerOne)
          case None => return PlayerOneWon
        }

        if (game.hasWon(PlayerOne)) {
          return PlayerOneWon
        }

        newActor.move() match {
          case Some(move) => game.move(move._1, move._2, PlayerTwo)
          case None => return PlayerTwoWon
        }

        if (game.hasWon(PlayerTwo)) {
          return PlayerTwoWon
        }
      }
      (0, 0)
    }
    doOneGame()
  }

  def main(args: Array[String]) {
    var (oldWon, newWon) = playAIvsAI()
    for (k <- 3 to 4) {
      for (i <- 0 until 2) {
        doRandomMoves(k / 2)
        val result = playAIvsAI()
        oldWon += result._1
        newWon += result._2
        println(game)
      }
      size = k
      game = new TTTGame(size)
    }
    println("old AI won: " + oldWon)
    println("new AI won: " + newWon)
    Util.printStats("move")
  }
}
