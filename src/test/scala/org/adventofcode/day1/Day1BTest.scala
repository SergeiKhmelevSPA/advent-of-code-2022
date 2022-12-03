package org.adventofcode.day1

import org.scalatest.funsuite.AnyFunSuite

class Day1BTest extends AnyFunSuite {

  test("testMain") {
    assertResult(215594) {
      Day1B.main()
    }
  }
}
