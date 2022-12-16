package org.adventofcode.day8

import org.adventofcode.ResourceLoader

object Day8A {

  def main(): Int = {

    val builder = new ForestBuilder

    ResourceLoader.processLineByLine(builder.processLine)

    val forest = builder.build()

    forest.foreach(markVisibleTrees)
    forest.transpose.foreach(markVisibleTrees)

    forest.map(_.count(_.marked)).sum
  }

  def markVisibleTrees(row: Array[Tree]): Unit = {
    row.foldLeft(new TreeMarker) { (state, el) => state.process(el) }
    row.foldRight(new TreeMarker) { (el, state) => state.process(el) }
  }

  class TreeMarker {

    private var currentMax: Int = -1

    def process(tree: Tree): TreeMarker = {
      if (tree.height > currentMax) {
        currentMax = tree.height
        tree.marked = true
      }

      this
    }
  }
}
