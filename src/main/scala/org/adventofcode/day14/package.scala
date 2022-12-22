package org.adventofcode

import scala.annotation.tailrec

package object day14 extends HasFileName {

  override val fileName: String = "day14.txt"

  class CaveBuilder {

    private val rockCoordsListBuilder = List.newBuilder[Seq[Coords]]
    private var maxX: Int = 0
    private var maxY: Int = 0

    def addRock(input: String): CaveBuilder = {
      rockCoordsListBuilder.addOne(
        input.split(" -> ")
          .map { unparsedCoords: String =>
            val coords = unparsedCoords.split(',') match {
              case Array(x, y) => Coords(x.toInt, y.toInt)
            }

            maxX = Math.max(maxX, coords.x)
            maxY = Math.max(maxY, coords.y)

            coords
          }
      )
      this
    }

    def buildWithFloor(): Cave = {
      // two plus the highest y coordinate
      val bottomY = maxY + 2
      maxX += maxY * 2
      rockCoordsListBuilder.addOne(Array(Coords(0, bottomY), Coords(maxX + 1, bottomY)))
      build()
    }

    def build(): Cave = {
      val rockCoordsList = rockCoordsListBuilder.result()

      val matrix = Array.fill(maxY + 3) {
        Array.fill[Tile](maxX + 2)(Air)
      }

      rockCoordsList.foreach(_.sliding(2)
        .foreach { case Seq(coords1, coords2) =>
          for (x <- math.min(coords1.x, coords2.x) to math.max(coords1.x, coords2.x)) {
            matrix(coords1.y)(x) = Rock
          }
          for (y <- math.min(coords1.y, coords2.y) to math.max(coords1.y, coords2.y)) {
            matrix(y)(coords1.x) = Rock
          }
        }
      )

      new Cave(matrix)
    }
  }

  class Cave(matrix: Array[Array[Tile]]) {

    val depth: Int = matrix.length
    val sandCoords: Coords = Coords(500, 0)

    private var counter: Int = 0

    printCave()

    def simulate(condition: Coords => Boolean): Int = {

      while (true) {
        val newCoords = sandFall(sandCoords)

        if (condition(newCoords)) {
          printCave()
          println("End:" + newCoords)
          return counter
        }

        matrix(newCoords.y)(newCoords.x) = Sand

        counter += 1
      }

      counter
    }

    @tailrec
    private def sandFall(c: Coords): Coords = {
      if (c.y + 1 >= depth) {
        c
      } else if (matrix(c.y + 1)(c.x) == Air) {
        sandFall(Coords(c.x, c.y + 1))
      } else if (matrix(c.y + 1)(c.x - 1) == Air) {
        sandFall(Coords(c.x - 1, c.y + 1))
      } else if (matrix(c.y + 1)(c.x + 1) == Air) {
        sandFall(Coords(c.x + 1, c.y + 1))
      } else {
        c
      }
    }

    private def printCave(): Unit = {
      for (y <- matrix.indices) {
        //println(matrix(y).map(_.toString).mkString.substring(280))
        println(matrix(y).map(_.toString).mkString)
      }
      println("---")
    }
  }

  case class Coords(x: Int, y: Int) {
    override def toString = s"($x, $y)"
  }

  sealed trait Tile

  case object Air extends Tile {
    override def toString: String = "."
  }

  case object Rock extends Tile {
    override def toString: String = "#"
  }

  case object Sand extends Tile {
    override def toString: String = "o"
  }
}
