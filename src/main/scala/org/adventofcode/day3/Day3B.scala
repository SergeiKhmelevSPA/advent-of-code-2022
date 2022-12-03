package org.adventofcode.day3

import org.adventofcode.ResourceLoader

object Day3B {

  def main(): Int = {
    ResourceLoader.processLineByLine(
      _.grouped(3)
        .map { lines => {
          val commonItem = lines.reduce((a, b) => a intersect b)
          commonItem.charAt(0).priority()
        }
        }
        .sum
    )
  }
}
