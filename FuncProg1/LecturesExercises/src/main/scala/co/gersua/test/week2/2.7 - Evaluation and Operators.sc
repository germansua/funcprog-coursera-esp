class Rational(x: Int, y: Int) {
  //require(y > 0, "denominator must be a positive number greater that zero")

  def this(x: Int) = this(x, 1)

  private val g = gcd(x, y)
  def numer = x / g
  def denom = y / g

  private def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)

  def < (that: Rational) = numer * that.denom < that.numer * denom

  def > (that: Rational) = if (this < that) that else this

  def +(that: Rational): Rational = {
    new Rational(
      numer * that.denom + denom * that.numer,
      denom * that.denom
    )
  }

  def -(that: Rational): Rational = this + -that

  def unary_-(): Rational = new Rational(-x, y)

  override def toString: String = numer + "/" + denom
}

val r1 = new Rational(1, 2)
val r2 = new Rational(2, 3)
r1 + r2

val x = new Rational(1, 3)
val y = new Rational(5, 7)
val z = new Rational(3, 2)

x - y - z
y + y
x < y
x > y
x
-x

// val strange = new Rational(3, 0)