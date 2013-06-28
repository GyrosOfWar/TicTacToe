package at.wambo.tictactoe.tests

import at.wambo.tictactoe.ai.AIPlayerMinimax
import at.wambo.tictactoe.game.{PlayerTwo, Player, PlayerOne, TTTGame}

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
    def doOneGame(): (Int, Int) = {
      while (!game.hasWon(PlayerOne) && !game.hasWon(PlayerTwo)) {
        val actor1Move = oldActor.move()
        if (actor1Move.isDefined) {
          val (x, y) = actor1Move.get
          game.move(x, y, PlayerOne)
        } else {
          return (1, 0)
        }

        if (game.hasWon(PlayerOne)) {
          return (1, 0)
        }

        val actor2Move = newActor.move()
        if (actor2Move.isDefined) {
          val (x, y) = actor2Move.get
          game.move(x, y, PlayerTwo)
        } else {
          return (1, 0)
        }

        if (game.hasWon(PlayerTwo)) {
          return (0, 1)
        }
      }
      (-100, -100)
    }

    doOneGame()
  }

  def main(args: Array[String]) {
    var (oldWon, newWon) = playAIvsAI()
    for (k <- 3 to 7) {
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
  }
}
