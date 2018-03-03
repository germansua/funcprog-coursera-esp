def seqScanLeft[A](input: Array[A], output: Array[A],
                  initElement: A, f: (A, A) => A): Unit = {
  var index = 0
  var acc = initElement
  output(index) = acc

  while (index < input.length) {
    acc = f(input(index), acc)
    index = index + 1
    output(index) = acc

  }
}

def recursiveSeqScanLef[A](input: Array[A], output: Array[A],
                           initElement: A, f: (A, A) => A): Unit = ???

var input = Array(1, 2, 3, 4, 5)
var output = Array(0, 0, 0, 0, 0, 0)
seqScanLeft[Int](input, output, 100, (x, y) => x + y)
output


def reduceSegment[A](input: Array[A], from: Int, to: Int,
                     initElement: A, f: (A, A) => A): A = {
  var index = from
  var acc = initElement

  while (index < to) {
    acc = f(input(index), acc)
    index = index + 1
  }
  acc
}

def recursiveReduceSegment[A](input: Array[A], from: Int, to: Int,
                              initElement: A, f: (A, A) => A): A = ???

reduceSegment[Int](Array(1, 2, 3, 4, 5), 0, 5, 100, (x, y) => x + y)

def mapSegment[A, B](input: Array[A], output: Array[B],
                     from: Int, to: Int, fi: (Int, A) => B): Unit = {
  var index = from
  while (index < to) {
    output(index) = fi(index, input(index))
    index = index + 1
  }
}

def recursiveMapSegment[A, B](input: Array[A], output: Array[B],
                              from: Int, to: Int, fi: (Int, A) => B): Unit = ???

input = Array(1, 2, 3, 4, 5)
output = Array(0, 0, 0, 0, 0)
mapSegment[Int, Int](input, output, 1, input.length - 1, (x, y) => y * y)
output

def seqScanLefMR[A](input: Array[A], output: Array[A],
                    initElement: A, f: (A, A) => A): Unit = {
  val fi = (i: Int, value: A) => reduceSegment(input, 0, i, initElement, f)
  mapSegment(input, output, 0, input.length, fi)
  val lastIndex = input.length - 1
  output(lastIndex + 1) = f(output(lastIndex), input(lastIndex))
}

input = Array(1, 2, 3, 4, 5)
output = Array(0, 0, 0, 0, 0, 0)
seqScanLefMR[Int](input, output, 100, (x, y) => x + y)
output

// ************************************************************ //
sealed abstract class Tree[A] {
  override def toString: String = this match {
    case Leaf(x) => x.toString
    case Node(left, right) => left.toString + " - " + right.toString
  }
}

case class Leaf[A](value: A) extends Tree[A]

case class Node[A](left: Tree[A], right: Tree[A]) extends Tree[A]

// ************************************************************ //
sealed abstract class TreeResults[A] {
  val result: A

  override def toString: String = this match {
    case LeafResult(x) => x.toString
    case NodeResult(left, x, right) => "(" + left.toString + " <-  " + x.toString + " -> " + right.toString + ")"
  }
}

case class LeafResult[A](override val result: A) extends TreeResults[A]

case class NodeResult[A](left: TreeResults[A],
                         override val result: A,
                         right: TreeResults[A]) extends TreeResults[A]

// ************************************************************ //

def reduceResults[A](tree: Tree[A], f: (A, A) => A): TreeResults[A] = tree match {
  case Leaf(x) => LeafResult(x)
  case Node(left, right) => {
    val (leftR, rightR) = (reduceResults(left, f), reduceResults(right, f))
    NodeResult(leftR, f(leftR.result, rightR.result), rightR)
  }
}

val tree: Tree[Int] = Node(Node(Leaf(1), Leaf(3)), Node(Leaf(8), Leaf(50)))
val reduceTree = reduceResults(tree, (x: Int, y: Int) => x + y)

def parallel[A, B](taskA: => A, taskB: => B): (A, B) = {
  val ta = taskA
  val tb = taskB
  (ta, tb)
}

def upsweep[A](tree: Tree[A], f: (A, A) => A): TreeResults[A] = tree match {
  case Leaf(x) => LeafResult(x)
  case Node(left, right) => {
    val (leftR, rightR) = parallel(upsweep(left, f), upsweep(right, f))
    NodeResult(leftR, f(leftR.result, rightR.result), rightR)
  }
}

upsweep(tree, (x: Int, y: Int) => x + y)

def downsweep[A](treeResults: TreeResults[A], initValue: A,
                 f: (A, A) => A): Tree[A] = treeResults match {
  case LeafResult(x) => Leaf(f(x, initValue))
  case NodeResult(leftR, value, rightR) => {
    val (left, right) = parallel(
      downsweep(leftR, initValue, f),
      downsweep(rightR, f(leftR.result, initValue), f)
    )
    Node(left, right)
  }
}

def prepend[A](tree: Tree[A], initValue: A): Tree[A] = tree match {
  case Leaf(x) => Node(Leaf(initValue), Leaf(x))
  case Node(left, right) => Node(prepend(left, initValue), right)
}

def scanLeft[A](tree: Tree[A], initValue: A, f: (A, A) => A): Tree[A] = {
  prepend(downsweep(upsweep(tree, f), initValue, f), initValue)
}

scanLeft(tree, 100, (x: Int, y: Int) => x + y)

// On arrays
