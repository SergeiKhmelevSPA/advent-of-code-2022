package org.adventofcode.day12

import org.adventofcode.ResourceLoader

object Day12B {

  def main(): Int = {
    val builder = new HeightmapBuilder((origin, target) => origin isClimbableFrom target)

    ResourceLoader.processLineByLine(builder.processLine)

    val heightmap = builder.build()
    new HeightmapWalker(heightmap, heightmap.end, _.elevation == 'a').shortestPathSize()
  }
}
