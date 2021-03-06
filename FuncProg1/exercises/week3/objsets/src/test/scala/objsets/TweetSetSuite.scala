package objsets

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TweetSetSuite extends FunSuite {

  trait TestSets {
    val set1 = new Empty
    val set2 = set1.incl(new Tweet("a", "a body", 20))
    val set3 = set2.incl(new Tweet("b", "b body", 20))
    val c = new Tweet("c", "c body", 7)
    val d = new Tweet("d", "d body", 9)
    val set4c = set3.incl(c)
    val set4d = set3.incl(d)
    val set5 = set4c.incl(d)
  }

  def asSet(tweets: TweetSet): Set[Tweet] = {
    var res = Set[Tweet]()
    tweets.foreach(res += _)
    res
  }

  def size(set: TweetSet): Int = asSet(set).size


  test("filter: on empty set") {
    new TestSets {
      assert(size(set1.filter(tw => tw.user == "a")) === 0)
    }
  }

  test("filter: a on set5") {
    new TestSets {
      assert(size(set5.filter(tw => tw.user == "a")) === 1)
    }
  }

  test("filter: 20 on set5") {
    new TestSets {
      assert(size(set5.filter(tw => tw.retweets == 20)) === 2)
    }
  }

  test("filter: mine") {
    new TestSets {
      val set = set1
        .incl(new Tweet("7", "7", 7))
        .incl(new Tweet("5", "5", 5))
        .incl(new Tweet("9", "9", 9))
        .incl(new Tweet("3", "3", 3))
        .incl(new Tweet("1", "6", 6))
        .incl(new Tweet("8", "8", 8))

      println("Even numbers: ")
      set.filter(t => t.retweets % 2 == 0)
        .foreach(t => print(" " + t))

      println("\nOdd numbers: ")
      set.filter(t => t.retweets % 2 != 0)
        .foreach(t => print(" " + t))
    }
  }

  test("union: set4c and set4d") {
    new TestSets {
      assert(size(set4c.union(set4d)) === 4)
    }
  }

  test("union: with empty set (1)") {
    new TestSets {
      assert(size(set5.union(set1)) === 4)
    }
  }

  test("union: with empty set (2)") {
    new TestSets {
      assert(size(set1.union(set5)) === 4)
    }
  }

  test("mostRetweeted: mine") {
    new TestSets {
      val set = set1
        .incl(new Tweet("7", "7", 7))
        .incl(new Tweet("5", "5", 5))
        .incl(new Tweet("9", "9", 9))
        .incl(new Tweet("3", "3", 3))
        .incl(new Tweet("1", "6", 6))
        .incl(new Tweet("8", "8", 8))
      println("mostRetweeted: " + set.mostRetweeted)
    }
  }

  test("descending: mine") {
    new TestSets {
      val set = set1
        .incl(new Tweet("7", "7", 7))
        .incl(new Tweet("5", "5", 5))
        .incl(new Tweet("9", "9", 9))
        .incl(new Tweet("3", "3", 3))
        .incl(new Tweet("1", "6", 6))
        .incl(new Tweet("8", "8", 8))
      set.descendingByRetweet.foreach(t => println(t))
    }
  }

  test("descending: set5") {
    new TestSets {
      val trends = set5.descendingByRetweet
      assert(!trends.isEmpty)
      assert(trends.head.user == "a" || trends.head.user == "b")
    }
  }

  test("Number4 : mine") {
    new TestSets {
      val set = set1
        .incl(new Tweet("1", "android is a operating system", 1))
        .incl(new Tweet("2", "iOS", 2))
      val google = List("android", "Android", "galaxy", "Galaxy", "nexus", "Nexus")

      lazy val googleTweets: TweetSet = set.filter(tweet => google.exists(s => tweet.text.contains(s)))
      googleTweets.foreach(t => println(t))
    }
  }
}
