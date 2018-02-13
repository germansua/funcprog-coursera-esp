package week1.lecture_1_4

class Account(private var amount: Int = 0) {
  def transfer(target: Account, n: Int) = {
    this.synchronized {
      target.synchronized {
        this.amount -= n
        target.amount += n
      }
    }
  }
}

object Main extends App {

  val a1 = new Account()
  val a2 = new Account()

  val t = startThread(a1, a2, 150000)
  val s = startThread(a2, a1, 150000)

  t.join()
  s.join()

  def startThread(a: Account, b: Account, n: Int): Thread = {
    val t = new Thread {
      override def run(): Unit = {
        for (i <- 0 until n) a.transfer(b, 1)
      }
    }
    t.start()
    t
  }
}


