package io.scalac.minesweeper.squared

import io.scalac.minesweeper.api.{Board, BoardSpec, Coordinate}

class SquaredBoardSpec extends BoardSpec(SquaredBoardFactory) {
  test("Should contain all coordinates") {
    val board = new SquaredBoard(9, _ => false)
    val coordinates = for {
      x <- 0 until 3
      y <- 0 until 3
    } yield SquaredCoordinate(x, y)

    board.allCoordinates.foreach(c => assert(coordinates.contains(c)))
    coordinates.foreach(c => assert(board.allCoordinates.contains(c)))
  }

  test("Should show correctly") {
    val size: Int = 9

    def hasMine(coordinate: Coordinate): Boolean =
      coordinate match {
        case SquaredCoordinate(x, _) if x == 0 => true
        case _                                 => false
      }

    val toUncover =
      Seq.tabulate(3)(SquaredCoordinate(1, _))
    val toFlag =
      Seq.tabulate(3)(SquaredCoordinate(0, _))

    val initialBoard: Board = new SquaredBoard(size, hasMine)

    val uncovered =
      toUncover.foldLeft(initialBoard)(_ uncover _)

    val flagged =
      toFlag.foldLeft(uncovered)(_ flag _)

    val expected =
      """F | F | F
        |2 | 3 | 2
        |+ | + | +
        |""".stripMargin

    assertNoDiff(flagged.show, expected)
  }
}
