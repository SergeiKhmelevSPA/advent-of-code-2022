package org.adventofcode.day3

import org.adventofcode.ResourceLoader

object Day3A {

  def main(): Int = {
    ResourceLoader.reduceLineByLine { line => {
      val (compartment1, compartment2) = getCompartmentItems(line)
      val commonItem = compartment1.intersect(compartment2)

      commonItem.charAt(0).priority()
    }
    }
  }

  private def getCompartmentItems(rucksack: String): (String, String) = {
    val mid = rucksack.length / 2
    val compartment1 = rucksack.substring(0, mid)
    val compartment2 = rucksack.substring(mid)

    (compartment1, compartment2)
  }
}
