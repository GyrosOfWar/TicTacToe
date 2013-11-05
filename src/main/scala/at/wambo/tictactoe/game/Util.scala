package at.wambo.tictactoe.game

import scala.reflect.ClassTag

/**
 * Created with IntelliJ IDEA.
 * User: Martin
 * Date: 24.06.13
 * Time: 13:40
 */
object Util {
  private var times = Map.empty[String, Seq[Long]].withDefaultValue(List.empty[Long])

  def makeString[A](xs: Array[Array[A]], sep1: String, sep2: String) = xs.map(c => c.mkString(sep1)).mkString(sep2)

  def diagonal[A](xs: Array[Array[A]]): String = (for (i <- 0 until xs.length) yield xs(i)(i)).mkString

  def fillArrayRandom[A: ClassTag](size: Int, values: List[A]): Array[Array[A]] = {
    Array.fill[A](size, size)(values(util.Random.nextInt(values.length)))
  }

  def timedCall[A](funcName: String, printTime: Boolean = true)(block: => A): A = {
    val t0 = System.nanoTime()
    val result = block
    val t1 = System.nanoTime()
    if (printTime) println(s"$funcName: Time: ${BigDecimal(t1 - t0) / BigDecimal(1000000)}")

    val newTimes = times(funcName) :+ (t1 - t0)
    times = times + (funcName -> newTimes)
    result
  }

  def printStats(funcName: String) {
    val msTimes = times(funcName).map(BigDecimal(_) / BigDecimal(1000000))
    require(msTimes != Nil)
    val median = msTimes.sorted.apply(msTimes.length / 2)

    println(s"Profiling information for function $funcName:")
    println(s"Median duration:\t\t$median ms")
    println(s"Max/Min/Range of durations:\t${msTimes.max} ms / ${msTimes.min} ms / ${msTimes.max - msTimes.min} ms")
    println(s"Number of samples: ${msTimes.size}")
  }
}
