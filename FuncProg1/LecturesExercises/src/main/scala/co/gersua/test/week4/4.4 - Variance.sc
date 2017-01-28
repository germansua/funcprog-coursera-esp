trait List[+T] {
  def isEmpty: Boolean
  def head: T
  def tail: List[T]
  // def prepend[T] (elem: T) = new Cons[T](elem, this) -> no Liskov Substitution Method,
  // so, above does not work
  def prepend[U >: T] (elem: U) = new Cons[U](elem, this)
}

class Cons[T](val head: T, val tail: List[T]) extends List[T] {
  def isEmpty: Boolean = false
}

object Nil extends List[Nothing] {
  def isEmpty: Boolean = true
  def head: Nothing = throw new NoSuchElementException("Nil.head")
  def tail: List[Nothing] = throw new NoSuchElementException("Nil.tail")
}

val x: List[String] = Nil

class A
class B extends A
class C extends A

val y1: List[A] = new Cons[A](new A, Nil).prepend(new B)

val y3: List[B] = new Cons[B](new B, Nil).prepend(new B)

val y2: List[A] = new Cons[B](new B, Nil).prepend(new C)
val y4 = new Cons[A](new B, y2)


