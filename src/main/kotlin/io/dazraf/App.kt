package io.dazraf

fun main(vararg args: String) {
  val reviews = "reviewers-and-reviewees.txt".parseFile()
  println(reviews)
}
