package week1.lecture_1_4

class Account2(private val id: Int, private var amount: Int = 0) {

  private def lockAndTransfer(target: Account2, n: Int) = {
    this.synchronized {
      target.synchronized {
        this.amount -= n
        target.amount += n
      }
    }
  }

  def transfer(target: Account2, n: Int) = {
    if (this.id < target.id) this.lockAndTransfer(target, n)
    else target.lockAndTransfer(this, -n)
  }
}

object Main2 extends App {

  val a1 = new Account2(1)
  val a2 = new Account2(2)

  val t = startThread(a1, a2, 150000)
  val s = startThread(a2, a1, 150000)

  t.join()
  s.join()

  def startThread(a: Account2, b: Account2, n: Int): Thread = {
    val t = new Thread {
      override def run(): Unit = {
        for (i <- 0 until n) a.transfer(b, 1)
      }
    }
    t.start()
    t
  }
}


