import java.util.*
import kotlin.math.pow
import kotlin.random.Random
import kotlin.time.ExperimentalTime

@ExperimentalTime
fun main(args: Array<String>) {
    val max = 10
    val seed = generateSeed()
    var board: Array<IntArray> = IntArray(max)
        .map { IntArray(max) { seed.pop() } }
        .toTypedArray()

    var tick = 1
    while (true) {
        board = tick(board)

        renderBoard(tick++, board)

        Thread.sleep(500)
    }
}

private fun generateSeed(): Stack<Int> {
    val rand = Random
    val seed = Stack<Int>()
    seed.addAll(IntArray(10.0.pow(2.0).toInt()) { rand.nextInt(0, 2) }.toTypedArray())
    return seed
}

private fun renderBoard(tick: Int, board: Array<IntArray>) {
    println("Tick nr: $tick")
    board.map { line -> println(line.joinToString(".")) }
    println()
}

fun tick(board: Array<IntArray>): Array<IntArray> {
    val world = convertToWorld(board)
    return convertToBoard(world.life())
}

class Cell(val status: String) {

}

class World(var matrix: List<List<Cell>>) {
    fun life(): World {
        return this
    }
}

fun convertToBoard(world: World): Array<IntArray> =
    (0..world.matrix.size-1)
        .map { x ->
            (0..world.matrix.size-1) .map { y: Int ->
                if (world.matrix[x][y].status == "life") 1 else 0
            }.toIntArray()
        }.toTypedArray()

fun convertToWorld(board: Array<IntArray>): World =
    World(board.map { x -> x.map { y -> Cell(if (y == 1) "life" else "death") } })