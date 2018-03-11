def sum(xs: Array[Int]): Int = {
  xs.par.fold(0)(_ + _)
}

def max(xs: Array[Int]): Int = {

  // for the neutral element f(x, neutral) must always be x

  xs.par.fold(Integer.MIN_VALUE)(math.max)
  // alternative (mine) xs.par.fold(xs(0))((x, y) => math.max(x, y))
}

max(Array(1, 2, 3, 4, 5))

def play(a: String, b: String): String = List(a, b).sorted match {
  case List("paper", "scissors") => "scissors"
  case List("paper", "rock") => "paper"
  case List("rock", "scissors") => "rock"
  case List(a, b) if (a == b) => a
  case List("", b) => b
}

// does not work properly on parallel because it isn't associative
Array("paper", "rock", "paper", "scissors").par.fold("")(play)

def isVowel(c: Char): Boolean = {
  Array('A','E','I','O','U').contains(c)
}

Array('E','P','F','L').par.aggregate(0)((count, c) => if (isVowel(c)) count + 1 else count, _ + _)