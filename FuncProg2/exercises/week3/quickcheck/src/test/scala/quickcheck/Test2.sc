import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Gen._
import org.scalacheck._

val myGen = for {
  n <- Gen.choose(10, 20)
  m <- Gen.choose(2 * n, 500)
} yield (n, m)

myGen.sample

val vowel = Gen.oneOf('A', 'E', 'I', 'O', 'U', 'Y')
vowel.sample

sealed abstract class Tree

case class Node(left: Tree, right: Tree, v: Int) extends Tree

case object Leaf extends Tree

val genLeaf = const(Leaf)

val genNode = for {
  v <- arbitrary[Int]
  left <- genTree
  right <- genTree
} yield Node(left, right, v)

def genTree: Gen[Tree] = oneOf(genLeaf, genNode)
genTree.sample

