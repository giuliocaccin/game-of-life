import Cell.Alive
import Cell.Dead

sealed class Cell {
    object Alive : Cell()
    object Dead : Cell()
}

class World private constructor(private val matrix: List<List<Block>>) {
    fun life(): World {
        return World(matrix.map { row ->
            row.map { block ->
                val current = block.cell
                val aliveNeighbours = block.neighborArea
                    .map { position -> matrix[position.x][position.y] }
                    .map { neighbour -> neighbour.cell }
                    .count { cell -> cell === Alive }
                if (current === Alive && aliveNeighbours < 2)
                    block.copy(cell = Dead)
                else if (current === Alive && (aliveNeighbours == 2 || aliveNeighbours == 3))
                    block.copy(cell = Alive)
                else if (current === Alive && aliveNeighbours > 3)
                    block.copy(cell = Dead)
                else if (current === Dead && aliveNeighbours == 3)
                    block.copy(cell = Alive)
                else if(current === Dead)
                    block.copy(cell = Dead)
                else
                    throw IllegalStateException()
            }
        })
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
                    .mapIndexed { y, value -> (if (value == 1) Alive else Dead) to Position(x, y) }
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