package org.adventofcode

package object day4 extends HasFileName {

  override val fileName: String = "day4.txt"


  def parseAssignments(assignments: String): (Assignment, Assignment) = {
    val assignedSections = assignments.split(',')

    val assignment1 = Assignment.parse(assignedSections(0))
    val assignment2 = Assignment.parse(assignedSections(1))

    (assignment1, assignment2)
  }

  class Assignment(private val range: Range.Inclusive) {

    def contains(other: Assignment): Boolean = {
      range.contains(other.range.start) && range.contains(other.range.end)
    }

    def overlaps(other: Assignment): Boolean = {
      range.contains(other.range.start) || range.contains(other.range.end)
    }
  }

  object Assignment {

    def parse(value: String): Assignment = {
      val pair = value.split('-').map(_.toInt)

      new Assignment(pair(0) to pair(1))
    }
  }
}
