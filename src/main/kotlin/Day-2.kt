enum class Move { ROCK, PAPER, SCISSORS }
val translations = mapOf(
    "A" to Move.ROCK,
    "X" to Move.ROCK,
    "B" to Move.PAPER,
    "Y" to Move.PAPER,
    "C" to Move.SCISSORS,
    "Z" to Move.SCISSORS,
)

fun main(args: Array<String>) {
    val input = object {}.javaClass.getResourceAsStream("day-2.txt")?.bufferedReader()?.readLines()
    var score = 0
    input?.forEach {
       val (opponent, me) = it.split(" ")
        score += calculateRoundScore(translations[opponent]!!, translations[me]!!)
    }
    println("And the score is... $score")
}

fun calculateRoundScore(opponent: Move, me: Move): Int {
    val base = when (me) {
        Move.ROCK -> 1
        Move.PAPER -> 2
        Move.SCISSORS -> 3
    }
    return base + calculateRoundPoints(opponent, me)
}

const val LOSE_POINTS = 0
const val TIE_POINTS = 3
const val WIN_POINTS = 6
private fun calculateRoundPoints(opponent: Move, me: Move): Int {
    var roundPoints = 0
    when (opponent) {
        Move.ROCK -> {
            roundPoints = when (me) {
                Move.ROCK -> TIE_POINTS
                Move.PAPER -> WIN_POINTS
                Move.SCISSORS -> LOSE_POINTS
            }
        }
        Move.PAPER -> {
            roundPoints = when (me) {
                Move.ROCK -> LOSE_POINTS
                Move.PAPER -> TIE_POINTS
                Move.SCISSORS -> WIN_POINTS
            }
        }
        Move.SCISSORS -> {
            roundPoints = when (me) {
                Move.ROCK -> WIN_POINTS
                Move.PAPER -> LOSE_POINTS
                Move.SCISSORS -> TIE_POINTS
            }
        }
    }
    return roundPoints
}
