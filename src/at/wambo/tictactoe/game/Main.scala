package at.wambo.tictactoe.game

import at.wambo.tictactoe.ai.AIPlayerMinimax

/**
 * Created with IntelliJ IDEA.
 * User: Martin
 * Date: 23.06.13
 * Time: 16:53
 */

object Main {
  val p1 = PlayerOne
  val p2 = PlayerTwo
  val game = new TTTGame(7)
  val actor1 = new AIPlayerMinimax(game)
  val actor2 = new AIPlayerMinimax(game)
  actor2.UseNewEvaluationStrategy = true

  def movePlayer(player: Player): (Int, Int) = {
    println("Player " + player + " move: ")
    println("Enter your move: x[1-3] y[1-3], e.g. 3 3")
    val s = readLine()
    val x = s.substring(0, 1).toInt
    val y = s.substring(2, 3).toInt
    (x - 1, y - 1)
  }

  def doOneGame(): (Int, Int) = {
    while (!game.hasWon(p1) && !game.hasWon(p2)) {
      //println(game + "\n")
      val actor1Move = actor1.move()
      if (actor1Move.isDefined) {
        val x = actor1Move.get._1
        val y = actor1Move.get._2
        game.move(x, y, p1)
      } else {
        return (1, 0)
      }

      if (game.hasWon(p1)) {
        return (1, 0)
      }

      val actor2Move = actor2.move()
      if (actor2Move.isDefined) {
        val x = actor2Move.get._1
        val y = actor2Move.get._2
        game.move(x, y, p2)
      } else {
        return (1, 0)
      }

      if (game.hasWon(p2)) {
        return (0, 1)
      }
    }
    (-100, -100)
  }

  def main(args: Array[String]) {
    var actor1Won = 0
    var actor2Won = 0
    var result = (0, 0)
    for (i <- 0 until 20) {
      game.reset()
      result = doOneGame()
      actor1Won += result._1
      actor2Won += result._2
    }

    println("a1: " + actor1Won)
    println("a2: " + actor2Won)
  }
}
