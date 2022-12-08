package org.adventofcode.day7

import org.adventofcode.ResourceLoader

object Day7B {

  def main(): Int = {
    val fileSystem = new FileSystem()

    ResourceLoader.processLineByLine(fileSystem.input)

    fileSystem.result()
  }

  implicit class Result(fileSystem: FileSystem) {

    def result(): Int = {
      val toEmpty = fileSystem.root.size - (70_000_000 - 30_000_000)

      def condition: Directory => Boolean = _.size >= toEmpty

      def conditionRecursiveSum(directory: Directory): Option[Int] = {
        if (condition(directory)) {
          Some(
            directory.children
              .flatMap(conditionRecursiveSum)
              .minOption
              .optMathMin(directory.size)
          )
        } else {
          None
        }
      }

      conditionRecursiveSum(fileSystem.root).get
    }
  }

  implicit class OptionComparator(element: Option[Int]) {

    def optMathMin(otherSize: Int): Int = {
      element match {
        case Some(value) => Math.min(value, otherSize)
        case None => otherSize
      }
    }
  }
}
