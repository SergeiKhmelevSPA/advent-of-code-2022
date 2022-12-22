package org.adventofcode.day14

import org.adventofcode.ResourceLoader

object Day14B {

  def main(): Int = {

    val cave = ResourceLoader.processLines(
      _.foldLeft(new CaveBuilder)(_.addRock(_))
    ).buildWithFloor()

    val condition: Coords => Boolean = _ == cave.sandCoords

    cave.simulate(condition) + 1 // +1 because we don't count the last sand
  }
}
