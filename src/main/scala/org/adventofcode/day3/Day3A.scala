package org.adventofcode.day3

import org.adventofcode.GetResource.using
import org.adventofcode.day3.RacksackItemPriority.ItemPriority

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

    commonItem.charAt(0).priority()
  }

  private def getCompartmentItems(rucksack: String): (String, String) = {
    val mid = rucksack.length / 2
    val compartment1 = rucksack.substring(0, mid)
    val compartment2 = rucksack.substring(mid)

    (compartment1, compartment2)
  }
}
