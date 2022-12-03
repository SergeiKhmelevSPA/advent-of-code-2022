package org.adventofcode.day1

import org.scalatest.funsuite.AnyFunSuite

class Day1ATest extends AnyFunSuite {

  test("testMain") {
    assertResult(75501) {
      Day1A.main()
    }
  }
}
