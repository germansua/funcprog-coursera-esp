package recfun

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")

    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
    def pascal(c: Int, r: Int): Int = {
      if (c == 0 || c == r) 1
      else if (c > r) 0
      else pascal(c, r - 1) + pascal(c - 1, r - 1)
    }

  /**
   * Exercise 2
   */
    def balance(chars: List[Char]): Boolean = {

      def calcVal(c: Char): Int = if (c == '(') 1 else if (c == ')') -1 else 0

      def iter(chars: List[Char], acc: Int): Int = {
        if (acc < 0) return -1
        if (chars.size == 1) return calcVal(chars.head) + acc
        else return iter(chars.tail, calcVal(chars.head) + acc)
      }

      return iter(chars, 0) == 0
    }
  
  /**
   * Exercise 3
   */
    def countChange(money: Int, coins: List[Int]): Int = {
      if (money == 0) return 1
      if (money < 0) return 0
      if (coins.size == 0) return 0

      countChange(money - coins.head, coins) + countChange(money, coins.tail)
    }
  }