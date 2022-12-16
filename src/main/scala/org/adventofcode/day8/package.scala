package org.adventofcode

package object day8 extends HasFileName {

  override val fileName: String = "day8.txt"

  class ForestBuilder {
    private val matrixBuilder = Array.newBuilder[Array[Tree]]

    def processLine(line: String): Unit = {
      matrixBuilder += line.map(el => new Tree(el.asDigit)).toArray
    }

    def build(): Array[Array[Tree]] = matrixBuilder.result
  }

  class Tree(val height: Int, var marked: Boolean = false) {
    override def toString: String = s"($height, $marked)"
  }
}
