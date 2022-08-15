package io.scalac.minesweeper.api

sealed trait BoardState

object BoardState {
  case object Playing extends BoardState
  case object Lost extends BoardState
  case object Won extends BoardState
}
