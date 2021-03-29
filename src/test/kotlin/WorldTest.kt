import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

internal class WorldTest {
    @Test
    fun fromBoardToBoardIsTheSame() {
        val expected = arrayOf(intArrayOf(0, 1), intArrayOf(1, 0))
        val sut = World.fromBoard(expected)
        assertTrue { sut.toBoard().contentDeepEquals(expected) }
    }

    @Test
    fun getNeighboursHandleTopLeft() {
        val matrix = arrayOf(
            intArrayOf(0, 1, 0),
            intArrayOf(0, 1, 0),
            intArrayOf(1, 0, 0)
        )
        assertTrue(
            getNeighbourArea(Position(0, 0), matrix).containsAll(setOf(
                Position(2, 2),
                Position(0, 2),
                Position(1, 2),
                Position(2, 0),
                Position(1, 0),
                Position(2, 1),
                Position(0, 1),
                Position(2, 1),
            )))
    }

    @Test
    fun getNeighboursHandleBottomRight() {
        val matrix = arrayOf(
            intArrayOf(0, 1, 0),
            intArrayOf(0, 1, 0),
            intArrayOf(1, 0, 0)
        )
        assertTrue(
            getNeighbourArea(Position(2, 2), matrix).containsAll(setOf(
                Position(1, 1),
                Position(2, 1),
                Position(0, 1),
                Position(1, 2),
                Position(0, 2),
                Position(1, 0),
                Position(2, 0),
                Position(0, 0),
            )))
    }

    @Test
    fun worldSupportBlinkerOscillator() {
        val matrix = arrayOf(
            intArrayOf(0, 0, 0, 0, 0),
            intArrayOf(0, 0, 1, 0, 0),
            intArrayOf(0, 0, 1, 0, 0),
            intArrayOf(0, 0, 1, 0, 0),
            intArrayOf(0, 0, 0, 0, 0)
        )
        assertTrue(World.fromBoard(matrix).life().life().toBoard().contentDeepEquals(matrix))
    }
}
