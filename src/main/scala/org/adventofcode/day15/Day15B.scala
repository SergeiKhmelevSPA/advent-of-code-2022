package org.adventofcode.day15

import org.adventofcode.InclusiveRange.collapseRanges
import org.adventofcode.ResourceLoader

object Day15B {

  def main(): Long = {

    val sensors = ResourceLoader.processLines(
      _.foldLeft(List.newBuilder[SensorWithBeacon])(_.addOne(_))
    ).result()

    val coords = findCoords(sensors)

    coords.x * 4_000_000L + coords.y
  }

  private def findCoords(sensors: List[SensorWithBeacon]): Coords = {
    val lower = 0
    val upper = 4_000_000

    for (row <- lower to upper) {
      val ranges = collapseRanges(sensors.flatMap(_.getRangeInRow(row)))

      ranges match {
        case Nil =>
        case head :: Nil => {
          // TODO impl corner case
        }
        case head :: _ => return Coords(head.end + 1, row)
      }
    }

    throw new IllegalStateException
  }
}
