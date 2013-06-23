package at.wambo.tictactoe.game.ai

import at.wambo.tictactoe.game.TTTGame
import scala.collection.mutable.ListBuffer

/**
 * Created with IntelliJ IDEA.
 * User: Martin
 * Date: 23.06.13
 * Time: 22:32
 */
class AIPlayerMinimax(val symbol: Char, val game: TTTGame) extends AIPlayer {
  def move: (Int, Int) = {
    val result = minimax(2, player)
    (result._2, result._3)
  }

  private val field = game.field
  private val player = game.Player1
  private val computer = game.Player2
  private val Empty = ' '

  private def minimax(depth: Int, p: Char): (Int, Int, Int) = {
    val nextMoves = generateMoves.toList
    var bestScore = if (player == computer) Int.MinValue else Int.MaxValue
    var bestX = -1
    var bestY = -1
    var currentScore = 0

    if (nextMoves.isEmpty || depth == 0)
      bestScore = evaluate
    else {
      for (move <- nextMoves) {
        field(move._1)(move._2) = p
        if (p == computer) {
          currentScore = minimax(depth - 1, player)._1
          if (currentScore > bestScore) {
            bestScore = currentScore
            bestX = move._1
            bestY = move._2
          }
        }
        else {
          currentScore = minimax(depth - 1, computer)._1
          if (currentScore < bestScore) {
            bestScore = currentScore
            bestX = move._1
            bestY = move._2
          }
        }
      }
      field(move._1)(move._2) = ' '
    }
    (bestScore, bestX, bestY)
  }

  private def evaluateLine(line: String): Int = {
    def matchLine(P: Char): Int = {
      var score = 0
      line match {
        case xs :: P :: P => score += 100
        case P :: xs :: P => score += 100
        case P :: P :: xs => score += 100

        case Empty :: P :: xs => score += 10
        case P :: Empty :: xs => score += 10
        case P :: xs :: Empty => score += 10
        case xs :: P :: Empty => score += 10

        case Empty :: Empty :: P => score += 1
        case Empty :: P :: Empty => score += 1
        case P :: Empty :: Empty => score += 1

        case _ => score = 0
      }
      println(score)
      score
    }
    matchLine(player) * -1 + matchLine(computer)
  }

  private def evaluate: Int = {
    var score = 0
    val row1 = field(0).mkString
    val row2 = field(1).mkString
    val row3 = field(2).mkString

    val fieldT = field.transpose

    val col1 = fieldT(0).mkString
    val col2 = fieldT(1).mkString
    val col3 = fieldT(2).mkString

    val diag1 = diagonal(field)
    val diag2 = diagonal(field.map(c => c.reverse))

    val all = List(row1, row2, row3, col1, col2, col3, diag1, diag2)
    for (v <- all)
      score += evaluateLine(v)

    score
  }

  private def diagonal[T](f: Array[Array[T]]): String = {
    var str = ""
    for (i <- 0 until f.size) {
      str += f(i)(i).toString
    }
    str
  }

  private def generateMoves: ListBuffer[(Int, Int)] = {
    val r = ListBuffer[(Int, Int)]()
    if (game.hasWon(player) || game.hasWon(computer))
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
