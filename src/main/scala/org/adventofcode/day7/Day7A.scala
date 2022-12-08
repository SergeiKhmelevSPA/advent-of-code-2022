package org.adventofcode.day7

import org.adventofcode.ResourceLoader

import scala.collection.View

object Day7A {

  def main(): Int = {
    val fileSystem = new FileSystem()

    ResourceLoader.processLineByLine(fileSystem.input)

    fileSystem.result()
  }

  implicit class Result(fileSystem: FileSystem) {

    def resultOld(): Int = {
      def condition: Directory => Boolean = _.size <= 100_000

      def conditionRecursiveSum(directory: Directory): Int = {
        val childrenSum = directory.children
          .map(conditionRecursiveSum)
          .sum

        if (condition(directory)) {
          childrenSum + directory.size
        } else {
          childrenSum
        }
      }

      conditionRecursiveSum(fileSystem.root)
    }

    def result(): Int = {
      def condition: Directory => Boolean = _.size <= 100_000

      def flattenDirectory(directory: Directory): View[Directory] = {
        View[Directory](directory) ++ directory.children.flatMap(flattenDirectory)
      }

      flattenDirectory(fileSystem.root)
        .filter(condition)
        .map(_.size)
        .sum
    }
  }
}
