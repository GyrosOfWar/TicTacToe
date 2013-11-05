package at.wambo.tictactoe.game

import at.wambo.tictactoe.ai.AIPlayerMinimax

/**
 * Created with IntelliJ IDEA.
 * User: Martin
 * Date: 23.06.13
 * Time: 16:53
 */

object Main {
  val size = 3
  val p1 = PlayerOne
  val p2 = PlayerTwo
  val game = new TTTGame(size)
  val actor = new AIPlayerMinimax(game)

  def movePlayer(player: Player): (Int, Int) = {
    println("Player " + player + " move: ")
    println("Enter your move: x[1-" + size + "] y[1-" + size + "], e.g. 3 3")
    val s = readLine()
    val x = s.substring(0, 1).toInt
    val y = s.substring(2, 3).toInt
    (x - 1, y - 1)
  }

  def gameLoop() {
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
      var actorMove = (0, 0)
      actor.move() match {
        case None => println("Tie!"); return
        case Some(move) => actorMove = move
      }
      game.move(actorMove._1, actorMove._2, p2)
      if (game.hasWon(p2)) {
        println(game)
        println("CPU won!")
        return
      }
      println(game + "\n")
    }
  }

  def main(args: Array[String]) {
    gameLoop()

    Util.printStats("hasWon")
    while (true) {
      println("Play another game? (y/n)")
      if (readLine()(0) == 'y') gameLoop()
      else return
    }
  }
}
