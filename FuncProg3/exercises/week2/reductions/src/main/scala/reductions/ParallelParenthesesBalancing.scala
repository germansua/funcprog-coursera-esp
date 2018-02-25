package reductions

import scala.annotation._
import org.scalameter._
import common._

object ParallelParenthesesBalancingRunner {

  @volatile var seqResult = false

  @volatile var parResult = false

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 40,
    Key.exec.maxWarmupRuns -> 80,
    Key.exec.benchRuns -> 120,
    Key.verbose -> true
  ) withWarmer (new Warmer.Default)

  def main(args: Array[String]): Unit = {
    val length = 100000000
    val chars = new Array[Char](length)
    val threshold = 10000
    val seqtime = standardConfig measure {
      seqResult = ParallelParenthesesBalancing.balance(chars)
    }
    println(s"sequential result = $seqResult")
    println(s"sequential balancing time: $seqtime ms")

    val fjtime = standardConfig measure {
      parResult = ParallelParenthesesBalancing.parBalance(chars, threshold)
    }
    println(s"parallel result = $parResult")
    println(s"parallel balancing time: $fjtime ms")
    println(s"speedup: ${seqtime / fjtime}")
  }
}

object ParallelParenthesesBalancing {

  /** Returns `true` iff the parentheses in the input `chars` are balanced.
    */
  def balance(chars: Array[Char]): Boolean = {

    def calcVal(element: Char): Int = {
      if (element == '(') 1
      else if (element == ')') -1
      else 0
    }

    //    def iter(chars: Array[Char], acc: Int): Int = {
    //      if (acc < 0) -1
    //      else if (chars.isEmpty) acc
    //      else iter(chars.tail, acc + calcVal(chars.head))
    //    }
    //iter(chars, 0) == 0

    chars.map(c => calcVal(c)).foldLeft(0)((x, y) => if (x < 0) -1 else x + y) == 0
  }

  /** Returns `true` iff the parentheses in the input `chars` are balanced.
    */
  def parBalance(chars: Array[Char], threshold: Int): Boolean = {

    def traverse(idx: Int, until: Int, balanceAcc: Int, rightParenAcc: Int): (Int, Int) = {
      var i = idx
      var left = 0
      var right = 0

      while (i < until) {
        val current = chars(i)

        if (current == '(') left += 1
        else if (current == ')') right += 1

        i += 1
      }

      (left - right, scala.math.min(0, left - right))
    }

    def reduce(from: Int, until: Int): (Int, Int) = {
      if (until - from <= threshold) traverse(from, until, 0, 0)
      else {
        val middle = (from + until) / 2
        val ((x1, y1), (x2, y2)) = parallel(reduce(from, middle), reduce(middle, until))
        (x1 + x2, x1 + y2)
      }
    }

    reduce(0, chars.length) == (0, 0)
  }

  // For those who want more:
  // Prove that your reduction operator is associative!

}
