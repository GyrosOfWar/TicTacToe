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
  val game: TTTGame = new TTTGame(p1, p2, size)

  test("game.canMove") {
    assert(!game.canMove(-1, 0, p1))
    assert(game.canMove(1, 0, p2))
  }

  test("game.hasWon") {
    game.move(0, 0, 'X')
    game.move(1, 1, 'O')
    game.move(1, 2, 'X')
    game.move(2, 0, 'O')
    assert(!game.hasWon('X'))
  }

  test("game.reset") {
    game.move(0, 1, 'O')
    game.reset()
    for (i <- 0 until game.field.length) {
      for (j <- 0 until game.field(i).length) {
        assert(game.field(i)(j) == ' ')
      }
    }
  }
}
