package io.scalac.minesweeper.squared

import io.scalac.minesweeper.api.*

class SquaredBoard(size: Int, _hasMine: Coordinate => Boolean) extends Board {
  previous =>
  override def uncover(coordinate: Coordinate): Board =
    new SquaredBoard(size, _hasMine) {
      override def playerState(_coordinate: Coordinate): PlayerState =
        if (_coordinate == coordinate && !previous.isFlagged(_coordinate))
          PlayerState.Uncovered
        else
          previous.playerState(_coordinate)

      override def state: BoardState =
        if (hasMine(coordinate))
          if (playerState(coordinate) == PlayerState.Flagged) previous.state
          else BoardState.Lost
        else if (won()) BoardState.Won
        else BoardState.Playing

      private def won(): Boolean =
        allCoordinates
          .filterNot(hasMine)
          .forall(playerState(_) == PlayerState.Uncovered)
    }

  private def isFlagged(coordinate: Coordinate): Boolean =
    playerState(coordinate) == PlayerState.Flagged

  override def flag(coordinate: Coordinate): Board =
    new SquaredBoard(size, _hasMine) {
      override def playerState(_coordinate: Coordinate): PlayerState =
        if (_coordinate == coordinate) PlayerState.Flagged
        else previous.playerState(_coordinate)
    }

  override lazy val allCoordinates: Seq[SquaredCoordinate] = {
    val sqrt = math.sqrt(size.toDouble).round.toInt
    (0 until size).map(n => SquaredCoordinate(n / sqrt, n % sqrt, size))
  }

  override def hasMine(coordinate: Coordinate): Boolean =
    _hasMine(coordinate)

  override def playerState(coordinate: Coordinate): PlayerState =
    PlayerState.Covered

  override def state: BoardState =
    BoardState.Playing
}
