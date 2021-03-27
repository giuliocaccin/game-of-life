import kotlin.random.Random
import kotlin.time.ExperimentalTime

@ExperimentalTime
fun main(args: Array<String>) {
    val edgeSize = 100
    val rand = Random
    var board: Array<IntArray> = IntArray(edgeSize)
        .map { IntArray(edgeSize) { rand.nextInt(0, 2) } }
        .toTypedArray()

    println("Seed")
    renderBoard(board)

    var tick = 1
    while (true) {
        board = World.fromBoard(board)
            .life()
            .toBoard()

        println("Tick nr: ${tick++}")
        renderBoard(board)

        Thread.sleep(500)
    }
}

private fun renderBoard(board: Array<IntArray>) {
    board.map { line -> println(line.joinToString("") { i -> if (i == 1) "█" else " " }) }
    println()
}

