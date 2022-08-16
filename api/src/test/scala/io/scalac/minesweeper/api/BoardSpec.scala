package io.scalac.minesweeper.api

import munit.FunSuite

abstract class BoardSpec(factory: BoardFactory) extends FunSuite {
  test("All cells should start covered") {
    val board = createBoard()

    board.allCoordinates
      .map(board.playerState)
      .foreach(assertEquals(_, PlayerState.Covered))
  }

  test("Flagging should set coordinate as flagged") {
    val board = createBoard()

    board.allCoordinates
      .map(coordinate => board.flag(coordinate).playerState(coordinate))
      .foreach(assertEquals(_, PlayerState.Flagged))
  }

  test("Flagging one cell should not affect other cells") {
    val board = createBoard()

    board.allCoordinates
      .map(coordinate => (coordinate, board.flag(coordinate)))
      .foreach { case (coordinate, updatedBoard) =>
        updatedBoard.allCoordinates
          .filterNot(_ == coordinate)
          .foreach { c =>
            assertEquals(updatedBoard.playerState(c), board.playerState(c))
          }
      }
  }

  test("Uncovering any empty cell should set coordinate as uncovered") {
    val board = createBoard()

    coordinatesWithoutMine(board)
      .map(coordinate => board.uncover(coordinate).playerState(coordinate))
      .foreach(assertEquals(_, PlayerState.Uncovered))
  }

  test("Uncovering some empty cell should keep state as Playing") {
    val board = createBoard()

    coordinatesWithoutMine(board)
      .map(coordinate => board.uncover(coordinate).state)
      .foreach(assertEquals(_, BoardState.Playing))
  }

  test("Board state should start as Playing") {
    val board = createBoard()

    assertEquals(board.state, BoardState.Playing)
  }

  test("Uncovering any cell which has mine should lose") {
    val board = createBoard()

    coordinatesWithMine(board)
      .map(coordinate => board.uncover(coordinate).state)
      .foreach(assertEquals(_, BoardState.Lost))
  }

  test("Uncovering all empty cells should win") {
    val board = createBoard()

    val finalState =
      coordinatesWithoutMine(board)
        .foldLeft(board)(_ uncover _)
        .state

    assertEquals(finalState, BoardState.Won)
  }

  test("Uncovering flagged mine should not lose") {
    val board = createBoard()

    coordinatesWithMine(board)
      .map(coordinate => board.flag(coordinate).uncover(coordinate).state)
      .foreach(assertEquals(_, BoardState.Playing))
  }

  private def createBoard(): Board =
    factory.create(5, isEven)

  private def isEven(coordinate: Coordinate): Boolean =
    coordinate.neighbors.size % 2 == 0

  private def coordinatesWithMine(board: Board): Seq[Coordinate] =
    filterCoordinates(board, board.hasMine)

  private def coordinatesWithoutMine(board: Board): Seq[Coordinate] =
    filterCoordinates(board, !board.hasMine(_))

  private def filterCoordinates(
      board: Board,
      predicate: Coordinate => Boolean
  ): Seq[Coordinate] =
    board.allCoordinates.filter(predicate)
}
