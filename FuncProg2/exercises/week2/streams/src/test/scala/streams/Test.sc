
case class Pos(row: Int, col: Int) {
  def deltaRow(d: Int): Pos = copy(row = row + d)

  def deltaCol(d: Int): Pos = copy(col = col + d)
}

val table = Vector(Vector('S', 'T'), Vector('o', '-'), Vector('o', 'o'))

val pos: Pos = Pos(1, 5)
def terrainFunction(levelVector: Vector[Vector[Char]]): Pos => Boolean = ???

val validPositions =
  for {
    i <- 0 to table.length - 1
    j <- 0 to table(i).length - 1
  } yield (i, j)

val valid = validPositions.contains(pos.row, pos.col) && table.apply(pos.row).apply(pos.col) != '-'


def findChar(c: Char, levelVector: Vector[Vector[Char]]): Pos = {
  val row = levelVector.indexWhere(inner => inner.indexOf(c) > -1)
  if (row > -1) {
    val col = levelVector(row).indexOf(c); Pos(row, col)
  } else Pos(-1, -1)
}

findChar('S', table)

