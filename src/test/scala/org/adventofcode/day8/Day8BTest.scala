package org.adventofcode.day8

import org.scalatest.funsuite.AnyFunSuite

class Day8BTest extends AnyFunSuite {

  test("testMain") {
    assertResult(405769) {
      Day8B.main()
    }
  }
}
