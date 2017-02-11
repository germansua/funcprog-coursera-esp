def squareList(xs: List[Int]): List[Int] = xs match {
  case Nil => xs
  case y :: ys => y * y :: squareList(ys)
}

def squareList2(xs: List[Int]): List[Int] =
  xs map (x => x * x)

val nums = List(2, -4, 5, 7, 1)
val fruis = List("apple", "pinapple", "orange", "banana")

nums filter (x => x > 0)
nums filterNot (x => x > 0)
nums partition (x => x > 0)

nums takeWhile (x => x % 2 == 0)
nums dropWhile (x => x % 2 == 0)
nums span (x => x % 2 == 0)


val l = List("a", "a", "a", "b", "c", "c", "a")
val (l1, l2) = l.span(e => e.eq("a"))
val (l3, l4) = l2.span(e => e.eq("b"))

def pack[T](xs: List[T]): List[List[T]] = xs match {
  case Nil => Nil
  case x :: xs1 => {
    val (l1, l2) = xs.span(s => s.equals(x))
    l1 :: pack(l2)
  }
}

val packResult = pack(List("a", "a", "a", "b", "c", "c", "a"))

def encode[T](listOfList: List[List[T]]): List[(T, Int)] = listOfList match {
  case Nil => Nil
  case x :: xs => (x.head, x.size) :: encode(xs)
}

encode(packResult)

def encode2[T](xs: List[T]): List[(T, Int)] = {
  pack(xs) map (ys => (ys.head, ys.size))
}
encode2(List("a", "a", "a", "b", "c", "c", "a"))
