package at.wambo.tictactoe.game

import scala.reflect.ClassTag

/**
 * Created with IntelliJ IDEA.
 * User: Martin
 * Date: 24.06.13
 * Time: 13:40
 */
object Util {
  def makeString[T](f: Array[Array[T]], sep1: String, sep2: String) = f.map(c => c.mkString(sep1)).mkString(sep2)

  def diagonal[T](f: Array[Array[T]]): String = {
    var str = ""
    for (i <- 0 until f.size) {
      str += f(i)(i).toString
    }
    str
  }

  def fillArrayRandom[T: ClassTag](size: Int, values: List[T]): Array[Array[T]] = {
    Array.fill[T](size, size)(values(util.Random.nextInt(values.length)))
  }
}
