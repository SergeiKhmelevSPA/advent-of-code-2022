package org.adventofcode.day3

import org.adventofcode.GetResource.using

import scala.io.Source

object Day3B {

  def main(args: Array[String]): Unit = {
    var result = 0

    using(Source.fromResource("day3.txt")) { source => {
      for (line <- source.getLines().grouped(3)) {
        result += calc(line)
      }
    }
    }

    println(result)
  }

  private def calc(line: Seq[String]): Int = {
    val commonItem = line.reduce((a, b) => a intersect b)

    Priority.calculate(commonItem.charAt(0))
  }

  private object Priority {
    private val map = (('a' to 'z').lazyZip(1 to 26) ++ ('A' to 'Z').lazyZip(27 to 52)).toMap

    /**
     * Lowercase item types a through z have priorities 1 through 26.
     *
     * Uppercase item types A through Z have priorities 27 through 52.
     */
    def calculate(element: Char): Int = map(element)
  }
}
