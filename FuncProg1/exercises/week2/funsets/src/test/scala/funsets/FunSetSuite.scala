package funsets

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

/**
  * This class is a test suite for the methods in object FunSets. To run
  * the test suite, you can either:
  *  - run the "test" command in the SBT console
  *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
  */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
    * Link to the scaladoc - very clear and detailed tutorial of FunSuite
    *
    * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
    *
    * Operators
    *  - test
    *  - ignore
    *  - pending
    */

  /**
    * Tests are written using the "test" operator and the "assert" method.
    */
  // test("string take") {
  //   val message = "hello, world"
  //   assert(message.take(5) == "hello")
  // }

  /**
    * For ScalaTest tests, there exists a special equality operator "===" that
    * can be used inside "assert". If the assertion fails, the two values will
    * be printed in the error message. Otherwise, when using "==", the test
    * error message will only say "assertion failed", without showing the values.
    *
    * Try it out! Change the values so that the assertion fails, and look at the
    * error message.
    */
  // test("adding ints") {
  //   assert(1 + 2 === 3)
  // }


  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
    * When writing tests, one would often like to re-use certain values for multiple
    * tests. For instance, we would like to create an Int-set and have multiple test
    * about it.
    *
    * Instead of copy-pasting the code for creating the set into every test, we can
    * store it in the test class using a val:
    *
    * val s1 = singletonSet(1)
    *
    * However, what happens if the method "singletonSet" has a bug and crashes? Then
    * the test methods are not even executed, because creating an instance of the
    * test class fails!
    *
    * Therefore, we put the shared values into a separate trait (traits are like
    * abstract classes), and create an instance inside each test method.
    *
    */

  trait TestSets {
    val s0 = singletonSet(0)
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val s4 = singletonSet(4)
    val s5 = singletonSet(5)
    val s6 = singletonSet(6)
    val s7 = singletonSet(7)
    val s8 = singletonSet(8)
    val s9 = singletonSet(9)

    val s_1 = singletonSet(-1)
    val s_2 = singletonSet(-2)
    val s_3 = singletonSet(-3)
    val s_4 = singletonSet(-4)
    val s_5 = singletonSet(-5)
    val s_6 = singletonSet(-6)
    val s_7 = singletonSet(-7)
    val s_8 = singletonSet(-8)
    val s_9 = singletonSet(-9)
  }

  /**
    * This test is currently disabled (by using "ignore") because the method
    * "singletonSet" is not yet implemented and the test would fail.
    *
    * Once you finish your implementation of "singletonSet", exchange the
    * function "ignore" by "test".
    */
  test("singletonSet(1) contains 1") {

    /**
      * We create a new instance of the "TestSets" trait, this gives us access
      * to the values "s1" to "s3".
      */
    new TestSets {
      /**
        * The string argument of "assert" is a message that is printed in case
        * the test fails. This helps identifying which assertion failed.
        */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements of each set") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("Larger set union") {
    new TestSets {
      val set = union(union(union(union(union(s1, s2), s3), s4), s8), s9)
      assert(contains(set, 1))
      assert(contains(set, 2))
      assert(contains(set, 3))
      assert(contains(set, 4))
      assert(contains(set, 8))
      assert(contains(set, 9))

      assert(!contains(set, 7))
    }
  }

  test("Intersection of two sets") {
    new TestSets {
      val set1 = union(union(union(union(union(s1, s2), s3), s4), s8), s9)
      val set2 = union(union(union(union(union(s0, s2), s3), s7), s6), s5)
      val inter = intersect(set1, set2)

      assert(contains(inter, 2))
      assert(contains(inter, 3))

      assert(!contains(inter, 0))
      assert(!contains(inter, 1))
      assert(!contains(inter, 4))
      assert(!contains(inter, 5))
      assert(!contains(inter, 6))
      assert(!contains(inter, 7))
      assert(!contains(inter, 8))
      assert(!contains(inter, 9))
    }
  }

  test("Difference of two sets") {
    new TestSets {
      val set1 = union(union(union(union(union(s1, s2), s3), s4), s8), s9)
      val set2 = union(union(union(union(union(s0, s2), s3), s7), s6), s5)
      val difference = diff(set1, set2)

      assert(contains(difference, 1))
      assert(contains(difference, 4))
      assert(contains(difference, 8))
      assert(contains(difference, 9))

      assert(!contains(difference, 0))
      assert(!contains(difference, 2))
      assert(!contains(difference, 3))
      assert(!contains(difference, 7))
      assert(!contains(difference, 6))
      assert(!contains(difference, 5))
    }
  }

  test("Positive Integers Filter") {
    new TestSets {
      val set = union(union(union(union(union(union(s1, s2), s3), s0), s_1), s_2), s_3)
      val positiveFilter = filter(set, n => n >= 0)

      assert(contains(positiveFilter, 0))
      assert(contains(positiveFilter, 1))
      assert(contains(positiveFilter, 2))
      assert(contains(positiveFilter, 3))

      assert(!contains(positiveFilter, -1))
      assert(!contains(positiveFilter, -2))
      assert(!contains(positiveFilter, -3))
      assert(!contains(positiveFilter, 17))
    }
  }

  test("Even numbers") {
    new TestSets {
      val set = union(union(union(union(union(union(s1, s2), s3), s0), s_1), s_2), s_3)
      val evenNumbers = filter(set, n => n % 2 == 0)

      assert(contains(evenNumbers, 0))
      assert(contains(evenNumbers, 2))
      assert(contains(evenNumbers, -2))

      assert(!contains(evenNumbers, -1))
      assert(!contains(evenNumbers, 1))
      assert(!contains(evenNumbers, 3))
      assert(!contains(evenNumbers, -3))
    }
  }

  test("Forall greater than 2") {
    new TestSets {
      val set = union(union(union(union(union(union(s3, s2), s1), s0), s_1), s_2), s4)
      val result = forall(set, n => n > 2)
      assert(!result)

      val set2 = union(union(union(union(union(union(s3, s4), s5), s6), s7), s8), s9)
      val result2 = forall(set2, n => n > 2)
      assert(result2)
    }
  }

  test("Forall odd numbers") {
    new TestSets {
      val set = union(union(union(s1, s2), s3), s4)
      val result = forall(set, n => n % 2 != 0)
      assert(!result)

      val set2 = union(union(union(s1, s3), s5), s7)
      val result2 = forall(set2, n => n % 2 != 0)
      assert(result2)
    }
  }

  test("Exist one odd number in the set") {
    new TestSets {
      val set = union(union(union(s2, s4), s3), s6)
      val result = exists(set, x => x % 2 != 0)
      assert(result)
    }
  }

  test("Exist one even number in the set") {
    new TestSets {
      val set = union(union(union(s1, s3), s5), s7)
      val result = exists(set, x => x % 2 == 0)
      assert(!result)
    }
  }

  test("Exist one number divisible by it self that results 1") {
    new TestSets {
      val set = union(union(union(s2, s4), s3), s6)
      val result = exists(set, x => x / x == 1)
      assert(result)
    }
  }

  test("Map x to x * x") {
    new TestSets {
      val set = union(union(union(s1, s2), s3), s4)
      val result = map(set, x => x * x)

      printSet(result)

      assert(contains(result, 1))
      assert(contains(result, 4))
      assert(contains(result, 9))
      assert(contains(result, 16))
    }
  }
}
