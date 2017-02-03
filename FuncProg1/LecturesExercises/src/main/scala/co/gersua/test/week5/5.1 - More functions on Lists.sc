def init[T](xs: List[T]): List[T] = xs match {
  case List() => throw new Error("init of empty list")
  case List(x) => List()
  case y :: ys => y :: init(ys)
}


def removeAt[T](n: Int, xs: List[T]): List[T] = xs match {
  case List() => throw new Error("It's a empty List")
  case List(x) => if (n == 0) List() else xs
  case y :: ys => if (n == 0) ys else y :: removeAt(n - 1, ys)
}

def removeAt2[T](n: Int, xs: List[T]): List[T] = (xs take n) ::: (xs drop n + 1)

removeAt(1, List('a', 'b', 'c', 'd')) // List(a, c, d)

removeAt2(1, List('a', 'b', 'c', 'd')) // List(a, c, d)


//def flatten(xs: List[Any]): List[Any] = xs match {
//  case List() => xs
//  case List(y) => xs
//  case List(y::ys) => y :: flatten(ys)
//}
//
//flatten(List(List(1, 1), 2, List(3, List(5, 8))))