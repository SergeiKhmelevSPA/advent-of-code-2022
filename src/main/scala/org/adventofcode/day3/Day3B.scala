package org.adventofcode.day3

import org.adventofcode.ResourceLoader

object Day3B {

  def main(args: Array[String]): Unit = {
    val result = ResourceLoader.processLineByLine(
      "day3.txt",
      _.grouped(3)
        .map(calc)
        .sum
    )

    println(result)
    assert(result == 8493)
  }

  private def calc(line: Seq[String]): Int = {
    val commonItem = line.reduce((a, b) => a intersect b)

    commonItem.charAt(0).priority()
  }
}
