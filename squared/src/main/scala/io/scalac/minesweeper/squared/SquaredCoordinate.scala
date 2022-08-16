package io.scalac.minesweeper.squared

import io.scalac.minesweeper.api.Coordinate

final case class SquaredCoordinate(x: Int, y: Int) extends Coordinate {
  override val neighbors: Seq[Coordinate] =
    Seq.tabulate(x)(SquaredCoordinate(_, 0))
}
