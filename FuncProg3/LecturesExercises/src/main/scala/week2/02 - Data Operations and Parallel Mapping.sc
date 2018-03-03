def mapSeqOnArray[A, B](input: Array[A], output: Array[B],
                        from: Int, to: Int, f: A => B): Unit = {
  if (from < to) {
    output(from) = f(input(from))
    mapSeqOnArray(input, output, from + 1, to, f)
  }
}

def mapParallelOnArray[A, B](input: Array[A], output: Array[B],
                             threshold: Int, from: Int, to: Int,
                             f: A => B): Unit = {
  if (to - from < threshold) mapParallelOnArray(input, output, from, to, f)
  else {
    val mid = from + (to - from) / 2
    parallel(
      mapParallelOnArray(input, output, threshold, from, mid, f),
      mapParallelOnArray(input, output, threshold, mid, to, f)
    )
  }
}

val input = Array(1, 2, 3, 4, 5)
val output = Array(0, 0, 0, 0, 0)



output
