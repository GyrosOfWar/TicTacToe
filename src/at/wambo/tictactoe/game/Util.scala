package at.wambo.tictactoe.game

/**
 * Created with IntelliJ IDEA.
 * User: Martin
 * Date: 24.06.13
 * Time: 13:40
 */
object Util {
  def makeString[T](f: Array[Array[T]], sep1: String, sep2: String) = f.map(c => c.mkString(sep1)).mkString(sep2)

}
