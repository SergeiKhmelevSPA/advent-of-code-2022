package org.adventofcode

import scala.collection.mutable

package object day12 extends HasFileName {

  override val fileName: String = "day12.txt"

  class HeightmapBuilder {
    private val matrixBuilder = Array.newBuilder[Array[Square]]

    private var yIndex = 0

    def processLine(line: String): Unit = {
      matrixBuilder += line.zipWithIndex
        .map { case (el: Char, xIndex: Int) => new Square(xIndex, yIndex, el) }
        .toArray
      yIndex += 1
    }

    def build(): Heightmap = new Heightmap(matrixBuilder.result)
  }

  class Heightmap(private val matrix: Array[Array[Square]]) {

    val start: Square = findStart(matrix)
    private val sizeX: Int = matrix(0).length
    private val sizeY: Int = matrix.length

    def findNextSteps(origin: Square): Seq[Square] = {
      val builder = mutable.ArrayBuilder.make[Square]

      def add(target: Square): Unit = {
        if (target isClimbableFrom origin) {
          builder.addOne(target)
        }
      }

      if (origin.x - 1 >= 0) {
        add(matrix(origin.y)(origin.x - 1))
      }
      if (origin.x + 1 < sizeX) {
        add(matrix(origin.y)(origin.x + 1))
      }
      if (origin.y - 1 >= 0) {
        add(matrix(origin.y - 1)(origin.x))
      }
      if (origin.y + 1 < sizeY) {
        add(matrix(origin.y + 1)(origin.x))
      }

      builder.result()
    }

    private def findStart(heightmap: Array[Array[Square]]): Square = {
      for (row <- heightmap) {
        for (el <- row) {
          if (el.isStart) {
            return el
          }
        }
      }

      throw new IllegalStateException("No start found")
    }
  }

  class Square(val x: Int, val y: Int, parsedElevation: Char) {

    val isStart: Boolean = parsedElevation == 'S'
    val isFinish: Boolean = parsedElevation == 'E'

    val elevation: Char = if (isStart) 'a' else if (isFinish) 'z' else parsedElevation


    /** at most one higher */
    def isClimbableFrom(origin: Square): Boolean = {
      val diff = elevation - origin.elevation
      diff == 0 || diff == 1
    }

    override def toString: String = s"($x, $y : $elevation)"
  }
}
