package at.wambo.tictactoe.game

/**
 * Created with IntelliJ IDEA.
 * User: Martin
 * Date: 22.06.13
 * Time: 23:23
 */
trait Player {
  val symbol: Char
}

case object PlayerOne extends Player {
  val symbol = 'X'
}

case object PlayerTwo extends Player {
  val symbol = 'O'
}

class TTTGame(val size: Int) {
  val Player1 = PlayerOne
  val Player2 = PlayerTwo
  val field = Array.fill(size, size)(' ')

  def hasWon(player: Player): Boolean = {
    val p = player.symbol.toString * size

    val rowWon = Util.makeString(field, "", " ").contains(p)
    val columnWon = Util.makeString(field.transpose, "", " ").contains(p)
    val diagonal1Won = Util.diagonal(field) == p
    val diagonal2Won = Util.diagonal(field.map(_.reverse)) == p

    rowWon || columnWon || diagonal1Won || diagonal2Won
  }

  def reset() {
    for (i <- 0 until size) {
      for (j <- 0 until size) {
        field(i)(j) = ' '
      }
    }
  }

  def isTie: Boolean = ???

  def move(x: Int, y: Int, player: Player) {
    field(x)(y) = player.symbol
  }

  def canMove(x: Int, y: Int, player: Player): Boolean = {
    try {
      player match {
        case Player1 | Player2
          if field(x)(y) != player.symbol &&
            field(x)(y) != Player1.symbol &&
            field(x)(y) != Player2.symbol => true
        case _ => false
      }
    } catch {
      case ex: ArrayIndexOutOfBoundsException => false
    }
  }

  override def toString = Util.makeString(field, " | ", "\n")
}