package at.wambo.tictactoe.game

/**
 * Created with IntelliJ IDEA.
 * User: Martin
 * Date: 22.06.13
 * Time: 23:23
 */

class TTTGame(val size: Int) {
  val Player1 = 'X'
  val Player2 = 'O'
  val field = Array.fill(size, size)(' ')

  def hasWon(player: Char): Boolean = {
    val p = player.toString * size

    val rowWon = Util.makeString(field, "", " ").contains(p)
    val columnWon = Util.makeString(field.transpose, "", " ").contains(p)
    val diagonal1Won = diagonal(field) == p
    val diagonal2Won = diagonal(field.map(_.reverse)) == p

    rowWon || columnWon || diagonal1Won || diagonal2Won
  }

  def reset() {
    for (i <- 0 until size) {
      for (j <- 0 until size) {
        field(i)(j) = ' '
      }
    }
  }

  def move(x: Int, y: Int, symbol: Char) {
    field(x)(y) = symbol
  }

  def canMove(x: Int, y: Int, symbol: Char): Boolean = {
    try {
      symbol match {
        case Player1 | Player2
          if field(x)(y) != symbol &&
            field(x)(y) != Player1 &&
            field(x)(y) != Player2 => true
        case _ => false
      }
    } catch {
      case ex: ArrayIndexOutOfBoundsException => false
    }
  }

  private def diagonal[T](f: Array[Array[T]]): String = {
    var str = ""
    for (i <- 0 until size) {
      str += f(i)(i).toString
    }
    str
  }

  override def toString = Util.makeString(field, " | ", "\n")
}