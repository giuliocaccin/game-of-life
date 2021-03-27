import Cell.Alive
import Cell.Dead

sealed class Cell {
    object Alive : Cell()
    object Dead : Cell()
}

class World private constructor(private val matrix: List<List<Block>>) {
    fun life(): World {
        (matrix.indices).map { x ->
            (matrix[x].indices).map { y ->
// TODO: implement rules
            }
        }
        return this
    }

    fun toBoard(): Array<IntArray> =
        matrix
            .map { row ->
                row.map { block ->
                    when (block.cell) {
                        is Dead -> 0
                        is Alive -> 1
                    }
                }.toIntArray()
            }.toTypedArray()

    companion object {
        fun fromBoard(board: Array<IntArray>): World =
            World(board.mapIndexed { x, row ->
                row
                    .map { y -> (if (y == 1) Alive else Dead) to Position(x, y) }
                    .map { pair -> Block(pair.first, getNeighbourArea(pair.second, board)) }
            })
    }
}


fun getNeighbourArea(center: Position, board: Array<IntArray>): Set<Position> =
    (center.x - 1..center.x + 1).flatMap { x ->
        (center.y - 1..center.y + 1).map { y -> Position(x, y) }
    }
        .asSequence()
        .map { position -> if (position.x < 0) position.copy(x = board.size - 1) else position }
        .map { position -> if (position.x >= board.size) position.copy(x = 0) else position }
        .map { position -> if (position.y < 0) position.copy(y = board[position.x].size - 1) else position }
        .map { position -> if (position.y >= board[position.x].size) position.copy(y = 0) else position }
        .filter { position -> position != center }
        .toSet()

data class Position(val x: Int, val y: Int)
data class Block(val cell: Cell, val neighborArea: Set<Position>)