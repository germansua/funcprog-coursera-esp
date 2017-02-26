trait Generator[+T] {
  self =>

  def generate

  def map[S](f: T => S) = f(self.generate)

}

val integers = new Generator[Int] {
  def generate = scala.util.Random.nextInt()
}

val booleans = integers.map(_ >= 0)