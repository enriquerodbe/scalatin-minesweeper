package io.scalac.minesweeper.squared

import io.scalac.minesweeper.api.*

class SquaredBoard(override val size: Int, _hasMine: Coordinate => Boolean)
    extends Board {
  previous =>
  override def uncover(coordinate: Coordinate): Board =
    new SquaredBoard(size, _hasMine) {
      override def playerState(_coordinate: Coordinate): PlayerState =
        if (_coordinate == coordinate) previous.playerState(coordinate).uncover
        else previous.playerState(_coordinate)

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

  override def flag(coordinate: Coordinate): Board =
    new SquaredBoard(size, _hasMine) {
      override def playerState(_coordinate: Coordinate): PlayerState =
        if (_coordinate == coordinate) previous.playerState(coordinate).flag
        else previous.playerState(_coordinate)
    }

  override lazy val allCoordinates: Seq[SquaredCoordinate] = {
    val maxIndex = SquaredCoordinate.maxIndex(size) + 1
    (0 until size).map(n => SquaredCoordinate(n / maxIndex, n % maxIndex))
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

  private def showCoordinate(coordinate: SquaredCoordinate): String =
    playerState(coordinate) match {
      case PlayerState.Covered => "+"
      case PlayerState.Uncovered =>
        neighbors(coordinate).count(hasMine).toString
      case PlayerState.Flagged => "F"
    }

  private def neighbors(coordinate: SquaredCoordinate): Seq[SquaredCoordinate] =
    Seq(
      SquaredCoordinate.validated(coordinate.x - 1, coordinate.y - 1, size),
      SquaredCoordinate.validated(coordinate.x - 1, coordinate.y, size),
      SquaredCoordinate.validated(coordinate.x - 1, coordinate.y + 1, size),
      SquaredCoordinate.validated(coordinate.x, coordinate.y - 1, size),
      SquaredCoordinate.validated(coordinate.x, coordinate.y + 1, size),
      SquaredCoordinate.validated(coordinate.x + 1, coordinate.y - 1, size),
      SquaredCoordinate.validated(coordinate.x + 1, coordinate.y, size),
      SquaredCoordinate.validated(coordinate.x + 1, coordinate.y + 1, size)
    ).flatten
}
