package io.scalac.minesweeper.cli

sealed trait Movement

object Movement {
  case object Uncover extends Movement
  case object Flag extends Movement
}
