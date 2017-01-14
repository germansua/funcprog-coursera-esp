class Rational(x: Int, y: Int) {
  require(y > 0, "denominator must be a positive number greater that zero")

  def this(x: Int) = this(x, 1)

  private val g = gcd(x, y)
  def numer = x
  def denom = y

  private def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)

  def less(that: Rational) = numer * that.denom < that.numer * denom

  def max(that: Rational) = if (this.less(that)) that else this

  def add(that: Rational): Rational = {
    new Rational(
      numer * that.denom + denom * that.numer,
      denom * that.denom
    )
  }

  def sub(that: Rational): Rational = add(that.neg)

  def neg(): Rational = new Rational(-x, y)

  // Not the same performance than below
  override def toString: String = numer / g + "/" + denom / g
}

val r1 = new Rational(1, 2)
val r2 = new Rational(2, 3)
r1.add(r2)

val x = new Rational(1, 3)
val y = new Rational(5, 7)
val z = new Rational(3, 2)

x.sub(y).sub(z)
y.add(y)
x.less(y)
x.max(y)

val strange = new Rational(3, 0)


// Previous code, above implements the lecture exercise

//class Rational(x: Int, y: Int) {
//  require(y > 0, "denominator must be a positive number greater that zero")
//
//  def this(x: Int) = this(x, 1)
//
//  private val g = gcd(x, y)
//  def numer = x / g
//  def denom = y / g
//
//  private def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)
//
//  def less(that: Rational) = numer * that.denom < that.numer * denom
//
//  def max(that: Rational) = if (this.less(that)) that else this
//
//  def add(that: Rational): Rational = {
//    new Rational(
//      numer * that.denom + denom * that.numer,
//      denom * that.denom
//    )
//  }
//
//  def sub(that: Rational): Rational = add(that.neg)
//
//  def neg(): Rational = new Rational(-x, y)
//
//  override def toString: String = numer + "/" + denom
//}
//
//val r1 = new Rational(1, 2)
//val r2 = new Rational(2, 3)
//r1.add(r2)
//
//val x = new Rational(1, 3)
//val y = new Rational(5, 7)
//val z = new Rational(3, 2)
//
//x.sub(y).sub(z)
//y.add(y)
//x.less(y)
//x.max(y)
//
//val strange = new Rational(3, 0)
