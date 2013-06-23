package at.wambo.tictactoe.game

import util.Random

/**
 * Created with IntelliJ IDEA.
 * User: Martin
 * Date: 23.06.13
 * Time: 20:21
 */
class TTTActor(val symbol: Char) {
  private val numAttempts = 30

  def move(t: TTTGame): Boolean = {
    if (t.hasWon(symbol))
      return true
    var x = Random.nextInt(t.size)
    var y = Random.nextInt(t.size)
    var count = 0
    while (count < numAttempts) {
      count += 1
      x = Random.nextInt(t.size)
      y = Random.nextInt(t.size)
      if (t.canMove(x, y, symbol)) {
        t.move(x, y, symbol)
      }
      return false
    }
    false
  }

  def moveNew(t: TTTGame) {
    //    def moveRec(coords: Tuple2[Int, Int]) {
    //      coords match {
    //        case ._1 == 1 => { }
    //      }
    //    }
  }
}
