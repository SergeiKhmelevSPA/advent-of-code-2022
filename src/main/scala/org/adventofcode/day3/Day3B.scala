package org.adventofcode.day3

import org.adventofcode.GetResource.using
import org.adventofcode.day3.RacksackItemPriority.ItemPriority

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

    commonItem.charAt(0).priority()
  }
}
