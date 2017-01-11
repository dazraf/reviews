package io.dazraf

/**
 * A simple class to walk the list of reviews and print them to stdout
 */
class Reviews(private val pairs: Iterable<Pair<String, String>>) {

  // build a map of all reviewers to a list of their respective reviewees ...
  private val reviews = pairs.groupByTo(mutableMapOf(), { it.first }, { it.second })

  // and those that are not being reviewed ...
  private val unreachable: Iterable<String>

  /**
   * explicit constructor because we have some work to do
   */
  init {
    // build a set of nodes we can reach and ...
    val reachable = pairs.map { it.second }.distinct().toHashSet()

    // ... use these to identify those we can't reach
    unreachable = pairs.filter { !reachable.contains(it.first) }.map { it.first }.distinct()
    validate()
  }

  override fun toString(): String {
    val sb = StringBuilder()
    unreachable.forEach { sb.print(prefix = "", name = it, tail = true) }
    return sb.toString()
  }

  @Throws(CycleDetectedException::class, EmptyException::class, IllegalStateException::class)
  private fun validate() {
    // we need at least one entry point
    if (unreachable.count() == 0) {
      throw EmptyException()
    }

    // check that each reviewee has one and only one reviewer
    pairs.groupByTo(mutableMapOf(), { it.second }, { it.first }).forEach {
      if (it.value.size > 1) {
        throw IllegalStateException("${it.key} is set to be reviewed by ${it.value}")
      }
    }

    // DFS
    val visited = mutableSetOf<String>()
    unreachable.forEach {
      validate(it, visited)
    }
  }

  private fun validate(name: String, visited: MutableSet<String>) {
    if (visited.contains(name)) {
      throw CycleDetectedException("cycle found with coder $name")
    }
    reviews[name]?.forEach { validate(it, visited) }
  }


  private fun StringBuilder.print(prefix: String, name: String, tail: Boolean) {
    appendln(prefix + (if (tail) "└── " else "├── ") + name)

    val children = reviews[name]

    children?.forEachIndexed { index, child ->
      print(prefix + (if (tail) "    " else "│   "), child, index == children.size - 1)
    }
  }
}

class CycleDetectedException(msg: String) : IllegalStateException(msg)
class EmptyException : IllegalStateException("no entry points into the graph - graph is cyclic or empty")