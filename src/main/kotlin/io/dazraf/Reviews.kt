package io.dazraf

/**
 * A simple class to hold a representation of the specialised graph for reviewers and reviewees
 * Validates
 * Can dump graph to string
 */
class Reviews(private val pairs: Iterable<Pair<String, String>>) {

  // build a map of all reviewers to a list of their respective reviewees ...
  private val reviews = pairs.groupByTo(mutableMapOf(), { it.first }, { it.second })

  // build a set of nodes we can reach and ...
  private val reachable = pairs.map { it.second }.distinct().toHashSet()

  // ... use these to identify those we can't reach
  private val unreachable = pairs.filter { !reachable.contains(it.first) }.map { it.first }.distinct()

  init { validate() }

  // -- to string --

  override fun toString(): String {
    val sb = StringBuilder()
    unreachable.forEach { sb.print(prefix = "", name = it, isLast = true) }
    return sb.toString()
  }

  private fun StringBuilder.print(prefix: String, name: String, isLast: Boolean) {
    appendln(prefix + (if (isLast) "└─ " else "├─ ") + name)
    val children = reviews[name]
    children?.forEachIndexed { index, child ->
      print(prefix + (if (isLast) "   " else "│  "), child, index == children.size - 1)
    }
  }

  // -- validation logic --

  private fun validate() {
    // we need at least one entry point
    validate_entryPoints()

    // check that each reviewee has one and only one reviewer
    validate_onReviewerPerReviewee()

    // depth-first search
    validate_dag()
  }

  private fun validate_onReviewerPerReviewee() {
    pairs.groupByTo(mutableMapOf(), { it.second }, { it.first }).forEach {
      if (it.value.size > 1) {
        throw IllegalStateException("${it.key} is set to be reviewed by ${it.value}")
      }
    }
  }

  private fun validate_entryPoints() {
    if (unreachable.count() == 0) {
      throw IllegalStateException("no unreachable nodes - graph is either empty or cyclic")
    }
  }

  private fun validate_dag() {
    val visited = mutableSetOf<String>()
    unreachable.forEach { validate_dag(it, visited) }
  }

  private fun validate_dag(name: String, visited: MutableSet<String>) {
    if (visited.contains(name)) {
      throw IllegalStateException("cycle found with coder $name")
    }
    reviews[name]?.forEach { validate_dag(it, visited) }
  }
}
