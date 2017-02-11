val xs = Array(1, 2, 3, 44)
xs map(x => x * 2)

val s = "Hello World"
s filter(c => c.isUpper)
s exists(c => c.isUpper)
s forall(c => c.isUpper)

val pairs = List(1, 2, 3) zip s
pairs.unzip

s flatMap(c => List('.', c))


def isPrime(n: Int): Boolean = (2 until n) forall(i => n % i != 0)
isPrime(2)
isPrime(3)
isPrime(4)
isPrime(5)
isPrime(6)
isPrime(7)
isPrime(8)
isPrime(9)
