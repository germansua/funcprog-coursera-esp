import org.scalacheck.Prop.{BooleanOperators, forAll}
import org.scalacheck.{Gen, Properties}

val propConcatList = forAll { (l1: List[Int], l2: List[Int]) =>
  l1.size + l2.size == (l1 ::: l2).size
}
propConcatList.check

val propSqrt = forAll { (n: Int) => math.sqrt(n * n) == n }
propSqrt.check

val propReverseList = forAll { l: List[String] => l.reverse.reverse == l }
propReverseList.check

val propConcatString = forAll { (s1: String, s2: String) =>
  (s1 + s2).endsWith(s2)
}
propConcatString.check

val smallInteger = Gen.choose(0, 100)
val propSmallInteger = forAll(smallInteger) {
  n: Int => n >= 0 && n <= 100
}
propSmallInteger.check

val propMakeList = forAll {
  n: Int => (n >= 0 && n < 10000) ==> (List.fill(n)("").length == n)
}

object StringSpecification extends Properties("String") {
  property("startsWith") = forAll { (a: String, b: String) =>
    (a + b).startsWith(a)
  }

  property("endsWith") = forAll { (a: String, b: String) =>
    (a + b).endsWith(b)
  }

  property("substring") = forAll { (a: String, b: String) =>
    (a + b).substring(a.length) == b
  }

  property("substring") = forAll { (a: String, b: String, c: String) =>
    (a + b + c).substring(a.length, a.length + b.length) == b
  }
}

StringSpecification.check