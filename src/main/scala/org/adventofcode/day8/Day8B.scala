package org.adventofcode.day8

import org.adventofcode.ResourceLoader

object Day8B {

  def main(): Int = {

    val builder = new ForestBuilder

    ResourceLoader.processLineByLine(builder.processLine)

    val forest = builder.build()
    val transposed = forest.transpose

    (
      for {
        x <- forest(0).indices.view
        y <- forest.indices.view
      } yield calculateScenicScore(forest, transposed, x, y)
      ).max
  }

  def calculateScenicScore(forest: Array[Array[Tree]], transposed: Array[Array[Tree]], x: Int, y: Int): Int = {
    val height = forest(y)(x).height

    //    println(s"($x, $y): $height")

    val leftRange = (x - 1) to 0 by -1
    val left = countVisibleTrees(forest(y), leftRange, height)

    val rightRange = (x + 1) until forest(y).length
    val right = countVisibleTrees(forest(y), rightRange, height)

    val upRange = (y - 1) to 0 by -1
    val up = countVisibleTrees(transposed(x), upRange, height)

    val downRange = (y + 1) until forest(y).length
    val down = countVisibleTrees(transposed(x), downRange, height)

    val result = left * right * up * down
    //    println(s"R: ($x, $y): l:$left r:$right u:$up d:$down")
    result
  }

  private def countVisibleTrees(row: Array[Tree], range: Range, height: Int): Int = {
    var count = 0
    for (i <- range) {
      count += 1
      if (row(i).height >= height) {
        return count
      }
    }

    count
  }
}
