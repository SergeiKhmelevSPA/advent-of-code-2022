package org.adventofcode.day4

import org.adventofcode.ResourceLoader

object Day4B {

  def main(): Int = {
    ResourceLoader.reduceLineByLine { line =>
      val (assignment1, assignment2) = parseAssignments(line)

      if (assignment1.overlaps(assignment2) || assignment2.overlaps(assignment1)) 1 else 0
    }
  }
}
