import Cell.Alive
import Cell.Dead

sealed class Cell {
    object Alive : Cell()
    object Dead : Cell()
}

class World private constructor(private val matrix: List<List<Block>>) {
    fun life(): World {
        return World(matrix.map { row ->
            row.map { current ->
                val aliveNeighbor = current.neighborArea
                    .map { position -> matrix[position.x][position.y] }
                    .map { neighbour -> neighbour.cell }
                    .count { cell -> cell === Alive }

                when (current.cell) {
                    is Alive -> {
                        when {
                            aliveNeighbor < 2 -> current.copy(cell = Dead)
                            aliveNeighbor > 3 -> current.copy(cell = Dead)
                            else -> current
                        }
                    }
                    is Dead -> {
                        when (aliveNeighbor) {
                            3 -> current.copy(cell = Alive)
                            else -> current
                        }
                    }
                }
            }
        }
        )
    }

    fun toBoard(): Array<IntArray> =
        matrix.map { row ->
            row.map {
                when (it.cell) {
                    is Dead -> 0
                    is Alive -> 1
                }
            }.toIntArray()
        }.toTypedArray()

    companion object {
        fun fromBoard(board: Array<IntArray>): World =
            World(board.mapIndexed { x, row ->
                row.mapIndexed { y, value -> (if (value == 1) Alive else Dead) to Position(x, y) }
                    .map { (cell, position) -> Block(cell, getNeighbourArea(position, board)) }
            })
    }
}


fun getNeighbourArea(center: Position, board: Array<IntArray>): Set<Position> =
    (center.x - 1..center.x + 1).flatMap { x ->
        (center.y - 1..center.y + 1).map { y -> Position(x, y) }
    }
        .map { position ->
            with(position) {
                when {
                    x < 0 -> copy(x = board.size - 1)
                    x >= board.size -> copy(x = 0)
                    else -> this
                }
            }
        }
        .map { position ->
            with(position) {
                when {
                    y < 0 -> copy(y = board[position.x].size - 1)
                    y >= board[position.x].size -> copy(y = 0)
                    else -> this
                }
            }
        }
        .filter { position -> position != center }
        .toSet()

data class Position(val x: Int, val y: Int)
data class Block(val cell: Cell, val neighborArea: Set<Position>)