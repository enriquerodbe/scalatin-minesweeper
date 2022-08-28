package io.scalac.minesweeper.api

trait Board {
  def uncover(coordinate: Coordinate): Board

  def flag(coordinate: Coordinate): Board

  def allCoordinates: Seq[Coordinate]

  def hasMine(coordinate: Coordinate): Boolean

  def playerState(coordinate: Coordinate): PlayerState

  def state: BoardState

  def isPlaying: Boolean = state == BoardState.Playing

  def show: String

  def size: Int
}
