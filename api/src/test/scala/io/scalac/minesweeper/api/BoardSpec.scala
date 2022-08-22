package io.scalac.minesweeper.api

import munit.ScalaCheckSuite
import org.scalacheck.Prop.*

abstract class BoardSpec(factory: BoardFactory) extends ScalaCheckSuite {
  private val gen = new Generators(factory)
  import gen.*

  property("All cells should start covered") {
    forAll(boardGen) { board =>
      board.allCoordinates.foreach { coordinate =>
        assertEquals(board.playerState(coordinate), PlayerState.Covered)
      }
    }
  }

  property("Flagging should set coordinate as flagged") {
    forAll(boardGen) { board =>
      forAll(coordinateGen(board)) { coordinate =>
        val flaggedBoard = board.flag(coordinate)
        assertEquals(flaggedBoard.playerState(coordinate), PlayerState.Flagged)
      }
    }
  }

  property("Flagging one cell should not affect other cells") {
    forAll(boardGen) { board =>
      forAll(coordinateGen(board)) { coordinate =>
        val updatedBoard = board.flag(coordinate)
        updatedBoard.allCoordinates
          .filterNot(_ == coordinate)
          .foreach { otherCoordinate =>
            assertEquals(
              updatedBoard.playerState(otherCoordinate),
              board.playerState(otherCoordinate)
            )
          }
      }
    }
  }

  property("Uncovering any empty cell should set coordinate as uncovered") {
    forAll(boardGen(chanceOfMine = .1)) { board =>
      forAll(emptyCoordinateGen(board)) { coordinate =>
        assertEquals(
          board.uncover(coordinate).playerState(coordinate),
          PlayerState.Uncovered
        )
      }
    }
  }

  property("Uncovering some empty cell should keep state as Playing") {
    forAll(boardGen(chanceOfMine = .1)) { board =>
      emptyCoordinates(board).size > 1 ==>
        forAll(emptyCoordinateGen(board)) { coordinate =>
          assertEquals(board.uncover(coordinate).state, BoardState.Playing)
        }
    }
  }

  property("Board state should start as Playing") {
    forAll(boardGen) { board =>
      assertEquals(board.state, BoardState.Playing)
    }
  }

  property("Uncovering any cell which has mine should lose") {
    forAll(boardGen(chanceOfMine = .8)) { board =>
      forAll(minedCoordinateGen(board)) { coordinate =>
        assertEquals(board.uncover(coordinate).state, BoardState.Lost)
      }
    }
  }

  property("Uncovering all empty cells should win") {
    forAll(boardGen) { board =>
      emptyCoordinates(board).nonEmpty ==> {
        val finalState =
          emptyCoordinates(board)
            .foldLeft(board)(_ uncover _)
            .state

        assertEquals(finalState, BoardState.Won)
      }
    }
  }

  property("Uncovering flagged mine should not lose") {
    forAll(boardGen(chanceOfMine = .8)) { board =>
      forAll(minedCoordinateGen(board)) { coordinate =>
        assertEquals(
          board.flag(coordinate).uncover(coordinate).state,
          BoardState.Playing
        )
      }
    }
  }

  private def emptyCoordinates(board: Board): Seq[Coordinate] =
    board.allCoordinates.filterNot(board.hasMine)
}
