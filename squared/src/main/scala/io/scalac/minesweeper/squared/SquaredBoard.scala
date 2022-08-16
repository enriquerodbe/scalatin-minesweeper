package io.scalac.minesweeper.squared

import io.scalac.minesweeper.api.*

class SquaredBoard(size: Int, _hasMine: Coordinate => Boolean) extends Board {
  override def uncover(coordinate: Coordinate): Board =
    new SquaredBoard(size, _hasMine) {
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
        else if (won()) BoardState.Won
        else BoardState.Playing
    }

  private def won(): Boolean =
    allCoordinates
      .filter(!hasMine(_))
      .forall(playerState(_) == PlayerState.Uncovered)

  override def flag(coordinate: Coordinate): Board =
    new SquaredBoard(size, _hasMine) {
      override def playerState(_coordinate: Coordinate): PlayerState =
        if (_coordinate == coordinate) PlayerState.Flagged
        else SquaredBoard.this.playerState(_coordinate)
    }

  override def allCoordinates: Seq[Coordinate] =
    Seq.tabulate(size)(SquaredCoordinate(_, 0))

  override def hasMine(coordinate: Coordinate): Boolean =
    _hasMine(coordinate)

  override def playerState(coordinate: Coordinate): PlayerState =
    PlayerState.Covered

  override def state: BoardState =
    BoardState.Playing
}
