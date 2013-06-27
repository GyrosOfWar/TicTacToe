object Scratchpad {
  def queens(n: Int): Set[List[Int]] = {
    def isSafe(col: Int, queens: List[Int]): Boolean = {
      val row = queens.length
      false
    }

    def placeQueens(k: Int): Set[List[Int]] = {
      if (k == 0) Set(List())
      else {
        for {
          queens <- placeQueens(k - 1)
          col <- 0 until n
          if isSafe(col, queens)
        } yield col :: queens
      }
    }
    placeQueens(n)
  }
}