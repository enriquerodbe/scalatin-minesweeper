package io.scalac.minesweeper.cli

import io.scalac.minesweeper.api.Coordinate

sealed trait Movement {
  def coordinate: Coordinate
}

object Movement {
  final case class Uncover(coordinate: Coordinate) extends Movement
  final case class Flag(coordinate: Coordinate) extends Movement
}
