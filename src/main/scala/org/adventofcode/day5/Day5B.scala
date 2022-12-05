package org.adventofcode.day5

import org.adventofcode.ResourceLoader

import scala.collection.immutable.ListMap
import scala.language.implicitConversions

object Day5B {

  def main(): String = {
    implicit val cargo: Cargo = new Cargo

    ResourceLoader.processLineByLine { line =>
      if (cargo.isReady) {
        cargo.apply(line)
      } else {
        cargo.populate(line)
      }
    }

    cargo.topContainers()
  }

  class Cargo {

    private var parsed: Boolean = false

    // not optimal structure. Should not use List here
    private var supplyStacks: Map[Int, List[Char]] = ListMap()

    def isReady: Boolean = parsed

    def populate(line: String): Unit = {
      if (line.isBlank) {
        parsed = true
        return
      }

      def extractChar(value: String): Option[Char] =
        if (value.isEmpty || value.toIntOption.isDefined) {
          None
        } else {
          Some(value.charAt(1))
        }

      def calculateNewVal(elem: Option[Char], idx: Int): List[Char] = {
        val appendix = elem match {
          case Some(newChar) => List(newChar)
          case None => List[Char]()
        }
        supplyStacks.getOrElse(idx, default = List[Char]()) ++ appendix
      }

      line.grouped(4)
        .map(_.trim)
        .map(extractChar)
        .zipWithIndex
        .foreach { case (elem, i) =>
          val idx = i + 1
          supplyStacks = supplyStacks.updated(idx, calculateNewVal(elem, idx))
        }
    }

    def apply(command: Command): Unit = {
      val oldFrom = supplyStacks(command.from)
      val (move, newFrom) = oldFrom.splitAt(command.amount)
      supplyStacks = supplyStacks.updated(command.from, newFrom)
      supplyStacks = supplyStacks.updated(command.to, move ++ supplyStacks(command.to))
    }

    def topContainers(): String =
      supplyStacks.values
        .map(_.head)
        .mkString
  }
}
