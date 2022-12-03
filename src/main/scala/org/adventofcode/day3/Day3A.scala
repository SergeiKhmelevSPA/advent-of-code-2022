package org.adventofcode.day3

import org.adventofcode.ResourceLoader
import org.adventofcode.day3.RacksackItemPriority.ItemPriority

object Day3A {

  def main(args: Array[String]): Unit = {
    val result = ResourceLoader.reduceLineByLine("day3.txt", calc)

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
