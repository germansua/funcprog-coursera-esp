import scala.io.Source

val in = Source.fromURL("http://lamp.epfl.ch/files/content/sites/lamp/files/" +
  "teaching/progfun/linuxwords.txt")

val words = in.getLines.toList.filter(word => word forall(chr => chr.isLetter))

val mnem = Map('2' -> "ABC", '3' -> "DEF", '4' -> "GHI", '5' -> "JKL",
  '6' -> "MNO", '7' -> "PQRS", '8' -> "TUV", '9' -> "WXYZ")

val str = "HellO"
val any = for (letter <- str; if letter.isUpper) yield letter
val invert: Map[String, Char] = for ((k, v) <- mnem) yield (v, k)

val charCode: Map[Char, Char] = for {
  (digit, alpha) <- mnem
  letter <- alpha
} yield letter -> digit

def wordCode(word: String): String = {
  word.toUpperCase map charCode
}

wordCode("JAVa")

val wordsForNum: Map[String, Seq[String]] = {
  // equivalent to words.groupBy(word => wordCode(word))
  words groupBy wordCode withDefaultValue Seq()
}

val spl = 1 to str.length
for (i <- spl) {
  println(i)
}

str take 2
str drop 2

def encode(number: String): Set[List[String]] = {
  if (number.isEmpty) Set(List())
  else {
    for {
      split <- 1 to number.length
      word <- wordsForNum(number.take(split))
      rest <- encode(number.drop(split))
    } yield word :: rest
  }.toSet
}

encode("7225247386")

def translate(number: String): Set[String] = {
  // equivalent to encode(number).map(list => list.mkString(" "))
  encode(number) map (_ mkString " ")
}

translate("7225247386")






