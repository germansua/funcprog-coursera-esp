trait Iterator[T] {
  def hasNext: Boolean

  def next(): T

  def foldLeft[S](z: S)(f: (S, T) => S): S = {
    var result = z
    while (hasNext) result = f(result, next())
    result
  }
}

// ************************************************ //
val threshold = 100

trait Splitter[T] {
  def split: Seq[Splitter[T]]

  def remaining: Int

  def fold(z: T)(f: (T, T) => T): T = {
    if (remaining < threshold) foldLeft(z)(f)
    else {
      val children: Seq[Task[T]] = for (child <- split) yield task {
        child.fold(z)(f)
      }
      children.map(_.join()).foldLeft(z)(f)
    }
  }
}

// ************************************************ //

trait Traversable[T] {
  def foreach(f: T => Unit): Unit

  def newBuilder: Builder[T, Traversable[T]]

  def filter(p: T => Boolean): Traversable[T] = {
    val b = newBuilder
    for (x <- this) if (p(x)) b += x
    b.result
  }
}