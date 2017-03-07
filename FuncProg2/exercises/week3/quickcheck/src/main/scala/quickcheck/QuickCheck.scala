package quickcheck

import org.scalacheck.Arbitrary._
import org.scalacheck.Gen._
import org.scalacheck.Prop.{BooleanOperators, forAll}
import org.scalacheck._

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  lazy val genHeap: Gen[H] = for {
    x <- arbitrary[Int]
    h <- oneOf(const(empty), genHeap)
  } yield insert(x, h)

  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)

  property("gen1") = forAll { (h: H) =>
    val m = if (isEmpty(h)) 0 else findMin(h)
    findMin(insert(m, h)) == m
  }

  property("is empty") = forAll { (h: H) =>
    isEmpty(empty)
  }

  property("min of one element") = forAll { a: A =>
    val h = insert(a, empty)
    findMin(h) == a
  }

  property("min of two elements") = forAll { (a1: A, a2: A) =>
      val h = insert(a2, insert(a1, empty))
      findMin(h) == a1.min(a2)
  }

  property("min of three elements") = forAll { (a1: A, a2: A, a3: A) =>
    val h = insert(a1, insert(a2, insert(a3, empty)))
    findMin(h) == a1.min(a2.min(a3))
  }

  property("insert one on empty, then delete min, so it should be empty") = forAll { (a: A) =>
    isEmpty(deleteMin(insert(a, empty))) == true
  }

  property("always get min elements") = forAll { h: H =>
    def minSorted(h: H): List[A] = {
      if (isEmpty(h)) List()
      else findMin(h) :: minSorted(deleteMin(h))
    }
    minSorted(h) == minSorted(h).sorted
  }

  property("min of two heaps of one element") = forAll { (a1: A, a2: A) =>
      val h1 = insert(a1, empty)
      val h2 = insert(a2, empty)
      findMin(meld(h1, h2)) == a1.min(a2)
  }

  property("delete min of two") = forAll { (a1: A, a2: A) =>
    val h1 = insert(a1, insert(a2, empty))
    deleteMin(h1) == insert(a1.max(a2), empty)
  }

  property("delete min of three") = forAll { (a1: A, a2: A, a3: A) =>
    val h1 = insert(a1, insert(a2, insert(a3, empty)))
    findMin(h1) == a1.min(a2.min(a3))
  }

  property("min of two heaps") = forAll { (h1: H, h2: H) =>
    findMin(meld(h1, h2)) == scala.math.min(findMin(h1), findMin(h2))
  }

  property("compare two meld heaps") = forAll { (h1: H, h2: H) =>
    def minSorted(h: H): List[A] = {
      if (isEmpty(h)) List()
      else findMin(h) :: minSorted(deleteMin(h))
    }

    val m1 = meld(h1, h2)
    val m2 = meld(deleteMin(h1), insert(findMin(h1), h2))
    minSorted(m1) == minSorted(m2)
  }
}
