package io.scalac.minesweeper.api

sealed trait PlayerState

object PlayerState {
  case object Covered extends PlayerState
  case object Uncovered extends PlayerState
  case object Flagged extends PlayerState
}
