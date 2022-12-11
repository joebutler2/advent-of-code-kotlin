data class Elf(val calories: MutableList<Int> = mutableListOf()) {
    fun totalCalories(): Int = calories.sum()
}

fun main(args: Array<String>) {
    val input = object {}.javaClass.getResourceAsStream("day-1.txt")?.bufferedReader()?.readLines()
    var current = Elf()
    val elves = mutableListOf(current)

    input?.forEach {
        if (it.isEmpty()) {
            current = Elf()
            elves.add(current)
            return@forEach
        }
        current.calories.add(Integer.parseInt(it))
    }
    val topElf = elves.maxBy { it.totalCalories() }
    println("And the top elf is... ${topElf.totalCalories()}")
}