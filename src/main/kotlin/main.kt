import java.util.*
import kotlin.math.pow
import kotlin.random.Random
import kotlin.time.ExperimentalTime

@ExperimentalTime
fun main(args: Array<String>) {
    val edgeSize = 15
    val seed = generateSeed(edgeSize.toDouble().pow(2.0).toInt())
    var board: Array<IntArray> = IntArray(edgeSize)
        .map { IntArray(edgeSize) { seed.pop() } }
        .toTypedArray()

    println("Start")
    renderBoard(board)
    println()

    var tick = 1
    while (true) {
        board = World.fromBoard(board)
            .life()
            .toBoard()

        println("Tick nr: ${tick++}")
        renderBoard(board)
        println()

        Thread.sleep(500)
    }
}

private fun generateSeed(length: Int): Stack<Int> {
    val rand = Random
    val seed = Stack<Int>()
    seed.addAll(IntArray(length) { rand.nextInt(0, 2) }.toTypedArray())
    return seed
}

private fun renderBoard(board: Array<IntArray>) {
    board.map { line -> println(line.joinToString(" ")) }
}

