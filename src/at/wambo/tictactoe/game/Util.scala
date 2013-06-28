package at.wambo.tictactoe.game

import scala.reflect.ClassTag

/**
 * Created with IntelliJ IDEA.
 * User: Martin
 * Date: 24.06.13
 * Time: 13:40
 */
object Util {
  def makeString[T](xs: Array[Array[T]], sep1: String, sep2: String) = xs.map(c => c.mkString(sep1)).mkString(sep2)

  def diagonal[T](xs: Array[Array[T]]): String = (for (i <- 0 until xs.length) yield xs(i)(i)).mkString

  def fillArrayRandom[T: ClassTag](size: Int, values: List[T]): Array[Array[T]] = {
    Array.fill[T](size, size)(values(util.Random.nextInt(values.length)))
  }
}
