import at.wambo.tictactoe.game.{Util, PlayerTwo, PlayerOne}

object Scratchpad {
  val human = PlayerOne
  val computer = PlayerTwo
  val field = Array(Array('X', 'O', ' '), Array('X', 'O', ' '), Array(' ', ' ', 'X'))

  var newMethod = false

  def evaluateLineNxN(line: String): Int = {
    val length = line.length
    // Count occurrences of player and computer symbol in the line
    val p = line count (_ == computer.symbol)
    val q = line count (_ == human.symbol)
    if (p > 0 && q > 0 || p == 0 && q == 0)
      0
    else {
      val computerGreater = p > q
      val r = math.max(p, q)
      val ratio = r / length.asInstanceOf[Double]
      val result = math.pow(10, ratio * 5).asInstanceOf[Int]
      if (computerGreater)
        result
      else
        -result
    }
  }

  def evaluateLine(line: String): Int = {
    var score = 0

    if (line(0) == computer.symbol) {
      score = 1
    }
    else if (line(0) == human.symbol) {
      score = -1
    }

    // Second cell
    if (line(1) == computer.symbol) {
      if (score == 1) {
        // cell1 is mySeed
        score = 10
      } else if (score == -1) {
        // cell1 is oppSeed
        return 0
      } else {
        // cell1 is empty
        score = 1
      }
    } else if (line(1) == human.symbol) {
      if (score == -1) {
        // cell1 is oppSeed
        score = -10
      } else if (score == 1) {
        // cell1 is mySeed
        return 0
      } else {
        // cell1 is empty
        score = -1
      }
    }

    // Third cell
    if (line(2) == computer.symbol) {
      if (score > 0) {
        // cell1 and/or cell2 is mySeed
        score *= 10
      } else if (score < 0) {
        // cell1 and/or cell2 is oppSeed
        return 0
      } else {
        // cell1 and cell2 are empty
        score = 1
      }
    } else if (line(2) == human.symbol) {
      if (score < 0) {
        // cell1 and/or cell2 is oppSeed
        score *= 10
      } else if (score > 1) {
        // cell1 and/or cell2 is mySeed
        return 0
      } else {
        // cell1 and cell2 are empty
        score = -1
      }
    }
    score
  }

  def evaluate: Int = {
    var score = 0
    val row1 = field(0).mkString
    val row2 = field(1).mkString
    val row3 = field(2).mkString

    val fieldT = field.transpose

    val col1 = fieldT(0).mkString
    val col2 = fieldT(1).mkString
    val col3 = fieldT(2).mkString

    val diag1 = Util.diagonal(field)
    val diag2 = Util.diagonal(field.map(c => c.reverse))

    val all = List(row1, row2, row3, col1, col2, col3, diag1, diag2)
    for (v <- all) {
      if (newMethod)
        score += evaluateLineNxN(v)
      else
        score += evaluateLine(v)
    }
    score
  }

  evaluate
  newMethod = true
  evaluate
}