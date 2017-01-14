// Product
def product(f: Int => Int)(a: Int, b: Int): Int = {
  if (a > b) 1
  else f(a) * product(f)(a + 1, b)
}
println(product(x => x)(1, 6))


def factorial(n: Int): Int = {
  product(n => n)(2, n)
}
println(factorial(6))

// As generic operation
def operation(f: Int => Int)(a: Int, b: Int)(neutral: Int)(operator: (Int, Int) => Int): Int = {
  if (a > b) neutral
  else operator( f(a), operation(f)(a + 1, b)(neutral)(operator) )
}

println( operation(x => x)(1, 5)(0)( (x, y) => x + y) )
println( operation(x => x)(1, 5)(1)( (x, y) => x * y) )

def mapReduce(f: Int => Int, combine: (Int, Int) => Int, zero: Int)(a: Int, b: Int): Int = {
  if (a > b) zero
  else combine(f(a), mapReduce(f, combine, zero )(a + 1, b))
}

// Sum Func rewrite
//def sum(f: Int => Int)(a: Int, b: Int): Int = {
//  def loop(a: Int, acc: Int): Int = {
//    if (a > b) acc
//    else loop(a + 1, f(a) + acc)
//  }
//  loop(a, 0)
//}
//
//println(sum(x => x)(1, 9))

// Factorial Remainder
//
//def factorial(x: Int): Int = {
//  if (x <= 0) 1
//  else x * factorial(x - 1)
//}
//
//def factorialTail(x: Int): Int = {
//  def loop(x: Int, acc: Int): Int = {
//    if (x <= 0) acc
//    else loop(x - 1, x * acc)
//  }
//  loop(x, 1)
//}
//
//println(factorial(15))
//println(factorialTail(15))