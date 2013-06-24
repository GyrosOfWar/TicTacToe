package at.wambo.tictactoe.tests

import at.wambo.tictactoe.game._
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith

/**
 * Created with IntelliJ IDEA.
 * User: Martin
 * Date: 23.06.13
 * Time: 17:56
 */
@RunWith(classOf[JUnitRunner])
class GameTest extends FunSuite {
  val p1 = 'X'
  val p2 = 'O'
  val size = 12
  val game: TTTGame = new TTTGame(size)

  test("game.canMove") {
    for (i <- 0 until size) {
      for (j <- 0 until size) {
        assert(game.canMove(i, j, p1))
        assert(game.canMove(i, j, p2))
      }
    }
    game.move(0, 2, p1)
    assert(!game.canMove(0, 2, p1))
    assert(!game.canMove(0, 2, p2))

  }

  test("game.hasWon") {
    for (i <- 0 until size) {
      game.move(0, i, p2)
    }
    assert(game.hasWon(p2))

    game.reset()

    for (j <- 0 until size) {
      game.move(j, 0, p2)
    }
    assert(game.hasWon(p2))

    game.reset()

    for (k <- 0 until size) {
      game.move(k, k, p2)
    }
    assert(game.hasWon(p2))

  }

  test("game.reset") {
    game.move(0, 1, p2)
    game.reset()
    for (i <- 0 until game.field.length) {
      for (j <- 0 until game.field(i).length) {
        assert(game.field(i)(j) == ' ')
      }
    }
  }

  test("Util.makeString") {
    val smallGame = new TTTGame(3)
    val result = "XXX      "
    smallGame.move(0, 0, p1)
    smallGame.move(0, 1, p1)
    smallGame.move(0, 2, p1)
    assert(Util.makeString(smallGame.field, "", "") == result)
  }
}
