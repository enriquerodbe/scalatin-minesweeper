package io.scalac.minesweeper.api

sealed trait PlayerState {
  def uncover: PlayerState
  def flag: PlayerState
}

object PlayerState {
  case object Covered extends PlayerState {
    override val uncover: PlayerState = Uncovered
    override val flag: PlayerState = Flagged
  }
  case object Uncovered extends PlayerState {
    override val uncover: PlayerState = Uncovered
    override val flag: PlayerState = Uncovered
  }
  case object Flagged extends PlayerState {
    override val uncover: PlayerState = Flagged
    override val flag: PlayerState = Covered
  }
}
