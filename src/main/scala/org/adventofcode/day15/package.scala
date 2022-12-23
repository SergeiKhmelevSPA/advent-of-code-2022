package org.adventofcode

import scala.language.implicitConversions
import scala.util.matching.Regex

package object day15 extends HasFileName {

  override val fileName: String = "day15.txt"

  case class Coords(x: Int, y: Int) {
    override def toString = s"($x, $y)"
  }

  implicit def stringToSensorWithBeacon(value: String): SensorWithBeacon = {
    val regex: Regex = """^Sensor at x=(-?\d+), y=(-?\d+): closest beacon is at x=(-?\d+), y=(-?\d+)$""".r

    value match {
      case regex(sX, sY, bX, bY) => new SensorWithBeacon(Coords(sX.toInt, sY.toInt), Coords(bX.toInt, bY.toInt))
    }
  }

  class SensorWithBeacon(
                          val sensor: Coords,
                          val beacon: Coords,
                        ) {

    val manhattanDistance: Int = calcManhattanDistance()

    def getRangeInRow(row: Int): Option[InclusiveRange] = {
      if (sensor.y - manhattanDistance to sensor.y + manhattanDistance contains row) {
        val shift = manhattanDistance - math.abs(sensor.y - row)

        Some(InclusiveRange(sensor.x - shift, sensor.x + shift))
      } else {
        None
      }
    }

    def getNoBeaconRangesInRow(row: Int): Seq[InclusiveRange] = {
      getRangeInRow(row) match {
        case Some(value) => {
          val from = value.start
          val to = value.end

          if (row == beacon.y) {
            if (from == beacon.x) {
              if (to == beacon.x) {
                Seq.empty
              } else {
                Seq(InclusiveRange(from + 1, to))
              }
            } else if (to == beacon.x) {
              Seq(InclusiveRange(from, to - 1))
            } else {
              Seq(InclusiveRange(from, beacon.x - 1), InclusiveRange(beacon.x + 1, to))
            }
          } else {
            Seq(value)
          }
        }
        case None => Seq.empty
      }
    }

    private def calcManhattanDistance(): Int =
      math.abs(sensor.x - beacon.x) + math.abs(sensor.y - beacon.y)

    override def toString: String = s"sensor: $sensor, beacon: $beacon, d: $manhattanDistance"
  }
}
