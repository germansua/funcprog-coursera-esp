trait Expression {
  def eval: Int = this match {
    case Number(n) => n
    case Sum(e1, e2) => e1.eval + e2.eval
  }

  def show: String = this match {
    case Number(n) => n.toString
    case Sum(e1, e2) => e1.show + " + " + e2.show
  }
}

case class Number(n: Int) extends Expression
case class Sum(e1: Expression, e2: Expression) extends Expression


Sum(Number(1), Number(2)).eval
Sum(Number(1), Number(2)).show