package org.adventofcode.day4

import org.adventofcode.ResourceLoader

object Day4A {

  def main(): Int = {
    ResourceLoader.mapAndSum { line =>
      val (assignment1, assignment2) = parseAssignments(line)

      if (assignment1.contains(assignment2) || assignment2.contains(assignment1)) 1 else 0
    }
  }
}
