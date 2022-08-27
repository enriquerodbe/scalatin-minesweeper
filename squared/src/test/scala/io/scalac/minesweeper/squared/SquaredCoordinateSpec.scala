package io.scalac.minesweeper.squared

import munit.FunSuite

class SquaredCoordinateSpec extends FunSuite {
  private val boardSize = 9
  private val lastIndex = SquaredCoordinate.maxIndex(boardSize)
  private val extremes = Seq(0, lastIndex)
  private val middle = 1 until lastIndex

  test("Corners should have 3 neighbors") {
    product(extremes, extremes)
      .map(_.neighbors.size)
      .foreach(assertEquals(_, 3))
  }

  test("Borders should have 5 neighbors") {
    (product(middle, extremes) ++ product(extremes, middle))
      .map(_.neighbors.size)
      .foreach(assertEquals(_, 5))
  }

  test("Internal should have 8 neighbors") {
    product(middle, middle)
      .map(_.neighbors.size)
      .foreach(assertEquals(_, 8))
  }

  private def product(xs: Seq[Int], ys: Seq[Int]): Seq[SquaredCoordinate] =
    for {
      x <- xs
      y <- ys
    } yield SquaredCoordinate(x, y, boardSize)
}
