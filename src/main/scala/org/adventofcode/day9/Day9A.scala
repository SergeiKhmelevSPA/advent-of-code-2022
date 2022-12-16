package org.adventofcode.day9

import org.adventofcode.ResourceLoader

import scala.collection.mutable

object Day9A {

  def main(): Int = {
    val visitedPositions: VisitedPositions = ResourceLoader.processLines(
      _.foldLeft(new VisitedPositions)(_.moveHead(_))
    )

    visitedPositions.count
  }

  class VisitedPositions {

    private var head: Position = Position(0, 0)
    private var tail: Position = Position(0, 0)
    private val visited: mutable.Set[Position] = mutable.Set[Position](tail)

    def moveHead(motion: Motion): VisitedPositions = {
      val action: () => Unit = motion.direction match {
        case Left => moveHeadLeft
        case Right => moveHeadRight
        case Up => moveHeadUp
        case Down => moveHeadDown
      }

      1 to motion.numberOfSteps foreach { _ => action() }

      this
    }

    private def moveHeadLeft(): Unit = {
      if (tail == Position(head.x + 1, head.y + 1) ||
        tail == Position(head.x + 1, head.y - 1)) {
        head = head.moveLeft
        moveTail(Position(head.x + 1, head.y))
      } else {
        head = head.moveLeft
        moveTail(tail.moveLeft)
      }
    }

    private def moveHeadRight(): Unit = {
      if (tail == Position(head.x - 1, head.y + 1) ||
        tail == Position(head.x - 1, head.y - 1)) {
        head = head.moveRight
        moveTail(Position(head.x - 1, head.y))
      } else {
        head = head.moveRight
        moveTail(tail.moveRight)
      }
    }

    private def moveHeadUp(): Unit = {
      if (tail == Position(head.x + 1, head.y - 1) ||
        tail == Position(head.x - 1, head.y - 1)) {
        head = head.moveUp
        moveTail(Position(head.x, head.y - 1))
      } else {
        head = head.moveUp
        moveTail(tail.moveUp)
      }
    }

    private def moveHeadDown(): Unit = {
      if (tail == Position(head.x + 1, head.y + 1) ||
        tail == Position(head.x - 1, head.y + 1)) {
        head = head.moveDown
        moveTail(Position(head.x, head.y + 1))
      } else {
        head = head.moveDown
        moveTail(tail.moveDown)
      }
    }

    private def moveTail(newPosition: Position): Unit = {
      if (!tail.isNearTo(head)) {
        tail = newPosition
        visited += newPosition
      }

      //      println(s"head: $head, tail: $tail")
    }

    def count: Int = visited.size
  }
}
