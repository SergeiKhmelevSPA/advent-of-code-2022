package org.adventofcode.day7

import org.adventofcode.ResourceLoader

import scala.collection.View

object Day7B {

  def main(): Int = {
    val fileSystem = new FileSystem()

    ResourceLoader.processLineByLine(fileSystem.input)

    fileSystem.result()
  }

  implicit class Result(fileSystem: FileSystem) {

    def resultOld(): Int = {
      val toEmpty = fileSystem.root.size - (70_000_000 - 30_000_000)
      val condition: Directory => Boolean = _.size >= toEmpty

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

    def result(): Int = {
      val toEmpty = fileSystem.root.size - (70_000_000 - 30_000_000)
      val condition: Directory => Boolean = _.size >= toEmpty

      def flattenDirectoryWithCondition(directory: Directory): View[Directory] = {
        if (condition(directory)) {
          View[Directory](directory) ++ directory.children.flatMap(flattenDirectoryWithCondition)
        } else {
          View.empty
        }
      }

      flattenDirectoryWithCondition(fileSystem.root)
        .map(_.size)
        .min
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
