package io.dazraf

import org.junit.Test

class Tests {

  @Test
  fun testHappyPath() {
    val str = Reviews(listOf(
      "a" to "b",
      "b" to "c",
      "b" to "d",
      "e" to "f"
    )).toString()
  }

  @Test(expected = CycleDetectedException::class)
  fun testThatEmptySetFails() {
    Reviews(listOf())
  }

  @Test(expected = CycleDetectedException::class)
  fun testThatCycleIsDetected() {
    Reviews(listOf(
      "a" to "b",
      "b" to "c",
      "c" to "a"
    ))
  }

  @Test(expected = IllegalStateException::class)
  fun testThatEachRevieweeHasOnlyOneReviewer() {
    Reviews(listOf(
      "a" to "c",
      "b" to "c"
    ))
  }

}
