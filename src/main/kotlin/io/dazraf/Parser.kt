package io.dazraf

import java.io.File

object Parser {
  private val re = Regex("^(.+) reviews (.+)$")

  fun parse(lines: List<String>): Reviews {
    val pairs = lines
      .mapIndexed({ index, line -> parseLine(index, line) })
      .filterNotNull()
      .toList()
    return Reviews(pairs)
  }

  private fun parseLine(index: Int, line: String): Pair<String, String>? {
    val mr = re.matchEntire(line)?.groups
    if (mr?.size != 3) {
      println("line $index does not conform to format: $line")
      return null
    }
    return mr!![1]!!.value to mr[2]!!.value
  }
}

fun String.parseFile(): Reviews = File(this).parseFile()
fun File.parseFile(): Reviews = Parser.parse(readLines())


