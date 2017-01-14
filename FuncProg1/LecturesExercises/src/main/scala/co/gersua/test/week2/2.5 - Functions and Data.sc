class Rational(x: Int, y: Int) {
  def numer = x
  def denom = y

  def add(that: Rational): Rational = {
    new Rational(
      numer * that.denom + denom * that.numer,
      denom * that.denom
    )
  }

  def sub(that: Rational): Rational = add(that.neg)

  def neg(): Rational = new Rational(-x, y)

  override def toString: String = numer + "/" + denom
}

val r1 = new Rational(1, 2)
val r2 = new Rational(2, 3)
r1.add(r2)

val x = new Rational(1, 3)
val y = new Rational(5, 7)
val z = new Rational(3, 2)

x.sub(y).sub(z)
