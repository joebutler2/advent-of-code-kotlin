import java.util.Stack

interface IOObject {
    fun getBaseName(): String
    fun calculateSize(): Int
}

// Composite
class Dir : IOObject {
    private var children: MutableList<IOObject> = mutableListOf()
    var name: String

    constructor(name: String) {
        this.name = name
    }

    override fun getBaseName(): String = name
    override fun calculateSize(): Int = children.sumOf { it.calculateSize() }
    fun addChild(child: IOObject) = children.add(child)
    fun findChild(dirName: String): Dir =
        children.filterIsInstance<Dir>().first { it.name == dirName }
    fun allDirsUnder(size: Int): List<Dir> =
         allDirsUnder(mutableListOf(), size)

    fun allDirsUnder(matches: MutableList<Dir>, size: Int): List<Dir> {
        children.forEach {
            if (it is Dir) {
                if(it.calculateSize() < 100000) {
                    matches.add(it)
                }
                it.allDirsUnder(matches, size)
            }
        }
        return matches
    }
}

// Leaf
class File : IOObject {
    var size: Int = 0
    var name: String

    constructor(name: String) {
        this.name = name
    }

    override fun getBaseName(): String = name
    override fun calculateSize(): Int = size
}

val root = Dir("root")
val pathToWorkingDirectory: Stack<Dir> = Stack()

fun main() {
    val input = object {}.javaClass.getResourceAsStream("day-7.txt")?.bufferedReader()?.readLines()!!.iterator()
    input.next() // skip first line since we already start at the root
    pathToWorkingDirectory.push(root)
    while (input.hasNext()) {
        val line = input.next()
        println("Processing line: $line")
        val segments = line.split(" ")
        if (line.startsWith("$")) {
            if (line.contains("cd")) {
                changeDirectory(segments[2])
            }
        } else {
            parseListedDirectory(line)
        }
    }

    val matchingDirs = root.allDirsUnder(100000)
    val totalSize = matchingDirs.sumOf { it.calculateSize() }
    println("And the total is... ${totalSize}")
}

fun parseListedDirectory(line: String) {
    println("Processing listed dir: $line")
    val (label, name: String) = line.split(" ")
    if (label.startsWith("d")) {
        val dir = Dir(name)
        pathToWorkingDirectory.peek().addChild(dir)
    } else if (label[0].isDigit()) {
        val file = File(name)
        file.size = label.toInt()
        pathToWorkingDirectory.peek().addChild(file)
    }
}

private fun changeDirectory(path: String) {
    if (path == "..") {
        pathToWorkingDirectory.pop()
    } else {
        val newDir = pathToWorkingDirectory.peek().findChild(path)
        pathToWorkingDirectory.push(newDir)
    }
}

