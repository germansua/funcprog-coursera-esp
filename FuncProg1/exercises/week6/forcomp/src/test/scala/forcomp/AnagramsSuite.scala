package forcomp

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import Anagrams._

@RunWith(classOf[JUnitRunner])
class AnagramsSuite extends FunSuite {

  //  test("my_wordOccurrences: abcd") {
  //    val w = "HeeeelLl0o World"
  //    val group = w.toLowerCase.filter(c => c.isLetter).groupBy(c => c)
  //      .map(pair => (pair._1, pair._2.length)).toList
  //      .sorted
  //      .sortWith((p1, p2) => p1._2.compareTo(p2._2) < 0)
  //
  //    println(group)
  //    //    (for ((char, dupl) <- group) yield (char -> dupl.length)).toList
  //    //    println(r.toList)
  //  }
  /*
    test("wordOccurrences: abcd") {
      assert(wordOccurrences("abcd") === List(('a', 1), ('b', 1), ('c', 1), ('d', 1)))
    }

    test("wordOccurrences: Robert") {
      assert(wordOccurrences("Robert") === List(('b', 1), ('e', 1), ('o', 1), ('r', 2), ('t', 1)))
    }


    test("sentenceOccurrences: abcd e") {
      assert(sentenceOccurrences(List("abcd", "e")) === List(('a', 1), ('b', 1), ('c', 1), ('d', 1), ('e', 1)))
    }
  */

  //  test("my dictionaryByOccurrences.get: eat") {
  //    println(dictionaryByOccurrences)
  //  }
  /*
    test("dictionaryByOccurrences.get: eat") {
      assert(dictionaryByOccurrences.get(List(('a', 1), ('e', 1), ('t', 1))).map(_.toSet) === Some(Set("ate", "eat", "tea")))
    }


    test("word anagrams: married") {
      assert(wordAnagrams("married").toSet === Set("married", "admirer"))
    }

    test("word anagrams: player") {
      assert(wordAnagrams("player").toSet === Set("parley", "pearly", "player", "replay"))
    }

  */
    test("subtract: lard - r") {
      val lard = List(('a', 1), ('d', 1), ('l', 1), ('r', 1))
      val r = List(('r', 1))
      val lad = List(('a', 1), ('d', 1), ('l', 1))
      assert(subtract(lard, r) === lad)
    }
  //
  //
  //  test("combinations: []") {
  //    assert(combinations(Nil) === List(Nil))
  //  }

//  test("my combinations: abba") {
//    val occurrences: Occurrences = List(('a', 2), ('b', 2))
//
//    def expandedOccurrences: Occurrences = for ((letter, count) <- occurrences; index <- 1 to count) yield (letter, index)
//
//    println(expandedOccurrences)
//    println("**********")
//
//    def combineHead(expanded: Occurrences): List[Occurrences] = expanded match {
//      case Nil => List()
//      case head :: Nil => List(expanded)
//      case head :: tail => for ((letter, count) <- tail; if letter != head._1) yield List(head, (letter, count))
//    }
//
//    println(combineHead(List()))
//    println(combineHead(expandedOccurrences))
//    println(combineHead(List(('a', 2))))
//    println("**********")
//
//    def combine(occurrences: Occurrences): List[Occurrences] = occurrences match {
//      case Nil => List()
//      case x :: Nil => combineHead(List(x))
//      case x :: xs => List(x) :: combineHead(occurrences) ::: combine(xs)
//    }
//
//    println(combine(List(('a', 2))))
//    println(combine(expandedOccurrences))
//    println("**********")
//
//    println(List() :: combine(expandedOccurrences))
//  }
//
//  test("combinations: abba") {
//    val abba = List(('a', 2), ('b', 2))
//    val abbacomb = List(
//      List(),
//      List(('a', 1)),
//      List(('a', 2)),
//      List(('b', 1)),
//      List(('a', 1), ('b', 1)),
//      List(('a', 2), ('b', 1)),
//      List(('b', 2)),
//      List(('a', 1), ('b', 2)),
//      List(('a', 2), ('b', 2))
//    )
//    assert(combinations(abba).toSet === abbacomb.toSet)
//  }

//  test("my fold left/right") {
//    val ints = List(1, 2, 3, 4, 5)
//
//    val acc: List[Int] = List()
//
//    println(ints.foldLeft(acc)((x, y) => x ::: List(y)))
//    println(ints.foldRight(0)(_ + _))
//  }

//  test("my fold left/right 2") {
//    val x = List(('a', 2), ('d', 1), ('l', 1), ('r', 1)).toMap
//    val y = List(('a', 1), ('r', 1))
//
//    def subtractPairs(x: (Char, Int), y: (Char, Int)): (Char, Int) = {
//      if (x._1 == y._1) (x._1, x._2 - y._2)
//      else x
//    }
//
//    def subtractMapPair(map: Map[Char, Int], pair: (Char, Int)): Map[Char, Int] = {
//      val newPair = subtractPairs((pair._1, map.apply(pair._1)), pair)
//      map.updated(newPair._1, newPair._2)
//    }
//
//    // println(subtractMapPair(x, y.head).filter(pair => pair._2 > 0))
//    println("*********")
//
////    val emptyMap: Map[Char, Int] = Map()
//    val newVal = y.foldLeft(x)((acc, element) => subtractMapPair(acc, element)).toList.filter(pair => pair._2 > 0).sorted
//    println(newVal)
//
//  }

  //  test("sentence anagrams: []") {
  //    val sentence = List()
  //    assert(sentenceAnagrams(sentence) === List(Nil))
  //  }
  //
  //  test("sentence anagrams: Linux rulez") {
  //    val sentence = List("Linux", "rulez")
  //    val anas = List(
  //      List("Rex", "Lin", "Zulu"),
  //      List("nil", "Zulu", "Rex"),
  //      List("Rex", "nil", "Zulu"),
  //      List("Zulu", "Rex", "Lin"),
  //      List("null", "Uzi", "Rex"),
  //      List("Rex", "Zulu", "Lin"),
  //      List("Uzi", "null", "Rex"),
  //      List("Rex", "null", "Uzi"),
  //      List("null", "Rex", "Uzi"),
  //      List("Lin", "Rex", "Zulu"),
  //      List("nil", "Rex", "Zulu"),
  //      List("Rex", "Uzi", "null"),
  //      List("Rex", "Zulu", "nil"),
  //      List("Zulu", "Rex", "nil"),
  //      List("Zulu", "Lin", "Rex"),
  //      List("Lin", "Zulu", "Rex"),
  //      List("Uzi", "Rex", "null"),
  //      List("Zulu", "nil", "Rex"),
  //      List("rulez", "Linux"),
  //      List("Linux", "rulez")
  //    )
  //    assert(sentenceAnagrams(sentence).toSet === anas.toSet)
  //  }

}
