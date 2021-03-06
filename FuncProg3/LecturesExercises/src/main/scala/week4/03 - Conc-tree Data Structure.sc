sealed trait List[+T] {
  def head: T
  def tail: List[T]
}

case class ::[T](head: T, tail: List[T]) extends List[T]

case object Nil extends List[Nothing] {
  override def head: Nothing = sys.error("empty list")
  override def tail: List[Nothing] = sys.error("empty list")
}

def filter[T](lst: List[T])(p: T => Boolean): List[T] = lst match {
  case x :: xs if (p(x)) =>  ::(x, filter(xs)(p))
  case x :: xs => filter(xs)(p)
  case Nil => Nil
}

//sealed trait Tree[+T]
//
//case class Node[T](left: Tree[T], right:Tree[T]) extends Tree[T]
//
//case class Leaf[T](elem: T) extends Tree[T]
//
//case object Empty extends Tree[Nothing]
//
//def filter[T](t: Tree[T])(p: T => Boolean) = t match {
//  case Node(left, right) => Node(parallel(filter(left)(p)), parallel(filter(right)(p)))
//  case Leaf(elem) => if (p(elem)) t else Empty
//  case Empty => Empty
//}


sealed trait Conc[+T] {
  def level: Int
  def size: Int
  def left: Conc[T]
  def right: Conc[T]
}

case object Empty extends Conc[Nothing] {
  def level: Int = 0
  def size: Int = 0
}

case class Single[T](val x: T) extends Conc[T] {
  def level = 0
  def size = 1
}

case class <>[T](left: Conc[T], right: Conc[T]) extends Conc[T] {
  val level = 1 + math.max(left.level, right.level)
  val size = left.size + right.size

  def <>(that: Conc[T]): Conc[T] = {
    if (this == Empty) that
    else if (that == Empty) this
    else concat(this, that)
  }

  private def concat(xs: Conc[T], ys: Conc[T]): Conc[T] = {
    val diff = ys.level - xs.level
    if (diff >= -1 || diff <= 1) new <>(xs, ys)
    else if (diff < -1) {
      if (xs.left.level >= xs.right.level) {
        val nr = concat(xs.right, ys)
        new <>(xs.left, nr)
      } else {
        val nrr = concat(xs.right.right, ys)
        if (nrr.level == xs.level - 3) {
          val nl = xs.left
          val nr = new <>(xs.right.left, nrr)
          new <>(nl, nr)
        } else {
          val nl = new<>(xs.left, xs.right.left)
          val nr = nrr
          new <>(nl, nr)
        }
      }
    }
  }
}

