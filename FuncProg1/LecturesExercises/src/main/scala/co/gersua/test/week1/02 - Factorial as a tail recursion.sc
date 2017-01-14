def factorial(x: Long): Long = {
  def innerFunc(acc: Long, newX: Long): Long = {
    if (newX == 0) acc
    else innerFunc(acc * newX, newX - 1)
  }
  innerFunc(1, x)
}

factorial(6)