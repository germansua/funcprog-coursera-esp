def merge(xs: List[Int], ys: List[Int]): List[Int] =
  (xs, ys) match {
    case (x, List()) => x
    case (List(), y) => y
    case (x :: xs1, y :: ys1) => if (x < y) x :: merge(xs1, ys) else y :: merge(xs, ys1)
  }

def msort(xs: List[Int]): List[Int] = {
  val n = xs.length / 2
  if (n == 0) xs
  else {
    val (fst, snd) = xs splitAt n
    merge(msort(fst), msort(snd))
  }
}

msort(List(6,3,5,7,2,9,8,1,4,0))