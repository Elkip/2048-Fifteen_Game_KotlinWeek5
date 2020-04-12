package games.gameOfFifteen

import board.Cell
import board.Direction
import board.GameBoard
import board.createGameBoard
import games.game.Game

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game =
    GameOfFifteen(initializer)

class GameOfFifteen(private val init: GameOfFifteenInitializer): Game {
    private val board = createGameBoard<Int?>(4)

    override fun initialize() {
        val values = init.initialPermutation

        var k = 0
        for (i in 1..board.width) {
            for (j in 1..board.width) {
                val value = if (k < values.size) values[k++] else null
                board[board.getCell(i, j)] = value
            }
        }
    }

    override fun canMove(): Boolean = true

    override fun hasWon(): Boolean {
        var correct = 1

        for (i in 1..board.width) {
            for (j in 1..board.width) {
                if (get(i, j) != correct && correct != board.width * board.width) {
                    return false
                }
                correct++
            }
        }
        return true
    }

    override fun processMove(direction: Direction) {
        val nCell = getNull()
        when (direction) {
            Direction.UP -> {
                if (board.getCellOrNull(nCell.i + 1, nCell.j) != null) {
                    val t = board.get(board.getCell(nCell.i + 1, nCell.j))
                    board.set(nCell, t)
                    board.set(board.getCell(nCell.i + 1, nCell.j), null)
                }
            }
            Direction.DOWN -> {
                if (board.getCellOrNull(nCell.i - 1, nCell.j) != null) {
                    val t = board.get(board.getCell(nCell.i - 1, nCell.j))
                    board.set(nCell, t)
                    board.set(board.getCell(nCell.i - 1, nCell.j), null)
                }
            }
            Direction.LEFT -> {
                if (board.getCellOrNull(nCell.i, nCell.j + 1) != null) {
                    val t = board.get(board.getCell(nCell.i, nCell.j + 1))
                    board.set(nCell, t)
                    board.set(board.getCell(nCell.i, nCell.j + 1), null)
                }
            }
            Direction.RIGHT -> {
                if (board.getCellOrNull(nCell.i, nCell.j - 1) != null) {
                    val t = board.get(board.getCell(nCell.i, nCell.j - 1))
                    board.set(nCell, t)
                    board.set(board.getCell(nCell.i, nCell.j - 1), null)
                }
            }
        }
    }

    override fun get(i: Int, j: Int): Int? = board.get(board.getCell(i, j))

    private fun getNull(): Cell = board.getAllCells().find { get(it.i, it.j) == null }!!
}

