abstract class Boolean {
  def ifThenElse[T](t: => T, e: => T): T

  def &&(x: => Boolean): Boolean = ifThenElse(x, False)

  def ||(x: => Boolean): Boolean = ifThenElse(True, x)

  def unary_! : Boolean = ifThenElse(False, True)

  def ==(x: Boolean): Boolean = ifThenElse(x, x.unary_!)

  def !=(x: Boolean): Boolean = ifThenElse(x.unary_!, x)
}

object True extends Boolean {
  def ifThenElse[T](t: => T, e: => T): T = t

  override def toString = "True"
}

object False extends Boolean {
  def ifThenElse[T](t: => T, e: => T): T = e

  override def toString = "False"
}

println(True == False)
println(True || False)
println(True && False)
println(True != False)
println(!True)
println(!False)


abstract class Nat {
  def isZero: Boolean

  def predecessor: Nat

  def successor: Nat = new Succ(this)

  def +(that: Nat): Nat

  def -(that: Nat): Nat
}

object Zero extends Nat {
  def isZero: Boolean = True

  def predecessor: Nat = throw new Error

  def +(that: Nat): Nat = that

  def -(that: Nat): Nat = if (that.isZero.==(True)) this else throw new Error
}

class Succ(n: Nat) extends Nat {
  def isZero: Boolean = False

  def predecessor: Nat = n

  def +(that: Nat): Nat = new Succ(n + that)

  def -(that: Nat): Nat = if (that.isZero.==(True)) this else n - that.predecessor
}