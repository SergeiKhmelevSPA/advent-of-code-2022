package org.adventofcode.day15

import org.scalatest.funsuite.AnyFunSuite

class Day15BTest extends AnyFunSuite {

  test("testMain") {
    assertResult(12630143363767L) {
      Day15B.main()
    }
  }
}
