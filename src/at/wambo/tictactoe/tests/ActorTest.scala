package at.wambo.tictactoe.tests

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import at.wambo.tictactoe.game.TTTGame
import scala.collection.mutable.ListBuffer
import at.wambo.tictactoe.game.ai.{AIPlayerMinimax, AIPlayerRandom}

/**
 * Created with IntelliJ IDEA.
 * User: Martin
 * Date: 23.06.13
 * Time: 20:28
 */
@RunWith(classOf[JUnitRunner])
class ActorTest extends FunSuite {
  val game = new TTTGame('X', 'O', 3)
  val random = new AIPlayerRandom('O', game)
  val minimax = new AIPlayerMinimax('O', game)

  test("random.move") {
    val counts = ListBuffer[Int]()
    for (i <- 0 until 100) {
      var success = game.hasWon('O')
      var count = 0
      while (!success) {
        val actorMove = random.move()
        assert(actorMove._1 != -1)
        game.move(actorMove._1, actorMove._2, 'O')
        success = game.hasWon('O')
        count += 1
      }
      counts.append(count)
      game.reset()
    }
    //println(counts.sum / counts.size.asInstanceOf[Double])
  }

  test("minimax.move") {
    for (i <- 0 until 10)
      println(minimax.move())
  }

}
