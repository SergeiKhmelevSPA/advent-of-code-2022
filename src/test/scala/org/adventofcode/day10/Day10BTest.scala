package org.adventofcode.day10

import org.scalatest.funsuite.AnyFunSuite

class Day10BTest extends AnyFunSuite {

  test("testMain") {
    assertResult(
      """####.#..#.###..#..#.####.###..#..#.####.
        |#....#.#..#..#.#..#.#....#..#.#..#....#.
        |###..##...#..#.####.###..#..#.#..#...#..
        |#....#.#..###..#..#.#....###..#..#..#...
        |#....#.#..#.#..#..#.#....#....#..#.#....
        |####.#..#.#..#.#..#.####.#.....##..####.
        |""".stripMargin) {
      Day10B.main()
    }
  }
}
