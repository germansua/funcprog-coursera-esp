def initializeArray(xs: Array[Int], v: Int): Unit = {
  for (i <- (0 until xs.length).par) xs(i) = v

  // Alternative to the range xs.indices.par
}

val arr = Array(1, 2, 3, 4, 5)
initializeArray(arr, 0)
arr