import scala.collection.GenSeq

val pairs = GenSeq((1, "one"), (1, "uno"), (2, "two"))

pairs.groupBy(_._1).mapValues(seq => seq.map(pair => pair._2))