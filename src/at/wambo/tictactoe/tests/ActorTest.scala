package at.wambo.tictactoe.tests

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import at.wambo.tictactoe.game.TTTGame
import scala.collection.mutable.ListBuffer
import at.wambo.tictactoe.game.ai.AIPlayerRandom

/**
 * Created with IntelliJ IDEA.
 * User: Martin
 * Date: 23.06.13
 * Time: 20:28
 */
@RunWith(classOf[JUnitRunner])
class ActorTest extends FunSuite {
  val game = new TTTGame('X', 'O', 3)
  val actor = new AIPlayerRandom('O', game)

  test("actor.move") {
    val counts = ListBuffer[Int]()
    for (i <- 0 until 100) {
      var success = actor.move
      var count = 0
      while (!success) {
        success = actor.move
        count += 1
      }
      counts.append(count)
      game.reset()
    }
    println(counts.sum / counts.size.asInstanceOf[Double])
  }

}
