package org.adventofcode.day3

import org.adventofcode.GetResource.using

import scala.io.Source

object Day3A {

  def main(args: Array[String]): Unit = {
    var result = 0

    using(Source.fromResource("day3.txt")) { source => {
      for (line <- source.getLines) {
        result += calc(line)
      }
    }
    }

    println(result)
  }

  private def calc(rucksack: String): Int = {
    val (compartment1, compartment2) = getCompartmentItems(rucksack)
    val commonItem = compartment1.intersect(compartment2)

    Priority.calculate(commonItem.charAt(0))
  }

  private def getCompartmentItems(rucksack: String): (String, String) = {
    val mid = rucksack.length / 2
    val compartment1 = rucksack.substring(0, mid)
    val compartment2 = rucksack.substring(mid)

    (compartment1, compartment2)
  }

  private object Priority {
    private val map = (('a' to 'z').lazyZip(1 to 26) ++ ('A' to 'Z').lazyZip(27 to 52)).toMap

    /**
     * Lowercase item types a through z have priorities 1 through 26.
     *
     * Uppercase item types A through Z have priorities 27 through 52.
     */
    def calculate(element: Char): Int = map(element)
  }
}
