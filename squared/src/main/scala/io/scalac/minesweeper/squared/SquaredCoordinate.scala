package io.scalac.minesweeper.squared

import io.scalac.minesweeper.api.Coordinate

final case class SquaredCoordinate(x: Int, y: Int, boardSize: Int)
    extends Coordinate {

  private val lastIndex = boardSize - 1

  override lazy val neighbors: Seq[Coordinate] =
    Seq(
      Option.when(x > 0 && y > 0)(
        SquaredCoordinate(x - 1, y - 1, boardSize)
      ),
      Option.when(x > 0)(
        SquaredCoordinate(x - 1, y, boardSize)
      ),
      Option.when(x > 0 && y < lastIndex)(
        SquaredCoordinate(x - 1, y + 1, boardSize)
      ),
      Option.when(y > 0)(
        SquaredCoordinate(x, y - 1, boardSize)
      ),
      Option.when(y < lastIndex)(
        SquaredCoordinate(x, y + 1, boardSize)
      ),
      Option.when(x < lastIndex && y > 0)(
        SquaredCoordinate(x + 1, y - 1, boardSize)
      ),
      Option.when(x < lastIndex)(
        SquaredCoordinate(x + 1, y, boardSize)
      ),
      Option.when(x < lastIndex && y < lastIndex)(
        SquaredCoordinate(x + 1, y + 1, boardSize)
      )
    ).flatten
}
