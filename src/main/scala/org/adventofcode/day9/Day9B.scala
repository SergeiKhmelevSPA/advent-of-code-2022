package org.adventofcode.day9

import org.adventofcode.ResourceLoader

import scala.annotation.tailrec
import scala.collection.mutable

object Day9B {

  def main(): Int = {
    val visitedPositions: VisitedPositions = ResourceLoader.processLines(
      _.foldLeft(new VisitedPositions)(_.moveHead(_))
    )

    visitedPositions.count
  }

  class VisitedPositions {

    private val lastTailIndex = 9

    private val rope: Array[Position] = Array.fill(lastTailIndex + 1)(Position(0, 0))
    private val visited: mutable.Set[Position] = mutable.Set[Position](Position(0, 0))

    def moveHead(motion: Motion): VisitedPositions = {
      val moveHead: Position => Position = motion.direction match {
        case Left => _.moveLeft
        case Right => _.moveRight
        case Up => _.moveUp
        case Down => _.moveDown
      }

      1 to motion.numberOfSteps foreach { _ =>
        val head = rope(0)

        val newHead = moveHead(head)
        rope(0) = newHead

        moveTail(1, newHead)
      }

      this
    }

    @tailrec
    private def moveTail(index: Int, newHead: Position): Unit = {
      val tail = rope(index)

      if (!tail.isNearTo(newHead)) {

        val x = if (newHead.x > tail.x) {
          tail.x + 1
        } else if (newHead.x < tail.x) {
          tail.x - 1
        } else {
          tail.x
        }

        val y = if (newHead.y > tail.y) {
          tail.y + 1
        } else if (newHead.y < tail.y) {
          tail.y - 1
        } else {
          tail.y
        }

        val newPosition = Position(x, y)

        rope(index) = newPosition

        if (index < lastTailIndex) {
          moveTail(index + 1, newPosition)
        } else if (index == lastTailIndex) {
          visited += newPosition
        }
      }
    }

    def count: Int = visited.size
  }
}
