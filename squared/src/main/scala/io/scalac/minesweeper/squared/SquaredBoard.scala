package io.scalac.minesweeper.squared

import io.scalac.minesweeper.api.*

class SquaredBoard extends Board {
  override def uncover(coordinate: Coordinate): Board =
    new SquaredBoard {
      override def playerState(_coordinate: Coordinate): PlayerState =
        if (SquaredBoard.this.playerState(_coordinate) == PlayerState.Flagged)
          PlayerState.Flagged
        else
          PlayerState.Uncovered

      override def state: BoardState =
        if (hasMine(coordinate))
          if (playerState(coordinate) == PlayerState.Flagged)
            SquaredBoard.this.state
          else
            BoardState.Lost
        else
          BoardState.Won
    }

  override def flag(coordinate: Coordinate): Board =
    new SquaredBoard {
      override def playerState(_coordinate: Coordinate): PlayerState =
        PlayerState.Flagged
    }

  override def allCoordinates: Seq[Coordinate] =
    Seq.tabulate(5)(SquaredCoordinate(_, 0))

  override def hasMine(coordinate: Coordinate): Boolean =
    coordinate.neighbors.size % 2 == 0

  override def playerState(coordinate: Coordinate): PlayerState =
    PlayerState.Covered

  override def state: BoardState =
    BoardState.Playing
}
