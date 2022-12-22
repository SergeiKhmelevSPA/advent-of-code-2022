package org.adventofcode.day13

import org.adventofcode.ResourceLoader

object Day13A {

  def main(): Int = {

    ResourceLoader.processLines(
      _.grouped(3)
        .map { case Seq(first, second, _@_*) =>
          convertToPacket(first) < convertToPacket(second)
        }
        .zipWithIndex
        .filter(_._1)
        .map(_._2 + 1) // index starts with 1
        .sum
    )
  }
}
