package io.scalac.minesweeper.api

import munit.FunSuite

abstract class BoardFactorySpec(create: () => BoardFactory) extends FunSuite {
  test("Should create board of given size") {
    val board = create().create(5, _ => false)
    assertEquals(board.allCoordinates.size, 5)
  }

  test("Should set mines on even number of neighbors") {
    val board = create().create(5, _.neighbors.size % 2 == 0)
    board.allCoordinates.foreach(c =>
      assertEquals(c.neighbors.size % 2 == 0, board.hasMine(c))
    )
  }

  test("Should set all mines") {
    val board = create().create(5, _ => true)
    board.allCoordinates.foreach(c => assert(board.hasMine(c)))
  }
}
