package at.wambo.tictactoe.ai

import at.wambo.tictactoe.game.{Util, PlayerTwo, PlayerOne}

/**
 * Created with IntelliJ IDEA.
 * User: Martin
 * Date: 25.06.13
 * Time: 11:25
 */
object CompareHeuristics {
  val human = PlayerOne
  val computer = PlayerTwo

  def newHeuristic(line: String): Int = {
    val length = line.length
    // Count occurrences of player and computer symbol in the line
    val p = line count (_ == computer.symbol)
    val q = line count (_ == human.symbol)
    if (p > 0 && q > 0 || p == 0 && q == 0)
      0
    else {
      val computerGreater = p > q
      val r = math.max(p, q)
      val ratio = r / length.toDouble
      val result = math.pow(4, ratio * 5).toInt
      if (computerGreater)
        result
      else
        -result
    }
  }

  def oldHeuristic(line: String): Int = {
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

  def evaluate(field: Array[Array[Char]], heuristic: String => Int): Int = {
    val rows = (for (s <- field) yield s.mkString).toVector
    val cols = (for (s <- field.transpose) yield s.mkString).toVector

    val diag1 = Util.diagonal(field)
    val diag2 = Util.diagonal(field.map(_.reverse))

    val all = rows ++ cols :+ diag1 :+ diag2
    (for(str <- all) yield heuristic(str)).sum
  }

  def main(args: Array[String]) {
    for (i <- 0 until 30) {
      val rdm = Util.fillArrayRandom(3, List('X', 'O', ' ', ' ', ' '))
      val o = evaluate(rdm, oldHeuristic)
      val n = evaluate(rdm, newHeuristic)
      println("old: " + o)
      println("new: " + n)
      println()
    }
  }
}
