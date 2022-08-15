package io.scalac.minesweeper.squared

import io.scalac.minesweeper.api.*

class SquaredBoard extends Board {
  override def uncover(coordinate: Coordinate): Board =
    new SquaredBoard {
      override def playerState(_coordinate: Coordinate): PlayerState =
        PlayerState.Uncovered

      override def state: BoardState =
        BoardState.Won
    }

  override def flag(coordinate: Coordinate): Board =
    new SquaredBoard {
      override def playerState(_coordinate: Coordinate): PlayerState =
        PlayerState.Flagged
    }

  override def allCoordinates: Seq[Coordinate] =
    Seq(SquaredCoordinate(0, 0))

  override def hasMine(coordinate: Coordinate): Boolean =
    false

  override def playerState(coordinate: Coordinate): PlayerState =
    PlayerState.Covered

  override def state: BoardState =
    BoardState.Playing
}
