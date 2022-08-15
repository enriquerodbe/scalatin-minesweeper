package io.scalac.minesweeper.api

trait Coordinate {
  def neighbors: Seq[Coordinate]
}
