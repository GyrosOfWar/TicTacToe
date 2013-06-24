package at.wambo.tictactoe.game

import at.wambo.tictactoe.ai.AIPlayerMinimax

/**
 * Created with IntelliJ IDEA.
 * User: Martin
 * Date: 23.06.13
 * Time: 16:53
 */

object Main {
  val p1 = 'X'
  val p2 = 'O'
  val game = new TTTGame(3)
  val actor = new AIPlayerMinimax(p2, game)

  def movePlayer(player: Char): (Int, Int) = {
    println("Player " + player + " move: ")
    println("Enter your move: x[1-3] y[1-3], e.g. 3 3")
    val s = readLine()
    val x = s.substring(0, 1).toInt
    val y = s.substring(2, 3).toInt
    (x - 1, y - 1)
  }

  def main(args: Array[String]) {
    while (!game.hasWon(p1) && !game.hasWon(p2)) {
      var tuple = movePlayer(p1)
      var x = tuple._1
      var y = tuple._2
      var success = game.canMove(x, y, p1)
      while (!success) {
        tuple = movePlayer(p1)
        x = tuple._1
        y = tuple._2
        success = game.canMove(x, y, p1)
      }
      game.move(x, y, p1)
      if (game.hasWon(p1)) {
        println(game)
        println("You won!")
        return
      }
      val actorMove = actor.move()
      game.move(actorMove._1, actorMove._2, p2)
      if (game.hasWon(p2)) {
        println(game)
        println("CPU won!")
        return
      }
      println(game + "\n")
    }
  }
}
