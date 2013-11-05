package at.wambo.tictactoe.ai

import at.wambo.tictactoe.game.{Util, PlayerOne, Player, TTTGame}
import scala.collection.mutable.ListBuffer

/**
 * Created with IntelliJ IDEA.
 * User: Martin
 * Date: 23.06.13
 * Time: 22:32
 */
class AIPlayerMinimax(val game: TTTGame) extends AIPlayer {
  private val field = game.field
  val player: Player = PlayerOne
  var UseNewEvaluationStrategy = false
  private val human = game.Player1
  private val computer = game.Player2
  private val Empty = ' '

  def move(): Option[(Int, Int)] = {
    Util.timedCall("move") {
      val result = minimax(2, human)
      if (result._2 != -1 && result._3 != -1) Some(result._2, result._3)
      else None
    }
  }

  private def minimax(depth: Int, p: Player): (Int, Int, Int) = {
    val nextMoves = generateMoves.toList
    var bestScore = if (p == computer) Int.MinValue else Int.MaxValue
    var bestX = -1
    var bestY = -1

    if (nextMoves.isEmpty || depth == 0)
      bestScore = evaluate
    else {
      for (move <- nextMoves) {
        field(move._1)(move._2) = p.symbol
        if (p == computer) {
          val currentScore = minimax(depth - 1, human)._1
          if (currentScore > bestScore) {
            bestScore = currentScore
            bestX = move._1
            bestY = move._2
          }
        }
        else {
          val currentScore = minimax(depth - 1, computer)._1
          if (currentScore < bestScore) {
            bestScore = currentScore
            bestX = move._1
            bestY = move._2
          }
        }
        field(move._1)(move._2) = Empty
      }
    }
    (bestScore, bestX, bestY)
  }

  private def evaluateLine(line: String): Int = {
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

  private def evaluateLineNxN(line: String): Int = {
    val length = line.length
    // Count occurrences of player and computer symbol in the line
    val p = line.count(_ == computer.symbol)
    val q = line.count(_ == human.symbol)
    if (p > 0 && q > 0 || p == 0 && q == 0)
      0
    else {
      val r = if (p > q) p else -q
      val ratio = r / length.toDouble
      math.pow(10, ratio * 5).toInt
    }
  }

  private def evaluate: Int = {
    val rows = (for (l <- field) yield l.mkString).toVector
    val cols = (for (l <- field.transpose) yield l.mkString).toVector

    val diag1 = Util.diagonal(field)
    val diag2 = Util.diagonal(field.map(c => c.reverse))

    val all: Vector[String] = rows ++ cols :+ diag1 :+ diag2

    if (UseNewEvaluationStrategy) {
      (for (v <- all) yield evaluateLineNxN(v)).sum
    }
    else {
      (for (v <- all) yield evaluateLine(v)).sum
    }
  }

  private def generateMoves: ListBuffer[(Int, Int)] = {
    val r = ListBuffer[(Int, Int)]()
    if (game.hasWon(human) || game.hasWon(computer))
      return r

    for (i <- 0 until game.size) {
      for (j <- 0 until game.size) {
        if (field(i)(j) == ' ') {
          r.append((i, j))
        }
      }
    }
    r
  }
}
