package at.wambo.tictactoe.tests

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfterEach
import at.wambo.tictactoe.game.{PlayerOne, PlayerTwo, TTTGame}
import at.wambo.tictactoe.ai._

/**
 * Created with IntelliJ IDEA.
 * User: Martin
 * Date: 23.06.13
 * Time: 20:28
 */
@RunWith(classOf[JUnitRunner])
class ActorTest extends FunSuite with BeforeAndAfterEach {
  val game = new TTTGame(7)
  val p1 = PlayerOne
  val p2 = PlayerTwo
  val random = new AIPlayerRandom(game)
  val minimax = new AIPlayerMinimax(game)

  test("minimax.move()") {
    minimax.UseNewEvaluationStrategy = true
    for (i <- 0 until 7) {
      val x = minimax.move()
      println(x)
      //game.move(x._1, x._2, p2)
      println(game)
    }
  }

  override protected def beforeEach() {
    game.reset()
  }
}
