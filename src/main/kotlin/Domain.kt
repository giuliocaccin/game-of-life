class Cell(val status: String) {

}

class World(var matrix: List<List<Cell>>) {
    fun life(): World {
        return this
    }
}