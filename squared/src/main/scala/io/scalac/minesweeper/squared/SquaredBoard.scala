package io.scalac.minesweeper.squared

import io.scalac.minesweeper.api.*

class SquaredBoard(override val size: Int, _hasMine: Coordinate => Boolean)
    extends Board {
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
        previous.playerState(_coordinate) match {
          case PlayerState.Covered if _coordinate == coordinate =>
            PlayerState.Flagged
          case PlayerState.Flagged if _coordinate == coordinate =>
            PlayerState.Covered
          case previousState =>
            previousState
        }
    }

  override lazy val allCoordinates: Seq[SquaredCoordinate] = {
    val maxIndex = SquaredCoordinate.maxIndex(size) + 1
    (0 until size).map(n => SquaredCoordinate(n / maxIndex, n % maxIndex, size))
  }

  override def hasMine(coordinate: Coordinate): Boolean =
    _hasMine(coordinate)

  override def playerState(coordinate: Coordinate): PlayerState =
    PlayerState.Covered

  override def state: BoardState =
    BoardState.Playing

  override val show: String =
    allCoordinates
      .groupBy(_.x)
      .toSeq
      .sortBy(_._1)
      .map(_._2)
      .map(_.sortBy(_.y))
      .map(_.map(showCoordinate))
      .map(_.mkString(" | "))
      .mkString("\n")

  private def showCoordinate(coordinate: Coordinate): String =
    playerState(coordinate) match {
      case PlayerState.Covered   => "+"
      case PlayerState.Uncovered => coordinate.neighbors.count(hasMine).toString
      case PlayerState.Flagged   => "F"
    }
}
