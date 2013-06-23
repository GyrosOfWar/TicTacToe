package at.wambo.tictactoe.tests

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import at.wambo.tictactoe.game.{TTTActor, TTTGame}
import scala.collection.mutable.ListBuffer

/**
 * Created with IntelliJ IDEA.
 * User: Martin
 * Date: 23.06.13
 * Time: 20:28
 */
@RunWith(classOf[JUnitRunner])
class ActorTest extends FunSuite {
  val game = new TTTGame('X', 'O', 3)
  val actor = new TTTActor('O')

  test("actor.move") {
    val counts = ListBuffer[Int]()
    for (i <- 0 until 100) {
      var success = actor.move(game)
      var count = 0
      while (!success) {
        success = actor.move(game)
        count += 1
      }
      counts.append(count)
      game.reset()
    }
    println(counts.sum / counts.size.asInstanceOf[Double])
  }

}
