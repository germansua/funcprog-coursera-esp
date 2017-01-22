trait List[T] {
  def isEmpty: Boolean
  def head: T
  def tail: List[T]
}

class Cons[T](val head: T, val tail: List[T]) extends List[T] {
  def isEmpty: Boolean = false
}

class Nill[T] extends List[T] {
  def isEmpty: Boolean = true
  def head: Nothing = throw new NoSuchElementException("Nil.head")
  def tail: Nothing = throw new NoSuchElementException("Nil.tail")
}

def nth[T](list: List[T], index: Int): T = {

  def iter(list: List[T], index: Int, acc: Int): T = {
    if (index < 0) throw new IndexOutOfBoundsException("Bad Index")
    else if (index == acc) list.head
    else if (!list.tail.isEmpty) iter(list.tail, index, acc + 1)
    else throw new IndexOutOfBoundsException("Bad Index")
  }

  iter(list, index, 0)
}

def nth2[T](list: List[T], index: Int): T = {
  if (list.isEmpty) throw new IndexOutOfBoundsException("Bad index")
  else if (index == 0) list.head
  else nth2(list.tail, index - 1)
}

val list = new Cons(1, new Cons(2, new Cons(3, new Nill)));
nth(list, 2)
nth2(list, 1)