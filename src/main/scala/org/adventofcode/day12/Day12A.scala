package org.adventofcode.day12

import org.adventofcode.ResourceLoader

object Day12A {

  def main(): Int = {
    val builder = new HeightmapBuilder((origin, target) => target isClimbableFrom origin)

    ResourceLoader.processLineByLine(builder.processLine)

    val heightmap = builder.build()
    new HeightmapWalker(heightmap, heightmap.start, _.isEnd).shortestPathSize()
  }
}
