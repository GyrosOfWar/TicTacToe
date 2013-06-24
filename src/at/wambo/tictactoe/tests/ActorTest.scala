package at.wambo.tictactoe.tests

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfterEach
import at.wambo.tictactoe.game.{PlayerOne, PlayerTwo, TTTGame}
import at.wambo.tictactoe.ai._
import scala.collection.mutable.ListBuffer

/**
 * Created with IntelliJ IDEA.
 * User: Martin
 * Date: 23.06.13
 * Time: 20:28
 */
@RunWith(classOf[JUnitRunner])
class ActorTest extends FunSuite with BeforeAndAfterEach {
  val game = new TTTGame(3)
  val p1 = PlayerOne
  val p2 = PlayerTwo
  val random = new AIPlayerRandom(game)
  val minimax = new AIPlayerMinimax(game)

  test("random.move") {
    val counts = ListBuffer[Int]()
    for (i <- 0 until 100) {
      var success = game.hasWon(p2)
      var count = 0
      while (!success) {
        val actorMove = random.move()
        assert(actorMove._1 != -1)
        game.move(actorMove._1, actorMove._2, p2)
        success = game.hasWon(p2)
        count += 1
      }
      counts.append(count)

    }
    println(counts.sum / counts.size.asInstanceOf[Double])
  }

  test("minimax.move()") {
    game.move(0, 1, p1)
    println(minimax.move())
  }

  override protected def beforeEach() {
    game.reset()
  }
}
