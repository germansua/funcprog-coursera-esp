abstract class IntSet {
  def incl(x: Int): IntSet
  def contains(x: Int): Boolean
  def union(other: IntSet): IntSet
}

class Empty extends IntSet {
  def incl(x: Int): IntSet = new NonEmpty(x, new Empty, new Empty)
  def contains(x: Int): Boolean = false
  def union(other: IntSet): IntSet = other

  override def toString = "."
}

class NonEmpty(elem: Int, left: IntSet, right: IntSet) extends IntSet {

  def incl(x: Int): IntSet = {
    if (x < elem) new NonEmpty(elem, left incl x, right)
    else if (x > elem) new NonEmpty(elem, left, right incl x)
    else this
  }

  def contains(x: Int): Boolean = {
    if (x < elem) left contains x
    else if (x > elem) right contains x
    else true
  }

  def union(other: IntSet): IntSet = {
    ((left union right) union other) incl elem
  }

  override def toString = "{" + left + elem + right + "}"
}

val t1 = new NonEmpty(3, new Empty, new Empty)
val t2 = t1 incl 4

t1 contains 4
t2 contains 4

// Testing unions
val u1 = new NonEmpty(8, new Empty, new Empty) incl 6 incl 10
val u2 = new NonEmpty(7, new Empty, new Empty) incl 5 incl 9
u1 union u2