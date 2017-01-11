package io.dazraf

class App {
  companion object {
    @JvmStatic fun main(vararg args: String) {
      val reviews = "reviewers-and-reviewees.txt".parseFile()
      println(reviews)
    }
  }
}
