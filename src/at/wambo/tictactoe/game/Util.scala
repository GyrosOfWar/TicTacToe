package at.wambo.tictactoe.game

import scala.reflect.ClassTag

/**
 * Created with IntelliJ IDEA.
 * User: Martin
 * Date: 24.06.13
 * Time: 13:40
 */
object Util {
  /**
   * Makes a string out of a two-dimensional Array.
   * @param xs Array of type T to convert to a string.
   * @param sep1 Separator element between each element in each inner array (y-dim)
   * @param sep2  Separator element between each inner array (x-dim)
   * @tparam T Type of the elements contained in the array.
   * @return String representation of the array
   */
  def makeString[T](xs: Array[Array[T]], sep1: String, sep2: String) = xs.map(c => c.mkString(sep1)).mkString(sep2)

  /**
   * Returns a string representation of the diagonal elements in xs.
   * @param xs
   * @tparam T
   * @return
   */
  def diagonal[T](xs: Array[Array[T]]): String = (for (i <- 0 until xs.length) yield xs(i)(i)).mkString

  def fillArrayRandom[T: ClassTag](size: Int, values: List[T]): Array[Array[T]] = {
    val random = util.Random.nextInt(values.length)
    Array.fill[T](size, size)(values(random))
  }
}
