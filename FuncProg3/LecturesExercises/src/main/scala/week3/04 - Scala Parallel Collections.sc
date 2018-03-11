import java.util.concurrent.ConcurrentSkipListSet

import scala.collection.concurrent.TrieMap
import scala.collection.{GenSet, mutable}

// It's important to avoid mutations to the same memory locations without synchronization
def intersectionWrong(a: GenSet[Int], b: GenSet[Int]): mutable.Set[Int] = {
  val result = mutable.Set[Int]()
  // This code is mutating and give different results on a parallel collections
  for (x <- a) if (b contains x) result += x
  result
}

val seqres = intersectionWrong((0 until 1000).toSet, (0 until 1000 by 4).toSet)
val parres = intersectionWrong((0 until 1000).par.toSet, (0 until 1000 by 4).par.toSet)

// The sizes can be the same but just "by accident"
seqres.size
parres.size

// One way to solve the problem
def intersectionRight(a: GenSet[Int], b: GenSet[Int]) = {
  val result = new ConcurrentSkipListSet[Int]() // This fixes the problem
  for (x <- a) if (b contains x) result.add(x)
  result
}

val seqres2 = intersectionRight((0 until 1000).toSet, (0 until 1000 by 4).toSet)
val parres2 = intersectionRight((0 until 1000).par.toSet, (0 until 1000 by 4).par.toSet)
seqres2.size
parres2.size

// Another using combinators (no side-effects)
def intersectionCombinator(a: GenSet[Int], b: GenSet[Int]) = {
  if (a.size < b.size) a.filter(b(_))
  else b.filter(a(_))
}

val seqres3 = intersectionCombinator((0 until 1000).toSet, (0 until 1000 by 4).toSet)
val parres3 = intersectionCombinator((0 until 1000).par.toSet, (0 until 1000 by 4).par.toSet)
seqres3.size
parres3.size


// Mutable map modeling a graph
val graph1 = mutable.Map[Int, Int]() ++= (0 until 100000).map(i => (i, i + 1))
graph1(99999) = 0
for ((k, v) <- graph1.par) graph1(k) = graph1(v)
val violation1 = graph1.find({case (i, v) => v != (i + 2) % graph1.size})
println(s"violation $violation1")

// Trie map offers a snapshot of O(1)
val graph = TrieMap[Int, Int]() ++= (0 until 100000).map(i => (i, i + 1))
graph(99999) = 0
val previous = graph.snapshot()
for ((k, v) <- graph.par) graph(k) = previous(v)
val violation = graph.find({case (i, v) => v != (i + 2) % graph.size})
println(s"violation $violation")

// Never write to a collection that is concurrent transversed
// Never read from a collection that is concurrent modified





