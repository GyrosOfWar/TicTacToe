package at.wambo.tictactoe.tests

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfterEach
import at.wambo.tictactoe.game.TTTGame
import at.wambo.tictactoe.ai._

/**
 * Created with IntelliJ IDEA.
 * User: Martin
 * Date: 23.06.13
 * Time: 20:28
 */
@RunWith(classOf[JUnitRunner])
class ActorTest extends FunSuite with BeforeAndAfterEach {
  val game = new TTTGame(3)
  val random = new AIPlayerRandom('O', game)
  val minimax = new AIPlayerMinimax('O', game)

  test("random.move") {
    //    val counts = ListBuffer[Int]()
    //    for (i <- 0 until 100) {
    //      var success = game.hasWon('O')
    //      var count = 0
    //      while (!success) {
    //        val actorMove = random.move()
    //        assert(actorMove._1 != -1)
    //        game.move(actorMove._1, actorMove._2, 'O')
    //        success = game.hasWon('O')
    //        count += 1
    //      }
    //      counts.append(count)
    //
    //    }
    //println(counts.sum / counts.size.asInstanceOf[Double])
  }

  test("minimax.generateMoves") {
    game.move(0, 1, 'X')
    game.move(0, 2, 'X')
    game.move(1, 1, 'X')
    val result = minimax.generateMoves
  }

  test("minimax.evaluateLine") {
    game.move(0, 2, ' ')
    game.move(0, 1, 'X')
    game.move(0, 0, 'X')
    println(minimax.evaluateLine(game.field(0).mkString))
  }

  test("minimax.minimax()") {
    println("mine:")
    game.move(0, 1, 'X')
    println(minimax.minimax(2, 'X'))
  }

  override protected def beforeEach() {
    game.reset()
  }
}
