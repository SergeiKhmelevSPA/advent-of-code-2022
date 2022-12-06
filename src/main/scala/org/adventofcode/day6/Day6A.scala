package org.adventofcode.day6

import org.adventofcode.ResourceLoader.using

import scala.io.Source

object Day6A {

  private val len = 4

  def main(): Int = {
    using(Source.fromResource(fileName)) { source =>
      val reader = source.bufferedReader()

      val buffer = new Array[Char](len)
      var endIndicator = 0

      val checker = new Checker(len)

      while (endIndicator != -1) {
        endIndicator = reader.read(buffer, 0, len)

        checker.add(buffer) match {
          case Some(markerIndex) => return markerIndex
          case None =>
        }
      }
    }

    -1 // something went wrong
  }
}
