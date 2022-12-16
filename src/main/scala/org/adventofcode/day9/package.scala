package org.adventofcode

import scala.language.implicitConversions
import scala.util.matching.Regex

package object day9 extends HasFileName {

  override val fileName: String = "day9.txt"

  implicit def stringToMotion(value: String): Motion = {
    val pattern: Regex = """^(\w) (\d+)$""".r

    value match {
      case pattern(d, count) => {
        val direction = d match {
          case "L" => Left
          case "R" => Right
          case "U" => Up
          case "D" => Down
        }

        Motion(direction, count.toInt)
      }
    }
  }

  case class Motion(direction: Direction, numberOfSteps: Int)

  sealed trait Direction

  case object Left extends Direction

  case object Right extends Direction

  case object Up extends Direction

  case object Down extends Direction


  case class Position(x: Int, y: Int) {

    def isNearTo(other: Position): Boolean =
      (x == other.x || x == other.x + 1 || x == other.x - 1) &&
        (y == other.y || y == other.y + 1 || y == other.y - 1)

    def moveLeft: Position = Position(x - 1, y)

    def moveRight: Position = Position(x + 1, y)

    def moveUp: Position = Position(x, y + 1)

    def moveDown: Position = Position(x, y - 1)

    override def toString: String = s"($x, $y)"
  }
}
