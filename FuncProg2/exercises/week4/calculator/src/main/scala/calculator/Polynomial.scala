package calculator

object Polynomial {
  def computeDelta(a: Signal[Double], b: Signal[Double],
                   c: Signal[Double]): Signal[Double] = {
    Var(scala.math.pow(b(), 2) - (4 * a() * c()))
  }

  def computeSolutions(a: Signal[Double], b: Signal[Double],
                       c: Signal[Double], delta: Signal[Double]): Signal[Set[Double]] = {

    val minusB = Var(-1 * b())
    val deltaSqrt = Var(scala.math.sqrt(delta()))
    val divisor = Var(2 * a())

    Var(
      if (delta() < 0) Set()
      else {
        val positiveSol = Var((minusB() + deltaSqrt()) / divisor())
        val negativeSol = Var((minusB() - deltaSqrt()) / divisor())
        Set(positiveSol(), negativeSol())
      }
    )
  }
}
