sealed class Cell {
    object Alive : Cell()
    object Dead : Cell()
}

class World(var matrix: List<List<Cell>>) {
    fun life(): World {
        return this
    }
}