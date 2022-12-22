package org.adventofcode.day14

import org.adventofcode.ResourceLoader

object Day14A {

  def main(): Int = {

    val cave = ResourceLoader.processLines(
      _.foldLeft(new CaveBuilder)(_.addRock(_))
    ).build()

    val condition: Coords => Boolean = _.y == cave.depth - 1

    cave.simulate(condition)
  }
}
