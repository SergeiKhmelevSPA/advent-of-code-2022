package org.adventofcode.day7

import org.adventofcode.ResourceLoader

object Day7A {

  def main(): Int = {
    val fileSystem = new FileSystem()

    ResourceLoader.processLineByLine(fileSystem.input)

    fileSystem.result()
  }

  implicit class Result(fileSystem: FileSystem) {

    def result(): Int = {
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
  }
}
