package io.scalac.minesweeper.squared

import io.scalac.minesweeper.api.Coordinate

final case class SquaredCoordinate(x: Int, y: Int, boardSize: Int)
    extends Coordinate {
  override lazy val neighbors: Seq[Coordinate] = {
    Seq(
      SquaredCoordinate.validated(x - 1, y - 1, boardSize),
      SquaredCoordinate.validated(x - 1, y, boardSize),
      SquaredCoordinate.validated(x - 1, y + 1, boardSize),
      SquaredCoordinate.validated(x, y - 1, boardSize),
      SquaredCoordinate.validated(x, y + 1, boardSize),
      SquaredCoordinate.validated(x + 1, y - 1, boardSize),
      SquaredCoordinate.validated(x + 1, y, boardSize),
      SquaredCoordinate.validated(x + 1, y + 1, boardSize)
    ).flatten
  }
}

object SquaredCoordinate {
  def validated(x: Int, y: Int, boardSize: Int): Option[SquaredCoordinate] = {
    val max = maxIndex(boardSize)
    if (x >= 0 && x <= max && y >= 0 && y <= max)
      Some(new SquaredCoordinate(x, y, boardSize))
    else
      None
  }

  def maxIndex(boardSize: Int): Int =
    math.sqrt(boardSize.toDouble).round.toInt - 1
}
